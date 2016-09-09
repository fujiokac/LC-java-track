package BaseballPlayer;

public class BaseballPlayer {

	/* A baseball player has a name and a jersey number. Most players hit either right or left,
	 * but some can hit either way. This object should be able to react when a player completes
	 * a game, recording how many hits and RBIs the player earned in that game. A player has a 
	 * certain number of runs and RBIs he or she has recorded over all games played. A player 
	 * has a certain number of games he or she has played.
	 */
	private String name;
	private int jersey, num_games, RBI, runs, style;
	
	private static class Game {
		private String ID;
		private int RBI, hits;
		
		private Game(String ID, int RBI, int hits) {
			this.ID = ID;
			this.RBI = RBI;
			this.hits = hits;
		}
		
		private String print() {
			return "Game "+ ID +": Hits:"+ hits +" RBI:"+ RBI + System.lineSeparator();  
		}
	}

	private java.util.List<Game> games = new java.util.LinkedList<>();
	
	public BaseballPlayer(String name, int jersey, int games, int RBI, int runs, int style) {
		this.name = name;
		this.jersey = jersey;
		this.num_games = games;
		this.RBI = RBI;
		this.runs = runs;
		this.style = style > 1 || style < -1 ? 0 : style;
	}
	public BaseballPlayer(String name, int jersey, int style) {
		this(name, jersey, 0, 0, 0, style);
	}
	
	public void newGame(String ID, int RBI, int hits, int runs) {
		games.add(new Game(ID, RBI, hits));
		num_games++;
		this.RBI += RBI;
		this.runs += runs;
	}
	
	private String getStyle() {
		switch(style) {
			case -1: return "Leftie";
			case 0: return "Ambi";
			case 1: return "Rightie";
			default: return "error";
		}
	}
	
	public String getStats() {
		String output = "Name: "+ name + " Jersey: "+ jersey + " Style: "+ this.getStyle() + System.lineSeparator();
		output += "Total Games: "+ num_games +" RBI: "+ RBI +" Runs: "+ runs + System.lineSeparator();
		return output;
	}
	
	public String listGames() {
		String output = "";
		for(Game game : games) {
			output += game.print();
		}
		return output;
	}
	
	public String toString() {
		return this.getStats() + this.listGames();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseballPlayer john = new BaseballPlayer("John", 36, -1);
		BaseballPlayer jenny = new BaseballPlayer("Jenny", 21, 5, 100, 200, 1);
		
		
		john.newGame("Jun 2", 44, 11, 4);
		john.newGame("May 3", 99, 5, 5);
		jenny.newGame("Jan 7", 100, 13, 5);
		jenny.newGame("Feb 16", 101, 13, 3);
		System.out.print(john);
		System.out.print(jenny);
		
	}

}
