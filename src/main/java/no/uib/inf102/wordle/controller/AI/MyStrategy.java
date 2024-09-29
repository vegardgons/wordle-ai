package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class MyStrategy implements IStrategy {
  private Dictionary dictionary;
  private WordleWordList guesses;

  public MyStrategy(Dictionary dictionary) {
    this.dictionary = dictionary;
    reset();
  }

  @Override
  public String makeGuess(WordleWord feedback) {
    if (feedback != null) {
      guesses.eliminateWords(feedback);
    }

    List<HashMap<Character, Integer>> frequencyMap = calculateLetterFrequency();

    String bestGuess = "";

    int highestScore = -1;

    List<String> filteredGuesses = filterWordsWithoutDup(guesses.possibleAnswers());

    List<String> possibleWords;
    if (!filteredGuesses.isEmpty()) {
        possibleWords = filteredGuesses;
    } else {
        possibleWords = guesses.possibleAnswers();  // Fallback to the original word list
    }

    for (String word : possibleWords) {
      int score = calculateExpectedMatches(word, frequencyMap);
      if (score > highestScore) {
        highestScore = score;
        bestGuess = word;
      }
    }
    if (bestGuess.isEmpty()) {
      bestGuess = guesses.possibleAnswers().get(0);
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

  private List<String> filterWordsWithoutDup(List<String> words) {
    List<String> filtered = new ArrayList<>();
    for (String word : guesses.possibleAnswers()) {
      if (!containsDuplicate(word)) {
        filtered.add(word);
      }
    }
    return filtered;
  }

  private boolean containsDuplicate(String word) {
    Set<Character> seenLetters = new HashSet<>();
    for (Character letter : word.toCharArray()) {
      if (!seenLetters.add(letter)) {
        return true;
      }
    }
    return false;

  }

  @Override
  public void reset() {
    guesses = new WordleWordList(dictionary);
  }

}
