package mainproject;

/*if player best <11 Hangman game, number of tries augmented to 26 + special string*/
public class Immortal extends Player {

	public Immortal() {
	}

	public Immortal(String name) {
		super(name);
	}

	@Override
	public int getMaxerrors() {
		return 26;
	}

	@Override
	public String toString() {
		return getName() + ", your current status is Immortal\nYou have 26 attempts to guess the word";
	}
}
