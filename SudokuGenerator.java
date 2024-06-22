package Sudoku;

import java.util.Random;

//Class to generate the permutation for the grid
public class SudokuGenerator {
    private static int board[][] = new int[9][9];

    //To make a simple solved puzzle for grid.
    public static void generateGrid(){
	board = SudokuSolver.solve(board);
    }

    //To get a new permutation of seed puzzle.
    public static void generatePermutation(int select){
	int num, num1, min = 0, max = 2;
	Random r = new Random(); //Used to create random numbers
	for(int i = 0; i < 3; i++){
	    num = r.nextInt(max - min + 1) + min; //Checks the next number
	    do{
		num1 = r.nextInt(max - min + 1) + min;
	    }while(num1 == num);
	    max += 3; min += 3;
	    if(select == 0) permutationCol(num, num1);
	    else permutationRow(num, num1);
	}
    }

    //To get permutation with 3x3 columns.
    public static void permutationCol(int num, int num1){
	for(int i = 0; i < 9; i++){//For Loop to set start values in grid
	    int temp = board[i][num];
	    board[i][num] = board[i][num1];
	    board[i][num1] = temp;
	}
    }

    //To get permutation with 3x3 rows.//For Loop to set start values in grid
    public static void permutationRow(int num, int num1){
	for(int i = 0; i < 9; i++){
	    int temp = board[num][i];
	    board[num][i] = board[num1][i];
	    board[num1][i] = temp;
	}
    }

    //To get permutation with 3x9 rows.//For Loop to set start values in grid
    public static void exchangeRows(int num, int num1){
	for(int i = 0; i < 3; i++)
	{
	    for(int j = 0; j < 9; j++)
	    {
		int temp = board[num][j];
		board[num][j] = board[num1][j];
		board[num1][j] = temp;
	    }
	    num++;
	    num1++;
	}
    }

    //To get permutation with 9x3 columns.//For Loop to set start values in grid
    public static void exchangeCols(int num, int num1){
	for(int i = 0; i < 3; i++)
	{
	    for(int j = 0; j < 9; j++)
	    {
		int temp = board[j][num];
		board[j][num] = board[j][num1];
		board[j][num1] = temp;
	    }
	    num++;
	    num1++;
	}
    }

    //To transpose the board.
    public static void transpose(){
	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		int temp = board[i][j];
		board[i][j] = board[j][i];
		board[j][i] = temp;
	    }
	}
    }

    //To clear space at (num, num1)
    public static int clearNum(int num, int num1)
    {
	int count = 9;
	for(int i = 1; i <= 9; i++){
	    if(!SudokuSolver.isMoveValid(num, num1, i, board)) count--;
	    if(count == 1){
		board[num][num1] = 0;
		return 1;
	    }
	}
	return 0;
    }
    
    //Randomizes the grid based on the difficulty of the program
    public static int[][] generate(int difficulty){
	Random r = new Random();

	int maxCount = 43;

	//Reset the old puzzle.
	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		board[i][j] = 0;
	    }
	}

	int count = 0, num, num1;
	int choice[] = {0, 3, 6};

	generateGrid();//Makes new grid for main
	if(r.nextInt(2) == 0) transpose();
	generatePermutation(1);//Generates new seed for game
	generatePermutation(0);//Generates new seed for game


	for(int i = 0; i < 2; i++){
	    num = choice[r.nextInt(3)];
	    do{
		num1 = choice[r.nextInt(3)];
	    }while(num == num1);

	    if(i % 2 == 0) exchangeRows(num, num1);
	    else exchangeCols(num, num1);
	}
	while(count < maxCount){
	    num = r.nextInt(9);
	    num1 = r.nextInt(9);
	    count += clearNum(num, num1);
	}
	return board;//Returns new board to main
    }
}
