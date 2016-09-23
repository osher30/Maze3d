package algorithms.mazeGenerators;

public abstract class Maze3dGeneratorBase implements Maze3dGenerator 
{
	private boolean isdone = false;
	
	public boolean isIsdone() 
	{
		return isdone;
	}

	public void setIsdone(boolean isdone) 
	{
		this.isdone = isdone;
	}

	@Override
	public String measureAlgotithmtTime(int floor, int row, int cols) 
	{
		long startTime = System.currentTimeMillis();
		this.generate(floor, row , cols);
		long endTime = System.currentTimeMillis();
		return String.valueOf(endTime - startTime);
	}

}

