/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape.board.coordinate;

import java.util.Objects;

/**
* The class for orthosquare coordinates to be used on square boards
* @version Apr 13, 2020
*/

public class OrthoSquareCoordinate implements Coordinate {
	private final int x;
	private final int y;
	
	/**
	 * The constructor for the OrthoSquareCoordinate class
	 * @param x the row
	 * @param y the column
	 */
	private OrthoSquareCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c) {
		return Math.abs(getX() - c.getX()) + Math.abs(getY() - c.getY());
	}
	
    /**
     * This method returns a new coordinate at the given x and y,
     * or returns the coordinate if it already exists
     * @param x the row value
     * @param y the column value
     * @return
     */
	public static OrthoSquareCoordinate makeCoordinate(int x, int y){
		return new OrthoSquareCoordinate(x, y);
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
		if(!(obj instanceof OrthoSquareCoordinate)) {
			return false;
		}
		OrthoSquareCoordinate other = (OrthoSquareCoordinate) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * @see escape.board.coordinate.OtherCoordinateMethods#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * @see escape.board.coordinate.OtherCoordinateMethods#getY()
	 */
	@Override
	public int getY() {
		return y;
	}
}

