package algorithms.search;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class BFS<T> extends CommonSearcher<T> {

	private PriorityQueue<State<T>> openList = new PriorityQueue<State<T>>();
	private Set<State<T>> closedList = new HashSet<State<T>>();
	
	/**
	 * search a solution to some problem by BFS algorithm
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {
		if(s == null)
			return null;
		
		State<T> startState = s.getStartState();
		openList.add(startState);
			
		while(!openList.isEmpty()){
			State<T> currState = popOpenList(openList);
			closedList.add(currState);
			
			State<T> goalState = s.getGoalState();
			if(currState.equals(goalState)) {
				return backTrace(currState);
			}
			List<State<T>> neighbors = s.getAllPossibleState(currState);
			
			for(State<T> neighbor : neighbors){
				if(!openList.contains(neighbor) && !closedList.contains(neighbor)){
					neighbor.setCameFrom(currState);
					neighbor.setCost(currState.getCost() + s.getMoveCost(currState,neighbor));
					openList.add(neighbor);}
				
				else{
					double newPathCost = currState.getCost() + s.getMoveCost(currState,neighbor);
					if(neighbor.getCost() > newPathCost){
						neighbor.setCost(newPathCost);
						neighbor.setCameFrom(currState);
						
						if(!openList.contains(neighbor)){
							openList.add(neighbor);}
						
						else { //must notify the priority  queue about the change of cost
							openList.remove(neighbor);
							openList.add(neighbor);}
					}
				}
			}
		}
		return null;
	}

}
