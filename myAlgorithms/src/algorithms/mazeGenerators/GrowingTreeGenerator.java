package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrowingTreeGenerator extends Maze3dGeneratorBase {
	
	public final static int WALL = 1;
	private cellSelector selector;
	public GrowingTreeGenerator(cellSelector s) {
		this.selector = s;
	
	}
	@Override
	public Maze3d generate(int floor, int row, int cols) {
		Maze3d maze = new Maze3d(floor,row,cols);
		
		//place walls in every cell
		intilizeMaze(maze);
		
		//choose random start cell
		List<Position> cells = new ArrayList<Position>();
		Position startPos = getRandomPosition(maze);
		cells.add(startPos);
		maze.setStartPosition(startPos);
		
		while(cells.size()>0){
		//currently choosing the last cell
		Position pos = selector.selectedCell(cells);
		
		List<Position> neighbors = getUnvisitedNeighbors(maze, pos);
		if(neighbors.size() > 0){
			//choose a random neighbor
			int idx = rand.nextInt(neighbors.size());
			Position neighbor = neighbors.get(idx);
			createPassageBetweenCells(maze,pos, neighbor);
			cells.add(neighbor);
		}
		else
			cells.remove(pos);
		}
		Position EndPos = setRandomGoal(maze);
		maze.setGoallPosition(EndPos);
		//System.out.println(maze.toString());
		return maze;
	}
	
	//set the maze full of walls
	private void intilizeMaze(Maze3d maze){
		int [][][]mat = maze.getMaze3d();
		for(int z=0;z<maze.getFloor();z++)
			for(int y=0;y<maze.getRow();y++)
				for(int x=0;x<maze.getCols();x++)
				{
					mat[z][y][x] = WALL; 
				}
	}
	//random a legal cell from the maze
	private Random rand = new Random();
	private Position getRandomPosition(Maze3d maze){
		int z = rand.nextInt(maze.getFloor()-2) + 1;
		while(z % 2 == 0){
			z = rand.nextInt(maze.getFloor()-2) + 1;
		}
		int y = rand.nextInt(maze.getRow()-2)+1;
		while(y % 2 == 0){
			y = rand.nextInt(maze.getRow()-2)+1;
		}
		int x = rand.nextInt(maze.getCols()-2)+1;
		while(x % 2 == 0){
			x = rand.nextInt(maze.getCols()-2)+1;
		}
		
		return new Position(z,y,x);
	}
	//get a position and return all UnVisited neighbors
	private List<Position> getUnvisitedNeighbors(Maze3d maze, Position pos){
		
		List<Position> neighbors = new ArrayList<Position>();
		int [][][] mat = maze.getMaze3d();
		if(pos.x - 2 >0){
			if(mat[pos.z][pos.y][pos.x-2] == Maze3d.WALL){
				neighbors.add(new Position(pos.z, pos.y, pos.x-2));
			}
		}
		if(pos.x + 2 <maze.getCols()-1){
			if(mat[pos.z][pos.y][pos.x+2] == Maze3d.WALL){
				neighbors.add(new Position(pos.z, pos.y, pos.x+2));
				}
		}
		if(pos.y - 2 >0){
			if(mat[pos.z][pos.y-2][pos.x] == Maze3d.WALL){
				neighbors.add(new Position(pos.z, pos.y-2, pos.x));
			}
		}
		if(pos.y + 2 <maze.getRow()-1){
			if(mat[pos.z][pos.y+2][pos.x] == Maze3d.WALL){
				neighbors.add(new Position(pos.z, pos.y+2, pos.x));
			}
		}
		if(pos.z - 1 >0){
			if(mat[pos.z-1][pos.y][pos.x] == Maze3d.WALL){
				neighbors.add(new Position(pos.z-1, pos.y, pos.x));
			}
		}
		if(pos.z + 1 <maze.getFloor()-1){
			if(mat[pos.z+1][pos.y][pos.x] == Maze3d.WALL){
				neighbors.add(new Position(pos.z+1, pos.y, pos.x));
			}
		}
		return neighbors;
	}
	
		private void createPassageBetweenCells(Maze3d maze,Position pos, Position neighbor){
			if(neighbor.x == pos.x - 2){
				maze.setFreeCell((new Position(pos.z, pos.y, pos.x-1)));
				maze.setFreeCell((new Position(pos.z, pos.y, pos.x-2)));
			}
			else if (neighbor.x == pos.x + 2){
				maze.setFreeCell((new Position(pos.z, pos.y, pos.x+1)));
				maze.setFreeCell((new Position(pos.z, pos.y, pos.x+2)));
			}
			else if (neighbor.y == pos.y - 2){
				maze.setFreeCell((new Position(pos.z, pos.y-1, pos.x)));
				maze.setFreeCell((new Position(pos.z, pos.y-2, pos.x)));
			}
			else if (neighbor.y == pos.y + 2){
				maze.setFreeCell((new Position(pos.z, pos.y+1, pos.x)));
				maze.setFreeCell((new Position(pos.z, pos.y+2, pos.x)));
			}
			else if (neighbor.z == pos.z - 1){
				maze.setFreeCell((new Position(pos.z-1, pos.y, pos.x)));
				
			}
			else if (neighbor.z == pos.z + 1){
				maze.setFreeCell((new Position(pos.z+1, pos.y, pos.x)));	}
		}
		
		private Position setRandomGoal(Maze3d maze){
			Position pos = 	getRandomPosition(maze);
			int [][][] a = maze.getMaze3d();
			while(a[pos.z][pos.y][pos.x] == WALL || pos.equals(maze.getStartPosition())) {
				pos = getRandomPosition(maze);
			}
			return pos;
			
		}

	}