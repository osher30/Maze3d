package algorithms.demo;

import java.util.ArrayList;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.serach.Searchable;
import algorithms.serach.State;
/**
 * Adapter from maze to states of problems.
 *
 */
public class MazeAdapter implements Searchable<Position> {

	private Maze3d maze;
	
	public MazeAdapter(Maze3d maze) 
	{
		this.maze = maze;
	}
	
	/**
	 * Adapter the start position of the maze to state.
	 */
	@Override
	public State<Position> getStartState() 
	{
		Position startPos = maze.getStartPosition();
		State <Position> startState = new State<Position>(startPos);
		return startState;
	}
	
	/**
	 * Adapter the goal position of the maze to state.
	 */
	@Override
	public State<Position> getGoalState() 
	{
		Position goalPos = maze.getGoalPosition();
		State <Position> goalState = new State<Position> (goalPos);
		return goalState;
	}
	
	/**
	 * Check the possible state we can get to from some initial state.
	 */
	@Override
	public List<State<Position>> getAllPossibleState(State<Position> s) 
	{
		Position currPos = s.getValue();
		
		List<Position> moves = maze.getAllPosibleMoves(currPos);
		List<State<Position>> states = new ArrayList<State<Position>>();
		
		for(Position pos: moves)
		{
			states.add(new State<Position>(pos));
		}
		return states;
	}
	
	/**
	 * Calculate the cost of move from one state to other.
	 */
	@Override
	public double getMoveCost(State<Position> currState, State<Position> neighbor) 
	{
		// TODO Auto-generated method stub
		return 1;
	}
	
}
