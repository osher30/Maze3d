	package algorithms.search;

import java.util.List;
import java.util.PriorityQueue;

public abstract class CommonSearcher<T> implements Searcher<T> {
	
	protected int evaluateNodes=0;
	

	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluateNodes;
	}
	/**
	 * creating the back trace to from the goal to start
	 * @param
	 * @return Solution
	 */
	protected Solution<T> backTrace(State<T> goalState) {
		Solution<T> sol = new Solution<T>();
		State<T> currState = goalState;
		
		List<State<T>> states = sol.getStates();
		while(currState != null){
			states.add(0, currState);
			currState = currState.getCameFrom();
		}
		return sol;
	}
	/**
	 * get the number of nodes +1 each time, and poll the first State<T> from the stack
	 * @param openList
	 * @return State<T>
	 */
	protected State<T> popOpenList(PriorityQueue<State<T>> openList){
		evaluateNodes++;
		return openList.poll();
	}
}
