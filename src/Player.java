import java.util.ArrayList;

public class Player {
	private ArrayList<Integer> guesses = new ArrayList<>();						//The list of guesses the player has made
	private ArrayList<GuessDistance> guessesTemperature = new ArrayList<>();	//
	private boolean isCPU;
	private int leftGuessBound;
	private int rightGuessBound;
	private boolean isWinner;
	
	public Player(boolean isCPU, int leftGuessBound, int rightGuessBound) {
		this.isCPU=isCPU;
		this.leftGuessBound=leftGuessBound;
		this.rightGuessBound=rightGuessBound;
		this.setWinner(false);
	}

	public ArrayList<Integer> getGuesses() {
		return guesses;
	}
	
	public boolean checkGuesses(int guess) {
		if (this.guesses.contains(guess)) {
			if (!this.isCPU) System.out.println("You've already used that number!");
			return false;
		}
		
		if (this.isCPU) {
			return (guess>this.leftGuessBound && guess<this.rightGuessBound);
		}
		return true;
	}
	
	public int addGuess(int guess, int numberToGuess) {
		GuessDistance newGuess = new GuessDistance(guess);
		int newIndex=0;
		int indexSmaller;
		int indexBigger;
		boolean isHighest=true;
		
		if (guess == numberToGuess)	return -1;
		
		if (this.guesses.size()==0) {
			if (guess > numberToGuess) {
				this.rightGuessBound=guess;
				newGuess.setGoBigger(false);
				newGuess.setGoSmaller(true);
			}
			else {
				this.leftGuessBound=guess;
				newGuess.setGoBigger(true);
				newGuess.setGoSmaller(false);
			}
			
		}
		else if (this.guesses.size()==1) {
			if (guess > numberToGuess) {
				if (guess < this.guesses.get(0)) {
					isHighest=false;
					this.rightGuessBound=guess;
				}
				newGuess.setGoBigger(false);
				newGuess.setGoSmaller(true);
			}
			else if (guess < numberToGuess) {
				if (guess > this.guesses.get(0))
					this.leftGuessBound=guess;
				else 
					isHighest=false;
				newGuess.setGoBigger(true);
				newGuess.setGoSmaller(false);
			}
		}
		else {
			for (int g : this.guesses) {
				if (guess < g)
				{
					boolean leftLimitExists;
					boolean rightLimitExists;
					
					isHighest=false;
					newIndex = this.guesses.indexOf(g);
					indexSmaller = newIndex-1;
					if (indexSmaller==-1) indexSmaller=0;
					indexBigger = newIndex;
					
					leftLimitExists = this.guessesTemperature.get(indexSmaller).isGoBigger();
					rightLimitExists = this.guessesTemperature.get(indexBigger).isGoSmaller();
					
					if (leftLimitExists==rightLimitExists) {
						if (guess>numberToGuess) {
							newGuess.setGoSmaller(true);
							this.rightGuessBound=guess;
						}
						else {
							newGuess.setGoSmaller(false);
							this.leftGuessBound=guess;
						}
							
						newGuess.setGoBigger(!newGuess.isGoSmaller());
					}
					else if (leftLimitExists) {
						newGuess.setGoBigger(true);
						newGuess.setGoSmaller(false);
					}
					else if (!leftLimitExists && this.leftGuessBound<g) {
						this.leftGuessBound=g;
						newGuess.setGoBigger(true);
						newGuess.setGoSmaller(false);
					}
					else if (rightLimitExists==true) {
						newGuess.setGoSmaller(true);
						newGuess.setGoBigger(false);
					}
					else if (!rightLimitExists && this.rightGuessBound>g) {
						this.rightGuessBound=g;
						newGuess.setGoBigger(true);
						newGuess.setGoSmaller(false);
					}
					
					break;
				}
			}
		}
		
		if (isHighest) {
			newIndex=this.guesses.size();
			if (this.guesses.size()>1) {
				if (guess > numberToGuess) {
					this.rightGuessBound=guess;
					newGuess.setGoBigger(false);
					newGuess.setGoSmaller(true);
				}
				else {
					this.leftGuessBound=guess;
					newGuess.setGoBigger(true);
					newGuess.setGoSmaller(false);
				}
			}
		}
		this.guesses.add(newIndex, guess);
		this.guessesTemperature.add(newIndex, newGuess);
		
		return newIndex;
	}

	public void addUserGuess(int bet, int numberToGuess) {
		
		int index = addGuess(bet, numberToGuess);
		
		if (index==-1) return;
		
		GuessDistance g = this.guessesTemperature.get(index);
		
		if (this.guesses.size()==1) {
			if (g.isGoBigger()) {
				System.out.println("Go higher than " + g.getGuessNum());
			}
			else
				System.out.println("Go lower than " + g.getGuessNum());
			
			return;
		}
		
		if (index==0) {
			if (g.isGoBigger()) {
				GuessDistance next = this.guessesTemperature.get(index+1);
				if (next.isGoBigger())
					System.out.println("Go higher than " + next.getGuessNum());
				else if (next.isGoSmaller())
					System.out.println("Go between " + g.getGuessNum() + " and " + next.getGuessNum());
			}
			else if (g.isGoSmaller()) {
				System.out.println("Go lower than " + g.getGuessNum());
			}
		}
		else if (index==this.guesses.size()-1) {
			if (g.isGoSmaller()) {
				GuessDistance previous = this.guessesTemperature.get(index-1);
				if (previous.isGoSmaller())
					System.out.println("Go lower than " + previous.getGuessNum());
				else if (previous.isGoBigger())
					System.out.println("Go between " + previous.getGuessNum() + " and " + g.getGuessNum());
			}
			else if (g.isGoBigger()) {
				System.out.println("Go lower than " + g.getGuessNum());
			}
		}
		else {
			GuessDistance previous = this.guessesTemperature.get(index-1);
			GuessDistance next = this.guessesTemperature.get(index+1);
			
			if (g.isGoBigger() && previous.isGoBigger()) {
				if (next.isGoBigger()) 
					System.out.println("Go higher than " + next.getGuessNum());
				else if (next.isGoSmaller())
					System.out.println("Go between " + g.getGuessNum() + " and " + next.getGuessNum());
			}
			else if (g.isGoSmaller() && previous.isGoBigger()) {
				System.out.println("Go between " + previous.getGuessNum() + " and " + g.getGuessNum());
			}
			else if (g.isGoBigger() && next.isGoSmaller()) {
				System.out.println("Go between " + g.getGuessNum() + " and " + next.getGuessNum());
			}
			else if (g.isGoSmaller() && next.isGoSmaller()) {
				if (previous.isGoSmaller()) 
					System.out.println("Go lower than " + previous.getGuessNum());
				else if (previous.isGoBigger())
					System.out.println("Go between " + previous.getGuessNum() + " and " + g.getGuessNum());
			}
		}
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}
	
}
