package mainproject;

import java.util.Scanner;

public class Mainmenu {

	public static void main(String[] args) {
	
		Scanner input = new Scanner(System.in);
		String choice;
		String[] games = {"rock","hangman"};
		int choice1,choice2;
		String player_name;
	
		do{
		
		do { /*loop to validate choice1, checking if entry is int 1 or 2 otherwise error */
			System.out.print("Please choose an option\n");
			System.out.print("1. New Player\n");
			System.out.print("2. Quit\n");
			choice = input.next();
			try {
				choice1=Integer.parseInt(choice);
				if (choice1 !=1 & choice1 != 2) {
					System.out.println("Please enter 1 or 2\n");
				}
			} catch (NumberFormatException error) {
				System.out.println("Please enter 1 or 2\n");
				choice1=0;
			}		
		} while (choice1 !=1 & choice1 != 2);
	
		switch(choice1){/*outside switch  choice1 for new player case 1 exit case 2*/

		case 1: /*loop to validate entry 1, 2 or -1 as int and as project pdf template*/
			System.out.print("Please enter a name: \n");
			player_name = input.next();
			Player newPlayer; 
		 	
			do {
				System.out.print("Hello "+ player_name + ". Please choose a game or -1 to quit: \n");
				System.out.print("1. Rock, Paper, Scissors, Well\n");
				System.out.print("2. Hangman\n");
				choice = input.next();
				try {
					choice2=Integer.parseInt(choice);
					if (choice2 !=1 & choice2 != 2 & choice2 !=-1) {
						System.out.println("Please enter 1,2 or -1\n");
					}
				} catch (NumberFormatException error) {
					System.out.println("Please enter 1,2 or -1\n");
					choice2=0;
				}		
			} while (choice2 !=1 & choice2 != 2 & choice2 !=-1);
			
			switch(choice2){/*inside switch choice2 to select game (1,2)or exit (-1)*/
		
			case 1: /*rock game selected, check master status and instantiate  new player accordingly + round*/
				Rock round1 = new Rock(player_name);
				newPlayer=HighScore.checkMasterplayer(player_name,"rock");
				Rock.playRock(round1, newPlayer);		
				break;
				
			case 2: /*hangman selected, check master and immortal status,  instantiate  new player accordingly + round*/
				Hangman round2 = new Hangman(player_name);
				newPlayer = HighScore.checkMasterplayer(player_name,"hangman");
				newPlayer= (newPlayer instanceof Masterplayer) ?
				newPlayer:HighScore.checkImmortalplayer(player_name,"hangman");	
				Hangman.playHangman(round2, newPlayer);		
				break;

			case -1:
				System.out.println("Back to menu...");
				break;
			}
	
			break;
		
		case 2: /*back to outside switch, exit option, call to HighScore to display board before exiting program*/
			for (String game : games ) {
				HighScore.leaderBoard(game);
				System.out.println("-".repeat(40));
			}
			System.out.println("\nExiting Program...");
			System.exit(0);
			break;
	}
		/* end of main do loop with new player choice*/
		} while(choice1 != 2 ); /*Exit loop when choice is 2*/
	 
		input.close();
	}
}



