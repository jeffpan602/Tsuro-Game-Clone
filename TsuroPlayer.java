import javafx.scene.paint.Color;

public class TsuroPlayer {
  
  private boolean isTurn = false;
  
  private TsuroButton[] hand = new TsuroButton[3];
  
  public TsuroPlayer (int playerNumber) {
    
    for(int i = 0; i < hand.length; i++) {
      hand[i] = new TsuroButton(100, 100);
      hand[i].setConnections(TsuroButton.makeRandomConnectionArray());
      
      if(playerNumber == 1) hand[i].addStone(Color.BLUE, 6);
      else if(playerNumber == 2) hand[i].addStone(Color.GREEN, 2);
    }
  }
  
  public TsuroButton[] getHand() {
    return this.hand;
  }
  
  public boolean isTurn() {
    return this.isTurn;
  }
}