/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape.board;

import java.util.function.BiPredicate;

import escape.board.coordinate.Coordinate;
import escape.board.coordinate.CoordinateID;
import escape.piece.MovementPatternID;

/**
* An interface for other methods the boards need
* @version Apr 16, 2020
*/

public interface OtherBoardMethods {
	
	/**
	 * determines whether or not a coordinate is valid for the given board
	 * @param c the coordinate being tested
	 * @return true if it is valid, false if not
	 */
	boolean isCoordinateValid(Coordinate c);
	
	/**
	 * sets the location type on the board at the given coordinate
	 * @param c the coordinate to set the location type
	 * @param lt the location type
	 */
	void setLocationType(Coordinate c, LocationType lt);
	
	
	/**
	 * returns the location type at a given coordinate
	 * @param c the coordinate to check
	 * @return the location type at the coordinate
	 */
	LocationType getLocationTypeAt(Coordinate c);
	
	/**
	 * Remove piece at given coordinate
	 * @param c the coordinate
	 */
	void removePiece(Coordinate c);
	
	/**
	 * returns the CoordinateID associated with a board
	 * @return the CoordinateID
	 */
	CoordinateID getID();
	
	/**
	 * Determines whether or not two coordinates are orthogonal
	 * @param coord1 the first coordinate
	 * @param coord2 the second coordinate
	 * @return true if so, false if not
	 */
	default boolean isOrthogonal(Coordinate coord1, Coordinate coord2) {
		BiPredicate<Coordinate, Coordinate> isOrthogonal = (c1, c2) -> c1.getX() == c2.getX() || c1.getY() == c2.getY();
		return isOrthogonal.test(coord1, coord2);
	}
	
	
	/**
	 * Determines whether or not two coordinates are diagonal
	 * @param coord1 the first coordinate
	 * @param coord2 the second coordinate
	 * @return true if so, false if not
	 */
	default boolean isDiagonal(Coordinate coord1, Coordinate coord2) {
		BiPredicate<Coordinate, Coordinate> isDiagonal = (c1, c2) -> Math.abs(c1.getX() - c2.getX()) == Math.abs(c1.getY() - c2.getY());
		return isDiagonal.test(coord1, coord2);
	}
	
	/**
	 * Determines whether or not two coordinates are linear
	 * @param coord1 the first coordinate
	 * @param coord2 the second coordinate
	 * @return true if so, false if not
	 */
	boolean isLinear(Coordinate coord1, Coordinate coord2);
	
	
	/**
	 * Determines whether or not two coordinates are omni
	 * @param coord1 the first coordinate
	 * @param coord2 the second coordinate
	 * @return true if so, false if not
	 */
	default boolean isOmni(Coordinate coord1, Coordinate coord2) {
		BiPredicate<Coordinate, Coordinate> isOmni = (c1, c2) -> !(c1.equals(c2));
		return isOmni.test(coord1, coord2);
	}
}