package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {


    public void inicia() {

        try {
            byte[][] quadro = Quadro.criaQuadro("0f:20:31:54:12:a0", "0f:20:31:54:12:a0", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".length());
            for (byte[] array :
                    quadro) {
                System.out.println(Arrays.toString(array) +" " + array.length);
            }


        } catch (QuadroException e) {
            e.printStackTrace();
        }


    }
}
