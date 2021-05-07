/**
 * 
 */

function loginUserOne() {
	
	let user = document.getElementById("userOne").value;
	
	fetch("UserServlet", {
		method: 'POST',
		header: {'Content-Type': 'application/json'},
		body: JSON.stringify({
			username: user,
			type: "login"
		})
	}).then(res => res.json())
	  .then((data) => {
			if(data == "Username not in database. ") {
				alert(data);
			} else {
				// user has been successfully logged in. 
				alert(user + " is logged in. ");
				document.getElementById("userOneRadio").innerHTML = data;
			}
		})
	
}

function loginUserTwo() {
	
	let user = document.getElementById("userTwo").value;
	
	fetch("UserServlet", {
		method: 'POST',
		header: {'Content-Type': 'application/json'},
		body: JSON.stringify({
			username: user,
			type: "login"
		})
	}).then(res => res.json())
	  .then((data) => {
			if(data == -1) {
				alert("Username is already taken. ");
			} else {
				// user has been successfully logged in. 
				alert(user + " is logged in. ");
				document.getElementById("userTwoRadio").innerHTML = data;
			}
		})
	
}

function registerUserOne() {
	
	let user = document.getElementById("userOne").value;
	
	fetch('UserServlet', {
		method: 'POST',
		header: {'Content-Type': 'application/json'},
		body: JSON.stringify({
			username: user,
			type: "register"
		})
	}).then(res => res.json())
	  .then((data) => {
			if(data == -1) {
				alert("Username is already taken. ");
			} else {
				// user has been successfully logged in. 
				alert(user + " is registered.");
				document.getElementById("userOneRadio").innerHTML = data;
			}
		})
	
}

function registerUserTwo() {
	
	let user = document.getElementById("userTwo").value;
	
	fetch('UserServlet', {
		method: 'POST',
		header: {'Content-Type': 'application/json'},
		body: JSON.stringify({
			username: user,
			type: "register"
		})
	}).then(res => res.json())
	  .then((data) => {
			if(data == -1) {
				alert("Username is already taken. ");
			} else {
				// user has been successfully logged in. 
				alert(user + " is registered. ");
				document.getElementById("userTwoRadio").innerHTML = data;
			}
		})
	
}

function startGame() {
	
	// validate game creation conditions
	if(document.getElementById("radio-one").checked) {
		document.getElementById("userOneServer").innerHTML = "Server";
	} else if(document.getElementById("radio-two").checked) {
		document.getElementById("userTwoServer").innerHTML = "Server";
	} else {
		alert("A server needs to be declared before the game starts!");
		return;
	}
	
	let user1 = document.getElementById("userOne").value;
	let user2 = document.getElementById("userTwo").value;
	
	if(user1 == "" || user2 == "") {
		alert("Two users need to be logged in for this game to start");
		return;
	}

	
	document.getElementById("game-div").style.display = "block";
	
	document.getElementById("userOnePoints").innerHTML = 0;
	document.getElementById("userTwoPoints").innerHTML = 0;
	
	document.getElementById("userOneName").innerHTML = user1;
	document.getElementById("userTwoName").innerHTML = user2;
	
	
	
	
}

function grabUserLeaderboard() {
	
	fetch('UserServlet')
		.then(res => res.json()) 
		.then((data) => {
			data.map((user) => {
				let current = document.createElement("div");
				current.innerHTML = user.username + " - wins: " + user.wins + " - points: " + user.totalPoints;
				document.getElementById("leaderboard-div").appendChild(current);
			})
		})
		
}

function incrementUserOnePoints() {
	
	let counter1 = parseInt(document.getElementById("userOnePoints").innerHTML);
	let counter2 = parseInt(document.getElementById("userTwoPoints").innerHTML);
	counter1++;
	
	let points = counter1 + counter2;
	if(points % 2 == 0 && points != 0) {
		if(document.getElementById("userOneServer").innerHTML == "Server") {
			document.getElementById("userOneServer").innerHTML = "";
			document.getElementById("userTwoServer").innerHTML = "Server";
		} else {
			document.getElementById("userTwoServer").innerHTML = "";
			document.getElementById("userOneServer").innerHTML = "Server";
		}
	}
	
	let user = document.getElementById("userOne").value;
	
	if(counter1 > 10) {
		if(counter2 < 10 || (counter1 - counter2 >= 2)) {
			alert("user one has won!");
			document.getElementById("userOnePoints").innerHTML = counter1;
			
			fetch('UserServlet', {
				method: 'POST',
				header: {'Content-Type': 'application/json'},
				body: JSON.stringify({
					username: user,
					totalPoints: counter1,
					wins: 1,
					type: "win"
				})
			}).then(res => res.json())
			.then((data) => {
				
			})
			// make requests to the backend to update leaderboard. 
		} else {
			document.getElementById("userOnePoints").innerHTML = counter1;
		}
	} else {
		document.getElementById("userOnePoints").innerHTML = counter1;
	}
	
	
}

function incrementUserTwoPoints() {
	
	let counter1 = document.getElementById("userOnePoints").innerHTML;
	let counter2 = document.getElementById("userTwoPoints").innerHTML;
	counter2++;
	
	let points = counter1 + counter2;
	if(points % 2 == 0 && points != 0) {
		if(document.getElementById("userOneServer").innerHTML == "Server") {
			document.getElementById("userOneServer").innerHTML = "";
			document.getElementById("userTwoServer").innerHTML = "Server";
		} else {
			document.getElementById("userTwoServer").innerHTML = "";
			document.getElementById("userOneServer").innerHTML = "Server";
		}
	}

	let user = document.getElementById("userTwo").value;
	
	if(counter2 > 10) {
		if(counter1 < 10 || counter2 - counter1 >= 2) {
			alert("user two has won!");
			document.getElementById("userTwoPoints").innerHTML = counter2;
			fetch('UserServlet', {
				method: 'POST',
				header: {'Content-Type': 'application/json'},
				body: JSON.stringify({
					username: user,
					totalPoints: counter2,
					wins: 1,
					type: "win"
				})
			}).then(res => res.json())
			.then((data) => {
				
			})

			// make requests to the backend to update leaderboard. 
		} else {
			document.getElementById("userTwoPoints").innerHTML = counter2;
		}
	} else {
		document.getElementById("userTwoPoints").innerHTML = counter2;
	}
}

function terminateGame() {
	
	document.getElementById("userOnePoints").innerHTML = 0;
	document.getElementById("userTwoPoints").innerHTML = 0;
	
	document.getElementById("userOneServer").innerHTML = "";
	document.getElementById("userTwoServer").innerHTML = "";
	
	document.getElementById("userOne").value = "";
	document.getElementById("userTwo").value = "";
	
	document.getElementById("radio-one").checked = false;
	document.getElementById("radio-two").checked = false;
	
	document.getElementById("userOneName").innerHTML = "";
	document.getElementById("userTwoName").innerHTML = "";
	
	document.getElementById("game-div").style.display = "none";
	
}
