package Sudoku;
import sun.audio.*;
import java.io.*;

//Class for music while playing the game
public class GameMusic
{
    //Creates local variables
    public InputStream SudokuMusic;
    public AudioStream audio;
    
    //Gets music from file entered in the main
    public GameMusic (String filepath)
    {
	try
	{
	    //Makes the file into an audio
	    SudokuMusic = new FileInputStream (new File (filepath));
	    audio = new AudioStream (SudokuMusic);
	}
	catch (Exception e)
	{
	    System.out.println ("Error");
	}
    }

    //Plays the music
    public void playGameMusic ()
    {
	AudioPlayer.player.start (audio);
    }

    //Stops the music
    public void stopGameMusic ()
    {
	AudioPlayer.player.stop (audio);
    }
}


