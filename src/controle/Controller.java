package controle;

import modelo.Estacao;

public class Controller {


    public void inicia() {
        //Iniciando as estações com seu endereço unicast e multicast
        Estacao estacao1 = new Estacao("00:0A:95:9D:68:01", "01:00:5E:00:00:AB");
        Estacao estacao2 = new Estacao("00:0A:95:9D:68:02", "01:00:5E:00:00:AB");
        Estacao estacao3 = new Estacao("00:0A:95:9D:68:03", "01:00:5E:00:0E:AA");

        //Ativando a thread que fica esperando pra receber dados
        Thread threadEstacao1 = new Thread(estacao1);
        threadEstacao1.start();
        Thread threadEstacao2 = new Thread(estacao2);
        threadEstacao2.start();
        Thread threadEstacao3 = new Thread(estacao3);
        threadEstacao3.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        estacao1.envia("", estacao2);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        estacao2.envia(new String(new char[1501]), estacao3); //Causará uma exception pelo tamanho dos dados>1500

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        estacao1.envia("Bom dia grupo");


    }


    public void sim() {

    }
}
