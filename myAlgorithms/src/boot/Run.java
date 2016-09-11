package boot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.cellRandomSelector;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class Run {

	public static void main(String[] args) {
		/*testMazeGenerator(new SimpleMaze3dGenerator());
		testMazeGenerator(new GrowingTreeGenerator(new cellRandomSelector()));
		testMazeGenerator(new GrowingTreeGenerator(new cellLastSelector()));
		*/
		Maze3d maze;
		Maze3dGenerator s = new GrowingTreeGenerator(new cellRandomSelector());
		maze = s.generate(4, 7, 7);
		System.out.println(maze);
		
		// save it to a file
				OutputStream out;
				try {
					out = new MyCompressorOutputStream(
							new FileOutputStream("1.maz"));
					byte[] arr = maze.toByteArray();
					
					out.write(arr.length/255);
					out.write(arr.length%255);
					out.write(arr);
					out.flush();
					out.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
		//load data from file
				InputStream in;
				try {
					in = new MyDecompressorInputStream(
							new FileInputStream("1.maz"));
					int size = (in.read()*255) + in.read();
					System.out.println(size); //check
					byte b[]=new byte[size];
					in.read(b);
					in.close();	
					
					Maze3d loaded = new Maze3d(b);
					System.out.println("maze loaded from file:");
					System.out.println(loaded);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		maze.getGoalPosition());

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

