package sample;

/**
 * Created by eduardo-pooch on 16/11/2016.
 */
public class Estação {
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
     * @param endereço destino
     */
    public void envia(String conteudo, String endereço) {

    }


    /**
     * Envia uma informação broadcast,
     *
     * @param conteudo quando é passado por parametro apenas o conteudo é feito um envio broadcast
     */
    public void envia(String conteudo) {

    }


    public String getMacUnicast() {
        return macUnicast;
    }

    public String getMacMulticast() {
        return macMulticast;
    }


}
