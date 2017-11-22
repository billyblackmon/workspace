package carma;


public class Player {
	String name;
	int xPos;
	int yPos;
	int markCount;
	
	
	public Player() {}
	
	public Player(String name, int x, int y) {
		this.name = name;
		this.xPos = x;
		this.yPos = y;
	}
	
	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getMarkCount() {
		return markCount;
	}

	public void setMarkCount(int markCount) {
		this.markCount = markCount;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", xPos=" + xPos + ", yPos=" + yPos + ", markCount=" + markCount + "]";
	}
}
