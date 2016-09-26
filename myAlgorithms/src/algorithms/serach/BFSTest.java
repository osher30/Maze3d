package algorithms.serach;

import static org.junit.Assert.*;

import org.junit.Test;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.cellLastSelector;
import algorithms.mazeGenerators.cellRandomSelector;
import algorithms.demo.MazeAdapter;;

public class BFSTest {

	@Test
	public void test() {
		
		BFS<Position> searcher = new BFS<Position>(); // create BFS searcher
		BFS<Position> searcher2 = new BFS<Position>(); //// create BFS searcher
		Maze3dGenerator generator1 = new GrowingTreeGenerator(new cellLastSelector());
		Maze3dGenerator generator2 = new GrowingTreeGenerator(new cellRandomSelector());
		Solution<Position> sol;
		MazeAdapter ma;
		
		// Test 1- maze search adapter is null
		ma = null;
		sol = searcher.search(ma);
		assertNull(sol);
		
		// Test 2 - very big maze
		Maze3d maze1 = generator1.generate(10, 10, 10);
		ma = new MazeAdapter(maze1);
		sol = searcher.search(ma);
		assertNotNull(sol);
		
		// Test 3 - very small maze
		Maze3d maze2 = generator2.generate(1,5,5);
		ma = new MazeAdapter(maze2);
		sol = searcher2.search(ma);
		assertNotNull(sol);
	}
}
