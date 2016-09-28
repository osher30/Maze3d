package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.MazeAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.cellRandomSelector;
import algorithms.serach.BFS;
import algorithms.serach.DFS;
import algorithms.serach.Searcher;
import algorithms.serach.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import properties.Properties;
import properties.PropertiesLoader;

public class MyModel extends Observable implements Model {
	
	private ExecutorService executor;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<String, Solution<Position>> mazesSolutions = new ConcurrentHashMap<String, Solution<Position>>();
	private Properties properties;
	private List<Thread> threads = new ArrayList<Thread>();
		
	public MyModel() {
		properties = PropertiesLoader.getInstance().getProperties();
		executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
		loadSolutions();
	}				
				
	@Override
	public void generateMaze(String name,int floor, int rows, int cols) {
		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				GrowingTreeGenerator generator = new GrowingTreeGenerator(new cellRandomSelector());
				Maze3d maze = generator.generate(floor, rows, cols);
				mazes.put(name, maze);
				
				setChanged();
				//notifyObservers("display" + name);
				notifyObservers("maze_ready" + " " +  name);
				//notifyObservers("maze_ready " + name);		
				return maze;
			}
			
		});
			
	}

	@Override
	public Maze3d getMaze(String name) {
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
			mazes = (Map<String, Maze3d>)ois.readObject();
			mazesSolutions = (Map<String, Solution<Position>>)ois.readObject();		
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
			oos.writeObject(mazesSolutions);			
			
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
	
	public void saveMaze(Maze3d name, String fileName)
	{
		OutputStream out = null;
		try {
			Maze3d maze = name;//mazes.get(name);
			if (maze == null) {
				
				notifyObservers("No such maze " + name);
				return;
			}
			out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			new DataOutputStream(out).writeInt(maze.toByteArray().length);
		    out.write(maze.toByteArray());
		} catch (IOException e) {
			notifyObservers(String.format("Error while try to write maze to %s", fileName));
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				notifyObservers(String.format("Error while try to close handle of %s", fileName));
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
			notifyObservers(String.format("Error while try to read maze from %s", fileName));		
		} 
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} 
			catch (IOException e) {
				notifyObservers(String.format("Error while try to close handle of %s", fileName));
			}
		}
	}
	
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
					notifyObservers("Invalid algorithm, try with Capital laters.");
					return;
				}
				
				Maze3d maze = mazes.get(name);
				if (maze == null) {
					notifyObservers(String.format("Maze %s is not exist", name));
					return;
				}
				Solution<Position> solution = searchAlgo.search(new MazeAdapter(maze));
				mazesSolutions.put(name, solution);
				//getMazesSolutions().put(name, solution);
				notifyObservers(String.format("Solution for %s is ready.", name));
			}	
	 	});
		thread.start();
		threads.add(thread);		
	}

}
