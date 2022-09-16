import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	protected ArrayList<Integer> guesses = new ArrayList<>(); 	//The list of guesses the player has made
	protected int leftGuessBound;								//The lower limit for the player's guesses (exclusive)
	protected int rightGuessBound;								//The lower limit for the player's guesses (exclusive)
	private boolean isWinner;									//Conditional flag for whether the player has guessed correctly
	private String name;										//The player's name
	
	public Player(String name, int leftGuessBound, int rightGuessBound) {
		this.name = name;
		this.leftGuessBound=leftGuessBound;
		this.rightGuessBound=rightGuessBound;
		this.setWinner(false);
	}

	public ArrayList<Integer> getGuesses() {
		return guesses;
	}
	
	public int getNewGuess(int upperLimit, int numberToGuess) {
		int guess;
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("It's " + this.getName() + "'s turn" + System.lineSeparator() 
						+ "(HINT: your guess must be between " + this.leftGuessBound + " and " + this.rightGuessBound + ".)");
		do { 
			System.out.print("Place your bet (1-" + upperLimit + "): "); 
			guess = keyboard.nextInt();
		} while (!this.guessIsUnique(guess));
		System.out.println();
		
		this.addGuess(guess, numberToGuess);
		return guess;
	}
	
	public boolean guessIsUnique(int guess) {
		if (this.guesses.contains(guess)) {
			System.out.println("You've already used that number!");
			return false;
		}
		
		if (guess<=this.leftGuessBound || guess>=this.rightGuessBound) {
			System.out.println("Choose a number between " + this.leftGuessBound + " and " + this.rightGuessBound + "!");
			return false;
		}
		
		return true;
	}
	
	public void addGuess(int guess, int numberToGuess) {
		
		this.guesses.add(guess);
		if (guess == numberToGuess)	
			return;
		else if (guess<numberToGuess)
			leftGuessBound=guess;
		else //guess>numberToGuess
			rightGuessBound=guess;
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public String getName() {
		return name;
	}
	
}
