package sample;

public class Meio {

    private Object info;

    public Object getInfo() {
        return info;
    }

    private void setInfo(Object info) {
        this.info = info;
    }

    public void transimitir(Object info) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setInfo(info);
                try {
                    byte[][] infoBytes = (byte[][]) info;
                    int tamanho = Quadro.getTamanhoTotal(infoBytes);

                    //Simulação do comportamento de transimissão à 10Mbits/s -> 1 byte a cada 0.0008ms
                    Thread.sleep((long) (tamanho*0.0008));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setInfo(null);
            }
        });
    }

}
