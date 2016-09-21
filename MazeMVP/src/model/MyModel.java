package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.cellRandomSelector;
import algorithms.serach.Solution;
import properties.Properties;
import properties.PropertiesLoader;

public class MyModel extends Observable implements Model {
	
	private ExecutorService executor;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();
	private Properties properties;
		
	public MyModel() {
		properties = PropertiesLoader.getInstance().getProperties();
		executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
		loadSolutions();
	}				
				
	@Override
	public void generateMaze(String name, int rows, int cols) {
		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				GrowingTreeGenerator generator = new GrowingTreeGenerator(new cellRandomSelector());
				Maze3d maze = generator.generate(floor,rows, cols);
				mazes.put(name, maze);
				
				setChanged();
				notifyObservers("maze_ready " + name);		
				return maze;
			}
			
		});
			
	}

	@Override
	public Maze2d getMaze(String name) {
		return mazes.get(name);
	}
	
	@SuppressWarnings("unchecked")
	private void loadSolutions() {
		File file = new File("solutions.dat");
		if (!file.exists())
			return;
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.dat")));
			mazes = (Map<String, Maze2d>)ois.readObject();
			solutions = (Map<String, Solution<Position>>)ois.readObject();		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	private void saveSolutions() {
		ObjectOutputStream oos = null;
		try {
		    oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.dat")));
			oos.writeObject(mazes);
			oos.writeObject(solutions);			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void exit() {
		executor.shutdownNow();
		saveSolutions();
		
		
		
	}
}
