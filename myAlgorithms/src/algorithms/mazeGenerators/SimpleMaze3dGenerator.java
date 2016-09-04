package algorithms.mazeGenerators;
public class SimpleMaze3dGenerator extends Maze3dGeneratorBase {
	
	public final static int WALL = 1;
	
	@Override
	public Maze3d generate(int floor, int row, int cols) {
		Maze3d temp =new Maze3d(floor,row,cols);
		temp.setStartPosition(new Position(1, 1, 1));
		temp.setGoallPosition(new Position(temp.getFloor()-2, temp.getRow()-1, temp.getCols()
				-2));
		int [][][] maz = temp.getMaze3d();
		for(int z=0;z<temp.getFloor();z++)
			for(int y=0;y<temp.getRow();y++)
				for(int x=0;x<temp.getCols();x++)
					{
						maz[z][y][x] = WALL; 
					}
		for(int x=1;x<temp.getCols()-1;x++)
			maz[1][1][x] = 0;
		for(int z=1;z<temp.getFloor()-1;z++)
			for(int y=0;y<temp.getRow();y++)
				maz[z][y][temp.getCols()-2] = 0;
		maz[1][0][temp.getCols()-2] = 1;
		return temp;
	}


}