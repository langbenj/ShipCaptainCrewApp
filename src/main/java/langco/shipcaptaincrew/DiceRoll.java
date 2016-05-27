package langco.shipcaptaincrew;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;


public class DiceRoll {
    ArrayList <Integer> mDiceList=new ArrayList<>();
    int mDiceMin;
    int mDiceMax;
    public DiceRoll() {
        mDiceMin=1;
        mDiceMax=6;
        int start_roll;
        Random random_seed = new Random();
        for (int i=0; i<5; i++) {
            start_roll = random_seed.nextInt(mDiceMax - mDiceMin + 1) + mDiceMin;
           mDiceList.add(start_roll);
        }
    }

    public ArrayList<Integer> rerollDice(ArrayList<Integer> passedList,Integer [] list_to_reroll) {
        Random random_seed = new Random();
        for (int i=0; i<passedList.size(); i++) {
            if (list_to_reroll[i]==1) {
                passedList.set(i, (random_seed.nextInt(mDiceMax - mDiceMin + 1) + mDiceMin));
            }
        }
        return passedList;
    }
}
