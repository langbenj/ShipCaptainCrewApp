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
    public int mCurrentPlayer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button rerollButton= (Button) findViewById(R.id.reroll_button);
        final Button nextPlayerButton= (Button) findViewById(R.id.next_player_button);
        final TextView remaining_tries = (TextView) findViewById(R.id.remaining_tries);
        final DiceRoll diceObject = new DiceRoll();

        player_list[0].setAIPlayer(false);
        lockTheDice(diceObject.mDiceList);
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
                    diceObject.rerollDice(diceObject.mDiceList, dice_to_reroll);
                    lockTheDice(diceObject.mDiceList);
                    setDicePictures(diceObject.mDiceList);
                    checkForWin(diceObject.mDiceList);


                    }

            });
        }

        if (nextPlayerButton != null) {
            nextPlayerButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (mCurrentPlayer<4) {
                        mCurrentPlayer++;
                    }
                    else {
                        mCurrentPlayer=0;
                    }
                    TextView player1_title = (TextView) findViewById(R.id.Player1);
                    TextView player2_title = (TextView) findViewById(R.id.Player2);
                    TextView player3_title = (TextView) findViewById(R.id.Player3);
                    TextView player4_title = (TextView) findViewById(R.id.Player4);
                    TextView player5_title = (TextView) findViewById(R.id.Player5);

                    switch (mCurrentPlayer) {
                        case 0: player5_title.setVisibility(View.INVISIBLE);
                            player1_title.setVisibility(View.VISIBLE);
                            break;
                        case 1: player1_title.setVisibility(View.INVISIBLE);
                            player2_title.setVisibility(View.VISIBLE);
                            break;
                        case 2: player2_title.setVisibility(View.INVISIBLE);
                            player3_title.setVisibility(View.VISIBLE);
                            break;
                        case 3: player3_title.setVisibility(View.INVISIBLE);
                            player4_title.setVisibility(View.VISIBLE);
                            break;
                        case 4: player4_title.setVisibility(View.INVISIBLE);
                            player5_title.setVisibility(View.VISIBLE);
                            break;
                    }
                    for (int i=0; i<dice_to_reroll.length; i++) {
                        dice_to_reroll[i]=1;
                    }

                    player_tries=2;
                    if (remaining_tries != null) {
                        remaining_tries.setText(String.valueOf(player_tries));
                    }

                    diceObject.rerollDice(diceObject.mDiceList, dice_to_reroll);
                    lockTheDice(diceObject.mDiceList);
                    setDicePictures(diceObject.mDiceList);
                    checkForWin(diceObject.mDiceList);
                    nextPlayerButton.setVisibility(View.INVISIBLE);
                    if (rerollButton != null) {
                        rerollButton.setVisibility(View.VISIBLE);
                    }

                }
            });
        }


    }

    public void lockTheDice(ArrayList<Integer> dice_list) {
        int wheres_the_4;
        int wheres_the_5;
        int wheres_the_6;
        for (int i=0; i<dice_to_reroll.length; i++) {
            dice_to_reroll[i]=1;
        }

        wheres_the_4=checkForDice(dice_list,4);
        if (checkForDice(dice_list,4)!=-1) {
            dice_to_reroll[wheres_the_4]=0;
        }

        wheres_the_5=checkForDice(dice_list,5);
        if (checkForDice(dice_list,5)!=-1 && checkForDice(dice_list,4)!=-1) {
            dice_to_reroll[wheres_the_5]=0;
        }

        wheres_the_6=checkForDice(dice_list,6);
        if (checkForDice(dice_list,4)!=-1 && checkForDice(dice_list,5)!=-1 && checkForDice(dice_list,6)!=-1) {
            dice_to_reroll[wheres_the_6]=0;
        }

        String debug = wheres_the_4+" "+wheres_the_5+" "+wheres_the_6;
        Log.d("Lock The Dice",debug);

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

    public int checkForDice(ArrayList<Integer> dice_list, int target_value) {
        int ship_found=-1;
        for (int i=0; i<dice_list.size(); i++) {
            if (dice_list.get(i)==target_value) {
                ship_found=i;
                break;
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
        if (checkForDice(dice_list, 4)!=-1) {
            toast_result="You've got a ship (4)!";
            if (checkForDice(dice_list, 5)!=-1) {
                toast_result="You've got a ship and a captain (4,5)!";
                if (checkForDice(dice_list, 6)!=-1) {
                    int current_score=findTheScore(dice_list);
                    toast_result= "You've got a ship, a captain and a crew (4,5,6)! Your current score is: "+current_score;
                    if (current_score>current_top_score) {
                        current_top_score=current_score;
                        TextView high_score = (TextView) findViewById(R.id.largest_crew);
                        if (high_score != null) {
                            high_score.setText("Player "+(mCurrentPlayer+1)+" with "+current_top_score);
                        }
                        Button nextPlayerButton= (Button) findViewById(R.id.next_player_button);
                        if (nextPlayerButton != null) {
                            nextPlayerButton.setVisibility(View.VISIBLE);
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
