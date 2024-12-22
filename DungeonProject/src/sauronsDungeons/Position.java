package sauronsDungeons;

import java.util.Objects;

public class Position {
	public int X;
	public int Y;
	public Position(int newX, int newY) {
		X = newX;
		Y = newY;
	}
	//used for hashmap stuff
	public int getX() {
		return X;
	}
	public int getY() {
		return Y;
	}
	@Override
	public boolean equals(Object o) {
		if(getClass() != o.getClass()) {
			return false;
		}
		Position po = (Position)o;
		return X == po.X && Y == po.Y;
	}
	@Override
	public int hashCode() {
		return Objects.hash(X,Y);
	}
	public String toString() {
		return X + ", " + Y;
	}
}

