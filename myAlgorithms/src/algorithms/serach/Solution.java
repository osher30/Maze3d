package algorithms.serach;

import java.util.ArrayList;
import java.util.List;

public class Solution<T> {
	private List<State<T>> states = new ArrayList<State<T>>();

	public List<State<T>> getStates() {
		return states;
	}

	public void setStates(List<State<T>> states) {
		this.states = states;
	}
	/**
	 * override to string for print the solution
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(State<T> s : states)
			sb.append(s.toString()).append(" ");
		return sb.toString();
	}
}
