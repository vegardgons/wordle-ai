package no.uib.inf102.wordle.view.gameSelect;

import no.uib.inf102.wordle.controller.WordleAIController;
import no.uib.inf102.wordle.controller.WordleHumanController;
import no.uib.inf102.wordle.controller.AI.AIStrategyType;
import no.uib.inf102.wordle.model.WordleBoard;
import no.uib.inf102.wordle.model.WordleModel;
import no.uib.inf102.wordle.view.gameView.WordleView;

import javax.swing.*;

import static no.uib.inf102.wordle.WordleMain.WINDOW_TITLE;

public class WordleGame {

  /**
   * Creates a Wordle game with either a human or a selected AI controller.
   * 
   * @param humanController true -> human; false -> AI
   * @param aiType          strategy to use if AI is selected (ignored if
   *                        humanController==true)
   */
  public WordleGame(boolean humanController, AIStrategyType aiType) {
    WordleBoard board = new WordleBoard(8, 5);
    WordleModel model = new WordleModel(board);
    WordleView view = new WordleView(model);
    if (humanController) {
      new WordleHumanController(model, view);
    } else {
      new WordleAIController(model, view, aiType);
    }

    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }

  public WordleGame(boolean humanController) {
    this(humanController, AIStrategyType.FREQUENCY);
  }
}
