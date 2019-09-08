package Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		while(true) {
			System.out.println("Please pick a column you would like to play in"); {
				try {
					String s = keyboard.readLine();
					int column = Integer.parseInt(s);
					if(column >= 0 && column < 7) {
						if (c.available[column] < 6) {
							return column;
					}
						else {
							System.out.println("This column is full! Try again");
						}
					}
					else {
						System.out.println("That column is out of bounds of the board. Try again!");
					}
				}
					catch (Exception e) {
						continue;
					}
				}
			}
		}

	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		 int lastMove = columnPlayed2;
		  int i = 1;
		  if (c.canWinNextRound(1) != -1) 
		   return c.canWinNextRound(1);
		  
		  if (c.canWinTwoTurns(1) != -1) 
		   return c.canWinTwoTurns(1);
		  
		  if (c.available[lastMove] <= 5)
		   return lastMove;
		  
		  else {
		   while(true) {
		    if ((lastMove-i) >= 0 && c.available[lastMove - i] <= 5)
		     return (lastMove -i);
		    if ((lastMove+i) <= 6 && c.available[lastMove + i] <= 5)
		     return (lastMove +i);
		    
		   }
		  }
		
	}
}


