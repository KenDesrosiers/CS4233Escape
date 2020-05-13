/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape.board;


import escape.board.coordinate.*;
import escape.piece.*;
import escape.util.*;

/**
* A board factory for making new boards
* @version Apr 14, 2020
*/

public class BoardFactory implements Factory{
	private static EscapeGameInitializer i;
	
	/**
	 * a constructor for the BoardFactory
	 * @param b a board initializer object
	 */
	public BoardFactory(EscapeGameInitializer i) {
		this.i = i;
	}
	
	/**
	 * This method makes a new board based on the board initializer
	 * @return the new board
	 */
	public Board make() {
		Board board = null;
		switch(i.getCoordinateType()) {
		case HEX:
			board = new HexBoard(i.getxMax(), i.getyMax(), CoordinateID.HEX);
			break;
		case SQUARE:
			board = new SquareBoard(i.getxMax(), i.getyMax(), CoordinateID.SQUARE);
			break;
		case ORTHOSQUARE:
			board = new SquareBoard(i.getxMax(), i.getyMax(), CoordinateID.ORTHOSQUARE);
			break;
		}
		
		initializeBoard(board, i.getLocationInitializers());
		return board;
	}
	
	/**
	 * initializes the board with info from initializers
	 * @param b the board
	 * @param initializers the LocationInitializer
	 */
	public static void initializeBoard(Board b, LocationInitializer... initializers) {
		Coordinate c = null;
		if(initializers.length != 0) {
			for (LocationInitializer li : initializers) {
				switch(i.getCoordinateType()) {
				case HEX:
					c = HexCoordinate.makeCoordinate(li.x, li.y);
					break;
				case SQUARE:
					c = SquareCoordinate.makeCoordinate(li.x, li.y);
					break;
				case ORTHOSQUARE:
					c = OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
					break;
					}
				if(!b.isCoordinateValid(c)) {
					continue;
					}
				if (li.locationType != null && li.locationType != LocationType.CLEAR) {
					b.setLocationType(c, li.locationType);
					}
				if (li.pieceName != null) {
					b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
					}
				}
			}
		}
}