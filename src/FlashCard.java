
import java.io.Serializable;

/**
 * Represents a text based flashcard.
 *
 * This class is immutable.
 * @invariant front != null && back != null
 */
public class FlashCard implements Serializable
{
  /**
   * Constructor setting up a flashcard object.
   *
   * @param challenge front of the card
   * @param response back of the card
   * @precondition challenge != null && response != null
   */
  FlashCard(String challenge, String response)
  {
    assert challenge != null && response != null;
   
    front = challenge;
    back = response;
  }

  /** Returns the front side. */
  public String getChallenge() { return front; }

  /** Returns the back side. */
  public String getResponse() { return back; }

  /**
   * Produces a textual representation of this flashcard.
   *
   * @return a string containing two lines, one for front and one for back.
   */
  public String toString()
  {
    return front + '\n' + back + '\n';
  }

  private final String front;
  private final String back;
}
