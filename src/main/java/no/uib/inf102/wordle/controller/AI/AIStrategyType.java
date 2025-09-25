package no.uib.inf102.wordle.controller.AI;

public enum AIStrategyType {
  RANDOM("Random"),
  ELIMINATE("Eliminate"),
  FREQUENCY("Frequency"),
  MY("MyStrategy");

  private final String display;

  AIStrategyType(String display) {
    this.display = display;
  }

  @Override
  public String toString() {
    return display;
  }
}
