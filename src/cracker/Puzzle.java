package cracker;


/**
 * Implements a solution to the Cracker Barrel puzzle.  Pegs in a triangular pattern jump over each other, removing
 * the peg that is jumped over.  Solution leaves one peg left.
 */
public class Puzzle {
	
	private static final int PUZZLE_SIZE = 7;
	boolean [][] puzzle;
	int[][] moves;
	int numMoves, numFilled, minNumFilled, maxFilled, totalMoves;
	
	/**
	 * Constructor
	 */
	public Puzzle() {
		initBoard();
		moves = new int[numFilled][4];
	}
	
	
	/**
	 * 
	 */
	public void initBoard() {
		puzzle = new boolean[ PUZZLE_SIZE ][ PUZZLE_SIZE ];
		puzzle[0][0] = true;
		numFilled = PUZZLE_SIZE * (PUZZLE_SIZE + 1) / 2 - 1;
		maxFilled = numFilled;
		minNumFilled = numFilled;
	}
	
	
	/**
	 * Makes a move on the puzzle, removing the peg that is jumped over.  The moves matrix is updates with the move
	 * that was made.
	 * @param i1 row of starting peg position
	 * @param j1 col of starting peg position
	 * @param i2 row of ending peg position
	 * @param j2 col of ending peg position
	 */
	public void makeMove(int i1, int j1, int i2, int j2) {
		
		moves[numMoves][0] = i1;
		moves[numMoves][1] = j1;
		moves[numMoves][2] = i2;
		moves[numMoves][3] = j2;
		
		puzzle[i1][j1] = true;
		puzzle[i2][j2] = false;
		puzzle[ i1 + (i2-i1)/2 ][ j1 + (j2-j1)/2 ] = true;
		
		numMoves++;
		numFilled--;
		totalMoves++;
		
//		System.out.printf("made move i1:%d j1:%d i2:%d j2:%d filled:%d moves:%d\n", i1,j1,i2,j2, numFilled, numMoves);
	}
	
	
	/**
	 * Backtracks one move in the puzzle.  Puts the peg that made the move back to its starting positiong and
	 * replaces the peg that was jumped over and removed.
	 */
	public void backtrackMove() {
		if(minNumFilled > numFilled) {
			System.out.printf("backtracked move i1:%d j1:%d i2:%d j2:%d filled:%d moves:%d\n", moves[numMoves-1][0],
					moves[numMoves-1][1], moves[numMoves-1][2], moves[numMoves-1][3], numFilled, numMoves);
			drawBoard();
			minNumFilled = numFilled;
		}
		numMoves--;
		numFilled++;
		totalMoves++;
		
		if(totalMoves % 1000000==0) 
			System.out.println("Total moves " + totalMoves);
		
//		System.out.printf("backtracked move i1:%d j1:%d i2:%d j2:%d filled:%d moves:%d\n", moves[numMoves][0],
//				moves[numMoves][1], moves[numMoves][2], moves[numMoves][3], numFilled, numMoves);
		
		
		puzzle[ moves[numMoves][0] ][ moves[numMoves][1] ] = false;
		puzzle[ moves[numMoves][2] ][ moves[numMoves][3] ] = true;
		int i = moves[numMoves][0] + (moves[numMoves][2] - moves[numMoves][0])/2,
			j = moves[numMoves][1] + (moves[numMoves][3] - moves[numMoves][1])/2;
		puzzle[i][j] = false;
	}
	
	public void addPossibleMove(int[][] possibleMoves, int idx, int i, int j) {
		possibleMoves[idx][0] = i;
		possibleMoves[idx][1] = j;
	}
	
	
	/**
	 * For a particular position in the puzzle, returns 
	 * @param possibleMoves array of possible ending positions, given the passed starting positions
	 * @param i row of starting peg position 
	 * @param j col of starting peg position 
	 * @return the number of possible moves for the peg in the specified position
	 */
	public int getPossibleMoves(int[][] possibleMoves, int i, int j) {
		int numPossibleMoves = 0;
		
		if(j>1 && j-2<=i && !puzzle[i][j-1] && puzzle[i][j-2])
			addPossibleMove(possibleMoves, numPossibleMoves++, i, j-2);
		if(j<PUZZLE_SIZE-2 && j+2<=i && !puzzle[i][j+1] && puzzle[i][j+2])
			addPossibleMove(possibleMoves, numPossibleMoves++, i, j+2);
		if(i>1 && j<=i-2 && !puzzle[i-1][j] && puzzle[i-2][j])
			addPossibleMove(possibleMoves, numPossibleMoves++, i-2, j);
		if(i<PUZZLE_SIZE-2 && j<=i+2 && !puzzle[i+1][j] && puzzle[i+2][j])
			addPossibleMove(possibleMoves, numPossibleMoves++, i+2, j);
		if(i>1 && j>1 && j<=i && !puzzle[i-1][j-1] && puzzle[i-2][j-2])
			addPossibleMove(possibleMoves, numPossibleMoves++, i-2, j-2);
		if(i<PUZZLE_SIZE-2 && j<=i && !puzzle[i+1][j+1] && puzzle[i+2][j+2])
			addPossibleMove(possibleMoves, numPossibleMoves++, i+2, j+2);
		
		return numPossibleMoves;
	}
	
	/**
	 * Recursively solves the puzzle.  Iterates over positions, gets the moves available to the peg at that position,
	 * 
	 */
	public void solve() {
		if(numFilled==1)
			return;
		
		int [][] possibleMoves = new int[6][2];
		for(int i=0; i<PUZZLE_SIZE; i++) {
			for(int j=0; j<=i; j++) {
				if(!puzzle[i][j]) {
					int numPossibleMoves = getPossibleMoves(possibleMoves, i, j);
					for(int k=0; k<numPossibleMoves; k++) {
						makeMove(i, j, possibleMoves[k][0], possibleMoves[k][1]);
						solve();
						if(numFilled==1)
							return;
						backtrackMove();
						if(numFilled==maxFilled)
							System.out.printf("starting point i:%d j:%d\n", i, j);
					}
				}
			}
		}
	}
	
	
	/**
	 * draws the triangular board.  Pegs are X's and blank spaces are O's
	 */
	public void drawBoard() {
		for(int i=0; i< puzzle.length; i++) {
			for(int j=1; j<puzzle.length-i; j++)
				System.out.print(" ");
			for(int j=0; j<=i; j++) {
				System.out.print((puzzle[i][j] ? "O":"X") + (j<i ? " ":""));
			}
			System.out.println();
		}
	}
	
	
	/**
	 * prints the board at the start, and after every move in the solution
	 */
	public void printSolution() {
		if(numFilled==1) {
			initBoard();
			drawBoard();
			for(int i=0; i<numFilled-1; i++) {
				System.out.printf("move:%d  row:%d col:%d -> row:%d col:%d\n", i+1, moves[i][0]+1, moves[i][1]+1,
						moves[i][2]+1, moves[i][3]+1);
				puzzle[ moves[i][0] ][ moves[i][1] ] = true;
				puzzle[ moves[i][2] ][ moves[i][3] ] = false;
				puzzle[ moves[i][0] + (moves[i][2]-moves[i][0])/2 ][ moves[i][1] + (moves[i][3]-moves[i][1])/2 ] = true;
				drawBoard();
			}
		}
		else {
			System.out.println("No solution found");
		}
	}

	
	/**
	 * main method
	 * @param args command line args
	 */
	public static void main(String[] args) {	
		long time = System.currentTimeMillis();
		
		Puzzle puzzle = new Puzzle();
		puzzle.solve();
		puzzle.printSolution();
		
		System.out.printf("\nRun time %dms total moves:%d", System.currentTimeMillis()-time, puzzle.totalMoves);
	}

}
