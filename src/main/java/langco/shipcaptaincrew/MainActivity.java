package langco.shipcaptaincrew;

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
    public int player_tries=2;
    public int current_top_score=0;
    public Player mPlayer1 = new Player();
    public Player mPlayer2 = new Player();
    public Player mPlayer3 = new Player();
    public Player mPlayer4 = new Player();
    public Player mPlayer5 = new Player();
    public Player [] player_list = {mPlayer1,mPlayer2,mPlayer3,mPlayer4,mPlayer5};
    public boolean rolled_456 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button rerollButton= (Button) findViewById(R.id.reroll_button);
        final Button nextPlayerButton= (Button) findViewById(R.id.next_player_button);
        ImageButton dice_button_1= (ImageButton) findViewById(R.id.dice_button_1);
        ImageButton dice_button_2= (ImageButton) findViewById(R.id.dice_button_2);
        ImageButton dice_button_3= (ImageButton) findViewById(R.id.dice_button_3);
        ImageButton dice_button_4= (ImageButton) findViewById(R.id.dice_button_4);
        ImageButton dice_button_5= (ImageButton) findViewById(R.id.dice_button_5);
        final TextView remaining_tries = (TextView) findViewById(R.id.remaining_tries);
        final DiceRoll diceObject = new DiceRoll();

        player_list[0].setAIPlayer(false);
        setDicePictures(diceObject.mDiceList);
        checkForWin(diceObject.mDiceList);

        if (remaining_tries != null) {
            remaining_tries.setText(String.valueOf(player_tries));
        }


        if (rerollButton != null) {
            rerollButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    player_tries--;
                    if (player_tries >= 0) {

                        setDicePictures(diceObject.rerollDice(diceObject.mDiceList, dice_to_reroll));
                        checkForWin(diceObject.mDiceList);

                        if (remaining_tries != null) {
                            remaining_tries.setText(String.valueOf(player_tries));
                        }

                        if (player_tries==0) {

                            if (nextPlayerButton != null) {
                                nextPlayerButton.setVisibility(View.VISIBLE);
                            }
                            rerollButton.setVisibility(View.INVISIBLE);

                        }
                    }



                    }

            });
        }

        if (nextPlayerButton != null) {
            nextPlayerButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i=0; i<dice_to_reroll.length; i++) {
                        dice_to_reroll[i]=1;
                    }
                    setDicePictures(diceObject.mDiceList);
                    player_tries=2;
                    if (remaining_tries != null) {
                        remaining_tries.setText(String.valueOf(player_tries));
                    }
                    setDicePictures(diceObject.rerollDice(diceObject.mDiceList, dice_to_reroll));
                    checkForWin(diceObject.mDiceList);
                    nextPlayerButton.setVisibility(View.INVISIBLE);
                    if (rerollButton != null) {
                        rerollButton.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        if (dice_button_1!=null) {
            dice_button_1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("Check for Win", String.valueOf(rolled_456));
                  clickOnDice(diceObject.mDiceList,0);
                }
            });
        }

        if (dice_button_2!=null) {
            dice_button_2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickOnDice(diceObject.mDiceList,1);
                }
            });
        }

        if (dice_button_3!=null) {
            dice_button_3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickOnDice(diceObject.mDiceList,2);
                }
            });
        }

        if (dice_button_4!=null) {
            dice_button_4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickOnDice(diceObject.mDiceList,3);
                }
            });
        }

        if (dice_button_5!=null) {
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
        }
        else  {
            dice_to_reroll[position]=0;
        }
        setDicePictures(dice_list);
    }

    public void setDicePictures(ArrayList<Integer> dice_list) {
        int view_id;
        int image_id;
        String image_string;
        String resource_id_string;
        for (int i=0; i<dice_list.size(); i++) {
            if (dice_to_reroll[i]==0) {
                image_string = "dice_selected_"+dice_list.get(i);
            }
            else  {
                image_string = "dice"+dice_list.get(i);
            }
            resource_id_string = "dice_button_"+(i+1);
            image_id = getResources().getIdentifier(image_string, "drawable", getPackageName());
            view_id = getResources().getIdentifier(resource_id_string, "id", getPackageName());
            ImageView target_image_field = (ImageView) findViewById(view_id);
            if (target_image_field != null) {
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
            toast_result="You've got a ship (4)!";
            if (checkForDice(dice_list, 5)) {
                toast_result="You've got a ship and a captain (4,5)!";
                if (checkForDice(dice_list, 6)) {
                    int current_score=findTheScore(dice_list);
                    toast_result= "You've got a ship, a captain and a crew (4,5,6)! Your current score is: "+current_score;
                    if (current_score>current_top_score) {
                        current_top_score=current_score;
                        TextView high_score = (TextView) findViewById(R.id.largest_crew);
                        if (high_score != null) {
                            high_score.setText(String.valueOf(current_top_score));
                        }
                    }
                    return_result=true;
                }
            }
        }
        Toast.makeText(getApplicationContext(), toast_result, Toast.LENGTH_SHORT).show();
        return return_result;
    }



}
