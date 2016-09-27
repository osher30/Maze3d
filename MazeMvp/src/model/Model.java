package model;

import algorithms.mazeGenerators.Maze3d;

public interface Model {
	void generateMaze(String name,int floor, int rows, int cols);
	Maze3d getMaze(String name);
	void solveMaze(final String name, final String algorithm);
	void saveMaze(Maze3d name, String fileName);
	void loadMaze(String fileName, String name);
	void exit();	
}
