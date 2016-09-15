package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import controller.Controller;;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void setCommands(HashMap<String, Command> commands);
	void start();	
	void setController(Controller controller);
	int askForXYZ();
	void displayCrossSection(Maze3d maze, int index); 
	void displaySolution(String name);
}

/*
package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze2d;
import controller.Command;
import controller.Controller;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze2d maze);
	void setCommands(HashMap<String, Command> commands);
	void start();
	void setController(Controller controller);
}
*/