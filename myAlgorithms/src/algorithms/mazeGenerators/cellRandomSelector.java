package algorithms.mazeGenerators;

import java.util.List;
import java.util.Random;

public class cellRandomSelector implements cellSelector {
	private Random rand = new Random();
	public Position selectedCell(List<Position> cells){
		int c = rand.nextInt(cells.size());
		Position pos = cells.get(c);
		
		return pos;
		
	}

}
