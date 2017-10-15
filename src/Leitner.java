
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class implementing the Leitner system for flashcard boxes.
 *
 * @invariant boxes != null
 */
public class Leitner extends AbstractStudyMethod implements Serializable
{
  /**
   * Constructs a new Leitner object.
   *
   * @precondition theBoxes != null
   */
  public Leitner(ArrayList<Box> theBoxes)
  {
    assert theBoxes != null;

    boxes = theBoxes;
  }

  /**
   * Internal method that computes the weight of a box.
   * <p>
   * The weight of a Leitner box depends on the box id the number of cards
   * it stores. The current implementation assigns a card in a lower box double
   * the weight as a card in the next higher box.
   *
   * @param  somebox  the box for which this weight is computed.
   * @param  numBoxes total number of boxes. This is needed to compute the
   *                  distance from box to the box with the highest Leitner id.
   * @return the weight of this box.
   *
   * @precondition somebox != null && numBoxes >= somebox.id()
   */
  private int weight(Box somebox, int numBoxes)
  {
    assert somebox != null && numBoxes >= somebox.id();

    // Each card in box number N has a weight of 2 ^ (|boxes| - N).
    //   Note: 1 << X left-shifts the number 1 by X position ( = 2^X )
    return (1 << (numBoxes - somebox.id())) * somebox.size();
  }

  /**
   * Computes the total weight of all boxes.
   *
   * @return the total weight of all boxes
   */
  private int totalWeight()
  {
    int num = 0;

    for (Box aBox : boxes)
    {
      num += weight(aBox, boxes.size());
    }

    return num;
  }

  /**
   * randomly picks a box according to the Leitner system.
   * @return a random Box
   */
  private Box getRandomBox()
  {
    int           maxRand = totalWeight();
    assert maxRand > 0; // there must be at least one box with one card.

    // rndVal identifies the box where the card is located
    // | weight of box 1         | w. of box 2 | weight of Box 3 ... |
    // ^=0 [min rndVal]          ^first value for box 2             ^max(rndVal)
    //                                                              [last value for highest box]
    //                                  ^ rndVal [falls in Box 2]
    int           rndVal  = genRandomInt(maxRand);

    // Identify the box from where the card will be picked.
    Iterator<Box> boxIter = boxes.iterator();
    Box           currBox = boxIter.next();

    // Subtract the box's weight from rndVal until rndVal < weight(box)
    //    => we have identified the box.
    while (rndVal >= weight(currBox, boxes.size()))
    {
      rndVal -= weight(currBox, boxes.size());

      assert boxIter.hasNext();
      currBox = boxIter.next();
    }

    return currBox;
  }

  /**
   * Picks a random card and side from all boxes.
   *
   * Cards in lower boxes receive higher weight (priority). The method
   * returns void, but the picked card's sides can be accessed through
   * getQuestion and getAnswer, the answer tested using checkAnswer.
   *
   * @precondition there must be at least one box with one card.
   */
  @Override
  protected FlashCard pickRandomCard()
  {
    // Set the box from where the next card will be picked.
    box = getRandomBox();

    // All cards in a box are equally likely to be picked,
    //   thus we generate another pseudo random number.
    return box.get(genRandomInt(box.size()));
  }

  /**
   * Computes the box number where the card will be placed
   *
   * @param box current box number
   * @param correct indicates whether response was correct.
   *
   * @precondition box is a valid box number
   */
  protected int getTargetBox(int box, boolean correct)
  {
    int     newBoxId = 1; // set target box id in case the response is incorrect

    if (correct)
    {
      // response is correct -> update target box id
      newBoxId = Math.min(box, boxes.size());
    }

    return newBoxId;
  }


  /**
   * Moves card to the target box, depending on whether succ indicates
   * a correct/incorrect response.
   *
   * @param correct, indicates whether the last response was correct.
   */
  public void moveCard(boolean correct)
  {
    int       target = getTargetBox(box.id() + 1, correct);
    FlashCard card = currentCard();

    box.remove(card); // remove card from current box
    boxes.get(target - 1).add(card); // store card in target box

    System.out.println("moved card from " + box.id() + " to " + target);
  }


  /** Leitner always has cards, so hasMoreCards() is always true. */
  public boolean hasMoreCards() {
    return true;
  }
  public ArrayList<Box> getBoxes() {
	  return boxes;
  }
 
public String toString(){
	return "";
}

  /** last picked box. */
  private Box                  box = null;

  /** the list of boxes. */
  private final ArrayList<Box> boxes;
}
