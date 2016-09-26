package algorithms.search;

public interface Searcher<T> {
	
	/**
	 * do a search for solution to some problem we have
	 * @param s
	 * @return
	 */

    // the search method
    public Solution<T> search(Searchable<T> s);
    /**
     * get us the number of nodes that we check while search the solution
     * @return
     */
    // get how many nodes were evaluated by the algorithm
    public int getNumberOfNodesEvaluated();


}
