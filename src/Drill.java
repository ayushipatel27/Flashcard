
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class implementing the Drill system for a single flashcard box or pool.
 *
 * @invariant pool != null
 */
public class Drill extends AbstractStudyMethod implements Serializable
{
  /**
   * Constructs a new Drill object.
   *
   * @precondition box != null
   */
  public Drill(ArrayList<Box> boxes, int num)
  {
    assert boxes != null;
    Box box = boxes.get(num);
    this.pool = initPool(box);
  }

  /**
   * creates a new pool.
   *
   * Note, this method is provided so that a Simple method can modify
   * the behavior.
   * TODO: consider introducing a new abstract class that factors commonalities
   *       between Drill and Simple.
   *
   * @param box the box from where the cards are taken.
   */
  protected Box initPool(Box box)
  {
    Box newbox = new Box(99);

    for (FlashCard card : box.getAllCards())
    {
      newbox.add(card);
    }

    return newbox;
  }

  /**
   * Picks a random card from the pool.
   *
   * @precondition there must be at least one card.
   */
  @Override
  protected FlashCard pickRandomCard()
  {
    // All cards in the pool are equally likely to be picked,
    //   thus we generate another pseudo random number.
    return pool.get(genRandomInt(pool.size()));
  }

  /**
   * Moves card to the target box, depending on whether succ indicates
   * a correct/incorrect response.
   *
   * @param correct, indicates whether the last response was correct.
   */
  public void moveCard(boolean correct)
  {
    if (correct) pool.remove(currentCard());
  }

  /**
   * If the last card in the pool has been answered correctly and removed,
   * then the drill study session will end.
   * Otherwise, the method returns true.
   */
  public boolean hasMoreCards()
  {
	  return (pool.size() != 0);
  }

  /**
   * gets the pool box.
   */
  protected Box getPool()
  {
    return pool;
  }
  public ArrayList<Box> getBoxes() {
	  return boxes;
  }
  
  public String toString(){
		return "";
	}
/** box that contains all the cards. */
  private Box                  pool;
  private ArrayList<Box> 	   boxes;
}
