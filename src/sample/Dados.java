package sample;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by edupooch on 14/11/2016.
 */

public class Dados<Byte> extends ArrayList {

    private static final int MAX_ARRAY_SIZE = 1500;


    public Dados(){
      new ArrayList<Byte>();
    }

    @Override
    public boolean add(Object o) {
        if (size() < MAX_ARRAY_SIZE)
        return super.add(o);

        System.out.println("Dados apenas até 1500 bytes");
        return false;
    }

    @Override
    public void add(int index, Object element) {
        if (size() < MAX_ARRAY_SIZE) {
            super.add(index, element);
        } else{
            System.out.println("Dados apenas até 1500 bytes");
        }

    }

}
