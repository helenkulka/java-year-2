package GGame;

public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	public static int[] getARow(int[][] board, int which) {
		int[] row = new int[6];
		for (int i=0; i<6; i++) {
			row[i] = board[i][which];
		}
		return row;
	}

	
	public void addDisk (int index, int player){
		int a = available[index];
		if (a < 6) {
		board[index][a] = player;
		available[index]+=1;
		}
		if (available[index] == 6 ) {
			spaceLeft = false;
		}
	}
	public boolean diagonalWinning(int[] diagonalArray, int player) {
		for (int i=1; i<diagonalArray.length-1; i++) {
			if (diagonalArray[i] == player && diagonalArray[i+1] == player && diagonalArray[i-1] == player) {
				if (i+2<diagonalArray.length) {
					if (diagonalArray[i+2] == player) {
						return true;
					}
				}
				else if (i-2>=0) {
					if (diagonalArray[i-2] == player) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isWinning (int lastColumnPlayed, int player){
		//winning down a column
		for (int i=1; i<4; i++) {
			if (board[lastColumnPlayed][i] == player && board[lastColumnPlayed][i+1] == player && board[lastColumnPlayed][i-1] == player) {
				if (i-2>=0) {
					if (board[lastColumnPlayed][i-2] == player) {
						return true;
					}
				}
				else if (i+2<6) {
					if (board[lastColumnPlayed][i+2] == player) {
						return true;
					}
				}
			}
		}
		//winning down a row
		for (int i=0; i<6; i++) {
			int[] row = getARow(board, i);
				for (int j=1; j<5; j++) {
					if (row[j] == player && row[j+1] == player && row[j-1] == player) {
						if (j-2>=0) {
							if (row[j-2] == player) {
								return true;
							}
						}
						else if (j+2<7) {
							if (row[j+2] == player) {
								return true;
							}
						}
					}
			}
		}
		//winning down regular diagonal
		int [] diag41 = new int[4];
		diag41[0] = board[0][2];
		diag41[1] = board[1][3];
		diag41[2] = board[2][4];
		diag41[3] = board[3][5];
		
		if (diagonalWinning(diag41, player)) {
			return true;
		}
		
		int [] diag51 = new int[5];
		diag51[0] = board[0][1];
		diag51[1] = board[1][2];
		diag51[2] = board[2][3];
		diag51[3] = board[3][4];
		diag51[4] = board[4][5];
	
		if (diagonalWinning(diag51, player)) {
			return true;
		}
		//
		
		int [] diag61 = new int[6];
		for (int i=0; i<6; i++) {
			diag61[i] = board[i][i];
		}
		if (diagonalWinning(diag61, player)) {
			return true;
		}
		//
		int [] diag62 = new int[6];
		for (int i=0; i<6; i++) {
			diag62[i] = board[i+1][i];
		}
		if (diagonalWinning(diag62, player)) {
			return true;
		}
		//
		
		int[] diag52 = new int[5];
		for (int i=0; i<5; i++) {
			diag52[i] = board[i+2][i];
		}
		if (diagonalWinning(diag52, player)) {
			return true;
		}
		
		int[] diag42 = new int[4];
		for (int i=0; i<4; i++) {
			diag42[i] = board[i+3][i];
		}
		if (diagonalWinning(diag42, player)) {
			return true;
		}
		
		//checking opposite diagonals
		int[] diag43 = new int[4];
		for (int i=0; i<4; i++) {
			diag43[i] = board[3-i][i];
		}
		if (diagonalWinning(diag43, player)) {
			return true;
		}
		//
		
		int[] diag53 = new int[5];
		for (int i=0; i<5; i++) {
			diag53[i] = board[4-i][i];
		}
		if (diagonalWinning(diag53, player)) {
			return true;
		}
		//
		int[] diag63 = new int[6];
		for (int i=0; i<6; i++) {
			diag63[i] = board[5-i][i];
		}
		if (diagonalWinning(diag63, player)) {
			return true;
		}
		//
		
		int[] diag64 = new int[6];
		for (int i=0; i<6; i++) {
			diag64[i] = board[6-i][i];
		}
		if (diagonalWinning(diag64, player)) {
			return true;
		}
		//
		
		int[] diag54 = new int[5];
		for (int i=0; i<5; i++) {
			diag54[i] = board[6-i][i+1];
		}
		if (diagonalWinning(diag54, player)) {
			return true;
		}
		//
		
		int[] diag44 = new int[4];
		for (int i=0; i<4; i++) {
			diag44[i] = board[6-i][i+2];
		}
		if (diagonalWinning(diag44, player)) {
			return true;
		}
	
		return false; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public int canWinNextRound (int player){
		for (int i=0; i<7; i++) {
			if (available[i] != 6) {
				int r = available[i];
				addDisk(i, player);
				if (isWinning(i, player)) {
					return i;
				}
				else {
					board[i][r] = 0;
					available[i] = r;
					spaceLeft = true;
			}
			}
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public int findpiece(int player) {
		int x = 0;
		for (int i=0; i<5; i++) {
			for (int j=0; j<6; j++) {
				if (board[j][i] != 0 && board[j][i] != player) {
					x = board[j][i];
					return x;
				}
			}
		}
		return 0;
	}
	
	public int canWinTwoTurns (int player){
		int count = 0;
		if (findpiece(player) != 0) {
		for (int i=0; i<6; i++) {
			if (available[i] != 6) {
				int v = available[i];
			addDisk(i, player);
			
			for (int j=0; j<6; j++) {
				int r = available[j];
				if (r!= 6) {
				addDisk(j, findpiece(player));
				if (canWinNextRound(player) != -1) {
					count++;
				}
				board[j][r] = 0;
				available[j] = r;
				spaceLeft = true;
				}
				if (count == 7) {
					board[i][v] = 0;
					available[i]--;
					spaceLeft = true;
					return i;
				}
			}
		}
			
		}
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}
