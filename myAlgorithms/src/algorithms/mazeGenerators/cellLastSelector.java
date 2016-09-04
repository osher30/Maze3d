package algorithms.mazeGenerators;

import java.util.List;

public class cellLastSelector implements cellSelector {

	@Override
	public Position selectedCell(List<Position> cells) {
		Position pos = cells.get(cells.size() - 1);
		return pos;
	}

}
