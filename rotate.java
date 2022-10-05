

public class rotate {

private static int [][] boarderNumbers = new int[2][4];
/**
 * a method that adds the numbers in order to the boarderNumber array
 */
public static void setBoarderNumbers(){
    boarderNumbers[0][0] = 0;
    boarderNumbers[1][0] = 1;
    boarderNumbers[0][1] = 2;
    boarderNumbers[1][1] = 3;
    boarderNumbers[0][2] = 5;
    boarderNumbers[1][2] = 4;
    boarderNumbers[0][3] = 7;
    boarderNumbers[1][3] = 6;

}

/**
 * a method that takes an int as input and searches the same int in a double int array and returns the number right below the target number in the array
 * @param target the original number in the list
 * @param list  the boarderNumber list
 * @return the location where the target number is after rotating clockwise 90 degrees
 */
public static int nextNumber(int target, int[][] list) {
    int returnNumber=1;
    for (int i = 0; i < list.length; i++) {
        for (int j = 0; j < list[i].length; j++) {
            if (target == list[i][j]) {
                returnNumber = list[i][(j+1)%4];
                System.out.println("next number is " + returnNumber);
                return returnNumber;

                }

            }
        }
    return 1;
    }

/**
 * a method that returns the a number's position if it was turned 90 degrees counter-clock wise
 * @param target the original number in the list
 * @param list  the boarderNumber list
 * @return the location where the target number is after rotating counter-clockwise 90 degrees
 */
public static int previousNumber(int target,int[][] list){
    int returnNumber =0;
    for (int i = 0; i < list.length; i++) {
        for (int j = 0; j < list[i].length; j++) {
            if (target == list[i][j]) {
                returnNumber = list[i][(j+3)%4];
                System.out.println("previous number is  " + returnNumber);
            }

        }
    }
    return returnNumber;

}

/**
 * a method that rotates the highlighted path tile 90 degrees
 * @param tile the highlighted tile
 */
public static void rotateButton(TsuroButton tile){
    rotate.setBoarderNumbers();
    int[] connections= tile.getConnections();
    int[] newConnections = new int[8];
    for (int i = 0; i < connections.length; i++){
        int currentNumber = connections[rotate.previousNumber(i,boarderNumbers)];
        int nextNumber = rotate.nextNumber(currentNumber,boarderNumbers);
        newConnections[i] = nextNumber;
    }
    tile.setConnections(newConnections);
}
}