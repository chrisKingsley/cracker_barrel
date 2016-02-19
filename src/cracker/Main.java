package cracker;

public class Main {
	
	private static final int PUZZLE_SIZE = 5;
	
	
	public static void printSolution(boolean[][] puzzle, int [][] moves) {
		for(int i=0; i< puzzle.length; i++) {
			for(int j=1; j<puzzle.length-i; j++)
				System.out.print(" ");
			for(int j=0; j<= i; j++) {
				System.out.print(puzzle[i][j] ? "O":"X" + (j<i ? " ":""));
			}
			System.out.println();
		}
	}
	
	public static void addMove(int[][] moves, int numMoves, int i1, int j1, int i2, int j2) {
		moves[numMoves][0] = i1;
		moves[numMoves][1] = j1;
		moves[numMoves][2] = i2;
		moves[numMoves][3] = j2;
	}
	
	public static int getPossibleMoves(boolean[][] puzzle, int[][] moves) {
		int numMoves = 0;
		
		for(int i=0; i<PUZZLE_SIZE; i++) {
			for(int j=0; j<=i; j++) {
				if(!puzzle[i][j]) {
					if(j>1 && !puzzle[i][j-1] && puzzle[i][j-2])
						addMove(moves, numMoves++, i, j, i, j-2);
					if(j<PUZZLE_SIZE-2 && !puzzle[i][j+1] && puzzle[i][j+2])
						addMove(moves, numMoves++, i, j, i, j+2);
					if(i>1 && !puzzle[i-1][j] && puzzle[i-2][j])
						addMove(moves, numMoves++, i, j, i-2, j);
					if(i<PUZZLE_SIZE-2 && !puzzle[i+1][j] && puzzle[i+2][j])
						addMove(moves, numMoves++, i, j, i+2, j);
					if(i>1 && j>1 && !puzzle[i-1][j-1] && puzzle[i-2][j-2])
						addMove(moves, numMoves++, i, j, i-2, j-2);
					if(i<PUZZLE_SIZE-2 && !puzzle[i+1][j+1] && puzzle[i+2][j+2])
						addMove(moves, numMoves++, i, j, i+2, j+2);
				}
			}
		}
		
		
		return numMoves;
	}
	
	public static void solvePuzzle(boolean[][] puzzle, int[][] moves, int numLeft) {
		if(numLeft>1) {
			int numMoves = getPossibleMoves(puzzle, moves);
			if(numMoves > 1) {
				
			}
		}
	}

	public static void main(String[] args) {
		boolean[][] puzzle = new boolean[ PUZZLE_SIZE ][ PUZZLE_SIZE ];
		puzzle[0][0] = true;
		
		int maxNumMoves = PUZZLE_SIZE * (PUZZLE_SIZE + 1) / 2;
		int[][] moves = new int[maxNumMoves][4];
		
		for(int i=0; i<moves.length; i++) {
			for(int j=0; j<4; j++)
				System.out.print(moves[i][j] + " ");
			System.out.println();
		}

		solvePuzzle(puzzle, moves, maxNumMoves-1);

	}

}
