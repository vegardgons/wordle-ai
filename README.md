<img align="right" src="images/Wordle_example.gif" width="400"/>

# Wordle AI 🎯  

_A Java/Maven implementation of Wordle with multiple AI strategies (INF102 Semester Assignment)._

## 📖 Overview

This project implements the classic game **Wordle**, extended with an AI that can play the game automatically.

Core features:

- Standard Wordle gameplay with a 5-letter word to guess within 6 attempts.
- Multiple AI strategies with increasing intelligence:
  - **RandomStrategy** – guesses random words without using feedback.
  - **EliminateStrategy** – eliminates impossible words based on feedback.
  - **FrequencyStrategy** – chooses guesses with the most common letters to reduce candidates faster.
  - **MyStrategy** – custom implementation that outperforms FrequencyStrategy.
