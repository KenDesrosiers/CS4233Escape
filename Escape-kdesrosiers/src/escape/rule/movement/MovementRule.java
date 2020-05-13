/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape.rule.movement;

import escape.board.Board;
import escape.board.coordinate.Coordinate;
import escape.piece.MovementPatternID;

/**
* A class for seeing whether or not two coordinates follow a certain movement pattern, used in A* for getting valid neighbors
* @version Apr 26, 2020
*/

public class MovementRule {
	private MovementPatternID pattern;
	private Board board;
	
	/**
	 * Constructor for the MovementRule class
	 * @param pattern the movement pattern of a piece type
	 * @param board the board to check against
	 */
	public MovementRule(MovementPatternID pattern, Board board) {
		this.pattern = pattern;
		this.board = board;
	}
	
	/**
	 * checks to see if the given coordinates are valid based on the type of movement
	 * @param neighbor the neighbor to check the validity of
	 * @param current the current coordinate
	 * @param from used to see if the neighbor is linear with the start (used only in linear)
	 * @param to used to see if the neighbor is linear with the end (used only in linear)
	 * @return
	 */
	public boolean isValidMoveType(Coordinate neighbor, Coordinate current, Coordinate from, Coordinate to) {
		boolean result = false;
		switch(pattern) {
		case ORTHOGONAL:
			result = board.isOrthogonal(neighbor, current);
			break;
		case DIAGONAL:
			result = board.isDiagonal(neighbor, current);
			break;
		case LINEAR:
			result = board.isLinear(neighbor, from) && board.isLinear(neighbor, to);
			break;
		case OMNI:
			result = board.isOmni(current, neighbor);
			break;
		}
		return result;
	}
}