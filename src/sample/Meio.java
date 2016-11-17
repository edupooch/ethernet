package sample;

class Meio {

    private static Object infoMeio;

    public static Object getInfo() {
        return infoMeio;
    }

    private static void setInfoMeio(Object object) {
        infoMeio = object;
    }

    public static void transimitir(Object infoRecebida) {
        new Thread(() -> {
            System.out.println("Transmitindo quadro...\n");


            setInfoMeio(infoRecebida);
                  try {
            byte[][] infoBytes = (byte[][]) infoRecebida;
            int tamanho = Quadro.getTamanhoTotal(infoBytes);

            //Simulação do comportamento de transimissão à 10Mbits/s -> 1 byte a cada 0.0008ms: tamanho*0.0008
             Thread.sleep(1*tamanho);
                  } catch (InterruptedException e) {
                       e.printStackTrace();
                     }
            setInfoMeio(null);
        }).start();
    }

}
