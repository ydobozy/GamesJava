package mainproject;

public class Game {

	private String game;
	private String player;
	private int persoBest;
	private int gameBest;

	public Game() { /*constructor no argument*/
	}

	public Game(String player, String game) {/*constructor with 2 arguments, get value for best from Highscore*/
		this.player = player;
		this.game = game;
		persoBest = HighScore.getHighPersoScoreGame(player, game);
		gameBest = HighScore.getHighScoreGame(game);
	}

	public String getGame() {
		return game;
	}

	public int getPersobest() {
		return persoBest;
	}

	public int getGamebest() {
		return gameBest;
	}
	
	/*no setters needed as variable predefined by user choice and saved scores*/
	
	public String toString() { /*general string representation for standard player*/
		String record = "The Top Score for " + game + " is: " + gameBest + "\nYour personal record for the game is: "
						+ persoBest;

		return record;
	}

}
