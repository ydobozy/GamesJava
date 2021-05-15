package mainproject;

import java.util.Random;
import java.util.Scanner;

public class Rock extends Game {
	/* Game variables */
	public static final char ROCK = 'r';
	public static final char PAPER = 'p';
	public static final char SCISSORS = 's';
	public static final char WELL = 'w';
	public static String validChoice = "rpswq";
	public static String[] nameChoice = { "Rock", "Paper", "Scissors", "Well", "Quit" };
	public static char playerMove;

	public Rock() {
	}

	public Rock(String player) {/*constructor for a round of game, "rock" is name of file to save scores*/
		super(player, "rock");
	}

	public static void playRock(Rock round, Player player) {
		System.out.println(round);
		System.out.println(player);

		do {/*get player's move*/
			System.out.println("Choose your hand!");
			System.out.println("R for ROCK");
			System.out.println("P for PAPER");
			System.out.println("S for SCISSORS");
			System.out.println("W for WELL");
			System.out.println();
			System.out.println("Q to QUIT GAME");
			System.out.println();
			/* method to collect user's entry and to return a valid one according to validChoice list*/
			playerMove = getPlayerMove();

			switch (playerMove) {

			case 'z':
				System.out.println("Wrong entry, please try again");
				break;

			case 'q': /*save score while quiting game and reset before new round*/
				HighScore.updateHighScoreMap(round.getGame(), player.getName(), player.getScore());
				player.resetScore();
				break;

			default: /*start the game, get computer move and compare results*/
				char computerMove = getComputerMove();
				if (playerMove == computerMove) {/*draw case*/
					player.incrementScore(5);
					System.out.println("Game is Tie !! your current score: " + player.getScore());
				} else if ((playerMove == Rock.ROCK & computerMove == Rock.SCISSORS)
						|| (playerMove == Rock.PAPER & computerMove == Rock.ROCK)
						|| (playerMove == Rock.PAPER & computerMove == Rock.WELL)
						|| (playerMove == Rock.SCISSORS & computerMove == Rock.PAPER)
						|| (playerMove == Rock.WELL & computerMove == Rock.ROCK)
						|| (playerMove == Rock.WELL & computerMove == Rock.SCISSORS)) {
					player.incrementScore(10);/*listed all scenarii for user's win*/
					System.out.println("You won!! your current score: " + player.getScore());
				} else { /*if no tie or user's win, only computer win left*/
					player.decrementScore(10);
					System.out.println("You lost, Computer Wins!! your current score: " + player.getScore());
				} /* check if current score is personal best and/or game recored*/
				checkTopscore(player.getScore(), round.getPersobest(), round.getGamebest());
				break;
			}
		} while (playerMove != 'q' /* Exit loop when choice is -1 */);
	}

	/* Get Computer's move using Random class nextInt() method */
	public static char getComputerMove() {
		char computermove;
		Random random = new Random();
		int input = random.nextInt(4);
		if (input == 0)
			computermove = Rock.ROCK;
		else if (input == 1)
			computermove = Rock.PAPER;
		else if (input == 2)
			computermove = Rock.SCISSORS;
		else
			computermove = Rock.WELL;

		System.out.println("Computer move is: " + nameChoice[input]);
		System.out.println();
		return computermove;
	}

	// Get Player's move and check if entry is in valid choice r,p,s,w,q
	public static char getPlayerMove() {
		Scanner input = new Scanner(System.in);
		String s = input.next();
		char playermove = Character.toLowerCase(s.charAt(0));
		if (validChoice.indexOf(playermove) != -1) {
			if (playermove == 'q') {
				playermove = 'q';
			} else
				System.out.println("Player move is: " + nameChoice[validChoice.indexOf(playermove)]);
		} else {
			playermove = 'z';
		}
		return playermove;
	}

	public static void checkTopscore(int score, int persoBest, int gameBest) {
		if (score > persoBest) {
			System.out.println("Well done! New personal best! Quit to save or Play again!");
		}
		if (score > gameBest) {
			System.out.println("Well done! New personal best! Quit to save or Play again!");
		}

	}
	
}