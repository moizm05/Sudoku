package Sudoku;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

//Main Class
public class Sudoku
    // ICS 4U1
    // Sudoku Game
    // Written by: Moiz Mohammed & Noah Hsu
    // Written for: Mrs. Ganesan
    // Due date: January 25, 2023
    // This program will allow the user to play the classic game, Sudoku
{

    public JFrame frame, frame2; //Creates frames
    JLabel difficultyLabel = new JLabel ("Select difficulty:"); //Creates label
    boolean isStarted = false; //To check if game has started
    int prevBoard[] [] = new int [9] [9]; //Creates previous board that user will submit

    private final Timer timer = new Timer (1000, (ActionListener) null); //Timer
    //Initializing different JComponents
    final JLabel timerLabel = new JLabel ("Time Left:");
    final JButton startButton = new JButton ("Start");
    final JButton rulesButton = new JButton ("Rules");
    final JButton submitButton = new JButton ("Submit");
    final JTextField grid[] [] = new JTextField [9] [9];
    final JRadioButton easyButton = new JRadioButton ("Easy");
    final JRadioButton mediumButton = new JRadioButton ("Medium");
    final JRadioButton hardButton = new JRadioButton ("Hard");

    JLabel bestEasy, bestMedium, bestHard; //Labels for best times
    String easy, medium, hard; //Save best times to write into file

    ButtonGroup bg = new ButtonGroup ();

    int timeCount = -1;
    int counter = 0;
    int counter2 = 0;
    int stringChecker = 0; //Increases if they enter a string to stop the game
    String user = "";
    String pass = "";
    int difficulty = 1;


    //To convert counter into time.
    public String countToTime (int count)
    {
	String min = Integer.toString (count / 60);
	String sec = Integer.toString (count % 60);
	if (Integer.parseInt (min) == 0)
	    min = "0" + min;
	if (Integer.parseInt (sec) / 10 == 0)
	    sec = "0" + sec;
	return min + ":" + sec;
    }


    //Event handler when game is over.
    public void gameOver ()
    {
	try
	{
	    timerLabel.setVisible (false); //Gets rid of timer
	    timer.stop ();
	    prevBoard = SudokuSolver.solve (prevBoard); //Calls upon SudokuSolver class to check if the user got the answers correct or not
	    boolean isFine = true; //Used to check if user got everything correct
	    for (int i = 0 ; i < 9 ; i++)
	    {
		for (int j = 0 ; j < 9 ; j++)
		{
		    if (grid [i] [j].getText ().equals ("")) //In case user leaves anything blank they lost
		    {
			isFine = false;
			break;
		    }
		    else if (Integer.parseInt (grid [i] [j].getText ()) != prevBoard [i] [j]) //If user gets something wrong they lose
		    {
			isFine = false; //Set to false to indicate they lose
			break;
		    }
		}
	    }
	    if (isFine && isStarted) //If both are true they win
	    {
		JOptionPane.showMessageDialog (null, "You Won."); //Popup message saying they won
		try
		{
		    bestTime ();
		}
		catch (IOException e)
		{
		    throw new RuntimeException (e);
		}
	    }
	    else
	    {
		JOptionPane.showMessageDialog (null, "You Lose."); //Popup message saying they lost

	    }
	    isStarted = false;
	    startButton.setText ("Start");
	    rulesButton.setText ("Rules");

	    //Sets background colour for the boxes depending on if the user got the number in the box right or wrong
	    //If user gets the number right the box turns green, otherwise it turns red
	    for (int i = 0 ; i < 9 ; i++)
	    {
		for (int j = 0 ; j < 9 ; j++)
		{
		    grid [i] [j].setEditable (false);
		    if (grid [i] [j].getText ().equals (""))
		    {
			grid [i] [j].setBackground (Color.red);
		    }
		    else if (grid [i] [j].getText ().length () > 1)
		    {
			JOptionPane.showMessageDialog (null, "Please Only Enter One Number For Each Box Next Time You Play!"); //Pop up message in case they enter more than 1 digit in a box
			grid [i] [j].setBackground (Color.decode ("#ff3333"));
		    }
		    else if (Integer.parseInt (grid [i] [j].getText ()) == prevBoard [i] [j])
		    {
			grid [i] [j].setBackground (Color.decode ("#47d147"));
			grid [i] [j].setText (prevBoard [i] [j] + "");
			counter++;
		    }
		    else if (Integer.parseInt (grid [i] [j].getText ()) != prevBoard [i] [j])
		    {
			grid [i] [j].setBackground (Color.decode ("#ff3333"));
		    }
		}
	    }
	}
	catch (NumberFormatException exc)  //In case they enter a string
	{
	    JOptionPane.showMessageDialog (null, "                                                      ERROR!\nPlease Only Enter Numbers and One Number Per Box Next Time You Play!");
	    stringChecker++;

	    isStarted = false;
	    startButton.setText ("Start");
	}
	//Resetting everything for new game
	submitButton.setVisible (false);
	difficultyLabel.setText ("Select Difficulty:");
	easyButton.setVisible (true);
	mediumButton.setVisible (true);
	hardButton.setVisible (true);
	GameMusic gm = new GameMusic ("Sudoku\\GameTheme.wav");
	gm.stopGameMusic ();
	if (stringChecker == 0) //If they did not enter a string then it tells them how many they got correct
	{
	    JOptionPane.showMessageDialog (null, "You got " + counter + "/81 correct!");
	}
	rulesButton.setVisible (true);
    }


    //Write best times into file
    public void bestTime ()
	throws java.io.IOException
    {
	FileWriter fw = new FileWriter (user + ".txt");
	fw.write (user + "\r\n");
	fw.write (pass + "\r\n");
	if (difficulty == 0)
	{
	    fw.write (countToTime (counter2) + "\r\n");
	}
	else if (difficulty == 1)
	{
	    fw.write (countToTime (counter2) + "\r\n");
	}
	else if (difficulty == 2)
	{
	    fw.write (countToTime (counter2) + "\r\n");
	}
	fw.close ();
    }


    //Read in the best times
    public void readBestTime ()
	throws java.io.IOException
    {
	FileReader fr = new FileReader (user + ".txt");
	BufferedReader bfr = new BufferedReader (fr);

	user = bfr.readLine ();
	pass = bfr.readLine ();
	easy = bfr.readLine ();
	if (easy == null)
	{
	    bestEasy = new JLabel ("Easy: -");
	}
	else
	{
	    bestEasy = new JLabel ("Easy: " + easy);
	}
	medium = bfr.readLine ();
	if (medium == null)
	{
	    bestMedium = new JLabel ("Medium: -");
	}
	else
	{
	    bestMedium = new JLabel ("Medium: " + medium);
	}
	hard = bfr.readLine ();
	if (hard == null)
	{
	    bestHard = new JLabel ("Hard: -");
	}
	else
	{
	    bestHard = new JLabel ("Hard: " + hard);
	}
	//Adding the best time labels and times to the panel
	frame.getContentPane ().add (bestEasy);
	bestEasy.setBounds (435, 103, 127, 25);
	bestEasy.setVisible (true);
	bestEasy.setFont (new Font ("Calibri Light", Font.BOLD, 16));

	frame.getContentPane ().add (bestMedium);
	bestMedium.setBounds (435, 133, 127, 25);
	bestMedium.setVisible (true);
	bestMedium.setFont (new Font ("Calibri Light", Font.BOLD, 16));

	frame.getContentPane ().add (bestHard);
	bestHard.setBounds (435, 163, 127, 25);
	bestHard.setVisible (true);
	bestHard.setFont (new Font ("Calibri Light", Font.BOLD, 16));

	fr.close ();
    }


    public static void main (String[] args)
    {
	EventQueue.invokeLater (new Runnable ()  //Causes this to run first
	{
	    public void run ()  //Starts program
	    {
		Login lg = new Login (); //Calls upon log in screen
		lg.Login1 ();
	    }
	}


	);
    }


    //Starting timer
    public Sudoku ()
    {
	initialize ();
    }


    //Setting up timer
    private void initialize ()
    {
	timer.addActionListener (new ActionListener ()
	{
	    //Subtracting timer and calling CountToTime method to convert string to timer constantly
	    public void actionPerformed (ActionEvent arg0)
	    {
		timeCount--;
		timerLabel.setText ("Time Left- " + countToTime (timeCount));
		counter2++; //Used to save best time
		//In case time runs out
		if (timeCount == 0)
		{
		    gameOver ();
		}
	    }
	}


	);


	frame = new JFrame ();
	frame.setBounds (350, 150, 668, 438);
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.getContentPane ().setLayout (null);
	frame.getContentPane ().setBackground (Color.decode ("#7d9ccf"));

	//////////////////////////////////GRID START /////////////////////////////////////////////////////////////

	//h,w,hi,wi used to set the placement and size of the boxes on the grid
	int h = 12, w = 13, hi = 39, wi = 37;

	for (int i = 0 ; i < 9 ; i++)
	{
	    //Setting width
	    if (i % 3 == 0 && i != 0)
	    {
		w += 13;
	    }

	    //Setting height
	    for (int j = 0 ; j < 9 ; j++)
	    {
		if (j % 3 == 0 && j != 0)
		{
		    h += 11;
		}

		grid [i] [j] = new JTextField ();
		grid [i] [j].setColumns (10);
		grid [i] [j].setBounds (h, w, 38, 37);
		frame.getContentPane ().add (grid [i] [j]);
		h += hi;
	    }
	    h = 12;
	    w += wi;
	}

	//Setting font, background, etc. For boxes.
	for (int i = 0 ; i < 9 ; i++)
	{
	    for (int j = 0 ; j < 9 ; j++)
	    {
		grid [i] [j].setFont (new Font ("Tw Cen MT Condensed Extra Bold", Font.BOLD, 22));
		grid [i] [j].setHorizontalAlignment (SwingConstants.CENTER);
		grid [i] [j].setEditable (false);
		grid [i] [j].setBackground (Color.white);
	    }
	}


	//////////////////////////////////  GRID END  /////////////////////////////////////////////////////////////

	submitButton.setVisible (false);
	timerLabel.setVisible (false);
	submitButton.addActionListener (new ActionListener ()
	{
	    public void actionPerformed (ActionEvent arg0)
	    {
		gameOver (); //If submit button is pressed calls upon gameOver method
	    }
	}


	);

	//Sets the font, background, position, and adds to panel for each JComponent
	submitButton.setFont (new Font ("Calibri Light", Font.BOLD, 18));
	submitButton.setBounds (435, 270, 155, 37);
	submitButton.setBackground (Color.decode ("#ff4433"));
	frame.getContentPane ().add (submitButton);


	difficultyLabel.setFont (new Font ("Calibri Light", Font.BOLD, 16));
	difficultyLabel.setBounds (435, 70, 155, 24);
	frame.getContentPane ().add (difficultyLabel);

	timerLabel.setFont (new Font ("Segoe UI Black", Font.BOLD, 13));
	timerLabel.setFont (new Font ("Calibri Light", Font.BOLD, 16));
	timerLabel.setBounds (435, 13, 176, 16);
	frame.getContentPane ().add (timerLabel);

	easyButton.setFont (new Font ("Calibri Light", Font.BOLD, 13));
	easyButton.setBounds (435, 103, 127, 25);
	frame.getContentPane ().add (easyButton);
	easyButton.setBackground (Color.decode ("#7d9ccf"));

	mediumButton.setFont (new Font ("Calibri Light", Font.BOLD, 13));
	mediumButton.setBounds (435, 133, 127, 25);
	frame.getContentPane ().add (mediumButton);
	mediumButton.setBackground (Color.decode ("#7d9ccf"));

	hardButton.setFont (new Font ("Calibri Light", Font.BOLD, 13));
	hardButton.setBounds (435, 163, 127, 25);
	frame.getContentPane ().add (hardButton);
	hardButton.setBackground (Color.decode ("#7d9ccf"));

	//Adds radio buttons to button group
	bg.add (easyButton);
	bg.add (mediumButton);
	bg.add (hardButton);
	bg.setSelected (mediumButton.getModel (), true); //Sets medium as default radio button



	startButton.setBounds (435, 206, 155, 37);
	rulesButton.setBounds (435, 256, 155, 37);
	frame.getContentPane ().add (startButton);
	frame.getContentPane ().add (rulesButton);
	startButton.setFont (new Font ("Calibri Light", Font.BOLD, 18));
	rulesButton.setFont (new Font ("Calibri Light", Font.BOLD, 18));
	rulesButton.setBackground (Color.decode ("#ff4433"));
	startButton.setBackground (Color.decode ("#61b0ff"));

	//Opens up new frame with rules on how to play the game
	rulesButton.addActionListener (new ActionListener ()
	{
	    public void actionPerformed (ActionEvent arg0)
	    {
		frame2 = new JFrame ();
		frame2.setBounds (350, 150, 668, 438);
		frame2.getContentPane ().setLayout (null);
		frame2.getContentPane ().setBackground (Color.decode ("#7d9ccf"));
		frame2.setVisible (true);
		final JLabel ruleLabel = new JLabel ("Rules");
		final JLabel ruleLabel2 = new JLabel ("The goal of the game is to completely fill the grid with numbers.");
		final JLabel ruleLabel3 = new JLabel ("There are a set of restrictions you must follow:");
		final JLabel ruleLabel4 = new JLabel ("1. Number may not be repeated in a row.");
		final JLabel ruleLabel5 = new JLabel ("2. Numbers may not be repeated in a column.");
		final JLabel ruleLabel6 = new JLabel ("3. Numbers may not be repeated within a sub-square.");
		final JLabel ruleLabel7 = new JLabel ("4. Complete the grid within the time given.");
		final JLabel ruleLabel8 = new JLabel ("5. Have Fun!");

		ruleLabel.setFont (new Font ("Calibri Light", Font.BOLD, 50));
		ruleLabel2.setFont (new Font ("Calibri Light", Font.BOLD, 20));
		ruleLabel3.setFont (new Font ("Calibri Light", Font.BOLD, 20));
		ruleLabel4.setFont (new Font ("Calibri Light", Font.BOLD, 20));
		ruleLabel5.setFont (new Font ("Calibri Light", Font.BOLD, 20));
		ruleLabel6.setFont (new Font ("Calibri Light", Font.BOLD, 20));
		ruleLabel7.setFont (new Font ("Calibri Light", Font.BOLD, 20));
		ruleLabel8.setFont (new Font ("Calibri Light", Font.BOLD, 20));

		ruleLabel.setVisible (true);
		ruleLabel2.setVisible (true);
		ruleLabel3.setVisible (true);
		ruleLabel4.setVisible (true);
		ruleLabel5.setVisible (true);
		ruleLabel6.setVisible (true);
		ruleLabel7.setVisible (true);
		ruleLabel8.setVisible (true);

		frame2.getContentPane ().add (ruleLabel);
		frame2.getContentPane ().add (ruleLabel2);
		frame2.getContentPane ().add (ruleLabel3);
		frame2.getContentPane ().add (ruleLabel4);
		frame2.getContentPane ().add (ruleLabel5);
		frame2.getContentPane ().add (ruleLabel6);
		frame2.getContentPane ().add (ruleLabel7);
		frame2.getContentPane ().add (ruleLabel8);


		ruleLabel.setBounds (263, 0, 176, 100);
		ruleLabel2.setBounds (30, 45, 1400, 100);
		ruleLabel3.setBounds (30, 80, 1400, 100);
		ruleLabel4.setBounds (30, 115, 1400, 100);
		ruleLabel5.setBounds (30, 150, 1400, 100);
		ruleLabel6.setBounds (30, 185, 1400, 100);
		ruleLabel7.setBounds (30, 220, 1400, 100);
		ruleLabel8.setBounds (30, 255, 1400, 100);
	    }
	}


	);
	//Starts the game
	startButton.addActionListener (new ActionListener ()
	{
	    public void actionPerformed (ActionEvent arg0)
	    {
		rulesButton.setVisible (false);
		try
		{
		    frame2.setVisible (false);
		}
		catch (NullPointerException exc)
		{
		}

		if (isStarted) //When game has started
		{
		    isStarted = false; //If user presses give up the board fills up with the correct answer
		    prevBoard = SudokuSolver.solve (prevBoard);
		    //Box changes background depending on if user got it right or wrong
		    for (int i = 0 ; i < 9 ; i++)
		    {
			for (int j = 0 ; j < 9 ; j++)
			{
			    grid [i] [j].setEditable (false);
			    if (grid [i] [j].getText ().equals (""))
			    {
				grid [i] [j].setBackground (Color.red);
			    }
			    else if (Integer.parseInt (grid [i] [j].getText ()) == prevBoard [i] [j])
			    {
				grid [i] [j].setBackground (Color.decode ("#47d147"));
				grid [i] [j].setText (prevBoard [i] [j] + "");
				counter++;
			    }
			    else if (Integer.parseInt (grid [i] [j].getText ()) != prevBoard [i] [j])
			    {
				grid [i] [j].setBackground (Color.decode ("#ff3333"));
			    }

			}
		    }
		    //Resets for new game
		    difficultyLabel.setText ("Select Difficulty:");
		    easyButton.setVisible (true);
		    mediumButton.setVisible (true);
		    hardButton.setVisible (true);
		    startButton.setText ("Start");
		    rulesButton.setVisible (true);
		    timer.stop ();
		    timerLabel.setVisible (false);
		    submitButton.setVisible (false);
		    bestEasy.setVisible (false);
		    bestMedium.setVisible (false);
		    bestHard.setVisible (false);
		}
		//When game is running
		else
		{

		    GameMusic gm = new GameMusic ("Sudoku\\GameTheme.wav"); //Calls upon GameMusic class to play audio
		    gm.playGameMusic ();

		    //Sets radio buttons to not visible so they can be replaced with the best times
		    difficultyLabel.setText ("Best Times:");
		    easyButton.setVisible (false);
		    mediumButton.setVisible (false);
		    hardButton.setVisible (false);

		    try
		    {
			readBestTime (); //Calls upon readBestTime method
		    }
		    catch (IOException e)
		    {
			throw new RuntimeException (e);
		    }

		    //Sets difficulty and changes the length of the timer depending on the difficulty
		    if (easyButton.isSelected ())
		    {
			difficulty = 0;
		    }
		    else if (hardButton.isSelected ())
		    {
			difficulty = 2;
		    }
		    else
		    {
			difficulty = 1;
		    }

		    if (difficulty == 0)
		    {
			timeCount = 600;
		    }
		    else if (difficulty == 1)
		    {
			timeCount = 360;
		    }
		    else
		    {
			timeCount = 180;
		    }

		    //Creates 9x9 board
		    int board[] [] = new int [9] [9];
		    do
		    {
			board = SudokuGenerator.generate (difficulty); //Generates numbers from SudokuGenerator class
		    }
		    while (board [0] [0] == -1); //Continues till there is a number in each mini grid
		    for (int i = 0 ; i < 9 ; i++)
		    {
			//replaces previous board with new board
			for (int j = 0 ; j < 9 ; j++)
			{
			    prevBoard [i] [j] = board [i] [j];
			    grid [i] [j].setBackground (Color.white);
			    counter = 0; //Resets how many user got right
			}
		    }
		    //Sets the digits generated into the boxes
		    for (int i = 0 ; i < 9 ; i++)
		    {
			for (int j = 0 ; j < 9 ; j++)
			{
			    if (board [i] [j] != 0)
			    {
				grid [i] [j].setText (Integer.toString (board [i] [j]));
			    }
			    else
			    {
				grid [i] [j].setText ("");
				grid [i] [j].setEditable (true);
			    }
			}
		    }
		    //Game has started
		    submitButton.setVisible (true);
		    startButton.setText ("Give up!");
		    timerLabel.setVisible (true);
		    timer.start ();
		    isStarted = true;
		}
	    }
	}


	);
    }
}
