# SmartTwigs Ping-Pong Leaderboard Challenge

## How to get the app running 

I've attached a zipped file version of the project that you should be able to import into Eclipse and run. In order to connect to the MySQL database, run a connection of MySQL on your localhost with username=root and password=root, and create a schema called 'ping-pong'. Run the SQL script I've included in the /src folder and this will initialize an SQL table that uses the variable names and types I used across the backend. 

After importing the zip and creating the SQL database, you'll have to run the project on an Apache Tomcat server, and in this instance I used Apache Tomcat 9.0. I'm not sure if you'll have to download it on your end, but if you do: https://tomcat.apache.org/download-90.cgi

Upon download, go to the Servers tab above the Eclipse console (assuming you're using the Web view), and you should be able to create a new Tomcat instance - make sure to give Eclipse access to the folder you downloaded Tomcat to. 

Start the Tomcat server. After starting, right click the root folder of the project, and click Run As -> Run on Server. This should bring up the web page of the index.html in a sort of pseudo-browser in Eclipse. Copy and paste the url at the top into an actual browser and you'll find the project running there. 
