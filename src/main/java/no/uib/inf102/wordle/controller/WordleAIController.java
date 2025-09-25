package no.uib.inf102.wordle.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

import no.uib.inf102.wordle.controller.AI.EliminateStrategy;
import no.uib.inf102.wordle.controller.AI.IStrategy;
import no.uib.inf102.wordle.controller.AI.MyStrategy;
import no.uib.inf102.wordle.controller.AI.RandomStrategy;
import no.uib.inf102.wordle.controller.AI.FrequencyStrategy;
import no.uib.inf102.wordle.controller.AI.AIStrategyType;
import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.GameState;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.view.gameView.WordleView;

public class WordleAIController extends WordleController {

  private IStrategy AI;
  private WordleWord feedback;
  private final Timer timer;

  public WordleAIController(ControllableWordleModel model, WordleView view, AIStrategyType strategyType) {
    super(model, view);

    Dictionary dictionary = model.getDictionary();
    this.timer = new Timer(model.getTimerDelay(), this::clockTick);

    // Choose AI based on enum
    switch (strategyType) {
      case RANDOM -> this.AI = new RandomStrategy(dictionary);
      case ELIMINATE -> this.AI = new EliminateStrategy(dictionary);
      case FREQUENCY -> this.AI = new FrequencyStrategy(dictionary);
      case MY -> this.AI = new MyStrategy(dictionary);
      default -> this.AI = new RandomStrategy(dictionary);
    }

    view.addKeyListener(this);
    view.setFocusable(true);
    this.timer.start();
  }

  public void clockTick(ActionEvent e) {
    if (model.getGameState() == GameState.GAME_OVER)
      return;

    String guess = AI.makeGuess(feedback);
    for (int i = 0; i < guess.length(); i++) {
      model.addCharacter(guess.charAt(i));
    }
    feedback = model.makeGuess();

    if (feedback.allMatch()) {
      timer.stop();
    }

    timer.setDelay(model.getTimerDelay());
    model.clockTick();
    view.repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_1) {
      AI.reset();
      model.reset();
      feedback = null;
      this.timer.start();
      view.repaint();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
