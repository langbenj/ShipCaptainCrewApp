package langco.shipcaptaincrew;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Integer [] dice_to_reroll = {1,1,1,1,1};
    public int player_tries=0;
    public int current_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button rerollButton= (Button) findViewById(R.id.reroll_button);
        ImageButton dice_button_1= (ImageButton) findViewById(R.id.dice_button_1);
        ImageButton dice_button_2= (ImageButton) findViewById(R.id.dice_button_2);
        ImageButton dice_button_3= (ImageButton) findViewById(R.id.dice_button_3);
        ImageButton dice_button_4= (ImageButton) findViewById(R.id.dice_button_4);
        ImageButton dice_button_5= (ImageButton) findViewById(R.id.dice_button_5);
        final DiceRoll diceObject = new DiceRoll();
        
        setDicePictures(diceObject.mDiceList);
        checkForWin(diceObject.mDiceList);

        if (rerollButton != null) {
            rerollButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    player_tries++;
                    setDicePictures(diceObject.rerollDice(diceObject.mDiceList, dice_to_reroll));
                    checkForWin(diceObject.mDiceList);
                    }
            });
        }

        if (dice_button_1 != null) {
            dice_button_1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickOnDice(diceObject.mDiceList,0);
                }
            });
        }

        if (dice_button_2 != null) {
            dice_button_2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickOnDice(diceObject.mDiceList,1);
                }
            });
        }

        if (dice_button_3 != null) {
            dice_button_3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickOnDice(diceObject.mDiceList,2);
                }
            });
        }

        if (dice_button_4 != null) {
            dice_button_4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickOnDice(diceObject.mDiceList,3);
                }
            });
        }

        if (dice_button_5 != null) {
            dice_button_5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickOnDice(diceObject.mDiceList,4);
                }
            });
        }
    }

    public void clickOnDice(ArrayList<Integer> dice_list, int position) {
        if (dice_to_reroll[position]==0) {
            dice_to_reroll[position]=1;
            String image_string = "dice"+dice_list.get(position);
            String resource_id_string = "dice_button_"+(position+1);
            int image_id = getResources().getIdentifier(image_string, "drawable", getPackageName());
            int view_id = getResources().getIdentifier(resource_id_string, "id", getPackageName());
            ImageView target_image_field = (ImageView) findViewById(view_id);
            target_image_field.setImageResource(image_id);
        }
        else  {
            dice_to_reroll[position]=0;
            String image_string = "dice_selected_"+dice_list.get(position);
            String resource_id_string = "dice_button_"+(position+1);
            int image_id = getResources().getIdentifier(image_string, "drawable", getPackageName());
            int view_id = getResources().getIdentifier(resource_id_string, "id", getPackageName());
            ImageView target_image_field = (ImageView) findViewById(view_id);
            target_image_field.setImageResource(image_id);
        }
    }

    public void setDicePictures(ArrayList<Integer> dice_list) {
        int view_id;
        int image_id;
        String image_string;
        String resource_id_string;
        for (int i=0; i<dice_list.size(); i++) {
            if (dice_to_reroll[i]==0) {

                image_string = "dice_selected_"+dice_list.get(i);
                resource_id_string = "dice_button_"+(i+1);
                image_id = getResources().getIdentifier(image_string, "drawable", getPackageName());
                view_id = getResources().getIdentifier(resource_id_string, "id", getPackageName());
                ImageView target_image_field = (ImageView) findViewById(view_id);
                target_image_field.setImageResource(image_id);
            }
            else  {

                image_string = "dice"+dice_list.get(i);
                resource_id_string = "dice_button_"+(i+1);
                image_id = getResources().getIdentifier(image_string, "drawable", getPackageName());
                view_id = getResources().getIdentifier(resource_id_string, "id", getPackageName());
                ImageView target_image_field = (ImageView) findViewById(view_id);
                target_image_field.setImageResource(image_id);
            }
        }
    }

    public boolean checkForDice(ArrayList<Integer> dice_list, int target_value) {
        boolean ship_found=false;
        for (int i=0; i<dice_list.size(); i++) {
            if (dice_list.get(i)==target_value) {
                ship_found=true;
            }
        }
        return ship_found;
    }

    public int findTheScore(ArrayList<Integer> dice_list) {
        int return_value=0;
        for (int i=0; i<dice_list.size(); i++) {
           return_value=return_value+dice_list.get(i);
        }
        return_value=return_value-15;
        return return_value;
    }

    public boolean checkForWin(ArrayList<Integer> dice_list) {
        boolean return_result=false;
        String toast_result="You need a ship (4)!";
        if (checkForDice(dice_list, 4)) {
            toast_result="You've got a ship!";
            if (checkForDice(dice_list, 5)) {
                toast_result="You've got a ship and a captain!";
                if (checkForDice(dice_list, 6)) {
                    toast_result= "You've got a ship, a captain and a crew! Your current score is: "+findTheScore(dice_list);
                    return_result=true;
                }
            }
        }
        Toast.makeText(getApplicationContext(), toast_result, Toast.LENGTH_SHORT).show();
        return return_result;
    }

}
