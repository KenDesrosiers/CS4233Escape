/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board;

import java.util.*;
import java.util.function.Predicate;

import escape.board.coordinate.Coordinate;
import escape.board.coordinate.CoordinateID;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.board.coordinate.SquareCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * An example of how a Board might be implemented. This board has
 * square coordinates and finite bounds, represented by xMax and yMax.
 * All methods required by the Board interface have been implemented. Students
 * would naturally add methods based upon theire design.
 * **CHOSE TO KEEP THIS FILE**
 * @version Apr 2, 2020
 */
public class SquareBoard implements Board<Coordinate> {
	Map<Coordinate, LocationType> squares;
	Map<Coordinate, EscapePiece> pieces;
	private final int xMax, yMax;
	CoordinateID id;
	
	/**
	 * Constructor to create a SquareBoard
	 * @param xMax the x value limit
	 * @param yMax the y value limit
	 */
	public SquareBoard(int xMax, int yMax, CoordinateID id) {
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
	public EscapePiece getPieceAt(Coordinate coord) {
		return pieces.get(coord);
	}
	
	/**
	 *@see escape.board.OtherBoardMethods#getLocationTypeAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public LocationType getLocationTypeAt(Coordinate c) {
		if(squares.get(c) == null) {
			squares.put(c, LocationType.CLEAR);
		}
		return squares.get(c);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, Coordinate coord) {
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
	 * returns the x limit for the board
	 * @return the x limit
	 */
	public int getxMax() {
		return xMax;
	}
	
	
	/**
	 *@see escape.board.OtherBoardMethods#getID()
	 */
	@Override
	public CoordinateID getID() {
		return this.id;
	}

	/**
	 * returns the y limit for the board
	 * @return the y limit
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @see escape.board.OtherBoardMethods#setLocationType(escape.board.coordinate.Coordinate, escape.board.LocationType)
	 */
	@Override
	public void setLocationType(Coordinate c, LocationType lt) {
		squares.put(c, lt);
	}
	
	
	/**
	 * A lambda to check to see if the given square coordinate is valid for the board
	 * @param c the coordinate to check
	 * @return true or false based on validity
	 */
	public Predicate<SquareCoordinate> isSquareCoordinateValid = (c) ->
	(c.getX() <= getxMax() && c.getY() <= getyMax() && c.getX() >= 1 && c.getY() >= 1);
	
	/**
	 * A lambda to check to see if the given orthosquare coordinate is valid for the board
	 * @param c the coordinate to check
	 * @return true or false based on validity
	 */
	public Predicate<OrthoSquareCoordinate> isOrthoCoordinateValid = (c) ->
	(c.getX() <= getxMax() && c.getY() <= getyMax() && c.getX() >= 1 && c.getY() >= 1);
	
	
	/**
	 * @see escape.board.OtherBoardMethods#isCoordinateValid(escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean isCoordinateValid(Coordinate c) {
		boolean result;
		result = (c instanceof SquareCoordinate) ? isSquareCoordinateValid.test((SquareCoordinate) c)
				: isOrthoCoordinateValid.test((OrthoSquareCoordinate) c);
		return result;
	}
	
	/**
	 *@see escape.board.OtherBoardMethods#isLinear(Coordinate, Coordinate)
	 */
	@Override
	public boolean isLinear(Coordinate c1, Coordinate c2) {
		return isDiagonal(c1, c2) || isOrthogonal(c1, c2);
	}

	/**
	 *@see escape.board.OtherBoardMethods#removePiece(Coordinate)
	 */
	@Override
	public void removePiece(Coordinate c) {
		pieces.remove(c);
	}
	
}
