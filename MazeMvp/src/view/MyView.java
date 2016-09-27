package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;

public class MyView extends Observable implements View, Observer {
	
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;	

	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
				
		cli = new CLI(in, out);
		cli.addObserver(this);
	}	

	@Override
	public void notifyMazeIsReady(String name) {
		out.println("maze " + name + " is ready");
		out.flush();
	}

	@Override
	public void displayMaze(Maze3d maze) {
		out.println(maze);
		out.flush();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		cli.start();
	}

	@Override
	public void displayMessage(String msg) {
		out.println(msg);
		out.flush();		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
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


}
