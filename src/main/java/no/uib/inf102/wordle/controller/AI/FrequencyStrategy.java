package no.uib.inf102.wordle.controller.AI;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleCharacter;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

import java.util.HashMap;
import java.util.Map;

/**
 * This strategy finds the word within the possible words which has the highest
 * expected
 * number of green matches.
 */
public class FrequencyStrategy implements IStrategy {

  private Dictionary dictionary;
  private WordleWordList guesses;

  public FrequencyStrategy(Dictionary dictionary) {
    this.dictionary = dictionary;
    reset();
  }

  @Override
  public String makeGuess(WordleWord feedback) {
    if (feedback != null) {
      guesses.eliminateWords(feedback);
    }

    Map<Integer, Map<Character, Integer>> frequencyMap = calculateLetterFrequency();

    String bestGuess = "";
    int highestScore = -1;

    for (String word : guesses.possibleAnswers()) {
      int score = calculateExpectedMatches(word, frequencyMap);
      if (score > highestScore) {
        highestScore = score;
        bestGuess = word;
      }
    }
    return bestGuess;
  }

  private Map<Integer, Map<Character, Integer>> calculateLetterFrequency() {
    Map<Integer, Map<Character, Integer>> frequencyMap = new HashMap<>();
    int wordLength = guesses.possibleAnswers().get(0).length();

    for (int i = 0; i < wordLength; i++) {
      frequencyMap.put(i, new HashMap<>());
    }

    for (String word : guesses.possibleAnswers()) {
      for (int i = 0; i < word.length(); i++) {
        char letter = word.charAt(i);
        Map<Character, Integer> letterFrequencies = frequencyMap.get(i);
        letterFrequencies.put(letter, letterFrequencies.getOrDefault(letter, 0) + 1);
      }
    }

    return frequencyMap;
  }

  private int calculateExpectedMatches(String word, Map<Integer, Map<Character, Integer>> frequencyMap) {
    int score = 0;

    for (int i = 0; i < word.length(); i++) {
      char letter = word.charAt(i);
      Map<Character, Integer> letterFrequencies = frequencyMap.get(i);
      score += letterFrequencies.getOrDefault(letter, 0);
    }
    return score;
  }

  @Override
  public void reset() {
    guesses = new WordleWordList(dictionary);
  }
}