import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static enum MODES {
		MAN_VS_MACHINE, CPU_BATTLE, MANO_A_MANO, MIXED
	};

	public static Map<Integer, Main.MODES> gameMode = new HashMap<>() {
		{
			put(1, MODES.MAN_VS_MACHINE);
			put(2, MODES.CPU_BATTLE);
			// put(3, GAME_MODE.MANO_A_MANO);
			// put(4, GAME_MODE.MIXED);
		}
	};

	public static void main(String[] args) {
		/*
		 * int upperLimit; //An upper limit to the given range Scanner keyboard = new
		 * Scanner(System.in); //Input reader Random rd = new Random(); //Seed int
		 * numberToGuess; //The number to be guessed
		 */

		Main.MODES currentGameMode = chooseMode(); // Player chooses mode

		switch (currentGameMode) {
		case MAN_VS_MACHINE:

			break;
		case CPU_BATTLE:
			cpuBattle();
			break;
		case MANO_A_MANO:

			break;
		case MIXED:

			break;
		}

		/*
		 * ArrayList<Boolean> winners = new ArrayList<>(); //ArrayList that contains the
		 * winners of the game once it is over for (int i=0;i<3;i++) winners.add(false);
		 */
		// User inputs an upper limit
		/*
		 * do { System.out.print("Set a max value (0-100): ");
		 * upperLimit=keyboard.nextInt(); } while (upperLimit<0 || upperLimit>100);
		 * 
		 * //Player creation Player CPU1 = new Player(true, 0, upperLimit); Player CPU2
		 * = new Player(true, 0, upperLimit); Player user = new Player(false, 0,
		 * upperLimit);
		 */
		// Number generation
		/* numberToGuess = rd.nextInt(upperLimit); */

		/*
		 * Every loop represents a new turn
		 * 
		 * 
		 *
		 */
		/*
		 * do { System.out.println("----------------NEW TURN------------------------");
		 * int bet, p1Guess, p2Guess;
		 * 
		 * //User places bet
		 * 
		 * do { System.out.print("Place your bets (0-100): "); bet = keyboard.nextInt();
		 * 
		 * } while (bet<0 || bet>100 || !user.checkGuesses(bet));
		 * 
		 * 
		 * //CPUs place bet p1Guess = getCPUGuess(CPU1, upperLimit, numberToGuess);
		 * p2Guess = getCPUGuess(CPU2, upperLimit, numberToGuess);
		 * //user.addUserGuess(bet, numberToGuess);
		 * 
		 * //Notify user about this turn's guesses and results
		 * System.out.println("**********************");
		 * System.out.println("Player 1 guess: " + p1Guess);
		 * System.out.println("Player 2 guess: " + p2Guess);
		 * //System.out.println("User guess: " + bet);
		 * System.out.println("**********************");
		 * 
		 * if (p1Guess == numberToGuess) {
		 * System.out.println("Player 1 guesses correctly!"); winners.set(0, true); } if
		 * (p2Guess == numberToGuess) {
		 * System.out.println("Player 2 guesses correctly!"); winners.set(1, true); }
		 * 
		 * 
		 * if (bet == numberToGuess) { System.out.println("User guesses correctly!");
		 * winners.set(2, true); }
		 * 
		 * } while (!winners.contains(true));
		 */

		// Form sentence about the winner
		/*
		 * String winnerStr = " "; for (boolean win : winners) { if (win) if
		 * (winners.indexOf(win) == 0) winnerStr.concat("Player 1, "); else if
		 * (winners.indexOf(win) == 1) winnerStr.concat("Player 2, "); else if
		 * (winners.indexOf(win) == 2) winnerStr.concat("User"); }
		 */

		//System.out.println("GAME OVER. Winner(s): " + winnerStr);
	}

	private static void cpuBattle() {
		int upperLimit = 0;								//An upper limit to the given range
		Scanner keyboard = new Scanner(System.in);		//Input reader
		Random rd = new Random();						//Seed
		int numberToGuess = 0;								//The number to be guessed
		ArrayList<Boolean> winners = new ArrayList<>(); //ArrayList that contains the winners of the game once it is over
		int playerCount=0;
		ArrayList<Player> cpuPlayers = new ArrayList<>();
		boolean winnerExists = false;
		
		//User chooses the amount of CPUs
		do {
			if (playerCount<2) System.out.println("Please enter a valid number");
						
			System.out.print("Choose the number of players (2 or more): ");
			playerCount = keyboard.nextInt();
		} while (playerCount<2);
		
		//User inputs an upper limit
		do
		{
			if (upperLimit!=0) System.out.println("Please enter a valid number for the upper limit");
			
			System.out.print("Set an upper limit (2-100): ");
			upperLimit=keyboard.nextInt();
		} while (upperLimit<2 || upperLimit>100); 
		
		for (int i=0;i<playerCount;i++) cpuPlayers.add(new Player(true, 0, upperLimit));
		
		//User inputs the number to be guessed
		do
		{
			if (numberToGuess!=0) System.out.println("Please enter a valid number");
			
			System.out.print("Choose the number to be guessed (1-" + upperLimit + "): ");
			numberToGuess=keyboard.nextInt();
		} while (numberToGuess<1 || numberToGuess>upperLimit); 

		//Game start
		do 
		{
			System.out.println("----------------NEW TURN------------------------");
			ArrayList<Integer> guesses = new ArrayList<>();
			
			//CPUs place bets
			for (int i=0;i<playerCount;i++) guesses.add(getCPUGuess(cpuPlayers.get(i), upperLimit, numberToGuess));
				
			//Notify user about this turn's guesses and results one by one
			System.out.println("**********************");

			for (int i=0;i<playerCount;i++) {
				//Notify user about this turn's guesses and results one by one
				int guess = guesses.get(i);
				System.out.print("CPU" + i+1 + " guess: " + guess);
				if (guess == numberToGuess) {
					  System.out.println(". CPU" + i+1 + " guesses correctly!"); 
					  cpuPlayers.get(i).setWinner(true);
					  winnerExists = true;
				  } 
			}
			
			System.out.println("**********************");

		} while (!winnerExists);
		
		//Create winner string
		
		/*String winnerStr = " ";
		for (boolean win : winners) {
			if (win)
				if (winners.indexOf(win) == 0)
					winnerStr.concat("Player 1, ");
				else if (winners.indexOf(win) == 1)
					winnerStr.concat("Player 2, ");
				else if (winners.indexOf(win) == 2)
					winnerStr.concat("User");
		}*/
		
		
		
		
	}

	private static Main.MODES chooseMode() {

		int choice = -1; // User's choice for game mode
		Scanner keyboard = new Scanner(System.in); // Input reader

		System.out.println("Available game modes: " + System.lineSeparator()
				+ "1) Man VS Machine - Face off against a number of CPU opponents" + System.lineSeparator()
				+ "2) Battle of the CPUs - Have CPUs face off against each other (you choose the number to guess)"
				+ System.lineSeparator()
		// + "3) Mano-a-mano - Face off against a number of friends" +
		// System.lineSeparator()
		// + "4) Mixed - Face off against a number of friends and CPU opponents" +
		// System.lineSeparator()
		);

		do {
			if (choice > 0)
				System.out.print("Please choose a valid game mode!");

			System.out.print("Choose a game mode (1,2,3,etc): ");
			choice = keyboard.nextInt();

		} while (!Main.gameMode.containsKey(choice));

		return Main.gameMode.get(choice);
	}

	// Methods used in-game

	/*
	 * Returns a unique guess by a CPU player
	 * 
	 * 
	 */
	public static int getCPUGuess(Player CPU, int upperLimit, int numberToGuess) {
		int guess;
		Random rdnew = new Random();

		do {
			guess = rdnew.nextInt(upperLimit);
		} while (!CPU.checkGuesses(guess));

		CPU.addGuess(guess, numberToGuess);
		return guess;
	}

}
