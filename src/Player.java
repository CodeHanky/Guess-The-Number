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
	
	public int getNewUserGuess(int upperLimit, int numberToGuess) {
		int guess;
		Scanner keyboard = new Scanner(System.in);
		
		do { 
			System.out.print("Place your bet (1-" + upperLimit + ") :"); 
			guess = keyboard.nextInt();
			System.out.println();
		} while (!this.guessIsUnique(guess));

		this.addGuess(guess, numberToGuess);
		keyboard.close();
		return guess;
	}
	
	public boolean guessIsUnique(int guess) {
		if (this.guesses.contains(guess)) {
			System.out.println("You've already used that number!");
			return false;
		}
		return (guess>this.leftGuessBound && guess<this.rightGuessBound);
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
