package algorithms.mazeGenerators;

public interface Maze3dGenerator {
	Maze3d generate(int floor,int row,int cols);
	String measureAlgotithmtTime(int floor,int row,int cols);
	void setDone(boolean isdone);
}
