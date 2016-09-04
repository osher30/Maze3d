package algorithms.mazeGenerators;

public class Position {
	int z;
	int y;
	int x;
	
	public Position(int z,int y,int x){
		this.z = z;
		this.y = y;
		this.x = x;
	}
	
	public boolean equals(Object obj){
		Position pos = (Position) obj;
		return (this.x == pos.x && this.y == pos.y && this.z == pos.z);
	}

	@Override
	public String toString() {
		return "(" + z + "," + y + "," + x + ")";
	}
}
