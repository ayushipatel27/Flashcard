

import java.util.Random;

/**
 * An abstract class implementing many common methods of study methods.
 *
 * @invariant pool != null
 */
abstract public class AbstractStudyMethod implements StudyMethod
{
  /**
   * picks a card randomly according to the study method.
   *
   * a concrete study method needs to implement this method accordingly.
   */
  abstract protected FlashCard pickRandomCard();

  /**
   * Picks a random card from the pool.
   *
   * @precondition there must be at least one card.
   */
  public void pickCard()
  {
    // pick a random card
    card = pickRandomCard();

    // Choose side of card.
    displayFront = (genRandomInt(2) == 0);
  }

  /**
   * Returns the question for the last picked card.
   *
   * @return the question
   *
   * @precondtion pickCard() has been called at least once
   */
  public String getQuestion()
  {
    assert card != null;
    return card.getChallenge();
  }

  /**
   * Returns the answer for the last picked card.
   *
   * @return the answer
   *
   * @precondtion pickCard() has been called at least once
   */
  public String getAnswer()
  {
    assert card != null;
    return card.getResponse();
  }

  /**
   * returns true if answer is a correct answer
   *         false otherwise.
   *
   * @param answer a user response
   */
  public boolean testAnswer(String answer)
  {
    return getAnswer().equals(answer);
  }

  /**
   * generates a random number in the range (0;max] .
   */
  protected int genRandomInt(int max)
  {
    return rand.nextInt(max);
  }

  /**
   * returns current card.
   */
  protected FlashCard currentCard()
  {
    return card;
  }
  public String toString(){
	 return card.getChallenge() + "\n" + card.getResponse();

  }

  private final Random         rand = new Random(); /// random number generator.
  private FlashCard            card = null;         /// last picked card.
  private boolean              displayFront = true; /// indicates side to display.
}
