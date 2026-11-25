# R6-Team-Assistant
Final Project for Tools

Project Goal:
I will build a Java program called R6 Team Assistant that takes inputs from the user to pick what map, bomb site, attacking or defending, bans, and teammate operators they are playing with. Then the program will analyze all of those inputs and give recommendations on which operator they should play. 

MVP Features (Do):
Map, bomb site, and attacking/defending selection.
Operator bans. (excluding banned operators from recommendations)
Manual team builder that lets users enter teammates selected operators (exclude too).
Analyze the team and identify missing roles and overlapping utility.
Operator recommendation system that ranks available operators based on everything.
Random balanced five-operator team generator (if 5-stack).

Future Plans (Schedule): 
User interface with operator portraits and interactive maps.
Save and load favorite team compositions.
More advanced map specific strategies and site recommendations.
Operator statistics and updates based on current game balancing updates.  

Tech Stack: 
Language: Java 
Libraries and packages: Java standard library: 
Scanner - to read terminal input.
ArrayList - to store the collection of operators and team selections that might change.
HashMap (maybe) - store and retrieve operator information by using their name as a key.
and Random - select the operators randomly for the random balanced team generator.
Input: Select map, sites, sides, bans, and operators.
Data storage: CSV and txt (maybe) files which include operators.csv (for operators), maps.csv (for maps), and teams.txt (maybe for saved team compositions if there is a save/load feature).
External tools: Git and GitHub
