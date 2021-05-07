
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class JDBCConnector {
	
	public static int loginUser(String user) {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int userID = -1;
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ping-pong?user=root&password=root");
			String username = user;
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Users WHERE username='" + username + "'");
			while(rs.next()) {
				userID = rs.getInt("userID");
			}
			
		} catch(SQLException sqle) {
			System.out.println("SQLE in loginUser. ");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return userID;
	}
	
	public static int registerUser(String user) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int userID = -1;
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ping-pong?user=root&password=root");
			String username = user;
			st = conn.createStatement();
			st.execute("INSERT INTO Users(username, totalPoints, wins) VALUES ('" + username + "', 0, 0);");
			
		} catch(SQLException sqle) {
			System.out.println("SQLE in RegisterUser. ");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return userID;
	}
	
	
	
	public static int updateUserPointsAndWins(String user, int points, int wins) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int res = 0;
		int userID = -1;
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ping-pong?user=root&password=root");
			String username = user;
			int currentPoints = 0;
			int currentWins = 0;
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Users WHERE username='" + username + "'");
			while(rs.next()) {
				userID = rs.getInt("userID");
				currentPoints = rs.getInt("totalPoints");
				currentWins = rs.getInt("wins");
			}
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ping-pong?user=root&password=root");
			st = conn.createStatement();
			currentPoints += points;
			res = st.executeUpdate("UPDATE Users SET totalPoints=" + currentPoints + " WHERE username='" + username + "'");
			System.out.println(currentPoints);
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ping-pong?user=root&password=root");
			st = conn.createStatement();
			currentWins += wins;
			
			res = st.executeUpdate("UPDATE Users SET wins=" + currentWins + " WHERE username='" + username + "'");
			System.out.println(currentWins);
		} catch(SQLException sqle) {
			System.out.println("SQLE in loginUser. ");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return userID;
	}
	
	public static ArrayList<User> grabUsers() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<User> userList = new ArrayList<User>();
		int userID = -1;
		
		String username = "";
		int totalPoints = 0;
		int wins = 0;
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ping-pong?user=root&password=root");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Users");
			while(rs.next()) {
				username = rs.getString("username");
				totalPoints = rs.getInt("totalPoints");
				wins = rs.getInt("wins");
				User newUser = new User(username, totalPoints, wins);
				userList.add(newUser);
			}
			
			Collections.sort(userList, new Comparator<User>(){
				   public int compare(User t1, User t2){
					   int current = t2.wins - t1.wins;
					   if(current == 0) {
						  return (int) (t1.totalPoints - t2.totalPoints);
					   } else {
						  return (int) (t2.wins - t1.wins);
					   }
				   }
				});
			
		} catch(SQLException sqle) {
			System.out.println("SQLE in loginUser. ");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return userList;
	}
}
