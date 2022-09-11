
public class GuessDistance {
	private int guessNum;
	private boolean goBigger;
	private boolean goSmaller;
	
	public GuessDistance(int guessNum) {
		this.guessNum = guessNum;
	}

	public int getGuessNum() {
		return guessNum;
	}

	public boolean isGoBigger() {
		return goBigger;
	}

	public boolean isGoSmaller() {
		return goSmaller;
	}

	public void setGoBigger(boolean goBigger) {
		this.goBigger = goBigger;
	}

	public void setGoSmaller(boolean goSmaller) {
		this.goSmaller = goSmaller;
	}
	
}
