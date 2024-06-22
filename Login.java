package Sudoku;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

//Class for login screen
public class Login extends JFrame implements ActionListener
{
    //Creates JComponents
    JFrame frame, frame2;
    JPanel contentPane, center, east, bottom, north, create2;
    JLabel user, pass, title, username, password, space;
    JTextField textField, textField3, textField4;
    JPasswordField passField;
    JButton login, create, submit;
    int counter = 0;
    String userfw, userfr, passfr, passfw, input2;
    String input = "";



    //Main Login screen
    public void Login1 ()
    {
	//Setting up the login frame
	this.setTitle ("Login");

	this.setBounds (420, 100, 500, 500);

	contentPane = (JPanel) this.getContentPane ();
	contentPane.setLayout (new BorderLayout ());

	user = new JLabel ("Username: ");
	user.setFont (new Font ("Serif", Font.BOLD, 15));
	user.setForeground (Color.white);
	textField = new JTextField (null, 5);

	pass = new JLabel ("Password: ");
	pass.setFont (new Font ("Serif", Font.BOLD, 15));
	pass.setForeground (Color.white);
	passField = new JPasswordField (null, 10);

	title = new JLabel (new ImageIcon ("Sudoku\\Sudoku Title.png")); //Importing image from folder

	space = new JLabel ("");

	east = new JPanel ();
	east.setLayout ((new GridLayout (18, 2)));
	center = new JPanel ();
	center.setLayout ((new GridLayout (10, 10)));
	bottom = new JPanel ();
	north = new JPanel ();



	login = new JButton ("Login");
	login.setBackground (Color.decode ("#61b0ff"));
	login.setForeground (Color.black);
	login.setFont (new Font ("Serif", Font.BOLD, 15));

	create = new JButton ("Create Account");
	create.setBackground (Color.decode ("#ff4433"));
	create.setForeground (Color.black);
	create.setFont (new Font ("Serif", Font.BOLD, 15));

	//Adding JComponents to panels
	north.add (title);
	center.add (user);
	center.add (textField);

	center.add (pass);
	center.add (passField);

	center.add (space);

	center.add (login);
	center.add (create);

	//Changing the background for the panels
	north.setBackground (Color.decode ("#505095"));
	center.setBackground (Color.decode ("#505095"));

	contentPane.add (north, BorderLayout.NORTH);
	contentPane.add (center, BorderLayout.CENTER);
	contentPane.add (east, BorderLayout.EAST);
	contentPane.add (bottom, BorderLayout.SOUTH);


	login.addActionListener (this);
	create.addActionListener (this);

	this.show ();

    }


    //Method to create an account, Opens up a new frame and disposes of the main login frame
    public void CreateAccount ()
    {
	//Setting up new frame to create account
	frame2 = new JFrame ("Create Account");

	frame2.setBounds (420, 100, 500, 500);

	create2 = (JPanel) frame2.getContentPane ();
	contentPane.setLayout (new BorderLayout ());

	username = new JLabel ("Create Username: ");
	textField3 = new JTextField (null, 5);
	username.setFont (new Font ("Serif", Font.BOLD, 15));
	username.setForeground (Color.white);

	password = new JLabel ("Create Password: ");
	textField4 = new JTextField (null, 10);
	password.setFont (new Font ("Serif", Font.BOLD, 15));
	password.setForeground (Color.white);

	title = new JLabel (new ImageIcon ("Sudoku\\Sudoku Title.png"));

	space = new JLabel ("");

	east = new JPanel ();
	east.setLayout ((new GridLayout (18, 2)));
	center = new JPanel ();
	center.setLayout ((new GridLayout (10, 10)));
	bottom = new JPanel ();
	north = new JPanel ();

	submit = new JButton ("Create Account");
	submit.setBackground (Color.decode ("#ff4433"));
	submit.setForeground (Color.black);
	submit.setFont (new Font ("Serif", Font.BOLD, 15));

	//Adding JComponents to panels
	north.add (title);
	center.add (username);
	center.add (textField3);

	center.add (password);
	center.add (textField4);

	center.add (space);

	center.add (submit);

	//Changing the background for the panels
	north.setBackground (Color.decode ("#505095"));
	center.setBackground (Color.decode ("#505095"));

	create2.add (north, BorderLayout.NORTH);
	create2.add (center, BorderLayout.CENTER);
	create2.add (east, BorderLayout.EAST);
	create2.add (bottom, BorderLayout.SOUTH);

	submit.addActionListener (this);

	frame2.setVisible (true);

    }

    //Writes the information entered in CreateAccount to a file
    public void NewAccount ()
	throws java.io.IOException
    {

	userfw = textField3.getText ();
	passfw = textField4.getText ();


	FileWriter fw = new FileWriter (userfw + ".txt");
	fw.write (userfw + "\r\n");
	fw.write (passfw + "\r\n");
	fw.close ();
	
	//Opens up Sudoku Game Screen once the user has successfully made an account
	Sudoku window = new Sudoku ();
	window.frame.setVisible (true);
	window.user = userfw;
	window.pass = passfw;
    }

    //Used to check if the login entered by user in the main login frame matches the login information in the file
    public void Check ()
	throws java.io.IOException
    {
	userfr = textField.getText ();
	passfr = passField.getText ();


	try
	{
	    //Reading from file
	    FileReader fr = new FileReader (userfr + ".txt");
	    BufferedReader bfr = new BufferedReader (fr);
	    input = bfr.readLine ();
	    input2 = bfr.readLine ();
	    fr.close ();



	    //Opens up Sudoku Game Screen once the user has successfully logged in
	    if (input.equals (userfr) && input2.equals (passfr))
	    {
		this.dispose ();
		Sudoku window = new Sudoku ();
		window.frame.setVisible (true);
		window.user = input;
		window.pass = input2;
	    }
	    //Gives a popup message if users login information was not correct
	    else
	    {
		JOptionPane.showMessageDialog (null, "Your Username and Password do not Match!");
		textField.setText (null);
		passField.setText (null);
	    }
	}
	//Gives a popup message if users login information was not correct
	catch (FileNotFoundException exc)
	{
	    JOptionPane.showMessageDialog (null, "Your Username and Password do not Match!");
	    textField.setText (null);
	    passField.setText (null);
	}
    }



    //Actions for all buttons
    public void actionPerformed (ActionEvent evt)
    {
	Object command = evt.getSource ();
	if (command == create) //When user presses the button to create an account it calls upon CreateAccount method
	{
	    CreateAccount ();
	    this.dispose ();
	}
	else if (command == submit) //When user presses the submit button to create an account it calls upon NewAccount method
	{
	    try
	    {
		NewAccount ();
		frame2.dispose ();
	    }
	    catch (IOException e)
	    {
		throw new RuntimeException (e);
	    }

	}
	else if (command == login) //When user presses the button to login it calls upon Check method to see if their login information is correct
	{
	    try
	    {
		Check ();
	    }
	    catch (IOException e)
	    {
		throw new RuntimeException (e);
	    }

	}
	repaint ();
    }
}
