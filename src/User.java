
public class User {
	
	String username;
	String type;
	int totalPoints;
	int wins;
	
	User(String user, int tp, int win) {
		this.username = user;
		this.totalPoints = tp;
		this.wins = win;
	}

}
