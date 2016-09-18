package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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
import algorithms.serach.BFS;
import algorithms.serach.DFS;
import algorithms.serach.Searcher;
import algorithms.serach.Solution;
import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * Implements the interface model. 
 */
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
	}
	
	private Controller controller;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<String,Solution> mazesSolutions;
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
	
	/**
	 * Save the compressed maze. 
	 */
	public void saveMaze(Maze3d name, String fileName)
	{
		OutputStream out = null;
		try {
			Maze3d maze = name;//mazes.get(name);
			if (maze == null) {
				
				controller.displayMessage("No such maze " + name);
				return;
			}
			out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			new DataOutputStream(out).writeInt(maze.toByteArray().length);
		    out.write(maze.toByteArray());
		} catch (IOException e) {
			controller.displayMessage(String.format("Error while try to write maze to %s", fileName));
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				controller.displayMessage(String.format("Error while try to close handle of %s", fileName));
			}
		}
	}
	
	public void loadMaze(String fileName, String name)
	{
		InputStream in = null;
		byte[] b = null;	  
		try {
			in = new MyDecompressorInputStream(new FileInputStream(fileName));
			b = new byte[new DataInputStream(in).readInt()];
			 in.read(b);
			 Maze3d maze = new Maze3d(b);
			 mazes.put(name, maze);
		} 
		catch(IOException e) {
			controller.displayMessage(String.format("Error while try to read maze from %s", fileName));		
		} 
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} 
			catch (IOException e) {
				controller.displayMessage(String.format("Error while try to close handle of %s", fileName));
			}
		}
	}
	
	/**
	 * Solve maze with given algorithm and add to hash-map.
	 * @param name - name of maze to solve
	 * @param fileName - algorithm that would be use to solve maze
	 */
	@Override
	public void solveMaze(final String name, final String algorithm){
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() 
			{
				Searcher<Position> searchAlgo = null;
				if (algorithm.equals("BFS")) {
					searchAlgo = new BFS<Position>();
				} else if(algorithm.equals("DFS")) {
					searchAlgo = new DFS<Position>();
				} else {
					controller.displayMessage("Invalid algorithm, try with Capital laters.");
					return;
				}
				
				Maze3d maze = mazes.get(name);
				if (maze == null) {
					controller.displayMessage(String.format("Maze %s is not exist", name));
					return;
				}
				Solution<Position> solution = searchAlgo.search(new MazeAdapter(maze));
				getMazesSolutions().put(name, solution);
				controller.displayMessage(String.format("Solution for %s is ready.", name));
			}	
	 	});
		thread.start();
		threads.add(thread);		
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
			public Map<String, Solution> getMazesSolutions() {
				
				return mazesSolutions;
			}
			
			public void setMazesSolutions(Map<String,Solution> mazesSolutions) {
				this.mazesSolutions = mazesSolutions;
			}
			
			/**
			 * Show the way(steps) that use to solve to maze<br>
			 * @param name - name of the maze that print his solution
			 */
			@Override
			public void displaySolution(String name) {
				Solution<Position> sol = getMazesSolutions().get(name);
				if (sol == null) {
					controller.displayMessage(String.format("Solution for %s is not exist", name));
				} else {
					controller.displayMessage(sol.toString());
				}
			}
			
			/**
			 * Close all the open handles.
			 */
			@Override
			public void exit() 
			{
				for(Thread thread: this.threads) {
					thread.interrupt();
				}
			}
}