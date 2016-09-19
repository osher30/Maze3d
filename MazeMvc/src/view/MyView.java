package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.serach.BFS;
import algorithms.serach.DFS;
import algorithms.serach.Solution;
import controller.Command;
import controller.Controller;
import model.Model;
import model.MyModel;

/**
 * The mvc view. 
 */
public class MyView implements View {
	
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	private Controller controller;

	public MyView (BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		
		cli = new CLI(in,out);
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void notifyMazeIsReady(String name) {
		// TODO Auto-generated method stub
		out.println("maze " + name + " is ready");
		out.flush();
	}

	@Override
	public void displayMaze(Maze3d maze) {
		// TODO Auto-generated method stub
		out.println(maze);
		out.flush();
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		cli.setCommands(commands);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		cli.start();

	}
	
	public int askForXYZ()
	{
		Scanner scanner = new Scanner (System.in);
		int answer; 
		System.out.println("Enter 1 if you want to getCrossSectionByX");
		System.out.println("Enter 2 if you want to getCrossSectionByY");
		System.out.println("Enter 3 if you want to getCrossSectionByZ");
		answer = scanner.nextInt();
		scanner.close();
		return answer;
	}
	
public void displayCrossSection (Maze3d maze, int index)
	{
		//int answer = askForXYZ();
		int[][] maze2d;
		switch (index) {
		case 1:
			maze2d = maze.getCrossSectionByX(index);
			for(int z = 0;z < maze.getFloor();z++)
			{
				for(int y = 0;y < maze.getRow();y++)
					System.out.print(maze2d[z][y]);
				System.out.print("\n");
			}
			break;
		case 2:
			maze2d = maze.getCrossSectionByY(index);
			for(int z = 0;z < maze.getFloor();z++){
				for(int x = 0;x < maze.getCols();x++)
					System.out.print(maze2d[z][x]);
				System.out.print("\n");
			}
			break;
		case 3:
			maze2d = maze.getCrossSectionByZ(index);
			for(int y = 0;y < maze.getRow();y++){
				for(int x = 0;x < maze.getCols();x++)
					System.out.print(maze2d[y][x]);
				System.out.print("\n");
			}
			break;

		default:
			System.out.println("Invaild index");
			break;
		}
	}
	
	/**
	 * Display data to user with the output source
	 * @param msg - The message to display
	 */
	@Override
	public void displayMessage(String msg) 
	{
			out.println(msg);
			out.flush();	
	}
	
	// Auxiliary function
			private static String _fileType(File f){
				if(f.isFile()){
					return "<File>";
				}
				return "<Directory>";
			}
	
	/**
	 * Get the list of directories and files of given path
	 * @param path - the file system path to present
	 */
	@Override
	public void getDirList(String path) 
	{
		File dir = new File(path);
		// check path exist
		if(!dir.exists()){
			controller.displayMessage("No such file or directory");
			return;
		}
		File[] files = dir.listFiles();
		// check that path is not empty
		if (files.length == 0) {
			controller.displayMessage("The directory is empty");
		} 
		else 
		{
		    for (File iterFile : files) 
		    {
		    	controller.displayMessage(_fileType(iterFile) + " - " + iterFile.getName());
		    }
		}
	}
}
