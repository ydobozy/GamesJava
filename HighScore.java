package mainproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HighScore {

	public static Map<String, Integer> getHighScoreMap(String game) {
		String filePath = game + ".txt"; /*each game has its own file to save scores*/
		HashMap<String, Integer> mapScore = new HashMap<String, Integer>(); /*create empty map to collect info from file*/
		File file = new File(filePath);

		try {
			if (file.createNewFile()) { /*in case file does not exist as in first installation, create file*/
				return mapScore; /*if new file, map return empty*/
			} else {
				String line;
				BufferedReader reader = new BufferedReader(new FileReader(filePath));

				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(":", 2); /*for each line, create list of key and value*/
					if (parts.length >= 2) {
						String name = parts[0]; /*first element is user's name*/
						int highscore = Integer.parseInt(parts[1]);/*second element is score*/
						mapScore.put(name, highscore);/*create map*/
					} else {
						System.out.println("ignoring line: " + line);
					}
				}
				reader.close();
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return mapScore;
	}
	/*receiving names of player and game + score, checking if update is necessary*/
	public static void updateHighScoreMap(String game, String player, int newScore) {
		Map<String, Integer> HighScoreMap = getHighScoreMap(game); /*pulling up info from txt file*/
		if (HighScoreMap.containsKey(player)) {/*if player already in file. compare scores*/
			int highScore = HighScoreMap.get(player);
			if (newScore > highScore) {
				highScore = newScore;
				HighScoreMap.put(player, highScore);
			}
		} else {/*if player not in file, create new entry*/
			HighScoreMap.put(player, newScore);
		}

		saveHighScore(HighScoreMap, game);/*send to save method to write new info*/
	}

	public static int getHighScoreGame(String game) { /*return high score for the game, max set to 1st elem and iterate*/

		Map<String, Integer> HighScoreMap = getHighScoreMap(game);
		Map.Entry<String, Integer> maxScore = null;
		for (Map.Entry<String, Integer> entry : HighScoreMap.entrySet()) {
			if (maxScore == null || entry.getValue().compareTo(maxScore.getValue()) > 0) {
				maxScore = entry;
			}
		}
		return (maxScore == null) ? 0 : maxScore.getValue();
	}

	public static int getHighPersoScoreGame(String player, String game) {/*return value for player*/
		int highScore = 0;
		Map<String, Integer> HighScoreMap = getHighScoreMap(game);
		if (HighScoreMap.containsKey(player)) {
			highScore = HighScoreMap.get(player);
		}
		return highScore;
	}

	public static Player checkMasterplayer(String player, String game) {/*Master statuts defined if perso best >49*/
		int highscore = getHighPersoScoreGame(player, game);
		Player current = (highscore > 49) ? new Masterplayer(player) : new Player(player);
		return current;
	}

	public static Player checkImmortalplayer(String player, String game) {/*Immortal statuts defined if perso best <*/
		int highscore = getHighPersoScoreGame(player, game); /*sent only for Immortal class*/
		Player current = (highscore < 11) ? new Immortal(player) : new Player(player);
		return current;
	}

	public static void leaderBoard(String game) {/*see report for sorting map*/
		Map<String, Integer> HighScoreMap = getHighScoreMap(game);
		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
		HighScoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
		System.out.println(game.toUpperCase() + " LEADERBOARD");
		for (String player : reverseSortedMap.keySet()) {
			if (reverseSortedMap.get(player)<= 0) {
				System.out.print("");/*do not display if player has 0 or negative points*/
			} else {
				checkMasterplayer(player, game);/*special string if player is Master*/
				String master = (checkMasterplayer(player, game) instanceof Masterplayer) ? " ****Master status****"
						: "";
				System.out.println(player + ":" + reverseSortedMap.get(player) + master);
			}
		}
	}

	public static void saveHighScore(Map<String, Integer> gameHighScores, String game) {

		BufferedWriter writer = null;
		String filePath = game + ".txt";
		try {/*open txt file again to write the last map version*/

			// create new BufferedWriter for the output file
			writer = new BufferedWriter(new FileWriter(filePath));

			// iterate map entries
			for (Map.Entry<String, Integer> entry : gameHighScores.entrySet()) {

				// put key and value separated by a colon
				writer.write(entry.getKey() + ":" + entry.getValue());

				writer.newLine();
			}

			writer.flush();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} finally {

			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}
}
