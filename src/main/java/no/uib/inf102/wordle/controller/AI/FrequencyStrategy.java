package no.uib.inf102.wordle.controller.AI;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This strategy finds the word within the possible words which has the highest
 * expected number of green matches.
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

    // Changed to use List of HashMaps
    List<HashMap<Character, Integer>> frequencyMap = calculateLetterFrequency();

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

  private List<HashMap<Character, Integer>> calculateLetterFrequency() {
    int wordLength = guesses.possibleAnswers().get(0).length();

    // Initialize list with a HashMap for each position in the word
    List<HashMap<Character, Integer>> frequencyMap = new ArrayList<>();
    for (int i = 0; i < wordLength; i++) {
      frequencyMap.add(new HashMap<>());
    }

    // Fill the frequency map with letter frequencies at each position
    for (String word : guesses.possibleAnswers()) {
      for (int i = 0; i < word.length(); i++) {
        char letter = word.charAt(i);
        HashMap<Character, Integer> letterFrequencies = frequencyMap.get(i);
        letterFrequencies.put(letter, letterFrequencies.getOrDefault(letter, 0) + 1);
      }
    }

    return frequencyMap;
  }

  private int calculateExpectedMatches(String word, List<HashMap<Character, Integer>> frequencyMap) {
    int score = 0;

    for (int i = 0; i < word.length(); i++) {
      char letter = word.charAt(i);
      HashMap<Character, Integer> letterFrequencies = frequencyMap.get(i);
      score += letterFrequencies.getOrDefault(letter, 0);
    }
    return score;
  }

  @Override
  public void reset() {
    guesses = new WordleWordList(dictionary);
  }
}
