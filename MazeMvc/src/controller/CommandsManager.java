package controller;

import java.util.HashMap;
import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * Contain all the commands classes which implements Command interface 
 * and override doCommand method.
 */
public class CommandsManager {
	
	private Model model;
	private View view;
		
	/**
	 *  The c'tor
	 * @param model
	 * @param view
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	
	/**
	 * Contain all the commands objects as strings.
	 * @return the commands hash map. 
	 */
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("save_maze", new SaveMaze()); 
		commands.put("load_maze", new LoadMaze());
		commands.put("solve", new solveMaze());
		
		return commands;
	}
	
	/**
	 * Create classes for every single command. 
	 */
	public class GenerateMazeCommand implements Command 
	{

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int floor = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int cols = Integer.parseInt(args[3]);
			model.generateMaze(name, floor, rows, cols);
		}		
	}
	
	public class DisplayMazeCommand implements Command 
	{

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
		
	}
	
	public class DisplayCrossSectionCommand implements Command 
	{

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int index = Integer.parseInt(args[1]);
			Maze3d maze = model.getMaze(name);
			view.displayCrossSection(maze, index);
			
		}
		
	}
	
	public class SaveMaze implements Command 
	{

		@Override
		public void doCommand(String[] args) 
		{
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			String fileName = args[1];
			model.saveMaze(maze, fileName);
		}
	}
	
	public class LoadMaze implements Command 
	{

		@Override
		public void doCommand(String[] args) 
		{
			String name = args[0];
			String fileName = args[1];
			model.loadMaze(name, fileName);
		}
	}
	
	public class solveMaze implements Command 
	{

		@Override
		public void doCommand(String[] args)
		{
			String name = args[0];
			String algo = args[1]; 
			model.solveMaze(name, algo);
			
		}
	}
	
	public class dir implements Command
	{	
		@Override
		public void doCommand(String[] args) 
		{
			if(args.length == 0){
				view.displayMessage("Invalid path");
			}
			else{
				model.getDirList(args[0]);
			}
		}
	}
	
	}

