package algorithms.demo;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.cellLastSelector;
import algorithms.serach.BFS;
import algorithms.serach.DFS;
import algorithms.serach.Solution;

public class Demo {
	
	public void Run(){
	
		Maze3dGenerator generator = new GrowingTreeGenerator(new cellLastSelector());
		Maze3d maze = generator.generate(8, 8, 8);
		System.out.println(maze);
		System.out.println("Start position is :" + maze.getStartPosition());
		System.out.println("Goal Position is :" + maze.getGoallPosition());
		MazeAdapter adapter = new MazeAdapter(maze);
		BFS<Position> bfs = new BFS<Position>();		
		Solution<Position> solBfs =bfs.search(adapter);
		DFS<Position> dfs = new DFS<Position>();
		Solution<Position> solDfs =dfs.search(adapter);
		System.out.println("The solution of Bfs: " + solBfs);
		System.out.println("The solution of Dfs: " + solDfs);
		System.out.println("Number of nodes in Bfs : " + bfs.getNumberOfNodesEvaluated());
		System.out.println("Number of nodes in Dfs : " + dfs.getNumberOfNodesEvaluated());
	}
	

}
