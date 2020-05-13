/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape.board.coordinate;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
* The class for hex coordinates to be used on hex boards
* @version Apr 13, 2020
*/

public class HexCoordinate implements Coordinate {
	private final int x;
	private final int y;
	
	
    /**
     * This is the constructor for the HexCoordinate class
     * @param x the row value of the coordinate
     * @param y the column value of the coordinate
     */
	private HexCoordinate(int x, int y) {
		this.y = y;
		this.x = x;
	}
	
	
    /**
     * This method returns a new coordinate at the given x and y,
     * or returns the coordinate if it already exists
     * @param x the row value
     * @param y the column value
     * @return
     */
	public static HexCoordinate makeCoordinate(int x, int y){
		return new HexCoordinate(x, y);
	}

	/**
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 *  **CORRECTED ALGORITHM, FROM http://www-cs-students.stanford.edu/~amitp/Articles/HexLOS.html**
	 */
	@Override
	public int distanceTo(Coordinate c) {
		int xDiff = getX() - c.getX();
		int yDiff = getY() - c.getY();
		return (Integer.signum(xDiff) == Integer.signum(yDiff)) ? Math.abs(xDiff + yDiff)
				: Math.max(Math.abs(xDiff), Math.abs(yDiff));
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof HexCoordinate)) {
			return false;
		}
		HexCoordinate other = (HexCoordinate) obj;
		return x == other.x && y == other.y;
	}
	
	/**
	 * @see escape.board.coordinate.OtherCoordinateMethods#getY()
	 */
	@Override
	public int getY() {
		return y;
	}


	/**
	 * @see escape.board.coordinate.OtherCoordinateMethods#getX()
	 */
	@Override
	public int getX() {
		return x;
	}
	
	/**
	 *@see escape.board.coordinate.OtherCoordinateMethods#getNeighbors()
	 */
	public ArrayList<Coordinate> getNeighbors(){
		ArrayList<Point> modifiersForHex = new ArrayList<Point>(Arrays.asList(new Point(0,1), new Point(0,-1), new Point(-1,1),
				new Point(1,0), new Point(-1,0), new Point(1,-1)));
		ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();
		for(Point p : modifiersForHex) {
			int i = modifiersForHex.indexOf(p);
			int xMod = (int) modifiersForHex.get(i).getX();
			int yMod = (int) modifiersForHex.get(i).getY();
			neighbors.add(HexCoordinate.makeCoordinate(x+xMod,y+yMod));
		}
		return neighbors;
	}

}

