/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape.board.coordinate;

import java.util.ArrayList;

/**
* an interface for other methods that coordinates need
* @version Apr 16, 2020
*/

public interface OtherCoordinateMethods {
	
	/**
	 * returns the row for the coordinate
	 * @return the x
	 */
	int getX();
	
	
	/**
	 * returns the column for the coordinate
	 * @return the y
	 */
	int getY();
	
	/**
	 * Gets all viable neighbors of a coordinate
	 * @return an array of neighbors
	 */
	ArrayList<Coordinate> getNeighbors();
}

