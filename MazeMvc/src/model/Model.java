package model;

import algorithms.mazeGenerators.Maze3d;
import controller.Controller;

public interface Model {
	void generateMaze(String name,int floor, int rows, int cols);
	Maze3d getMaze(String name);
	void exit();
	void setController(Controller controller);
	void saveMaze(Maze3d name, String fileName);
	void loadMaze(String filename, Maze3d Name);
	void solveMaze(String name, String algo);

}




