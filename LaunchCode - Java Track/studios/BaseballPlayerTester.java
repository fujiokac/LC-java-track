import static org.junit.Assert.*;

import org.junit.Test;

public class BaseballPlayerTester {

	// Will fail if status syntax changes
	@Test
	public void testConstructor() {
		String jennyStats = "Name: Jenny Jersey: 21 Style: Rightie" + System.lineSeparator() +
			"Total Games: 5 RBI: 100 Runs: 200" + System.lineSeparator();
		String johnStats = "Name: John Jersey: 36 Style: Leftie" + System.lineSeparator() +
			"Total Games: 0 RBI: 0 Runs: 0" + System.lineSeparator();
		BaseballPlayer jenny = new BaseballPlayer("Jenny", 21, 5, 100, 200, 1);
		assertEquals("Full constructor failed", jennyStats, jenny.getStats());
		BaseballPlayer john = new BaseballPlayer("John", 36, -1);
		assertEquals("Abridged constructor failed", johnStats, john.getStats());
	}

	@Test
	public void testNewGame() {
		String jennyStats = "Name: Jenny Jersey: 21 Style: Rightie" + System.lineSeparator() +
			"Total Games: 7 RBI: 301 Runs: 208" + System.lineSeparator();
		BaseballPlayer jenny = new BaseballPlayer("Jenny", 21, 5, 100, 200, 1);
		jenny.newGame("Jan 7", 100, 13, 5);
		jenny.newGame("Feb 16", 101, 13, 3);
		assertEquals("newGame failed", jennyStats, jenny.getStats());
	}
	
	@Test
	public void testListGames() {
		String jennyGames = "Game Jan 7: Hits:13 RBI:100" + System.lineSeparator() +
			"Game Feb 16: Hits:13 RBI:101" + System.lineSeparator();
		BaseballPlayer jenny = new BaseballPlayer("Jenny", 21, 5, 100, 200, 1);
		jenny.newGame("Jan 7", 100, 13, 5);
		jenny.newGame("Feb 16", 101, 13, 3);
		assertEquals("ListGames failed", jennyGames, jenny.listGames());
	}
}
