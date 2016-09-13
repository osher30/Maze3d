package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.cellRandomSelector;
import controller.Controller;

public class MyModel implements Model {
	
	private List<GenerateMazeRunnable> generateMazeTasks = 
			new ArrayList<GenerateMazeRunnable>();
	
	class GenerateMazeRunnable implements Runnable {
		
		private int rows, cols, floor;
		private String name;
		private GrowingTreeGenerator generator;
		public GenerateMazeRunnable(int floor, int rows, int cols, String name) {
			this.floor = floor;
			this.rows = rows;
			this.cols = cols;
			this.name = name;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			generator = new GrowingTreeGenerator(new cellRandomSelector());
			Maze3d maze = generator.generate(floor, rows, cols);
			mazes.put(name,maze);
			controller.notifyMazeIsReady(name);			
		}
		
		public void terminate(){
			generator.setDone(true);
		}
	}
	
	private Controller controller;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private List<Thread> threads = new ArrayList<Thread>();
	
	public void setController(Controller controller){
		this.controller = controller;
	}
	
	@Override
	public void generateMaze(String name, int floor, int rows, int cols) {
		GenerateMazeRunnable generateMaze = new GenerateMazeRunnable(floor, rows, cols, name);
		generateMazeTasks.add(generateMaze);
		Thread thread = new Thread(generateMaze);
		thread.start();
		threads.add(thread);
	}
	
	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}
	
	public void exit() {
		for (GenerateMazeRunnable task : generateMazeTasks) {
			task.terminate();
		}
	}

}