
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class implementing a flashcard application
 *
 * @invariant boxes != null && boxes.size() > 0
 */
public class FlashCardApp implements Serializable
{
  /** Number of boxes. */
  private final int MAX_BOXES = 5;

  /** Constructs a new flashcard app object and initializes the boxes. */
  public FlashCardApp()
  {
    for (int i = 1; i <= MAX_BOXES; ++i)
    {
      boxes.add(new Box(i));
    }
  }

  /** Returns an object according to the Leitner study method. */
  public Leitner leitner()
  {
    return new Leitner(boxes);
  }

  /** Returns an object according to the Drill study method. */
  public Drill drill(int num)
  {
    return new Drill(boxes, num - 1);
  }

  /** Returns an object according to the Leitner study method. */
  public Simple simple(int num)
  {
    return new Simple(boxes, num);
  }


  /**
   * Returns an arraylist containing all flashcards in the system.
   *
   * @return ArrayList<FlashCard>
   */
  public ArrayList<FlashCard> getAllCards()
  {
    ArrayList<FlashCard> allCards = new ArrayList<FlashCard>();

    for (Box box : boxes)
    {
      allCards.addAll(box.getAllCards());
    }

    return allCards;
  }

  /**
   * Returns an arraylist containing all flashcards that contain a pattern.
   *
   * @param pattern search pattern for texts on flashcards.
   * @return ArrayList<FlashCard> where all elements contain pattern in either
   *         front or back of the card.
   *
   * @precondition pattern != null
   */
  public ArrayList<FlashCard> getCardsWith(String pattern)
  {
    assert pattern != null;

    ArrayList<FlashCard> foundCards = new ArrayList<FlashCard>();

    for (Box box : boxes)
    {
      ArrayList<FlashCard> cards = box.getAllCards();

      for (FlashCard card : cards)
      {
        boolean inclCard = (  card.getChallenge().indexOf(pattern) >= 0
                           || card.getResponse().indexOf(pattern) >= 0
                           );

        if (inclCard) foundCards.add(card);
      }
    }

    return foundCards;
  }

  /**
   * Returns an arraylist that contains all flash cards in a given box.
   *
   * @param boxid Leitner box id.
   * @return ArrayList<FlashCard> with all cards in box boxid.
   *
   * @precondition 0 < boxid <= number of boxes in the app
   */
  public ArrayList<FlashCard> getCards(int boxid)
  {
    assert boxid > 0 && boxid <= boxes.size();

    return boxes.get(boxid - 1).getAllCards();
  }


  /**
   * Creates a new flashcard and adds it to the first box.
   *
   * @precondition challenge != null && response != null
   */
  public void create(String challenge, String response)
  {
    assert challenge != null && response != null;

    boxes.get(0).add(new FlashCard(challenge, response));
  }

  public int lastBoxNumber()
  {
    return MAX_BOXES;
  }

  public String toString(){
	  return "" + boxes;
  }
  private final ArrayList<Box> boxes = new ArrayList<Box>(); /// List of boxes
}
