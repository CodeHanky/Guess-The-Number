import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int maxValue;
		Scanner keyboard = new Scanner(System.in);
		Random rd = new Random();
		int numberToGuess;
		
		ArrayList<Boolean> winners = new ArrayList<>();
		for (int i=0;i<3;i++) winners.add(false);
		
		do
		{
			System.out.print("Set a max value (0-100): ");
			maxValue=keyboard.nextInt();
		} while (maxValue<0 || maxValue>100); 
		
		Player CPU1 = new Player(true, 0, maxValue);
		Player CPU2 = new Player(true, 0, maxValue);
		Player user = new Player(false, 0, maxValue);
		numberToGuess = rd.nextInt(maxValue);
		
		do 
		{
			System.out.println("----------------NEW TURN------------------------");
			int bet, p1Guess, p2Guess;
			
			/*
			 * do { System.out.print("Place your bets (0-100): "); bet = keyboard.nextInt();
			 * 
			 * } while (bet<0 || bet>100 || !user.checkGuesses(bet));
			 */
			 
			
			p1Guess = getCPUGuess(CPU1, maxValue, numberToGuess); 
			p2Guess = getCPUGuess(CPU2, maxValue, numberToGuess);
			//user.addUserGuess(bet, numberToGuess);
			
			System.out.println("**********************");
			System.out.println("Player 1 guess: " + p1Guess);
			System.out.println("Player 2 guess: " + p2Guess);
			//System.out.println("User guess: " + bet);
			System.out.println("**********************");
			
			
			  if (p1Guess == numberToGuess) {
				  System.out.println("Player 1 guesses correctly!"); 
				  winners.set(0, true); 
			  } 
			  if (p2Guess == numberToGuess) {
				  System.out.println("Player 2 guesses correctly!"); 
				  winners.set(1, true); 
			  }
			 
			/*
			 * if (bet == numberToGuess) { System.out.println("User guesses correctly!");
			 * winners.set(2, true); }
			 */
		} while (!winners.contains(true));
		
		String winnerStr=" ";
		for (boolean win : winners) {
			if (win)
				if (winners.indexOf(win)==0)
					winnerStr.concat("Player 1, ");
				else if (winners.indexOf(win)==1)
					winnerStr.concat("Player 2, ");
				else if (winners.indexOf(win)==2)
					winnerStr.concat("User");
		}
		
		System.out.println("GAME OVER. Winner(s): " + winnerStr);
	}
	
	//Διαδικασίες παιχνιδιού
		
	public static int getCPUGuess(Player CPU, int maxValue, int numberToGuess) 
	{
		int guess;
		Random rdnew = new Random();
		
		do 
		{
			guess = rdnew.nextInt(maxValue);
		} while (!CPU.checkGuesses(guess));
		
		CPU.addGuess(guess, numberToGuess);
		return guess;
	}
	
}

