package sample;


public class Estação implements Runnable {
    private String macUnicast;
    private String macMulticast;

    public static final String MAC_BROADCAST = "ff:ff:ff:ff:ff:ff";

    public Estação(String macUnicast, String macMulticast) {
        this.macUnicast = macUnicast;
        this.macMulticast = macMulticast;
    }

    /**
     * Envia uma informação para uma estação
     *
     * @param conteudo string
     * @param destino  é um objeto do tipo estação
     */
    public void envia(String conteudo, Estação destino) {

        byte[][] quadro = Quadro.criaQuadro(destino.getMacUnicast(),this.getMacUnicast(),conteudo);

    }


    /**
     * Envia uma informação para um endereço, pode ser unicast, multicast ou broadcast
     *
     * @param conteudo string
     * @param endereco destino
     */
    public void envia(String conteudo, String endereco) {
        byte[][] quadro = Quadro.criaQuadro(endereco,this.getMacUnicast(),conteudo);
    }


    /**
     * Envia uma informação broadcast,
     *
     * @param conteudo quando é passado por parametro apenas o conteudo é feito um envio broadcast
     */
    public void envia(String conteudo) {
        byte[][] quadro = Quadro.criaQuadro(MAC_BROADCAST,this.getMacUnicast(),conteudo);
    }


    public String getMacUnicast() {
        return macUnicast;
    }

    public String getMacMulticast() {
        return macMulticast;
    }


    @Override
    public void run() {

    }
}
