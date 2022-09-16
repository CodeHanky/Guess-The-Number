import java.util.ArrayList;

public class Game {
	
	int upperBound;
	int numberToGuess;
	ArrayList<Player> players;

	public Game(int upperBound, int numberToGuess, ArrayList<Player> players) {
		this.upperBound = upperBound;
		this.numberToGuess = numberToGuess;
		this.players = players;
		
		startGame();
	}

	private void startGame() {
		
		int turnCount = 0;				//Number of turns passed since the game began
		boolean winnerExists = false;	//Flag to check for winners at the end of every turn
		/*
		 * Main game loop 
		 * The loop ends when a player(s) guesses the number; this is achieved with the winnerExists variable.
		 * There can be more than one winner
		 */		
		do 
		{
			//Notify the user of the start of a new turn
			System.out.println("----------------TURN " + (++turnCount) +"------------------------");
			
			//Re-create array of guesses
			ArrayList<Integer> guesses = new ArrayList<>();
			
			/*
			 * Players place bets
			 * Human players are shown a prompt to enter their guess.
			 * CPU players override the getNewGuess method to guess automatically.
			 */	
			for (Player player : this.players) guesses.add(player.getNewGuess(this.upperBound, numberToGuess));
			
			//Notify user about this turn's guesses and results one by one
			System.out.println("**********************");
	
			//Print guesses of both human and CPU players
			for (int i=0;i<this.players.size();i++) {
				int guess = guesses.get(i);
				System.out.print(this.players.get(i).getName() + "'s guess: " + guess);
				if (guess == numberToGuess) {
					  System.out.println(". " + this.players.get(i).getName() + " guesses correctly!"); 
					  this.players.get(i).setWinner(true);
					  winnerExists = true;
				} 
				else System.out.println();
			}
			System.out.println("**********************");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!winnerExists);
		
		//Create winner string
		System.out.println("-------------------GAME OVER---------------------------");
		
		String winnerStr = "WINNERS: | "; 
		for (Player player : this.players) { 
			if (player.isWinner()) 
				winnerStr+=player.getName()+" |"; 
		}
		System.out.println(winnerStr);
	}

}
