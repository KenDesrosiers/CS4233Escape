/************************************************************************
 * This file was created for the CS4233: Object-Oriented Analysis & Design
 * course at Worcester Polytechnic Institute.
 * Kenneth Desrosiers
 ************************************************************************/

package escape;
import java.util.ArrayList;
import java.util.HashMap;

import escape.board.Board;
import escape.board.LocationType;
import escape.board.coordinate.Coordinate;
import escape.board.coordinate.HexCoordinate;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.board.coordinate.SquareCoordinate;
import escape.piece.EscapePiece;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.piece.PieceName;
import escape.piece.PieceType;
import escape.piece.Player;
import escape.rule.movement.AStar;

/**
 * This class is a concrete implementation of the EscapeGameManager Interface
 * @version Apr 25, 2020
 */

public class GameManager implements EscapeGameManager<Coordinate> {
	private Board b;
	private HashMap<PieceName, PieceType> pieceTypes;

	/**
	 * This is the constructor for the Game Manager class
	 * @param b the board for the game
	 */
	public GameManager(Board b, HashMap<PieceName, PieceType> types) {
		this.b = b;
		this.pieceTypes = types;
	}


	/**
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to) {
		boolean answer = false;
		boolean valid = true && b.isCoordinateValid(from) && b.isCoordinateValid(to) && b.getPieceAt(from) != null;
		if(valid) {
			valid = valid && (pieceTypes.get(b.getPieceAt(from).getName()) != null);
		}
		if(valid) {
			EscapePiece p = b.getPieceAt(from);
			Player player = p.getPlayer();
			PieceName name = p.getName();
			answer = (pieceTypes.get(name).getAttributes().get(PieceAttributeID.FLY) != null) ? fly(from, to, p) : distance(from, to, p);
		}
		return answer;
	}

	/**
	 * @see escape.EscapeGameManager#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate c) {
		if(c == null) {
			return null;
		}
		if(!b.isCoordinateValid(c) || c == null) {
			return null;
		}
		return b.getPieceAt(c);
	}

	/**
	 *@see escape.EscapeGameManager#makeCoordinate(int, int)
	 */
	@Override
	public Coordinate makeCoordinate(int x, int y) {
		Coordinate c = null;
		switch(b.getID()) {
		case HEX:
			c = HexCoordinate.makeCoordinate(x,y);
			break;
		case ORTHOSQUARE:
			c = OrthoSquareCoordinate.makeCoordinate(x,y);
			break;
		case SQUARE:
			c = SquareCoordinate.makeCoordinate(x, y);
			break;
		}
		return (b.isCoordinateValid(c)) ? c : null;
	}


	/**
	 * performs move if the piece has a fly attribute
	 * @param from the start
	 * @param to the end
	 * @param p the piece to move
	 * @return true if movable, false if not
	 */
	public boolean fly(Coordinate from, Coordinate to, EscapePiece p) {
		PieceName name = p.getName();
		Player player = p.getPlayer();
		MovementPatternID pattern = pieceTypes.get(name).getMovementPattern();
		if(!from.equals(to)) {
			if((b.getPieceAt(to) == null && b.getLocationTypeAt(to) != LocationType.BLOCK) || (b.getPieceAt(to) != null && b.getPieceAt(to).getPlayer() != player)) {
				if (pattern == MovementPatternID.OMNI || pattern == MovementPatternID.ORTHOGONAL
						|| pattern == MovementPatternID.DIAGONAL) {
					if (from.distanceTo(to) <= pieceTypes.get(name).getAttributes().get(PieceAttributeID.FLY)
							.getIntValue()) {
						b.removePiece(from);
						b.putPieceAt(p, to);
						return true;
					}
				} else {
					if (b.isLinear(from, to) && from.distanceTo(to) <= pieceTypes.get(name).getAttributes()
							.get(PieceAttributeID.FLY).getIntValue()) {
						b.removePiece(from);
						b.putPieceAt(p, to);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * performs move if the piece has a distance attribute by running A*
	 * @param from the start
	 * @param to the end
	 * @param p the piece to move
	 * @return true if movable, false if not
	 */
	public boolean distance(Coordinate from, Coordinate to, EscapePiece p) {
		AStar pathFinder = new AStar(p.getName(), b, pieceTypes);
		ArrayList<Coordinate> path = pathFinder.findPath(from, to);
		if(path != null) {
			Coordinate target = path.get(0);
			if(b.getPieceAt(target) == null) {
				b.removePiece(from);
				b.putPieceAt(p, to);
				return true;
			}
			else if(b.getPieceAt(target).getPlayer() != p.getPlayer()) {
				b.removePiece(from);
				b.putPieceAt(p, to);
				return true;
			}
		}
		return false;
	}



	/**
	 *@see escape.EscapeGameManager#getBoard()
	 */
	@Override
	public Board getBoard() {
		return b;
	}


	/**
	 *@see escape.EscapeGameManager#getTypeMap()
	 */
	@Override
	public HashMap<PieceName, PieceType> getTypeMap() {
		return pieceTypes;
	}
}