package sample;


import javax.sql.rowset.BaseRowSet;
import java.util.Arrays;
import java.util.Random;

public class Estacao implements Runnable {
    private String macUnicast;
    private String macMulticast;

    public static final String MAC_BROADCAST = "FF:FF:FF:FF:FF:FF";
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
        byte[][] quadro = Quadro.criaQuadro(destino.getMacUnicast(), this.getMacUnicast(), conteudo);
        transmite(quadro);

    }


    /**
     * Envia uma informação para um endereço, pode ser unicast, multicast ou broadcast
     *
     * @param conteudo string
     * @param endereco destino
     */
    public void envia(String conteudo, String endereco) {
        byte[][] quadro = Quadro.criaQuadro(endereco, this.getMacUnicast(), conteudo);
        transmite(quadro);
    }


    /**
     * Envia uma informação broadcast,
     *
     * @param conteudo quando é passado por parametro apenas o conteudo é feito um envio broadcast
     */
    public void envia(String conteudo) {
        byte[][] quadro = Quadro.criaQuadro(MAC_BROADCAST, this.getMacUnicast(), conteudo);
        transmite(quadro);
    }

    private void transmite(byte[][] quadro) { //envio dos dados
        new Thread(() -> {
            //verifica se o meio está ocupado (Algoritmo backoff exponencial binário)
            int colisoes = 0;
            while(Meio.getInfo() != null){
                System.out.println("MEIO OCUPADO");
                colisoes++;
                int m = (int) (Math.pow(2,colisoes) - 1);
                int random = new Random().nextInt(m);
                double timeSlot = 0.5; //é 51.2 microsec no padrão, mas para a simulação utilizei 0.5 ms (500μs)
                System.out.println("Esperar" + random*timeSlot + "milissegundos");

                try {
                    Thread.sleep((long) (random*timeSlot));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Meio.transimitir(quadro);
        }).start();
    }


    public String getMacUnicast() {
        return macUnicast;
    }

    public String getMacMulticast() {
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
                System.out.println("info != null");
                if (!Arrays.deepEquals(infoRecebida, ultimaInfo)) {
                    System.out.println("info != ultima");

                    //Verifica se o destino é essa estação:
                    String destino = Quadro.getEnderecoDestino(infoRecebida);

                    if (destino.equals(getMacUnicast()) || destino.equals(getMacMulticast()) ||
                            destino.equals(MAC_BROADCAST)) {
                        System.out.println("destino ok");


                        ultimaInfo = infoRecebida;
                        System.out.println("QUADRO RECEBIDO EM : " +getMacUnicast() + Quadro.getDescricao(infoRecebida));
                    }

                }

            }
        }
    }
}
