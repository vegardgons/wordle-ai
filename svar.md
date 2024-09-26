# Runtime Analysis
For each method of the tasks give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well, e.g. any helper methods. You need to show how you analyzed any methods used by the methods listed below.**

The runtime should be expressed using these three parameters:
   * `n` - number of words in the list allWords
   * `m` - number of words in the list possibleWords
   * `k` - number of letters in the wordleWords


## Task 1 - matchWord
* `WordleAnswer::matchWord`: O(n)
    * *Insert description of why the method has the given runtime*
    The runtime of the code inside the loops is O(1), and the loops iterate n times, where n is the length of the word. Therefore, the given runtime of matchWord is O(k).

## Task 2 - EliminateStrategy
* `WordleWordList::eliminateWords`: O(n)
    * *Insert description of why the method has the given runtime*
    The runtime of creating a list is O(1), the runtime for checking word against possible words is O(n). Therefore, the rundime of EliminateWords is O(n)

## Task 3 - FrequencyStrategy
* `FrequencyStrategy::makeGuess`: O(?)
    * *Insert description of why the method has the given runtime*



# Task 4 - Make your own (better) AI
For this task you do not need to give a runtime analysis. 
Instead, you must explain your code. What was your idea for getting a better result? What is your strategy?

*Write your answer here*
