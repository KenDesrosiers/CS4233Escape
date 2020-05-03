/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape.board;

import java.util.*;
import java.util.function.Predicate;

import escape.board.coordinate.Coordinate;
import escape.board.coordinate.CoordinateID;
import escape.board.coordinate.HexCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
* A concrete class for hex boards
* @version Apr 13, 2020
*/

public class HexBoard implements Board<HexCoordinate> {
	Map<Coordinate, LocationType> squares;
	Map<Coordinate, EscapePiece> pieces;
	private final int xMax, yMax;
	private CoordinateID id;
	
	/**
	 * a constructor for making hex boards
	 * @param xMax the x limit
	 * @param yMax the y limit
	 */
	public HexBoard(int xMax, int yMax, CoordinateID id) {
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<Coordinate, EscapePiece>();
		squares = new HashMap<Coordinate, LocationType>();
		this.id = id;
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(HexCoordinate coord) {
		return pieces.get(coord);
	}
	
	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, HexCoordinate coord) {
		if(isCoordinateValid(coord)) {
			if(squares.get(coord) == LocationType.EXIT || p == null) {
				pieces.remove(coord);
			}
			else if(squares.get(coord) == LocationType.BLOCK) {
				throw new EscapeException("Pieces may not reside here!");
			}
			else {
				pieces.put(coord, p);
			}
		} else {
			throw new EscapeException("Coordinate is not valid!");
		}
	}
	
	/**
	 * gets the x limit of the board
	 * @return the x limit
	 */
	public int getxMax() {
		return xMax;
	}

	/**
	 * gets the y limit of the board
	 * @return the y limit
	 */
	public int getyMax() {
		return yMax;
	}
	
	/**
	 * @see escape.board.OtherBoardMethods#getLocationTypeAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public LocationType getLocationTypeAt(Coordinate c) {
		if(squares.get(c) == null) {
			squares.put(c, LocationType.CLEAR);
		}
		return squares.get(c);
	}

	/**
	 * @see escape.board.OtherBoardMethods#setLocationType(escape.board.coordinate.Coordinate, escape.board.LocationType)
	 */
	@Override
	public void setLocationType(Coordinate c, LocationType lt) {
		squares.put(c, lt);		
	}
	
	
	/**
	 * @see escape.board.OtherBoardMethods#getID()
	 */
	@Override
	public CoordinateID getID() {
		return this.id;
	}
	
	/**
	 * @see escape.board.OtherBoardMethods#isCoordinateValid(escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean isCoordinateValid(Coordinate c) {
		return isCoordinateValid.test((HexCoordinate) c);
	}
	/**
	 * a lambda function to see if the coordinate is valid
	 * @param c the coordinate to check
	 * @return true or false based on validity
	 */
	public Predicate<HexCoordinate> isCoordinateValid = (c) ->
	(c.getX() <= getxMax() && c.getX() >= 0 && getyMax() == 0 && getxMax() != 0) ||
	(c.getY() <= getyMax() && c.getY() >= 0 && getxMax() == 0 && getyMax() != 0) ||
	(getxMax() == 0 && getyMax() == 0);
	
	/**
	 *@see escape.board.OtherBoardMethods#isLinear(Coordinate, Coordinate)
	 */
	@Override
	public boolean isLinear(Coordinate c1, Coordinate c2) {
		boolean answer = false;
		if(c1.getX() == c2.getX()) {
			answer = true;
		}
		else if(c1.getY() == c2.getY()) {
			answer = true;
		}
		else if(Math.abs(c1.getX()) - Math.abs(c2.getX()) == Math.abs(c1.getY()) - Math.abs(c2.getY())
				&& c1.getX() - c2.getX() != c1.getY() - c2.getY()) {
			answer = true;
		}
		return answer;
	}

	/**
	 *@see escape.board.OtherBoardMethods#removePiece(Coordinate)
	 */
	@Override
	public void removePiece(Coordinate c) {
		pieces.remove(c);
	}
}