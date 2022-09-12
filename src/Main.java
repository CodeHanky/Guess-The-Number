import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static final int GLOBAL_LOWER_BOUND = 1;
	public static final int GLOBAL_UPPER_BOUND = 100; //The range will be at least [GLOBAL_LOWER_BOUND,GLOBAL_UPPER_BOUND]
	
	public static enum MODES {
		MAN_VS_MACHINE, CPU_BATTLE, MANO_A_MANO, MIXED
	};

	@SuppressWarnings("serial")
	public static Map<Integer, Main.MODES> gameMode = new HashMap<>() {
		{
			put(1, MODES.MAN_VS_MACHINE);
			put(2, MODES.CPU_BATTLE);
			// put(3, GAME_MODE.MANO_A_MANO);
			// put(4, GAME_MODE.MIXED);
		}
	};

	public static void main(String[] args) {

		Main.MODES currentGameMode = chooseMode(); 		// Player chooses mode
		int upperBound = 0;								//An upper limit to the given range 
		Scanner keyboard = new Scanner(System.in);		//Input reader
		Random rd = new Random();						//Seed
		int numberToGuess = 0;							//The number to be guessed
		boolean winnerExists = false;
		
		switch (currentGameMode) {
		case MAN_VS_MACHINE:
			manVSmachine(upperBound, keyboard, rd, numberToGuess, winnerExists);
			break;
		case CPU_BATTLE:
			cpuBattle(upperBound, keyboard, rd, numberToGuess, winnerExists);
			break;
		case MANO_A_MANO:

			break;
		case MIXED:

			break;
		}
	}

	private static void manVSmachine(int upperBound, Scanner keyboard, Random rd, int numberToGuess, boolean winnerExists) {
		int cpuCount=1;
		ArrayList<Player> allPlayers = new ArrayList<>();
		ArrayList<CPUplayer> cpuPlayers = new ArrayList<>();
		Player user;
		
		/*
		 * System decides an upper limit. 
		 * If GLOBAL_UPPER_BOUND is 100, nextInt is forced to return a number in the range 
		 * [0, GLOBAL_UPPER_BOUND+1), aka [0,GLOBAL_UPPER_BOUND] (e.g. [0,100]).
		 * 
		 * The desired range for the number to guess is [GLOBAL_LOWER_BOUND, upperBound], where upperBound is determined by the system in this case.
		 * Therefore, the condition ensures that upperBound will be at least GLOBAL_LOWER_BOUND+1, 
		 * meaning that the guess range will end up being at least [GLOBAL_LOWER_BOUND, GLOBAL_LOWER_BOUND+1]
		 */		
		do {
			upperBound = rd.nextInt(GLOBAL_UPPER_BOUND+1); 
		} while (upperBound<GLOBAL_LOWER_BOUND+1);
		
		/* 
		 * System decides the number to be guessed.
		 * As mentioned above, the desired guess range must be [GLOBAL_LOWER_BOUND, upperBound] (e.g. [1,50] inclusive)
		 * 
		 * Same as above, nextInt is forced to return a number in the range [0, upperBound] (e.g. [0,50])
		 * Finally, since the desired guess range is [GLOBAL_LOWER_BOUND, upperBound], 
		 * the condition checks that the lower bound is at least equal to GLOBAL_LOWER_BOUND (e.g. 1, meaning the range is at least [1,50]). 
		 */
		do {
			numberToGuess = rd.nextInt(upperBound+1);
		} while (numberToGuess<GLOBAL_LOWER_BOUND); 	
		
		//User player creation
		user = createUserPlayer(1,keyboard,GLOBAL_LOWER_BOUND,upperBound);
		
		//CPU player creation
		createCPUPlayers(cpuPlayers,keyboard,GLOBAL_LOWER_BOUND,upperBound);
		
		allPlayers.add(user);
		allPlayers.addAll(cpuPlayers);
		
		//Game start --CHECK THIS NEXT
		do 
		{
			System.out.println("----------------NEW TURN------------------------");
			ArrayList<Integer> guesses = new ArrayList<>();
			
			//User places bet
			int userGuess = user.getNewUserGuess(upperBound,numberToGuess);
			guesses.add(userGuess);
			
			//CPUs place bets
			for (int i=0;i<cpuCount;i++) guesses.add(cpuPlayers.get(i).getNewCPUGuess(upperBound, numberToGuess));
				
			//Notify user about this turn's guesses and results one by one
			System.out.println("**********************");
			
			//User's guess
			System.out.print(user.getName() + "'s guess: " + userGuess);
			if (userGuess == numberToGuess) {
				  System.out.println(". " + user.getName() + " guesses correctly!"); 
				  user.setWinner(true);
				  winnerExists = true;
			} 
			else System.out.println();
			
			//CPU guesses
			for (int i=0;i<cpuCount;i++) {
				int guess = guesses.get(i);
				System.out.print(cpuPlayers.get(i).getName() + "'s guess: " + guess);
				if (guess == numberToGuess) {
					  System.out.println(". " + cpuPlayers.get(i).getName() + " guesses correctly!"); 
					  cpuPlayers.get(i).setWinner(true);
					  winnerExists = true;
				} 
				else System.out.println();
			}
			
			System.out.println("**********************");

		} while (!winnerExists);
		
		//Create winner string
		System.out.println("-------------------GAME OVER---------------------------");
		/*
		 * String winnerStr = "Winners: "; for (int i=0;i<playerCount;i++) { if
		 * (cpuPlayers.get(i).isWinner()) winnerStr+=cpuPlayers.get(i).getName()+" "; }
		 * System.out.println(winnerStr);
		 */
		
	}
	
	private static Player createUserPlayer(int ascOrder, Scanner keyboard, int globalLowerBound, int upperBound) {
		Player user;
		
		System.out.print("Please enter a name for Player " + ascOrder + ": ");
		user = new Player(keyboard.next(), globalLowerBound-1, upperBound+1);
		System.out.println();
		
		return user;
	}

	private static void createCPUPlayers(ArrayList<CPUplayer> cpuPlayers, Scanner keyboard, int globalLowerBound, int upperBound) {
		int cpuCount=1;
		
		//User chooses the amount of CPUs to play against
		do {
			if (cpuCount<1) System.out.println("Please enter a valid number");
						
			System.out.print("Choose the number of CPU opponents (1 or more): ");
			cpuCount = keyboard.nextInt();
		} while (cpuCount<1);
		
		//Add CPUs to array
		for (int i=0;i<cpuCount;i++) cpuPlayers.add(new CPUplayer("CPU"+(i+1), globalLowerBound-1, upperBound+1));
		System.out.println();
	}

	private static void cpuBattle(int upperBound, Scanner keyboard, Random rd, int numberToGuess, boolean winnerExists) {
		int playerCount=2;
		ArrayList<CPUplayer> cpuPlayers = new ArrayList<>();
		
		//User chooses the amount of CPUs
		do {
			if (playerCount<2) System.out.println("Please enter a valid number");
						
			System.out.print("Choose the number of players (2 or more): ");
			playerCount = keyboard.nextInt();
		} while (playerCount<2);
		System.out.println();
		
		//User inputs an upper limit
		do
		{
			if (upperBound!=0) System.out.println("Please enter a valid number for the upper limit");
			
			System.out.print("Set an upper limit (2-100): ");
			upperBound=keyboard.nextInt();
		} while (upperBound<2 || upperBound>100); 
		System.out.println();
		
		for (int i=0;i<playerCount;i++) cpuPlayers.add(new CPUplayer("CPU"+(i+1), 0, upperBound));
		
		//User inputs the number to be guessed
		do
		{
			if (numberToGuess!=0) System.out.println("Please enter a valid number");
			
			System.out.print("Choose the number to be guessed (1-" + upperBound + "): ");
			numberToGuess=keyboard.nextInt();
		} while (numberToGuess<1 || numberToGuess>upperBound); 
		System.out.println();
		
		//Game start
		do 
		{
			System.out.println("----------------NEW TURN------------------------");
			ArrayList<Integer> guesses = new ArrayList<>();
			
			//CPUs place bets
			for (int i=0;i<playerCount;i++) guesses.add(cpuPlayers.get(i).getNewCPUGuess(upperBound, numberToGuess));
				
			//Notify user about this turn's guesses and results one by one
			System.out.println("**********************");

			for (int i=0;i<playerCount;i++) {
				//Notify user about this turn's guesses and results one by one
				int guess = guesses.get(i);
				System.out.print(cpuPlayers.get(i).getName() + "'s guess: " + guess);
				if (guess == numberToGuess) {
					  System.out.println(". " + cpuPlayers.get(i).getName() + " guesses correctly!"); 
					  cpuPlayers.get(i).setWinner(true);
					  winnerExists = true;
				} 
				else System.out.println();
			}
			
			System.out.println("**********************");

		} while (!winnerExists);
		
		//Create winner string
		System.out.println("-------------------GAME OVER---------------------------");
		String winnerStr = "Winners: ";
		for (int i=0;i<playerCount;i++) {
			if (cpuPlayers.get(i).isWinner()) 
				winnerStr+=cpuPlayers.get(i).getName()+" ";
		}
		System.out.println(winnerStr);
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
	

}
