package boot;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.mazeGenerators.cellLastSelector;
import algorithms.mazeGenerators.cellRandomSelector;

public class Run {

	public static void main(String[] args) {
		testMazeGenerator(new SimpleMaze3dGenerator());
		testMazeGenerator(new GrowingTreeGenerator(new cellRandomSelector()));
		testMazeGenerator(new GrowingTreeGenerator(new cellLastSelector()));
			
	}
private static void testMazeGenerator(Maze3dGenerator mg){ 
		//prints the time it takes the algorithm to run
		System.out.print("time it takes the algorithm to run : ");
		System.out.println(mg.measureAlgotithmtTime(5, 5, 5));
		// generate another 3d maze
		Maze3d maze=mg.generate(3, 3, 3);
		//print the new maze
		System.out.println(maze.toString());
		// get the maze entrance 
		Position p=maze.getStartPosition(); 
		// print the position
		System.out.println("maze start position : " + p); // format "{x,y,z}"
		// get all the possible moves from a position
		String[] moves=maze.getPossibleMoves(p);
		// print the moves 
		System.out.println("Posibole moves are :");
		for(String move : moves){
			System.out.println(move);}
		// prints the maze exit position 
		System.out.println("maze goal position : " + 
		maze.getGoallPosition());

		try {
			// get 2d cross sections of the 3d maze
			int[][] maze2dx=maze.getCrossSectionByX(2);
			System.out.println("new maze 2d by cols" );
			for(int z=0;z<maze.getFloor();z++){
				for(int y=0;y<maze.getRow();y++)
					System.out.print(maze2dx[z][y]);
				System.out.print("\n");
			}
			int[][] maze2dy=maze.getCrossSectionByY(3);
			System.out.println("new maze 2d by rows:" );
			for(int z=0;z<maze.getFloor();z++){
				for(int x=0;x<maze.getCols();x++)
					System.out.print(maze2dy[z][x]);
				System.out.print("\n");
			}
			
			int[][] maze2dz=maze.getCrossSectionByZ(1); 
			System.out.println("new maze 2d by floors:" );
			for(int y=0;y<maze.getRow();y++){
				for(int x=0;x<maze.getCols();x++)
					System.out.print(maze2dz[y][x]);
				System.out.print("\n");
			}
		}
		
		catch (IndexOutOfBoundsException e){
			System.out.println("good!"); 
		}
	}
}

