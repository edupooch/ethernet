package modelo;


import java.util.Arrays;
import java.util.Random;

public class Estacao implements Runnable {
    private static final String MAC_BROADCAST = "FF:FF:FF:FF:FF:FF";
    private final String macUnicast;
    private final String macMulticast;
    private byte[][] ultimaInfo;

    public Estacao(String macUnicast, String macMulticast) {
        this.macUnicast = macUnicast.toUpperCase();
        this.macMulticast = macMulticast.toUpperCase();
    }

    /**
     * Envia uma informação para uma estação
     *
     * @param conteudo string
     * @param destino  é um objeto do tipo estação
     */
    public void envia(String conteudo, Estacao destino) {
        byte[][] quadro = new byte[0][];
        try {
            quadro = Quadro.criaQuadro(destino.getMacUnicast(), this.getMacUnicast(), conteudo);
        } catch (QuadroException e) {
            //Fragmentar nas camadas superiores
            e.printStackTrace();

        }
        transmite(quadro);

    }


    /**
     * Envia uma informação para um endereço, pode ser unicast, multicast ou broadcast
     *
     * @param conteudo string
     * @param endereco destino
     */
    public void envia(String conteudo, String endereco) {
        byte[][] quadro = new byte[0][];
        try {
            quadro = Quadro.criaQuadro(endereco, this.getMacUnicast(), conteudo);
        } catch (QuadroException e) {
            e.printStackTrace();
            //Fragmentar nas camadas superiores
        }
        transmite(quadro);
    }


    /**
     * Envia uma informação broadcast,
     *
     * @param conteudo quando é passado por parametro apenas o conteudo é feito um envio broadcast
     */
    public void envia(String conteudo) {
        byte[][] quadro = new byte[0][];
        try {
            quadro = Quadro.criaQuadro(MAC_BROADCAST, this.getMacUnicast(), conteudo);
        } catch (QuadroException e) {
            e.printStackTrace();
            //Fragmentar nas camadas superiores
        }
        transmite(quadro);
    }

    private void transmite(byte[][] quadro) { //envio dos dados
        new Thread(() -> {
            //verifica se o meio está ocupado (Algoritmo backoff exponencial binário)
            int colisoes = 0;
            while (Meio.getInfo() != null) {
                System.out.println("MEIO OCUPADO PARA A TRANSMISSAO NA ESTAÇÃO " + getMacUnicast());
                colisoes++;
                int m = (int) (Math.pow(2, colisoes) - 1);
                int random = new Random().nextInt(m);
                double timeSlot = 0.0512; //é 51.2 microsec no padrão, para a simulação utilizei 0.512 ms
                double tempoEspera = random * timeSlot;
                System.out.println("Esperar " + String.format("%.3f",tempoEspera) + " milissegundos");

                try {
                    Thread.sleep((long) tempoEspera);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Meio.transimitir(quadro);
        }).start();
    }


    private String getMacUnicast() {
        return macUnicast;
    }

    private String getMacMulticast() {
        return macMulticast;
    }


    @Override
    public void run() { //Recebimento dos dados
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Meio.getInfo() != null) {
                byte[][] infoRecebida = (byte[][]) Meio.getInfo();
                if (!Arrays.deepEquals(infoRecebida, ultimaInfo)) {

                    //Verifica se o destino é essa estação:
                    String destino = Quadro.getEnderecoDestino(infoRecebida);

                    if (destino.equals(getMacUnicast()) || destino.equals(getMacMulticast()) ||
                            destino.equals(MAC_BROADCAST)) {
                        ultimaInfo = infoRecebida;

                        if (Quadro.leValorCRC(infoRecebida) == Quadro.criaValorCRC(infoRecebida)){
                            System.out.println("QUADRO RECEBIDO EM : " + getMacUnicast() + Quadro.getDescricao(infoRecebida));
                        } else {
                            System.out.println("QUADRO RECEBIDO EM : " + getMacUnicast() + "FOI DESCARTADO (ERRO NO CRC)");
                        }



                    }

                }

            }
        }
    }
}
