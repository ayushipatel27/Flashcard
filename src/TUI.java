
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;

import java.io.FileNotFoundException;
import java.io.File;


/** Thrown when a user input cannot be processed. */
class InvalidCommandException extends Exception
{
  static final long serialVersionUID = -5607746478748725987L;
}

/** Thrown when an entered box number is out of bounds. */
class InvalidBoxNumberException extends Exception
{
  static final long serialVersionUID = -7162827615380217362L;
}


/**
 * Class implementing a text based user interface.
 */
public class TUI
{
  /** Prints all cards on the console. */
  static void print(ArrayList<FlashCard> lst)
  {
    for (FlashCard card : lst)
    {
      System.out.println( card.toString() );
    }
  }

  /** Prints an error message. */
  static void error(String msg)
  {
    System.out.println("Err: " + msg);
  }

  /** UI for studying with the Leitner method. */
  static void study(StudyMethod studymethod)
  {
    Scanner inp = new Scanner(System.in);

    while (studymethod.hasMoreCards())
    {
      String  answer;
      boolean flip = false;

      studymethod.pickCard();

      do
      {
        System.out.println(" ?" + (flip ? studymethod.getAnswer() : studymethod.getQuestion()) );

        System.out.print(" >");
        answer = inp.nextLine();
        flip = !flip;
      } while ("%".equals(answer));

      if ("!exit".equals(answer)) break;

      boolean validResponse = studymethod.testAnswer(answer);

      studymethod.moveCard(validResponse);
      if (!validResponse) System.out.println(":(" + studymethod.getAnswer() + '\n');
    }
  }

  /** imports cards from a given file. */
  static int importCards(FlashCardApp app, String filename) throws FileNotFoundException
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


  /** handles user input and calls functions as needed. */
  static void handleCommand(FlashCardApp app, String s)
    throws InvalidCommandException, InvalidBoxNumberException
  {
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
        error("File " + filename + "not found");
      }
    }
    else if (cmd.equals("leitner"))
    {
      endOfCommand(tok);
      study(app.leitner());
    }
    else if (cmd.equals("drill"))
    {
      String what = nextToken(tok);
      endOfCommand(tok);
      study(app.drill(getBoxNumber(what, app.lastBoxNumber())));
    }
    else if (cmd.equals("simple"))
    {
      String what = nextToken(tok);
      endOfCommand(tok);
      study(app.simple(getBoxNumber(what, app.lastBoxNumber())));
    }
    else if (cmd.equals("list-all"))
    {
      endOfCommand(tok);
      print(app.getAllCards());
    }
    else if (cmd.equals("list-box"))
    {
      String  what = nextToken(tok);
      endOfCommand(tok);
      int     num = getBoxNumber(what, app.lastBoxNumber());

      print(app.getCards(num));
    }
    else if (cmd.equals("list-with"))
    {
      String what = nextToken(tok);

      endOfCommand(tok);
      print(app.getCardsWith(what));
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

  /** Main function and main user input loop. */
  public static void main(String[] args)
  {
    FlashCardApp app = new FlashCardApp();
    Scanner      inp = new Scanner(System.in);

    System.out.print(":");
    while (inp.hasNextLine())
    {
      try
      {
        handleCommand(app, inp.nextLine());
      }
      catch (InvalidBoxNumberException e)
      {
        error("invalid box number");
      }
      catch (InvalidCommandException e)
      {
        error("invalid command");
      }

      System.out.print(":");
    }
  }
}
