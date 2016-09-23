package algorithms.mazeGenerators;

import java.util.List;

public interface cellSelector 
{
	public Position selectedCell(List<Position> cells);
}