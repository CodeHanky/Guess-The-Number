import java.util.Random;

public class CPUplayer extends Player {

	public CPUplayer(String name, int leftGuessBound, int rightGuessBound) {
		super(name, leftGuessBound, rightGuessBound);
	}
	
	@Override
	public int getNewGuess(int upperLimit, int numberToGuess) {
		int guess;
		Random rdnew = new Random();

		do {
			guess = rdnew.nextInt(this.rightGuessBound);
		} while (!this.guessIsUnique(guess));

		this.addGuess(guess, numberToGuess);
		return guess;
	}

	@Override
	public boolean guessIsUnique(int guess) {
		if (this.guesses.contains(guess)) return false;						//CPU has already chosen that number, so it's not unique
		return (guess>this.leftGuessBound && guess<this.rightGuessBound); 	//If CPU chooses a number not in the range, it must choose again, otherwise its choice is valid
	}
}
