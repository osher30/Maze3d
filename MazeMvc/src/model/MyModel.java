package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.demo.MazeAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.cellRandomSelector;
import algorithms.serach.Searcher;
import algorithms.serach.Solution;
import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

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
	
	public void saveMaze(Maze3d name, String fileName)
	{
		OutputStream out;
		try {
			out = new MyCompressorOutputStream(
					new FileOutputStream("fileName"));
			byte[] arr = name.toByteArray();
			
			out.write(arr.length/127);
			out.write(arr.length%127);
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
	}
	
	public void loadMaze(String fileName, Maze3d name)
	{
		InputStream in;
		try {
			in = new MyDecompressorInputStream(
					new FileInputStream("fileName"));
			int size = (in.read()*255) + in.read();
			byte b[]=new byte[size];
			in.read(b);
			in.close();	
			
			name = new Maze3d(b);
			mazes.add(name); //need to add to mazes
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void solveMaze(Maze3d name, Searcher<Position> algo)
	{
		MazeAdapter adapter = new MazeAdapter(name);
		Solution<Position> sol =algo.search(adapter);
	}

}