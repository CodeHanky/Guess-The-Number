import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static final int GLOBAL_LOWER_BOUND = 1;
	public static final int GLOBAL_UPPER_BOUND = 1000; //The guess range will be at least [GLOBAL_LOWER_BOUND,GLOBAL_UPPER_BOUND]
		
	//Enumeration of all available game modes
	
	//If you need to add more game modes, add a new entry here...
	public static enum MODES {
		MAN_VS_MACHINE, CPU_BATTLE, MANO_A_MANO, MIXED
	}
	
	//...and continue here, with a proper title and a description. Everything else is calculated dynamically.
	@SuppressWarnings("serial")
	public static ArrayList<GameMode> MODE_INFO = new ArrayList<>() {
		{
			add(new GameMode(MODES.MAN_VS_MACHINE, 	"Man VS Machine", 		"Face off against a number of CPU opponents"));
			add(new GameMode(MODES.CPU_BATTLE, 		"Battle of the CPUs", 	"Have CPUs face off against each other (you choose the number to guess)"));
			add(new GameMode(MODES.MANO_A_MANO, 	"Mano-a-mano", 			"Face off against a number of friends"));
			add(new GameMode(MODES.MIXED, 			"Mixed", 				"Face off against a number of friends and CPU opponents"));
		}
	};

	//User chooses a mode
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in); // Input reader
		
		System.out.println("Global range for guess bounds is [" + GLOBAL_LOWER_BOUND + "," + GLOBAL_UPPER_BOUND + "]");
		System.out.println("If you would like to change the bounds, stop the program and edit the GLOBAL_LOWER_BOUND/GLOBAL_UPPER_BOUND variables");
		System.out.println("***********************************************************");
		
		do {
			switch (chooseMode(keyboard)) { // Player chooses a mode
			case MAN_VS_MACHINE:
				manVSmachine();
				break;
			case CPU_BATTLE:
				cpuBattle();
				break;
			case MANO_A_MANO:
				manoAMano();
				break;
			case MIXED:
				mixedMode();
				break;
			}
			
			System.out.println("--------------------------------------------------");
			
		} while (playerWillPlayAgain(keyboard));
		
		keyboard.close();
	}

	//MODE SELECTION AND PREPARATION METHODS

	private static MODES chooseMode(Scanner keyboard) {

		int choice = -1; // User's choice for game mode

		System.out.println("Available game modes: " + System.lineSeparator());
		
		for (int i=0;i<MODE_INFO.size();++i) {
			printModeInfo(MODE_INFO.get(i),i+1);
		}

		do {
			if (choice > 0)
				System.out.print("Please choose a valid game mode!");

			System.out.print("Choose a game mode (1,2,3,etc): ");
			choice = keyboard.nextInt();

		} while (!MODE_INFO.contains(MODE_INFO.get(choice-1)));

		return MODE_INFO.get(choice-1).getMode();
	}
	
	private static void printModeInfo(GameMode mode, int ascOrder) {
		System.out.println(ascOrder + ") " + mode.getTitle() + " - " + mode.getDescription());
	}

	private static void manVSmachine() {
		
		Scanner keyboard = new Scanner(System.in);			//Input reader
		
		ArrayList<Player> userPlayers = new ArrayList<>();	//List containing all human players
		ArrayList<CPUplayer> cpuPlayers = new ArrayList<>();//List containing all CPU players
		ArrayList<Player> allPlayers = new ArrayList<>();	//List containing all players (human and CPU)
		
		int upperBound = systemChosenUpperBound();
		int numberToGuess = systemChosenNumberToGuess(upperBound); 	
		
		//User player creation
		createSingleHumanPlayer(userPlayers,keyboard,GLOBAL_LOWER_BOUND,upperBound);
		
		//CPU player creation
		createCPUOpponents(cpuPlayers,keyboard,GLOBAL_LOWER_BOUND,upperBound);
		
		allPlayers.addAll(userPlayers);
		allPlayers.addAll(cpuPlayers);
		
		//Game start
		new Game(upperBound, numberToGuess, allPlayers);
	}

	private static void cpuBattle() {
		
		Scanner keyboard = new Scanner(System.in);			//Input reader
		
		ArrayList<CPUplayer> cpuPlayers = new ArrayList<>();//List containing all CPU players
		ArrayList<Player> allPlayers = new ArrayList<>();	//List containing all players (human and CPU)
		
		int upperBound = userChosenUpperBound(GLOBAL_LOWER_BOUND-1, keyboard);
		int numberToGuess = userChosenNumberToGuess(upperBound, keyboard);
		
		//CPU player creation
		createCPUPlayers(cpuPlayers, GLOBAL_LOWER_BOUND, upperBound, userChosenCpuOpponentCount(keyboard));
		
		allPlayers.addAll(cpuPlayers);
		
		//Game start
		new Game(upperBound, numberToGuess, allPlayers);
		
	}
	
	private static void manoAMano() {
		
		Scanner keyboard = new Scanner(System.in);			//Input reader
		
		ArrayList<Player> userPlayers = new ArrayList<>();	//List containing all human players
		
		int upperBound = systemChosenUpperBound();
		int numberToGuess = systemChosenNumberToGuess(upperBound); 	
		
		//User player creation
		createMultipleHumanPlayers(userPlayers,keyboard,GLOBAL_LOWER_BOUND,upperBound);
			
		//Game start
		new Game(upperBound, numberToGuess, userPlayers);
		
	}
	
	private static void mixedMode() {
		
		Scanner keyboard = new Scanner(System.in);			//Input reader
		
		ArrayList<Player> userPlayers = new ArrayList<>();	//List containing all human players
		ArrayList<CPUplayer> cpuPlayers = new ArrayList<>();//List containing all CPU players
		ArrayList<Player> allPlayers = new ArrayList<>();	//List containing all players (human and CPU)
		
		int upperBound = systemChosenUpperBound();
		int numberToGuess = systemChosenNumberToGuess(upperBound);
		
		//Human player creation
		createMultipleHumanPlayers(userPlayers,keyboard,GLOBAL_LOWER_BOUND,upperBound);
		
		//CPU Player creation
		createCPUOpponents(cpuPlayers,keyboard,GLOBAL_LOWER_BOUND,upperBound);
		
		allPlayers.addAll(userPlayers);
		allPlayers.addAll(cpuPlayers);
		
		//Game start
		new Game(upperBound, numberToGuess, allPlayers);
		
	}
	
	//METHODS FOR DETERMINING BOUNDS AND THE NUMBER TO GUESS
	
	/* 
	 * System decides the number to be guessed.
	 * As mentioned above, the desired guess range must be [GLOBAL_LOWER_BOUND, upperBound] (e.g. [1,50] inclusive)
	 * 
	 * Same as above, nextInt is forced to return a number in the range [0, upperBound] (e.g. [0,50])
	 * Finally, since the desired guess range is [GLOBAL_LOWER_BOUND, upperBound], 
	 * the condition checks that the lower bound is at least equal to GLOBAL_LOWER_BOUND (e.g. 1, meaning the range is at least [1,50]). 
	 */
	private static int systemChosenNumberToGuess(int upperBound) {
		Random rd = new Random(); //Seed
		int numberToGuess;
		
		do {
			numberToGuess = rd.nextInt(upperBound+1);
		} while (numberToGuess<GLOBAL_LOWER_BOUND);
		return numberToGuess;
	}

	/*
	 * System decides an upper limit. 
	 * If GLOBAL_UPPER_BOUND is 100, nextInt is forced to return a number in the range 
	 * [0, GLOBAL_UPPER_BOUND+1), aka [0,GLOBAL_UPPER_BOUND] (e.g. [0,100]).
	 * 
	 * The desired range for the number to guess is [GLOBAL_LOWER_BOUND, upperBound], where upperBound is determined by the system in this case.
	 * Therefore, the condition ensures that upperBound will be at least GLOBAL_LOWER_BOUND+1, 
	 * meaning that the guess range will end up being at least [GLOBAL_LOWER_BOUND, GLOBAL_LOWER_BOUND+1]
	 */		
	private static int systemChosenUpperBound() {
		Random rd = new Random();	//Seed
		int upperBound;
		
		do {
			upperBound = rd.nextInt(GLOBAL_UPPER_BOUND+1); 
		} while (upperBound<GLOBAL_LOWER_BOUND+1);
		
		return upperBound;
	}
	
	private static int userChosenUpperBound(int upperBound, Scanner keyboard) {
		//User inputs an upper limit
		do
		{
			if (upperBound!=0) System.out.println("Please enter a valid number for the upper limit");
			
			System.out.print("Set an upper limit (" + (GLOBAL_LOWER_BOUND+1) + " - " + GLOBAL_UPPER_BOUND + "): ");
			upperBound=keyboard.nextInt();
		} while (upperBound<(GLOBAL_LOWER_BOUND+1) || upperBound>GLOBAL_UPPER_BOUND); 
		System.out.println();
		return upperBound;
	}
	
	private static int userChosenNumberToGuess(int upperBound, Scanner keyboard) {
		int numberToGuess = 0;
		//User inputs the number to be guessed
		do
		{
			if (numberToGuess!=0) System.out.println("Please enter a valid number");
			
			System.out.print("Choose the number to be guessed (" + GLOBAL_LOWER_BOUND + " - " + upperBound + "): ");
			numberToGuess=keyboard.nextInt();
		} while (numberToGuess<GLOBAL_LOWER_BOUND || numberToGuess>upperBound); 
		System.out.println();
		
		return numberToGuess;
	}

	//HUMAN USER CREATION METHODS
	
	private static void createSingleHumanPlayer(ArrayList<Player> userPlayers, Scanner keyboard, int globalLowerBound, int upperBound) {
		createUserPlayer(1, userPlayers, keyboard, globalLowerBound, upperBound);
	}
	
	private static void createMultipleHumanPlayers(ArrayList<Player> userPlayers, Scanner keyboard, int globalLowerBound, int upperBound) {
		int humanPlayerCount=2;
		//User chooses the amount of human players to play against
		do {
			if (humanPlayerCount<2) System.out.println("Please enter a valid number");
						
			System.out.print("Choose the number of human players (2 or more): ");
			humanPlayerCount = keyboard.nextInt();
		} while (humanPlayerCount<2);
		
		for (int i=0;i<humanPlayerCount;i++) {
			createUserPlayer(i+1, userPlayers, keyboard, globalLowerBound, upperBound);
		}
	}

	private static void createUserPlayer(int ascOrder, ArrayList<Player> userPlayers, Scanner keyboard, int globalLowerBound, int upperBound) {
		System.out.print("Please enter a name for Player " + ascOrder + ": ");
		userPlayers.add(new Player(keyboard.next(), globalLowerBound-1, upperBound+1));
	}
	
	//CPU USER CREATION METHODS
	
	private static int userChosenCpuOpponentCount(Scanner keyboard) {
		int cpuCount=2;
		
		//User chooses the amount of CPUs
		do {
			if (cpuCount<2) System.out.println("Please enter a valid number");
						
			System.out.print("Choose the number of CPU players (2 or more): ");
			cpuCount = keyboard.nextInt();
		} while (cpuCount<2);
		System.out.println();
		return cpuCount;
	}
	
	private static void createCPUOpponents(ArrayList<CPUplayer> cpuPlayers, Scanner keyboard, int globalLowerBound, int upperBound) {
		int cpuCount=1;
		//User chooses the amount of CPUs to play against
		do {
			if (cpuCount<1) System.out.println("Please enter a valid number");
						
			System.out.print("Choose the number of CPU opponents (1 or more): ");
			cpuCount = keyboard.nextInt();
		} while (cpuCount<1);
		
		//Add CPUs to array
		createCPUPlayers(cpuPlayers, globalLowerBound, upperBound, cpuCount);
		System.out.println();
	}

	private static void createCPUPlayers(ArrayList<CPUplayer> cpuPlayers, int globalLowerBound, int upperBound,
			int cpuCount) {
		for (int i=0;i<cpuCount;i++) cpuPlayers.add(new CPUplayer("CPU_"+(i+1), globalLowerBound-1, upperBound+1));
	}
	
	//Input prompt for whether the user will play another round
		private static boolean playerWillPlayAgain(Scanner keyboard) {
			String answer;

			do {
				System.out.print("Would you like to play again? (y/n): ");
				answer = keyboard.next();
				
				switch ((answer.toLowerCase().charAt(0))) {
					case 'y':
						System.out.println("A new round will begin.");
						System.out.println("--------------------------------------------------");
						return true;
					case 'n' :
						System.out.println("Thank you for playing!");
						return false;
					default:
						System.out.println("Please enter a valid answer!");
						break;
				}
			} while (true);
		}
}
