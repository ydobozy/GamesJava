package mainproject;

public class Player {
	private String name;
	private int score;
	private int maxErrors;

	public Player() {
		this("", 0, 10);
	}

	public Player(String name) {
		this.name = name;
		maxErrors = 10;
	}

	public Player(String name, int score, int maxErrors) {
		this.name = name;
		score = 0;
		maxErrors = 10;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void incrementScore(int i) {
		score += i;
	}

	public void resetScore() {
		score = 0;
	}

	public void decrementScore(int i) {
		score -= i;
	}

	public void displayScore() {
		System.out.println("Current score is " + score);
	}

	public int getMaxerrors() {
		return maxErrors;
	}

	public void setMaxerrors(int maxErrors) {
		this.maxErrors = maxErrors;
	}

	public String toString() {
		return name + "enjoy the game!";
	}
}