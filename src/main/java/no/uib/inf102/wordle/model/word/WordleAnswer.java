package no.uib.inf102.wordle.model.word;

import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import no.uib.inf102.wordle.model.Dictionary;

/**
 * This class represents an answer to a Wordle puzzle.
 * 
 * The answer must be one of the words in the LEGAL_WORDLE_LIST.
 */
public class WordleAnswer {

  private final String WORD;

  private Dictionary dictionary;

  private static Random random = new Random();

  /**
   * Creates a WordleAnswer object with a random word from the answer word list
   */
  public WordleAnswer(Dictionary dictionary) {
    this(random, dictionary);
  }

  /**
   * Creates a WordleAnswer object with a random word from the answer word list
   * using a specified random object.
   * This gives us the opportunity to set a seed so that tests are repeatable.
   */
  public WordleAnswer(Random random, Dictionary dictionary) {
    this(getRandomWordleAnswer(random, dictionary), dictionary);
  }

  /**
   * Creates a WordleAnswer object with a given word.
   * 
   * @param answer
   */
  public WordleAnswer(String answer, Dictionary dictionary) {
    this.WORD = answer.toLowerCase();
    this.dictionary = dictionary;
  }

  /**
   * Gets a random wordle answer
   * 
   * @param random
   * @return
   */
  private static String getRandomWordleAnswer(Random random, Dictionary dictionary) {
    List<String> possibleAnswerWords = dictionary.getAnswerWordsList();
    int randomIndex = random.nextInt(possibleAnswerWords.size());
    String newWord = possibleAnswerWords.get(randomIndex);
    return newWord;
  }

  /**
   * Guess the Wordle answer. Checks each character of the word guess and gives
   * feedback on which that is in correct position, wrong position and which is
   * not in the answer word.
   * This is done by updating the AnswerType of each WordleCharacter of the
   * WordleWord.
   * 
   * @param wordGuess
   * @return wordleWord with updated answertype for each character.
   */
  public WordleWord makeGuess(String wordGuess) {
    if (!dictionary.isLegalGuess(wordGuess))
      throw new IllegalArgumentException("The word '" + wordGuess + "' is not a legal guess");

    WordleWord guessFeedback = matchWord(wordGuess, WORD);
    return guessFeedback;
  }

  /**
   * Generates a WordleWord showing the match between <code>guess</code> and
   * <code>answer</code>
   * 
   * @param guess
   * @param answer
   * @return
   */
  public static WordleWord matchWord(String guess, String answer) {
    int wordLength = answer.length();
    if (guess.length() != wordLength)
      throw new IllegalArgumentException(
          "Guess and answer must have same number of letters but guess = " + guess + " and answer = " + answer);
    AnswerType[] feedback = new AnswerType[wordLength];

    HashMap<Character, Integer> charCount = new HashMap<>();

    // Creating the hashmap
    for (int i = 0; i < wordLength; i++) { // n * O(1) = O(n)
      char answerChar = answer.charAt(i);  
      charCount.put(answerChar, charCount.getOrDefault(answerChar, 0) + 1); 
    }

    // Loop for correct characters
    for (int i = 0; i < wordLength; i++) {        // n * O(1) = O(n) 
      if (guess.charAt(i) == answer.charAt(i)) {
        feedback[i] = AnswerType.CORRECT; 
        char correctChar = guess.charAt(i);
        charCount.put(correctChar, charCount.get(correctChar) - 1);
      }
    }

    // Loop for wrong or misplaced
    for (int i = 0; i < wordLength; i++) {  // n * O(1) = O(n)
      if (feedback[i] != AnswerType.CORRECT) {
        char guessChar = guess.charAt(i);
        if (charCount.getOrDefault(guessChar, 0) > 0) {
          feedback[i] = AnswerType.MISPLACED;
          charCount.put(guessChar, charCount.get(guessChar) - 1);
        } else {
          feedback[i] = AnswerType.WRONG;
        }
      }
    }
    return new WordleWord(guess, feedback);
  }
}