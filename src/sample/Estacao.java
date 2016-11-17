package sample;


public class Estacao implements Runnable {
    private String macUnicast;
    private String macMulticast;

    public static final String MAC_BROADCAST = "ff:ff:ff:ff:ff:ff";
    private byte[][] ultimaInfo;

    public Estacao(String macUnicast, String macMulticast) {
        this.macUnicast = macUnicast;
        this.macMulticast = macMulticast;
    }

    /**
     * Envia uma informação para uma estação
     *
     * @param conteudo string
     * @param destino  é um objeto do tipo estação
     */
    public void envia(String conteudo, Estacao destino) {

        byte[][] quadro = Quadro.criaQuadro(destino.getMacUnicast(),this.getMacUnicast(),conteudo);
        Meio.transimitir(quadro);

    }


    /**
     * Envia uma informação para um endereço, pode ser unicast, multicast ou broadcast
     *
     * @param conteudo string
     * @param endereco destino
     */
    public void envia(String conteudo, String endereco) {
        byte[][] quadro = Quadro.criaQuadro(endereco,this.getMacUnicast(),conteudo);
        Meio.transimitir(quadro);
    }


    /**
     * Envia uma informação broadcast,
     *
     * @param conteudo quando é passado por parametro apenas o conteudo é feito um envio broadcast
     */
    public void envia(String conteudo) {
        byte[][] quadro = Quadro.criaQuadro(MAC_BROADCAST,this.getMacUnicast(),conteudo);
        Meio.transimitir(quadro);
    }


    public String getMacUnicast() {
        return macUnicast;
    }

    public String getMacMulticast() {
        return macMulticast;
    }


    @Override
    public void run() {
            while (true){
                try {
                    Thread.sleep((long) 0.001);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Meio.getInfo() != null) {
                    byte[][] infoRecebida = (byte[][]) Meio.getInfo();

                    if (infoRecebida != ultimaInfo){
                        ultimaInfo = infoRecebida;
                        System.out.println("Info RECEBIDA: " + Quadro.getDescricao(infoRecebida));
                    }


                }
            }
    }
}
