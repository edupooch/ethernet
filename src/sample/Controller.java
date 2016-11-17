package sample;

public class Controller {


    private Estacao estacao1;
    private Estacao estacao2;
    private Estacao estacao3;

    public void inicia() {
        estacao1 = new Estacao("00:0A:95:9D:68:16","01:00:5E:00:00:ab");
        Thread threadEstacao1 = new Thread(estacao1);
        threadEstacao1.start();

        estacao1.envia("aaaaa");

        System.out.println((long) (1500*0.005));


    }

    public void sim(){


    }
}
