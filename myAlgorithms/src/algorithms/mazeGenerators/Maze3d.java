package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze3d {
	private int [][][] maze3d;
	private int cols;
	private int row;
	private int floor;
	private Position StartPosition;
	private Position GoallPosition;
	
	public final static int FREE = 0;
	public final static int WALL = 1;
	
	public Maze3d(int originalfloor,int originalrow,int originalcols){
		this.floor = originalfloor+2;
		this.row = originalrow*2+1;
		this.cols = originalcols*2+1;
		maze3d=new int[floor][row][cols];
	}
	public void setFreeCell(Position pos){
		maze3d[pos.z][pos.y][pos.x] = FREE;
	}
	
	public void setWallCell(Position pos){
		maze3d[pos.z][pos.y][pos.x] = WALL;
	}
	

	public Position getStartPosition() {
		return StartPosition;
	}

	public void setStartPosition(Position start) {
		this.StartPosition = start;
	}

	public Position getGoallPosition() {
		return GoallPosition;
	}

	public void setGoallPosition(Position end) {
		this.GoallPosition = end;
	}

	public int[][][] getMaze3d() {
		return maze3d;
	}

	public int getCols() {
		return cols;
	}

	public int getRow() {
		return row;
	}

	public int getFloor() {
		return floor;
	}
	
	public List<Position> getAllPosibleMoves(Position pos){
		List<Position> moves = new ArrayList<Position>();
		
		if(pos.x>0 && maze3d[pos.z][pos.y][pos.x-1] == FREE)
			moves.add(new Position(pos.z, pos.y, pos.x-1));
		
		if(pos.x<cols-1  && maze3d[pos.z][pos.y][pos.x+1] == FREE)
			moves.add(new Position(pos.z, pos.y, pos.x+1));
		
		if(pos.y>0 && maze3d[pos.z][pos.y-1][pos.x] == FREE)
			moves.add(new Position(pos.z, pos.y-1, pos.x));
		
		if(pos.y<row-1 && maze3d[pos.z][pos.y+1][pos.x] == FREE)
			moves.add(new Position(pos.z, pos.y+1, pos.x));
		
		if(pos.z>0 && maze3d[pos.z-1][pos.y][pos.x] == FREE)
			moves.add(new Position(pos.z-1, pos.y, pos.x));
		
		if(pos.z<floor && maze3d[pos.z+1][pos.y][pos.x] == FREE)
			moves.add(new Position(pos.z+1, pos.y, pos.x));
		
		return moves;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int z = 0;z<floor;z++){
			for(int y = 0;y<row;y++){
				for(int x = 0;x<cols;x++){
					if(z == StartPosition.z && y == StartPosition.y && x == StartPosition.x){
						sb.append("E");
						sb.append(" ");
					}
					else if (z == GoallPosition.z && y == GoallPosition.y && x == GoallPosition.x){
						sb.append("X");
						sb.append(" ");
					}
					else{
						sb.append(maze3d[z][y][x]);
						sb.append(" ");
					}
				}
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public int[][] getCrossSectionByX(int constant) {
		int [][][] maze = this.getMaze3d();
		int [][]temp = new int[floor][row];
		if(constant>=0 && constant<cols)
		{	
		for(int z=0;z<floor;z++)
			for(int y=0;y<row;y++)
				temp[z][y] = maze[z][y][constant];
		}
		else{
			throw new IndexOutOfBoundsException();
			}
		
		return temp;
	}
	
	public int[][] getCrossSectionByY(int constant) {
		int [][][] maze = this.getMaze3d();
		int [][]temp = new int[floor][cols];
		if(constant>=0 && constant<row)
		{	
		for(int z=0;z<floor;z++)
			for(int x=0;x<cols;x++)
				temp[z][x] = maze[z][x][constant];
		}
		return temp;
	}
	
	public int[][] getCrossSectionByZ(int constant) {
		int [][][] maze = this.getMaze3d();
		int [][]temp = new int[row][cols];
		if(constant>=0 && constant<floor)
		{	
		for(int y=0;y<row;y++)
			for(int x=0;x<cols;x++)
				temp[y][x] = maze[y][x][constant];
		}
		else{
			throw new IndexOutOfBoundsException();
			}
		
		return temp;
	}
	
	
	public String[] getPossibleMoves(Position pos)
	{
		String [] temp= new String[6];
		int index = 0;
		int[][][] mat = this.getMaze3d();
		if(pos.x - 1 >=0){
			if(mat[pos.z][pos.y][pos.x-1] == Maze3d.FREE){	
				temp[index] = "left";
				index++;
			}
		}
		if(pos.x + 1 <this.getCols()){
			if(mat[pos.z][pos.y][pos.x+1] == Maze3d.FREE){
				temp[index] = "right";
				index++;
				}
		}
		if(pos.y - 1 >=0){
			if(mat[pos.z][pos.y-1][pos.x] == Maze3d.FREE){
				temp[index] = "backwards";
				index++;
			}
		}
		if(pos.y + 1 <this.getRow()){
			if(mat[pos.z][pos.y+1][pos.x] == Maze3d.FREE){
				temp[index] = "forward";
				index++;
			}
		}
		if(pos.z - 1 >=0){
			if(mat[pos.z-1][pos.y][pos.x] == Maze3d.FREE){
				temp[index] = "down";
				index++;
			}
		}
		if(pos.z + 1 <this.getFloor()){
			if(mat[pos.z+1][pos.y][pos.x] == Maze3d.FREE){
				temp[index] = "up";
				index++;
			}
		}
		String[] moves = Arrays.copyOf(temp, index);
		return moves;
	}
	
}
