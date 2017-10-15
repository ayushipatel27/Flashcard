

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;



public class GUI {
	
	
			  /** imports cards from a given file. */
			  public static int importCards(FlashCardApp app, String filename) throws FileNotFoundException
			  {
			    int        cnt = 0;

			    try (Scanner inp = new Scanner(new File(filename));)
			    {
			      while (inp.hasNextLine())
			      {
			        String challenge = inp.nextLine();

			        if (!inp.hasNextLine()) break;
			        String response  = inp.nextLine();

			        app.create(challenge, response);
			        ++cnt;

			        // skip empty line (except for end of file)
			        if (inp.hasNextLine()) inp.nextLine();
			      }
			    }

			    return cnt;
			  }

			  /** Prints an error message. */
			  static void error(String msg)
			  {
			    System.out.println("Err: " + msg);
			  }
	



			  /** tokenizes user command. */
			  static String nextToken(StringTokenizer tok) throws InvalidCommandException
			  {
			    if (!tok.hasMoreTokens()) throw new InvalidCommandException();

			    return tok.nextToken();
			  }

			  /** checks that there remains no user input unhandled. */
			  static void endOfCommand(StringTokenizer tok) throws InvalidCommandException
			  {
			    if (tok.hasMoreTokens()) throw new InvalidCommandException();
			  }

			  /**
			   * converts num to a valid box number.
			   *
			   * @param num box number as string
			   * @param max highest valid box number
			   * @return a valid box number (value == num)
			   * @throws InvalidBoxNumberException if num was out of bounds.
			   * @throws InvalidCommandException if command was ill formed.
			   */
			  static int getBoxNumber(String num, int max)
			    throws InvalidCommandException, InvalidBoxNumberException
			  {
			    try
			    {
			      int val = Integer.parseInt(num);

			      if (val <= 0 || val > max)
			        throw new InvalidBoxNumberException();

			      return val;
			    }
			    catch (NumberFormatException ex)
			    {
			      throw new InvalidCommandException();
			    }
			  }


			  /** handles user input and calls functions as needed. 
			 * @throws ClassNotFoundException */
			  static void handleCommand(FlashCardApp a, String s)
			    throws InvalidCommandException, InvalidBoxNumberException, ClassNotFoundException
			  {
				  app = a;
			    StringTokenizer tok = new StringTokenizer(s, " ", false);
			    String cmd = nextToken(tok);
				 if (cmd.equals("import"))
				    {
				      String filename = nextToken(tok);

				      endOfCommand(tok);

				      try
				      {
				        int cnt = importCards(app, filename);

				        System.out.println(cnt + " cards imported.");
				      }
				      catch (FileNotFoundException ex)
				      {
				        error("File " + filename + " not found");
				      }
				    }
			    
			    else if (cmd.equals("leitner-gui"))
			    {
			      endOfCommand(tok);
			      new FlashcardGUI(app.leitner());
			    }
			    else if (cmd.equals("drill-gui"))
			    {
			      String what = nextToken(tok);
			      endOfCommand(tok);
			      new FlashcardGUI(app.drill(getBoxNumber(what, app.lastBoxNumber())));
			    }
			    else if (cmd.equals("simple-gui"))
			    {
			      String what = nextToken(tok);
			      endOfCommand(tok);
			      new FlashcardGUI(app.simple(getBoxNumber(what, app.lastBoxNumber())));
			    }
			    else if (cmd.equals("save"))
			    {
			      endOfCommand(tok);
			      try {
						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.txt"));
						out.writeObject(app);
					} catch (IOException i) {
						i.printStackTrace();
					}
			    }
			    else if (cmd.equals("load"))
			    {
			      endOfCommand(tok);
			      FlashCardApp savedstuff = app;
					try {
						ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.txt"));
						savedstuff = (FlashCardApp) in.readObject();
						app = savedstuff;
						} catch (IOException i) {
						i.printStackTrace();
					}
			    }
			    else if (cmd.equals("!exit"))
			    {
			      endOfCommand(tok);
			      System.exit(0);
			    }
			    else
			    {
			      throw new InvalidCommandException();
			    }
			    }

			  /** Main function and main user input loop. 
			 * @throws InvalidBoxNumberException 
			 * @throws InvalidCommandException 
			 * @throws ClassNotFoundException */
			  public static void main(String[] args) throws InvalidCommandException, InvalidBoxNumberException, ClassNotFoundException
			  {
			    	FlashCardApp myApp = new FlashCardApp(); // create a new FlashcardApp boxes that takes in boxes 
			    	app = myApp;
			    	Scanner inp = new Scanner(System.in);
					System.out.println("What is your command?");
					while (inp.hasNextLine())
				    {
				        handleCommand(app, inp.nextLine());
				     }
					
				}
private static FlashCardApp app;
}