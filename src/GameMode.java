
public class GameMode {

	Main.MODES mode;
	String title;
	String description;
	
	public GameMode(Main.MODES mode, String title, String description) {
		this.mode = mode;
		this.title = title;
		this.description = description;
	}

	public Main.MODES getMode() {
		return mode;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
}
