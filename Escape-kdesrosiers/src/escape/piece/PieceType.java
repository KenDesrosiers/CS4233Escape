/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape.piece;

import java.util.*;

import escape.util.PieceTypeInitializer.PieceAttribute;

/**
* A class to organize the various piece types
* @version Apr 25, 2020
*/

public class PieceType {
	private MovementPatternID movementPattern;
	private HashMap<PieceAttributeID, PieceAttribute> attributes;
	
	/**
	 * the constructor for the PieceType class
	 * @param pattern the movement pattern of a piece type
	 * @param attributes the hashmap of attributes for a piece type
	 */
	public PieceType(MovementPatternID pattern, HashMap<PieceAttributeID, PieceAttribute> attributes) {
		this.movementPattern = pattern;
		this.attributes = attributes;
	}

	/**
	 * returns the attributes map
	 * @return attributes
	 */
	public HashMap<PieceAttributeID, PieceAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * returns the movement pattern
	 * @return movement pattern
	 */
	public MovementPatternID getMovementPattern() {
		return movementPattern;
	}
}