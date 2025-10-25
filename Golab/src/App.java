import java.util.Scanner;
public class App {
    
    static String[][] GoBoard = {
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
        {"|","-|","-|","-|","-|","-|","-|","-|","-|"},
            };
    static int wpoints = 0;
    static int bpoints = 0;
    static int btimesbordered = 0;
    static int wtimesbordered = 0;
    static int runcount = 0;
    static boolean[][] territory = new boolean[9][9];
    static boolean[][] beenChecked = new boolean[9][9];

    static void calculateScore(){
        for (int i = 0; i < GoBoard.length; i++) {
            for (int j = 0; j < GoBoard[i].length; j++) {
                if (beenChecked[i][j] == false){
                    territorycheck(i, j);
                    if (btimesbordered > wtimesbordered && wtimesbordered == 0){
                        bpoints = bpoints + runcount;
                    }
                    if (wtimesbordered > btimesbordered && btimesbordered == 0){
                        wpoints = wpoints + runcount;
                    }
                    runcount = 0;
                    btimesbordered = 0;
                    wtimesbordered = 0;
                }
            }
        }
        System.out.println("White Scored: "+ wpoints);
        System.out.println("Black Scored: "+ bpoints);
    }

    static void territorycheck(int xcord, int ycord){
        runcount++;
        beenChecked[xcord][ycord] = true;
        if (beenChecked[xcord][ycord-1]==false && ycord != 0){
            beenChecked[xcord][ycord-1] = true;
            if (GoBoard[xcord][ycord-1].equals("-*")){
                btimesbordered++;
            }
             if (GoBoard[xcord][ycord-1].equals("-o")){
                wtimesbordered++;
            }
             if (GoBoard[xcord][ycord-1].equals("-|")){
                territorycheck(xcord,ycord-1);
            }
        }
        if (beenChecked[xcord][ycord+1]==false && ycord != 9){
            beenChecked[xcord][ycord+1] = true;
            if (GoBoard[xcord][ycord+1].equals("-*")){
                btimesbordered++;
            }
            if (GoBoard[xcord][ycord+1].equals("-o")){
                wtimesbordered++;
            }
            if (GoBoard[xcord][ycord+1].equals("-|")){
                territorycheck(xcord,ycord+1);
            }
        }
        if (beenChecked[xcord+1][ycord]==false && xcord != 9){
            beenChecked[xcord+1][ycord] = true;
            if (GoBoard[xcord+1][ycord].equals("-*")){
                btimesbordered++;
            }
            if (GoBoard[xcord+1][ycord].equals("-o")){
                wtimesbordered++;
            }
            if (GoBoard[xcord+1][ycord].equals("-|")){
                territorycheck(xcord-1,ycord);
            }
        }
        if (beenChecked[xcord-1][ycord]==false && xcord != 0){
            beenChecked[xcord-1][ycord] = true;
            if (GoBoard[xcord-1][ycord].equals("-*")){
                btimesbordered++;
            }
            if (GoBoard[xcord-1][ycord].equals("-o")){
                wtimesbordered++;
            }
            if (GoBoard[xcord-1][ycord].equals("-|")){
                territorycheck(xcord+1,ycord);
            }
        }
    }

    static boolean nexttomecheck(int xcord, int ycord, String pieceColor, String directionFrom){
        if (!beenChecked[xcord][ycord]){
            beenChecked[xcord][ycord] = true;
            if (GoBoard[xcord][ycord].equals(pieceColor)){
                return captureCheck(xcord, ycord,pieceColor,directionFrom);
            }
            else if(GoBoard[xcord][ycord].equals("-|")){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    static void clearchecks(){
        for (int i = 0; i < beenChecked.length; i++) {
            for (int j = 0; j < beenChecked[i].length; j++) {
                beenChecked[i][j] = false;
            }
        }
    }

    static boolean checkingtime(int xcord, int ycord, String pieceColor){
        // the amount of nesting I had to use for this is a tragedy
        boolean placesetter = captureCheck(xcord, ycord, GoBoard[xcord][ycord], "none");
        clearchecks();
        if (placesetter != true){
            if (GoBoard[xcord-1][ycord].equals("-*")){
                if (GoBoard[xcord][ycord].equals("-o")){
                    placesetter = captureCheck(xcord-1, ycord, "-o", "none");
                    clearchecks();
                }
                if (GoBoard[xcord+1][ycord].equals("-o")){
                    placesetter = captureCheck(xcord+1, ycord, "-o", "none");
                    clearchecks();
                }
                if (GoBoard[xcord][ycord-1].equals("-o")){
                    placesetter = captureCheck(xcord, ycord-1, "-o", "none");
                    clearchecks();
                }
                if (GoBoard[xcord][ycord+1].equals("-o")){
                    placesetter = captureCheck(xcord-1, ycord, "-o", "none");
                    clearchecks();
                }
            }
            else{
                if (GoBoard[xcord][ycord].equals("-o")){
                    placesetter = captureCheck(xcord-1, ycord, "-*", "none");
                    clearchecks();
                }
                if (GoBoard[xcord+1][ycord].equals("-*")){
                    placesetter = captureCheck(xcord+1, ycord, "-*", "none");
                    clearchecks();
                }
                if (GoBoard[xcord][ycord-1].equals("-*")){
                    placesetter = captureCheck(xcord, ycord-1, "-*", "none");
                    clearchecks();
                }
                if (GoBoard[xcord][ycord+1].equals("-*")){
                    placesetter = captureCheck(xcord-1, ycord, "-*", "none");
                    clearchecks();
                }
            }
            if (pieceColor.equals("-*")){
                    bpoints--;
                }
            if (pieceColor.equals("-o")){
                    wpoints--;
            }
            return true;
        }
        else{
            return false;
        }
    }

    static boolean captureCheck(int xcord, int ycord, String pieceColor, String directionFrom){
        // plan: run through this if the pieces next have been checked or not, and check them, using another function to check what color they are
        beenChecked[xcord][ycord] = true;
        boolean blw = false;
        boolean abv = false;
        boolean rgh = false;
        boolean lft = false;
        if (directionFrom.equals("below")== false && ycord != 0){
            blw = nexttomecheck(xcord, ycord-1, pieceColor, "below");
        }
        if (directionFrom.equals("above")==false && ycord != 9){
            abv = nexttomecheck(xcord, ycord+1, pieceColor, "above");
        }
        if (directionFrom.equals("right")==false && xcord != 9){
            rgh = nexttomecheck(xcord+1, ycord, pieceColor, "right");
        }
        if (directionFrom.equals("left")==false && xcord != 0){
            lft = nexttomecheck(xcord-1, ycord, pieceColor, "left");
        }
        if (((blw || abv)||(rgh || lft)) != true){
            GoBoard[xcord][ycord] = "-|";
            if (pieceColor.equals("-*")){
                wpoints++;
            }
            else{
                bpoints++;
            }
        }
        return ((blw || abv)||(rgh || lft));
    }
    public static void main(String[] args) throws Exception{
        
        int moveX;
        int moveY;

        Scanner scn = new Scanner(System.in);
        boolean changeturn = false;
        boolean gameGoing = true;
        boolean turnflipper = false;
        while (gameGoing) {
            turnflipper = !turnflipper;
            for (int i = 0; i < GoBoard.length; i++){
                for (int j = 0; j < GoBoard[i].length; j++){
                    System.out.print(GoBoard[i][j]);
    
                    }
                    System.out.println();
                }
                if (turnflipper){
                    System.out.println("White Enter an x coordinate");
                    moveX = scn.nextInt();
                    System.out.println("And a Y coordinate");
                    moveY = scn.nextInt();
                }
                else{
                    System.out.println("Black Enter an x coordinate");
                    moveX = scn.nextInt();
                    System.out.println("And a Y coordinate");
                    moveY = scn.nextInt();
                }
                if (moveX == -1 || moveY == -1){
                    break;
                }
                if (GoBoard[moveX][moveY] != "-*"  ){
                    if (GoBoard[moveX][moveY] != "-o"){
                        if (!turnflipper){
                            GoBoard[moveX][moveY] = "-*";
                            changeturn = checkingtime(moveX, moveY,"*");
                            if (changeturn == true){turnflipper = !turnflipper;}
                            changeturn = false;
                        }
                        else{
                            GoBoard[moveX][moveY] = "-o";
                            changeturn = checkingtime(moveX, moveY,"-o");
                            if (changeturn == true){turnflipper = !turnflipper;}
                            changeturn = false;
                        }
                    }
                    else{
                        turnflipper = !turnflipper;
                        }    
                }
                else{
                    turnflipper = !turnflipper;
                }

            }
        scn.close();
        calculateScore();
    }

}
