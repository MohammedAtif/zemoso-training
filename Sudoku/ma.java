class ma{
	


	public static boolean isRectangleLegal(int[][] board, int x1, int x2, int y1, int y2, String errormsg) {
boolean[] isPresent = {false, false, false, false, false, false, false, false, false, false};
  
for (int x=x1; x<=x2; x++) {
    for (int y=y1; y<=y2; y++) {
if (board[x][y] > 0) {
    if (isPresent[board[x][y]]) {
//System.out.println(errormsg + ": multiple " + board[x][y] + "s");
return false;
    }
    isPresent[board[x][y]] = true;
}
    }
}
return true;
    }
  
    public static boolean isLegal(int[][] board) {
// Check the nine blocks.
if (!isRectangleLegal(board, 0, 2, 0, 2, "Block 1")) return false;
if (!isRectangleLegal(board, 3, 5, 0, 2, "Block 2")) return false;
if (!isRectangleLegal(board, 6, 8, 0, 2, "Block 3")) return false;
if (!isRectangleLegal(board, 0, 2, 3, 5, "Block 4")) return false;
if (!isRectangleLegal(board, 3, 5, 3, 5, "Block 5")) return false;
if (!isRectangleLegal(board, 6, 8, 3, 5, "Block 6")) return false;
if (!isRectangleLegal(board, 0, 2, 6, 8, "Block 7")) return false;
if (!isRectangleLegal(board, 3, 5, 6, 8, "Block 8")) return false;
if (!isRectangleLegal(board, 6, 8, 6, 8, "Block 9")) return false;
  
// check the nine columns
if (!isRectangleLegal(board, 0, 0, 0, 8, "Column 0")) return false;
if (!isRectangleLegal(board, 1, 1, 0, 8, "Column 1")) return false;
if (!isRectangleLegal(board, 2, 2, 0, 8, "Column 2")) return false;
if (!isRectangleLegal(board, 3, 3, 0, 8, "Column 3")) return false;
if (!isRectangleLegal(board, 4, 4, 0, 8, "Column 4")) return false;
if (!isRectangleLegal(board, 5, 5, 0, 8, "Column 5")) return false;
if (!isRectangleLegal(board, 6, 6, 0, 8, "Column 6")) return false;
if (!isRectangleLegal(board, 7, 7, 0, 8, "Column 7")) return false;
if (!isRectangleLegal(board, 8, 8, 0, 8, "Column 8")) return false;
  
// check the nine rows
if (!isRectangleLegal(board, 0, 8, 0, 0, "Row 0")) return false;
if (!isRectangleLegal(board, 0, 8, 1, 1, "Row 1")) return false;
if (!isRectangleLegal(board, 0, 8, 2, 2, "Row 2")) return false;
if (!isRectangleLegal(board, 0, 8, 3, 3, "Row 3")) return false;
if (!isRectangleLegal(board, 0, 8, 4, 4, "Row 4")) return false;
if (!isRectangleLegal(board, 0, 8, 5, 5, "Row 5")) return false;
if (!isRectangleLegal(board, 0, 8, 6, 6, "Row 6")) return false;
if (!isRectangleLegal(board, 0, 8, 7, 7, "Row 7")) return false;
if (!isRectangleLegal(board, 0, 8, 8, 8, "Row 8")) return false;
return true;
    }
  
    public static boolean solve(int[][] board) {
 
// Find a position that's still empty.
for (int x=0; x<9; x++) {
    for (int y=0; y<9; y++) {
if (board[x][y] == 0) {
  
    // Try each possibile value in this space
    // and see if the rest of the puzzle can be filled in.
    for (board[x][y]=1; board[x][y]<=9; board[x][y]++) {
if (isLegal(board) && solve(board)) {
    return true;
}
    }
  
    // There is no value that we can put in the first
    // empty space that was found, so the puzzle is
    // unsolvable given the values put in up to this
    // point.
    board[x][y] = 0;
    return false;
}
    }
}
  
// There were no empty spaces to fill in, so the
// puzzle must be solved.
return true;
    }
  
    public static void printBoard(int[][] board) {
        for (int x=0; x<9; x++) {
    System.out.println(x%3==0 ? "+-----+-----+-----+" : "|     |     |     |");
    for (int y=0; y<9; y++)
System.out.print((y%3==0 ? "|" : " ") + board[x][y]); 
    System.out.println("|");
}
System.out.println("+-----+-----+-----+");
    }










	public static void main(String[] args){
		int[][] board;
		int t=50;
		int nextval=0;
	while(t>0)
	{
	readFile r= new readFile();
    //open file
	r.openFile();
    //reading each sodoku to board... Grid 01 takes two next()
	board=r.readfile(nextval*11);
		/*	for(int i = 0; i<9; i++)
		{
		    for(int j = 0; j<9; j++)
		    {
		        System.out.print(array[i][j]);
		    }
		    System.out.println();
		}*/

    //Passing the read board to solve method
	if (solve(board)){
    //Printing the solved board
    	printBoard(board);
    	System.out.println();
    }
	else
    	System.out.println("no solution");


	r.closeFile();
	t--;
	nextval++;
	//System.out.println();
	}
	
}
}