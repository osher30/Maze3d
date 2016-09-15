package view;

import java.io.BufferedReader;
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
		int answer = askForXYZ();
		int[][] maze2d;
		
		if (answer == 1)
		{
			maze2d = maze.getCrossSectionByX(index);
			for(int z = 0;z < maze.getFloor();z++)
			{
				for(int y = 0;y < maze.getRow();y++)
					System.out.print(maze2d[z][y]);
				System.out.print("\n");
			}
		}
		else if (answer == 2)
		{
			maze2d = maze.getCrossSectionByY(index);
			for(int z = 0;z < maze.getFloor();z++){
				for(int x = 0;x < maze.getCols();x++)
					System.out.print(maze2d[z][x]);
				System.out.print("\n");
			}
		}
		else if (answer == 3) 
		{
			maze2d = maze.getCrossSectionByZ(index);
			for(int y = 0;y < maze.getRow();y++){
				for(int x = 0;x < maze.getCols();x++)
					System.out.print(maze2d[y][x]);
				System.out.print("\n");
			}
		}
		else
		{
			System.out.println("The point isn't valid");
		}
	}
	
	public void displaySolution(Maze3d name)
	{
		Scanner scanner = new Scanner (System.in);
		int answer; 
		System.out.println("Enter 1 if you want to solve by BFS");
		System.out.println("Enter 2 if you want to solve by DFS");
		answer = scanner.nextInt();
		if(answer == 1) {
			BFS<Position> bfs = new BFS<Position>();
		}
		else if(answer == 2) {
			DFS<Position> dfs = new DFS<Position>();
		}
		else {
			System.out.println("wrong input");
		}
	}
}