package algorithms.serach;

import java.util.ArrayList;
import java.util.List;

public class DFS<T> extends CommonSearcher<T> {

	private List<State<T>> visited = new ArrayList<State<T>>();
	Solution<T> sol = new Solution<T>();
	
	/**
	 * search a solution to some problem by DFS algorithm, base on recursion
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {
		visited.add(s.getStartState());
		return DfsAl(s,s.getStartState());	
	}
	
	private Solution<T> DfsAl(Searchable<T> s,State<T> currState){
		
		if(currState.equals(s.getGoalState())){
			sol = backTrace(currState);
		}
		else {
			List<State<T>> neighbours = s.getAllPossibleState(currState);
			for (State<T> neighbour : neighbours) {
				evaluateNodes++;
				if(!visited.contains(neighbour))
				{
					neighbour.setCameFrom(currState);
					visited.add(neighbour);
					DfsAl(s,neighbour);
				}
			}
		}
		return sol;
	}
}