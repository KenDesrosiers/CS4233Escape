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
package escape.board.coordinate;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * This is an example of how a SquareCoordinate might be organized.
 * ** DECIDED TO KEEP **
 * 
 * @version Mar 27, 2020
 */
public class SquareCoordinate implements Coordinate {
    private final int x;
    private final int y;
    
    /**
     * This is the constructor for the SquareCoordinate class
     * @param x the row value of the coordinate
     * @param y the column value of the coordinate
     */
    private SquareCoordinate(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    /**
     * This method returns a new coordinate at the given x and y,
     * or returns the coordinate if it already exists
     * @param x the row value
     * @param y the column value
     * @return
     */
    public static SquareCoordinate makeCoordinate(int x, int y) {
    	return new SquareCoordinate(x, y);
    }
    
    /*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c) {
		return Math.max(Math.abs(getX() - c.getX()), Math.abs(getY() - c.getY()));
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

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SquareCoordinate)) {
			return false;
		}
		SquareCoordinate other = (SquareCoordinate) obj;
		return x == other.x && y == other.y;
	}
}
