package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import controller.Controller;;

/**
 * Interface of mvc view. 
 */
public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void setCommands(HashMap<String, Command> commands);
	void start();	
	void setController(Controller controller);
	int askForXYZ();
	void displayCrossSection(Maze3d maze, int index); 
	void displayMessage(String msg);
}
