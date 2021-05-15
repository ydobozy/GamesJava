package mainproject;

import java.io.*;
import java.util.*;

public class Hangman extends Game {

	private static final String dict = "dict.txt";
	private static int maxErrors;
	
	static char[] usedLetters = new char[maxErrors];
	static int i;
	static int nbrErrors = 0;
	
	public Hangman() {
	}

	public Hangman(String player) {/*constructor for a round of game, "rock" is name of file to save scores*/
		super(player, "hangman");
	}

	public static void playHangman(Hangman round, Player player) {
		System.out.println(round);
		System.out.println(player);
		maxErrors = player.getMaxerrors();/*get max errors according to player status (immortal or default)*/
		char playerMove;
		do {
			char[] wordTarget = wordRandom(); /*get random word from dict*/
			char[] wordProgress = prepareGuess(wordTarget); /*set up arrays for current guess and target word to compare*/
			displayProgress(wordProgress);
			boolean wordFound = false; 

			do {/*while target word not found (wordFound false) and max error not reached , user interaction needed*/
				char letter = getPlayerletter(); /*get player's letter*/
				if (letter != 0) {
					wordFound = checkLetter(letter, wordProgress, wordTarget); /*check resulting array*/
				}
				String attemptLeft = ""; /*string will remain empty if letter is found, otherwise updated with errors count*/
				if (maxErrors - nbrErrors > 1) {
					attemptLeft = ("You have " + (maxErrors - nbrErrors) + " attempts left. ");
				} else if (maxErrors - nbrErrors == 1) {
					attemptLeft = "Careful! This is your last attempt! ";
				}
				System.out.print(attemptLeft);
				displayProgress(wordProgress);

			} while (nbrErrors < maxErrors & !wordFound);
			/*word found or max nbr errors reached, end of loop and action accordingly*/	
			if (wordFound) {/*win, increment score, compare against high scores*/
				System.out.println("You won!! Word searched was: " + String.valueOf(wordTarget));
				// System.out.println(wordTarget);
				player.incrementScore(10);
				System.out.println("Your current score is: " + player.getScore());
				checkTopscore(player.getScore(), round.getPersobest(), round.getGamebest());

			} else {/*not found, decrement score, display solution, no need to compare with high scores*/
				System.out.print("Too many attempts! The word was: ");
				System.out.println(wordTarget);
				player.decrementScore(10);
				System.out.println("Your current score is: " + player.getScore());
			}

			do {/*menu to offer to play again or quit. If quit, score saved and reset*/
				System.out.println("Press ENTER to guess a new word or \"Q\" to quit");
				Scanner input = new Scanner(System.in);
				String strletter = input.nextLine();
				if (strletter.equals("")) {
					playerMove = 'c';
				} else {
					playerMove = Character.toLowerCase(strletter.charAt(0));
				}
			} while (playerMove != 'q' & playerMove != 'c');

		} while (playerMove != 'q'); // *Exit do while when choice is q*/
		HighScore.updateHighScoreMap(round.getGame(), player.getName(), player.getScore());
		player.resetScore();

		System.out.println("Goodbye!");
	}

	public static char[] wordRandom() {
		char[] randomArray = null;
		try { /*read from dict.txt file*/
			BufferedReader reader = new BufferedReader(new FileReader(dict));
			String line;
			List<String> words = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(" "); /*1 word per line in txt but still clean up for consistency*/
				for (String word : parts) {
					if (word.length() > 2) {/*select only words from 3 letters and more*/
						words.add(word);
					}
				}
			}
			Random rand = new Random();
			String randomWord = words.get(rand.nextInt(words.size()));/*get word in array by random index*/
			/*CHEAT MODE: UNCOMMENT LINE BELOW TO DISPLAY SELECTED WORD*/
			// System.out.println("random word from dict: " + randomWord); 
			randomArray = randomWord.toCharArray();
			reader.close();

		} catch (Exception e) {
			System.out.println("wrong");
		}
		return randomArray;/*return array of the string for display and comparison purpose*/
	}

	public static char[] prepareGuess(char[] target) {
		nbrErrors = 0;
		usedLetters = new char[maxErrors];/*letters tested cannot be more than nbr of errors authorized*/
		int lenTarget = target.length;
		char wordToguess[];
		wordToguess = new char[lenTarget]; /*create array for word to guess, only 1st and last letter disclosed*/

		for (i = 0; i < wordToguess.length; i++) {
			if (i == 0) {
				wordToguess[i] = target[i];
			} else if (i == wordToguess.length - 1) {
				wordToguess[i] = target[i];
			} else {
				wordToguess[i] = '-';
			}
		}
		System.out.println("\n");
		return wordToguess;
	}

	public static void displayProgress(char[] guess) {
		System.out.print("This is the word to guess: ");
		System.out.println(guess);
		System.out.println("*".repeat(maxErrors - nbrErrors));
	}

	public static char getPlayerletter() { /*get letter from user and check validity (letter only)*/

		boolean invalid;
		char letter;
		do {
			System.out.print("Which letter would you like to try? ");
			Scanner input = new Scanner(System.in);
			String strletter = input.next();
			letter = Character.toLowerCase(strletter.charAt(0));
			invalid = (!(letter >= 'a' && letter <= 'z')) ? true : false;
			if (invalid) {
				System.out.println("Please enter a letter between A and Z");
			}
		} while (invalid == true);

		for (i = 0; i < usedLetters.length; i++) { /* if letter not found in used, letter added to array at last index*/
			if (usedLetters[i] == 0) {
				usedLetters[i] = letter;
				break;
			} else { /*letter already in list, invalid letter valid set to 0*/
				if (usedLetters[i] == letter) {
					System.out.println("Letter was already used, please try again");
					letter = 0;
					break;
				}
			}
		}
		return letter;
	}

	public static boolean checkLetter(char letter, char[] guess, char[] target) { /*check letter if in target word*/
		boolean match = false;
		for (i = 1; i < target.length - 1; i++) {
			if (letter == target[i]) {
				guess[i] = letter; /*if match, guess updated*/
				match = true; /*letter in word*/
			}
		}
		if (match == false) {
			System.out.println("Ooops, wrong guess!");
			nbrErrors += 1;
		} else {
			System.out.println("Well done, letter is in the word!");
			match = Arrays.equals(guess, target);
		}
		return match;
	}

	public static void checkTopscore(int score, int persoBest, int gameBest) {
		if (score > persoBest) {
			System.out.println("Well done! New personal best! Quit to save or Risk again!");
		}
		if (score > gameBest) {
			System.out.println("Well done! New game record! Quit to save or Risk again!");
		}
	}
}
