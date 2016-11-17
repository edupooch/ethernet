package sample;

public class Meio {

    private static Object infoMeio;

    public static Object getInfo() {
        return infoMeio;
    }

    private static void setInfoMeio(Object object) {
        infoMeio = object;
    }

    public static void transimitir(Object infoRecebida) {
        new Thread(() -> {
            System.out.println("Transmitindo quadro");


            setInfoMeio(infoRecebida);
            try {
                byte[][] infoBytes = (byte[][]) infoRecebida;
                int tamanho = Quadro.getTamanhoTotal(infoBytes);

                //Simulação do comportamento de transimissão à 10Mbits/s -> 1 byte a cada 0.0008ms: tamanho*0.0008
                Thread.sleep((long) (tamanho*0.0008));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setInfoMeio(null);
        }).start();
    }

}
