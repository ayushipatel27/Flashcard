
import java.util.ArrayList;

/**
 * Class implementing an interface for study methods using flashcards.
 *
 */
public interface StudyMethod {

	/** method for responsible for picking a random flashcard to show to the user. */
	public void pickCard();

	/** method responsible for returning the question given to the user. */
	public String getQuestion();

	/** method responsible for returning the answer for the given question. */
	public String getAnswer();

	/**
	 * method that shows whether the study method object has anymore cards left.
	 *
	 * it is meant for the drill study method, so that when all the cards have been answered correctly
	 * the study session will end.
	 *
	 * Leitner.hasMoreCards() will always return true, since there is always at least one box with cards.
	 */
	public boolean hasMoreCards();

  /**
   * returns true if answer is a correct answer
   *         false otherwise.
   *
   * @param answer a user response
   */
  public boolean testAnswer(String answer);


  /**
   * Moves card to the target box, depending on whether succ indicates
   * a correct/incorrect response.
   *
   * @param correct, indicates whether the last response was correct.
   */
  public void moveCard(boolean correct);

  public String toString();
  
  public ArrayList<Box> getBoxes();
 }
