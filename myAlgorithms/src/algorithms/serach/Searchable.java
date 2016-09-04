	package algorithms.serach;

import java.util.List;
/**
 * Interface that represents a problem that we search for a solution
 * @author osher_000
 *
 * @param <T>
 */
public interface Searchable<T> {
	/**
	 * Returns the initial state
	 * @return
	 */
	State<T> getStartState();
	State<T> getGoalState();
	/**
	 * Returns all the possible states we can go to from the current state
	 * @param s - the current state
	 * @return
	 */
	List<State<T>> getAllPossibleState(State<T> s);
	double getMoveCost(State<T> currState,State<T> neighbor);
}
