package view;

import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void displayMessage(String msg);
	
	//void printAnswers(String[] filelist);
	//void notifySolutionIsReady(String name);
	void displayMazeSolution(List<Position> solution);
	//void printErrorMessage(String[] msg);
	
	
	//int askForXYZ();
	//void displayCrossSection(Maze3d maze, int index); 
	//void displaySolution(String name);
	//void getDirList(String path);
	void start();	
}
