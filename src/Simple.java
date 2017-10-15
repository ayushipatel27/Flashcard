
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class implementing the Simple system.
 *
 * @invariant pool != null
 */
public class Simple extends Drill implements Serializable
{
  public Simple(ArrayList<Box> theBoxes, int num)
  {
    super(theBoxes, num-1);

    int rightBoxNum = Math.min(num + 1, theBoxes.size());
    int wrongBoxNum = Math.max(num - 1, 1);

    rightBox = theBoxes.get(rightBoxNum - 1);
    wrongBox = theBoxes.get(wrongBoxNum - 1);
  }

  /**
   * The chosen box = our pool.
   *
   * @param box a box from where the cards are drawn.
   */
  protected Box initPool(Box box)
  {
    return box;
  }

  /**
   * Returns the box number where the card will be placed
   *
   * @param correct indicates whether response was correct.
   *
   * @precondition box is a valid box number
   */
  protected Box getTargetBox(boolean correct)
  {
    return correct ? rightBox : wrongBox;
  }


  /**
   * Moves card to the target box, depending on whether succ indicates
   * a correct/incorrect response.
   *
   * @param correct, indicates whether the last response was correct.
   */
  public void moveCard(boolean correct)
  {
    Box tstBox = getPool();
    Box target = getTargetBox(correct);

    // nothing todo if we place the card in the same box.
    if (target == tstBox) return;

    FlashCard card = currentCard();

    tstBox.remove(card); // remove card from current box
    target.add(card); // store card in target box
    
  }
  public ArrayList<Box> getBoxes() {
	  return theBoxes;
  }
  
  public String toString(){
		return "";
	}

  private ArrayList<Box> theBoxes;
  /** box where correct cards will be placed. */
  private Box rightBox;

  /** box where correct cards will be placed. */
  private Box wrongBox;
}
