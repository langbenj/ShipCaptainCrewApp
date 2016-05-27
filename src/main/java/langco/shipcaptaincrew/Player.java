package langco.shipcaptaincrew;


public class Player {


    private int mCurrentMoney;
    private int mCurrentHighRoll;
    private String mPlayerName;
    private boolean mAIPlayer;



    public Player() {
        mCurrentMoney=100;
        mCurrentHighRoll=0;
        mPlayerName="";
        mAIPlayer=true;
    }

    public int getCurrentMoney() {
        return mCurrentMoney;
    }

    public void setCurrentMoney(int mCurrentMoney) {
        this.mCurrentMoney = mCurrentMoney;
    }

    public int getCurrentHighRoll() {
        return mCurrentHighRoll;
    }

    public void setCurrentHighRoll(int mCurrentHighRoll) {
        this.mCurrentHighRoll = mCurrentHighRoll;
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public void setPlayerName(String mPlayerName) {
        this.mPlayerName = mPlayerName;
    }

    public boolean isAIPlayer() {
        return mAIPlayer;
    }

    public void setAIPlayer(boolean mAIPlayer) {
        this.mAIPlayer = mAIPlayer;
    }

}
