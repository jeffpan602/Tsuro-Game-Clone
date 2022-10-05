import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * A JavaFX appliaction to play the game Tsuro 
 * Tsuro uses class TsuroButton to implement the game 
 * 
 * @author Jeffrey Pan
 */
public class Tsuro extends Application {
  
  /** The width of the Tsuro board */
  private static int width = 6;
  
  /** The height of the Tsuro board */
  private static int height = 6;
  
  /** The hand size for each player hand */
  private static int handSize = 3;
  
  /** A 2D array of TsuroButtons to represent the height x width Tsuro board */
  private static TsuroButton[][] grid; // = new TsuroButton[height][width];
  
  /** An array to store hand of player 1 */
  private static TsuroButton[] player1Hand; //= new TsuroButton[handSize];
  
  /** An array to store the hand of player 2 */
  private static TsuroButton[] player2Hand; // = new TsuroButton[handSize];
  
  /** A boolean to store if it is player 1's turn */
  private static boolean isPlayer1Turn;
  
  /** A boolean to store if it is player 2's turn */
  private static boolean isPlayer2Turn;
  
  /** A boolean to store if it is player 2's first turn */
  private static boolean isPlayer2FirstTurn;
  
  /** A boolean to store if it is player 1's first turn */
  private static boolean isPlayer1FirstTurn;
  
  /** A TsuroButton to store the TsuroButton that has player 1's stone */
  private static TsuroButton player1Button;
  
  /** A TsuroButton to store the TsuroButton that has player1's stone */
  private static TsuroButton player2Button;
   
   /** 
   * Overrides the start method of Application to create the GUI for Tsuro with a 6x6 grid of blank TsuroButtons
   * @param primaryStage the JavaFX main window
   */
  public void start(Stage primaryStage) {
    
    grid = new TsuroButton[height][width];
    player1Hand = new TsuroButton[handSize];
    player2Hand = new TsuroButton[handSize];
    
    GridPane gridPane = new GridPane();
    // loop to initialize each spot in the array to a TsuroButton
    for(int i = 0; i < grid.length; i++ ) {
      for(int j = 0; j < grid[i].length; j++ ) {
        grid[i][j] = new TsuroButton(75, 75);
        grid[i][j].setOnAction(new BoardButtonAction());
        gridPane.add(grid[i][j], j , i);
      }
    }
    
    Scene scene = new Scene(gridPane);

    primaryStage.setTitle("Tsuro");
    primaryStage.setScene(scene);            
    primaryStage.show();
    
    isPlayer1Turn = true;
    isPlayer2Turn = false;
    isPlayer1FirstTurn = true;
    isPlayer2FirstTurn = true;
    
    Stage player1Stage = new Stage();
    Stage player2Stage = new Stage();
    GridPane player1Pane = new GridPane();
    GridPane player2Pane = new GridPane();
    for(int i = 0; i < handSize; i++) {
      player1Hand[i] = new TsuroButton(75, 75);
      player1Hand[i].setConnections(TsuroButton.makeRandomConnectionArray());
      player1Hand[i].addStone(Color.BLUE, 6);
      player1Hand[i].setOnAction(new PlayerButtonAction());
      player1Pane.add(player1Hand[i], i, 0);
    }
    for(int i = 0; i < handSize; i++) {
      player2Hand[i] = new TsuroButton(75, 75);
      player2Hand[i].setConnections(TsuroButton.makeRandomConnectionArray());
      player2Hand[i].addStone(Color.GREEN, 2);
      player2Hand[i].setOnAction(new PlayerButtonAction());
      player2Pane.add(player2Hand[i], i, 0);
    }
    Scene player1Scene = new Scene(player1Pane);
    Scene player2Scene = new Scene(player2Pane);
    player1Stage.setScene(player1Scene);
    player2Stage.setScene(player2Scene);
    player1Stage.setTitle("Player 1");
    player2Stage.setTitle("Player 2");
    player1Stage.show();
    player2Stage.show();
  }
  
  private class PlayerButtonAction implements EventHandler<ActionEvent> {
    
    public void handle(ActionEvent e) {
      
      TsuroButton b = (TsuroButton)e.getSource();
      TsuroButton[] playerHand;
      boolean isInHand = false;
      
      //if(player1Hand[0].equals(b) || player1Hand[1].equals(b) || player1Hand[2].equals(b))  playerHand = player1Hand;
      //else playerHand = player2Hand;
      
      if(isPlayer1Turn) playerHand = player1Hand;
      else playerHand = player2Hand;
      
      for(int i = 0; i < playerHand.length; i++) 
        if(playerHand[i].equals(b)) isInHand = true;
      
      
      
      if(isPlayer1Turn && isInHand) {
        
        if(b.getBackgroundColor().equals(Color.WHITE)) {
          b.setBackgroundColor(Color.YELLOW); 
          for(int i = 0; i < handSize; i++) {
            if(playerHand[i].getBackgroundColor().equals(Color.YELLOW) && !playerHand[i].equals(b))
              playerHand[i].setBackgroundColor(Color.WHITE);
          }
        }
        else if(b.getBackgroundColor().equals(Color.YELLOW)) {
          b.rotateButton();
        }
      }
      else if(isPlayer2Turn && isInHand){
        
        if(b.getBackgroundColor().equals(Color.WHITE)) {
          b.setBackgroundColor(Color.YELLOW); 
          for(int i = 0; i < handSize; i++) {
            if(playerHand[i].getBackgroundColor().equals(Color.YELLOW) && !playerHand[i].equals(b))
              playerHand[i].setBackgroundColor(Color.WHITE);
          }
        }
        else if(b.getBackgroundColor().equals(Color.YELLOW)) {
          b.rotateButton();
        }
      }
    }
   
  }

  
  private class BoardButtonAction implements EventHandler<ActionEvent> {
    
    public void handle(ActionEvent e) {
      
      TsuroButton b = (TsuroButton)e.getSource();
      
      if(isPlayer1Turn && hasHighlightedButton(player1Hand)) {
        
        if(isPlayer1FirstTurn) {
          for(int i = 0; i < grid.length; i++) {
            if(b.equals(grid[i][0])) {
              b.setConnections(getHighlightedButton(player1Hand).getConnections());
              b.addStone(Color.BLUE, b.getConnections()[6]);
              player1Button = grid[i][0];
              
              for(TsuroButton button: player1Hand) {
                button.removeStone(6);
                button.addStone(Color.BLUE, getPathStartPoint(player1Button));
              }
              
              getHighlightedButton(player1Hand).setConnections(TsuroButton.makeRandomConnectionArray());
              getHighlightedButton(player1Hand).setBackgroundColor(Color.WHITE);
              isPlayer1FirstTurn = false;
              isPlayer1Turn = false;
              isPlayer2Turn = true;
            }
          }
        }
        else if(!isPlayer1FirstTurn) {
          if(isLegalLocation(b)) {
            b.setConnections(getHighlightedButton(player1Hand).getConnections());
            
            moveStones();
            adjustHands();
            
            getHighlightedButton(player1Hand).setConnections(TsuroButton.makeRandomConnectionArray());
            getHighlightedButton(player1Hand).setBackgroundColor(Color.WHITE);
            isPlayer1Turn = false;
            isPlayer2Turn = true;
          }
        }
      }
      else if(isPlayer2Turn){
        
        if(isPlayer2FirstTurn && hasHighlightedButton(player2Hand)) {
          
          for(int i = 0; i < grid.length; i++) {
            if(b.equals(grid[i][width-1])) {
              b.setConnections(getHighlightedButton(player2Hand).getConnections());
              b.addStone(Color.GREEN, b.getConnections()[2]);
              player2Button = grid[i][width-1];
              
              for(TsuroButton button: player2Hand) {
                button.removeStone(2);
                button.addStone(Color.GREEN, getPathStartPoint(player2Button));
              }
              
              getHighlightedButton(player2Hand).setConnections(TsuroButton.makeRandomConnectionArray());
              getHighlightedButton(player2Hand).setBackgroundColor(Color.WHITE);
              isPlayer2FirstTurn = false;
              isPlayer2Turn = false;
              isPlayer1Turn = true;
            }
          }         
        }
        else if(!isPlayer2FirstTurn) {
          if(isLegalLocation(b)) {
            b.setConnections(getHighlightedButton(player2Hand).getConnections());
           
            moveStones();
            adjustHands();
            
            getHighlightedButton(player2Hand).setConnections(TsuroButton.makeRandomConnectionArray());
            getHighlightedButton(player2Hand).setBackgroundColor(Color.WHITE);
            isPlayer1Turn = true;
            isPlayer2Turn = false;
          }
        }
      } 
    }
  }
  
  /**
   * A method to return the Tsuro board grid
   * @return TsuroButton[][] 2D array that represents the board
   */ 
  public TsuroButton[][] getGrid() {
    return this.grid;
  }
  
  /**
   * A method to set the Tsuro board grid
   * @param grid Tsuro[][] grid the 2D array to represent the board
   */
  public void setGrid(TsuroButton[][] grid) {
    this.grid = grid;
  }
 
  /** 
   * A method to check if there is a highlighted TsuroButton in player hand
   * @param currentPlayer TsuroButton[] of the current player
   * @return boolean value indicating if player 1 has a  highlighted TsuroButton 
   */ 
  public static boolean hasHighlightedButton(TsuroButton[] currentPlayer) {
    /*
    TsuroButton[] currentPlayer;
    if(isPlayer1Turn) currentPlayer = player1Hand;
    else currentPlayer = player2Hand; */
    
    boolean hasHighlight = false;
    
    for(int i = 0; i < currentPlayer.length; i++) {
      if(currentPlayer[i].getBackgroundColor().equals(Color.YELLOW)) hasHighlight = true;
    }
    return hasHighlight;
  }
  
  /**
   * A method to get the highlighted TsuroButton from a player's hand
   * @param currentPlayer TsuroButton[] of the current player
   * @return int index of the TsuroButton that is highlighted in the player's hand 
   */
  public static TsuroButton getHighlightedButton(TsuroButton[] currentPlayer) {
    /*
    TsuroButton[] currentPlayer;
    if(isPlayer1Turn) currentPlayer = player1Hand;
    else currentPlayer = player2Hand; */
    
    for(int i = 0; i < currentPlayer.length; i++) 
      if(currentPlayer[i].getBackgroundColor().equals(Color.YELLOW)) return currentPlayer[i];
    
    return null;
  }
  
   /** 
   * A method to check if the button clicked on the Tsuro board is a legal location/is adjacent to the player's stone
   * @param b TsuroButton to be checked if the location is legal
   * @return boolean indicating if the button clicked is a legal position
   */
  public static boolean isLegalLocation(TsuroButton b) {
    
    if(b.getConnections() != null) return false;
    
    TsuroButton currentButton;
    if(isPlayer1Turn) currentButton = player1Button;
    else currentButton = player2Button;
    
    if(currentButton.getStoneEndPoint() == 0 || currentButton.getStoneEndPoint() == 1) return isButtonAbove(b);
    else if(currentButton.getStoneEndPoint() == 2 || currentButton.getStoneEndPoint() == 3) return isButtonRight(b);
    else if(currentButton.getStoneEndPoint() == 4 || currentButton.getStoneEndPoint() == 5) return isButtonBellow(b);
    else return isButtonLeft(b);
  }
  
  /**
   * A method to check if the input TsuroButton is the TsuroButton above the button with the current player's stone
   * @param b TsuroButton to be checked 
   * @return boolean indicating the input is the button above current player's stone 
   */
  private static boolean isButtonAbove(TsuroButton b) {
    
    TsuroButton currentButton;
    if(isPlayer1Turn) currentButton = player1Button;
    else currentButton = player2Button;
    
    try {
      return grid[getButtonRow(currentButton)-1][getButtonColumn(currentButton)].equals(b);
    }
    catch(ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }
  
    /**
   * A method to check if the input TsuroButton is the TsuroButton bellow the button with the current player's stone
   * @param b TsuroButton to be checked 
   * @return boolean indicating the input is the button bellow current player's stone 
   */
  private static boolean isButtonBellow(TsuroButton b) {
    
    TsuroButton currentButton;
    if(isPlayer1Turn) currentButton = player1Button;
    else currentButton = player2Button;
    
    try {
      return grid[getButtonRow(currentButton)+1][getButtonColumn(currentButton)].equals(b);
    }
    catch(ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }
  
   /**
   * A method to check if the input TsuroButton is the TsuroButton left of the button with the current player's stone
   * @param b TsuroButton to be checked 
   * @return boolean indicating the input is the button left of the current player's stone 
   */
  private static boolean isButtonLeft(TsuroButton b) {
    
    TsuroButton currentButton;
    if(isPlayer1Turn) currentButton = player1Button;
    else currentButton = player2Button;
    
    try {
      return grid[getButtonRow(currentButton)][getButtonColumn(currentButton)-1].equals(b);
    }
    catch(ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }
  
   /**
   * A method to check if the input TsuroButton is the TsuroButton right of the button with the current player's stone
   * @param b TsuroButton to be checked 
   * @return boolean indicating the input is the button right of current player's stone 
   */
  private static boolean isButtonRight(TsuroButton b) {
    
    TsuroButton currentButton;
    if(isPlayer1Turn) currentButton = player1Button;
    else currentButton = player2Button;
    
    try {
      return grid[getButtonRow(currentButton)][getButtonColumn(currentButton)+1].equals(b);
    }
    catch(ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }
  
  /**
   * A method to get the column number for the input TsuroButton on the Tsuro board 
   * @param button the TsuroButton to be checked
   * @return int the column number of the input TsuroButton on the board 
   */
  public static int getButtonColumn(TsuroButton button) {
    
    int column = -1;
    
    for(int i = 0; i < grid.length; i++) {
      for(int j = 0; j < grid[i].length; j++) {
        if(grid[i][j].equals(button)) column = j;
      }
    }
    return column;
  }
  
  /**
   * A method to get the row number for the input TsuroButton on the Tsuro board 
   * @param button the TsuroButton to be checked
   * @return int the row number of the input TsuroButton on the board 
   */
  public static int getButtonRow(TsuroButton button) {
    
    int row = -1;
    
    for(int i = 0; i < grid.length; i++) {
      for(int j = 0; j < grid[i].length; j++) {
        if(grid[i][j].equals(button)) row = i;
      }
    }
    return row;
  }
  
  /** A method to indicate if the button adjacent to input TsuroButton with the player stone has a path 
    * that the stone can be moved along 
    * @param button the TsuroButton that has the stone 
    * @return boolean the boolean to indicate if there exists a stone with a path the stone can move on
    */
  public static boolean hasPath(TsuroButton button) {
    
    try {
      if(button.getStoneEndPoint() == 0 || button.getStoneEndPoint() == 1) 
        return grid[getButtonRow(button)-1][getButtonColumn(button)].getConnections() != null;
      else if(button.getStoneEndPoint() == 2 || button.getStoneEndPoint() == 3) 
        return grid[getButtonRow(button)][getButtonColumn(button)+1].getConnections() != null;
      else if(button.getStoneEndPoint() == 4 || button.getStoneEndPoint() == 5) 
        return grid[getButtonRow(button)+1][getButtonColumn(button)].getConnections() != null;
      else return grid[getButtonRow(button)][getButtonColumn(button)-1].getConnections() != null;
    }
    catch(ArrayIndexOutOfBoundsException e) {
      return false; 
    }
  }
  
  /** A method to get the adjacent button to input TsuroButton with a path player stone can travel along 
    * @param button the TsuroButton with the stone 
    * @return TsuroButton the adjacent TsuroButton with a path 
    */
  public static TsuroButton getAdjacentButton(TsuroButton button) {
    
    try {
      if(button.getStoneEndPoint() == 0 || button.getStoneEndPoint() == 1) 
        return grid[getButtonRow(button)-1][getButtonColumn(button)];
      else if(button.getStoneEndPoint() == 2 || button.getStoneEndPoint() == 3) 
        return grid[getButtonRow(button)][getButtonColumn(button)+1];
      else if(button.getStoneEndPoint() == 4 || button.getStoneEndPoint() == 5) 
        return grid[getButtonRow(button)+1][getButtonColumn(button)];
      else return grid[getButtonRow(button)][getButtonColumn(button)-1];
    }
    catch(ArrayIndexOutOfBoundsException e) {
      return null;
    }
  }
  
  /**
   * A method to get the endpoint of the adjacent button that allows for the player stone to travel on a path
   * This method is used along with assume hasPath method and will only run if hasPath is true
   * @param button TsuroButton with the stone currently
   * @return int the endpoint of the first endpoint (start point) on the path of the adjacent stone
   */
  private static int getPathStartPoint(TsuroButton button) {
    
    if(button.getStoneEndPoint() == 0) return 4;
    else if(button.getStoneEndPoint() == 1) return 5;
    else if(button.getStoneEndPoint() == 2) return 6;
    else if(button.getStoneEndPoint() == 3) return 7;
    else if(button.getStoneEndPoint() == 4) return 0;
    else if(button.getStoneEndPoint() == 5) return 1;
    else if(button.getStoneEndPoint() == 6) return 2;
    else return 3;
  }
  
  /**
   * A method to move the stones to the proper location on the Tsuro board path after a new game tile is placed 
   */
  public static void moveStones() {
    
    TsuroButton newPlayer1Button = player1Button;
    while(hasPath(player1Button) && !stonesCollide()) {
      int startPoint = getPathStartPoint(player1Button);
      newPlayer1Button = getAdjacentButton(player1Button);
      int endPoint = newPlayer1Button.getConnections()[startPoint];
      player1Button.removeStone(player1Button.getStoneEndPoint());
      newPlayer1Button.addStone(Color.BLUE, endPoint);
      
      player1Button = newPlayer1Button;
    }
    
    TsuroButton newPlayer2Button = player2Button;
    while(hasPath(player2Button) && !stonesCollide()) {
      int startPoint = getPathStartPoint(player2Button);
      newPlayer2Button = getAdjacentButton(player2Button);
      int endPoint = newPlayer2Button.getConnections()[startPoint];
      player2Button.removeStone(player2Button.getStoneEndPoint());
      newPlayer2Button.addStone(Color.GREEN, endPoint);
      
      player2Button = newPlayer2Button;
    }
  }
  
  /**
   * A method to adjust the stone positions on the TsuroButtons in each player hand
   */
  private void adjustHands() {
    
    for(TsuroButton b: player1Hand) {
      b.removeStone(b.getStoneEndPoint());
      b.addStone(Color.BLUE, getPathStartPoint(player1Button));
    }
    for(TsuroButton b: player2Hand) {
      b.removeStone(b.getStoneEndPoint());
      b.addStone(Color.GREEN, getPathStartPoint(player2Button));
    }
  }
  
  /**
   * A method to check if stones from players will hit each other once a new game tile forming a path is placed
   * @return boolean boolean inidcating if player stones will hit each other 
   */
  private static boolean stonesCollide() {
    
    if(isPlayer1Turn && getAdjacentButton(player1Button).equals(player2Button)) 
      return getPathStartPoint(player1Button) == player2Button.getStoneEndPoint(); 
    else if(isPlayer2Turn && getAdjacentButton(player2Button).equals(player1Button))
      return getPathStartPoint(player2Button) == player1Button.getStoneEndPoint();
    
    return false;
  }

   /**
   * The method to launch the program.
   * @param args  The command line arguments.  The arguments are passed on to the JavaFX application.
   */
   public static void main(String[] args) {
     
     if(args.length == 0) {
       Tsuro.height = 6;
       Tsuro.width = 6;
       handSize = 3;
     }
     
     else if(args.length == 2) {
       if(Integer.parseInt(args[0]) <= 8) Tsuro.height = Integer.parseInt(args[0]);
       else Tsuro.height = 8;
       
       if(Integer.parseInt(args[1]) <= 8) Tsuro.width = Integer.parseInt(args[1]);
       else Tsuro.width = 8;
     }
     else if(args.length == 3) {
       if(Integer.parseInt(args[0]) <= 8) Tsuro.height = Integer.parseInt(args[0]);
       else Tsuro.height = 8;
       
       if(Integer.parseInt(args[1]) <= 8) Tsuro.width = Integer.parseInt(args[1]);
       else Tsuro.width = 8;
       
       if(Integer.parseInt(args[2]) <= 5) Tsuro.handSize = Integer.parseInt(args[2]);
       else Tsuro.handSize = 5;
     }
     
     Application.launch(args);
   }
}