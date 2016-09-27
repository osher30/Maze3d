package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.serach.Solution;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void displayMessage(String msg);
	//Checkkkkkkkkkkkkkkkkkkkkk
	//void printAnswers(String[] filelist);
	//void notifySolutionIsReady(String name);
	//void displayMazeSolution(Solution<Position> solution);
	//void printErrorMessage(String[] msg);
	
	
	//int askForXYZ();
	//void displayCrossSection(Maze3d maze, int index); 
	//void displaySolution(String name);
	//void getDirList(String path);
	void start();	
}
