import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// handle insert into user db
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Gson g = new Gson();
		
		User newUser = g.fromJson(request.getReader(), User.class);
		
		String username = newUser.username;
		String type = newUser.type; // register or login
		
		if(username == null || username.isBlank() || type == null || type.isBlank()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "User info missing";
			pw.write(g.toJson(error));
			pw.flush();
		}
		
		//otherwise, username is valid
		
		int userID = JDBCConnector.loginUser(username); // this will return -1 if there's no user
		
		if(type.equals("register")) {
			
			if(userID == -1) {
				
				int userRegistered = JDBCConnector.registerUser(username);
				response.setStatus(HttpServletResponse.SC_OK);
				pw.write(g.toJson(username));
				pw.flush();
			} else {
				// already a user, not a valid login
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				String error = "Username already taken";
				pw.write(g.toJson(error));
				pw.flush();
				
			}
		} else if(type.equals("win") ) {
			System.out.println("here" + newUser.username + " " + newUser.totalPoints + " " + newUser.wins);
			int user = JDBCConnector.updateUserPointsAndWins(newUser.username, newUser.totalPoints, newUser.wins);
			response.setStatus(HttpServletResponse.SC_OK);
			pw.write(g.toJson(user));
			pw.flush();
			
		} else { // type can only be otherwise login
			if(userID == -1) {
				// not a username in the db
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				String error = "Username not in database. ";
				pw.write(g.toJson(error));
				pw.flush();
			} else {
				response.setStatus(HttpServletResponse.SC_OK);
				pw.write(g.toJson(username));
				pw.flush();
			}
		}
		
	}
	
	// handle grab user to check for duplicates, or login
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// we want to get the leaderboard
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Gson g = new Gson();
		
		ArrayList<User> userList = JDBCConnector.grabUsers();
		
		response.setStatus(HttpServletResponse.SC_OK);
		pw.write(g.toJson(userList));
		pw.flush();
		
	}

}
