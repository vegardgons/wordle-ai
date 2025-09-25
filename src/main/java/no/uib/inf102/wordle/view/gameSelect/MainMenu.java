package no.uib.inf102.wordle.view.gameSelect;

import no.uib.inf102.wordle.WordleMain;
import no.uib.inf102.wordle.controller.AI.AIStrategyType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu implements ActionListener {
  private final JButton humanControllerButton;
  private final JButton AIControllerButton;
  private final JFrame frame;

  public MainMenu() {
    // window for main menu
    frame = new JFrame();
    frame.setTitle(WordleMain.WINDOW_TITLE);

    // panel for buttons
    JPanel buttons = new JPanel();
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
    buttons.setBorder(new EmptyBorder(10, 10, 30, 10));

    // add buttons for choosing controller
    humanControllerButton = addButton(buttons, "Human Player");
    AIControllerButton = addButton(buttons, "AI Player");

    // add buttons to window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(buttons);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Adds buttons with a fixed style and add it to the panel
   *
   * @param buttons - The JPanel containing all the buttons.
   * @param name    - The name to be displayed on the button.
   * @return
   * @author Martin Vatshelle - martin.vatshelle@uib.no
   */
  JButton addButton(JPanel buttons, String name) {
    JButton button = new JButton();
    button.setText(name);
    button.setFont(new Font("Arial", Font.PLAIN, 40));
    button.addActionListener(this);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setBorder(new RoundedBorder(20));
    buttons.add(Box.createRigidArea(new Dimension(20, 20)));
    buttons.add(button);
    return button;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    boolean humanController = e.getSource() == humanControllerButton;

    if (humanController) {
      new WordleGame(true, AIStrategyType.FREQUENCY); // aiType ignored
      frame.dispose();
      return;
    }

    // Ask which AI to use
    AIStrategyType[] options = AIStrategyType.values();
    AIStrategyType choice = (AIStrategyType) JOptionPane.showInputDialog(
        frame,
        "Choose AI strategy:",
        "Select AI",
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        AIStrategyType.FREQUENCY // default
    );

    if (choice != null) {
      new WordleGame(false, choice);
      frame.dispose();
    }
    // If user canceled, do nothing
  }
}
