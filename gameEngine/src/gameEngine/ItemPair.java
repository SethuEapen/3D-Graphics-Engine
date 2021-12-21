package gameEngine;

import java.util.ArrayList;
import java.util.Collections;

public class ItemPair implements Runnable {
    public ArrayList<GameObject> list;
    public boolean sort;

    public ItemPair(ArrayList<GameObject> list, boolean sort){
        this.list = list;
        this.sort = sort;
    }

    @Override
    public void run() {
        Collections.sort(list);        
    }
}
