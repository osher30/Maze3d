package model;

import algorithms.mazeGenerators.Maze3d;
import controller.Controller;

public interface Model {
	void generateMaze(String name,int floor, int rows, int cols);
	Maze3d getMaze(String name);
	void exit();
	void setController(Controller controller);
}




