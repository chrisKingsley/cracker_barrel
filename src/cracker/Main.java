package cracker;

public class Main {
	
	private static int puzzleSize = 5;
	
	
	public static void printPuzzle(boolean[][] puzzle) {
		for(int i=0; i< puzzle.length; i++) {
			for(int j=0; j< puzzle.length; j++) {
				if(j <= i)
					System.out.print(puzzle[i][j] ? "X":"O");
			}
			System.out.println();
		}
	}
	
	public static short[][] getPossibleMoves(boolean[][] puzzle) {
		int maxNumMoves = puzzleSize * (puzzleSize + 1) / 2;
		short[][] possibleMoves = new short[maxNumMoves][3];
		
		
		return possibleMoves;
	}
	
	public static void solvePuzzle(boolean[][] puzzle, short[][] moves) {
		short[][] possibleMoves = getPossibleMoves(puzzle);
	}

	public static void main(String[] args) {
		if(args.length > 0)
			puzzleSize = Integer.parseInt(args[0]);
		
		boolean[][] puzzle = new boolean[ puzzleSize ][ puzzleSize ];
		puzzle[0][0] = true;
		
		int maxNumMoves = puzzleSize * (puzzleSize + 1) / 2;
		short[][] moves = new short[maxNumMoves][3];

		solvePuzzle(puzzle, moves);

	}

}
