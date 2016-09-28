package presenter;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import presenter.CommandsManager.DisplayCrossSectionCommand;
import presenter.CommandsManager.LoadMaze;
import presenter.CommandsManager.SaveMaze;
import presenter.CommandsManager.dir;
import presenter.CommandsManager.displaySolution;
import presenter.CommandsManager.ExitCommand;
import presenter.CommandsManager.solveMaze;
import model.Model;
import view.View;

public class CommandsManager {
	
	private Model model;
	private View view;
		
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("maze_ready", new MazeReadyCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("save_maze", new SaveMaze()); 
		commands.put("load_maze", new LoadMaze());
		commands.put("solve", new solveMaze());
		commands.put("dir", new dir());
		commands.put("display_solution", new displaySolution());
		commands.put("exit", new ExitCommand());
		
		return commands;
	}
	
	class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int floor = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int cols = Integer.parseInt(args[3]);
			model.generateMaze(name,floor, rows, cols);
		}		
	}
	
	class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			if(maze == null){
				String msg = "Maze does not exist";
				view.displayMessage(msg);
			}
			else
			view.displayMaze(maze);
		}		
	}
	
	class ExitCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.exit();
		}		
	}
	
	class MazeReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			view.notifyMazeIsReady(name);
		}
		
	}
	
	public class DisplayCrossSectionCommand implements Command 
	{

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int index = Integer.parseInt(args[1]);
			Maze3d maze = model.getMaze(name);
			//view.displayCrossSection(maze ,index);
			
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
				//view.displayMessage("Invalid path");
			}
			else{
				//view.getDirList(args[0]);
			}
		}
	}
	
	public class exit implements Command
	{
		@Override
		public void doCommand(String[] args)
		{
			//model.exit();
		}
	}
	
	public class displaySolution implements Command
	{
		@Override
		public void doCommand(String[]args)
		{
			String name = args[0];
			//model.displaySolution(name);
		}
	}
}
