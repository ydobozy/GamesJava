package mainproject;

/*if player Master (perso best >50), 5 extra points for a win, loss reduced by 5 points*/
public class Masterplayer extends Player {

	public Masterplayer() {
	}

	public Masterplayer(String name) {
		super(name);
	}

	@Override
	public void incrementScore(int i) {
		setScore(getScore() + i + 5);
	}

	@Override
	public void decrementScore(int i) {
		setScore(getScore() - i + 5);
	}

	@Override
	public String toString() {
		return getName() + ", your current status is Master player\nEach win is increased by 5 points"
				+ " and each loss is reduced by 5 points";
	}
}
