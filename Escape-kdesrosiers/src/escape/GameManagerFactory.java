/************************************************************************
 * This file was created for the CS4233: Object-Oriented Analysis & Design
 * course at Worcester Polytechnic Institute.
 * Kenneth Desrosiers
 ************************************************************************/

package escape;

import java.util.ArrayList;
import java.util.HashMap;

import escape.board.Board;
import escape.board.BoardFactory;
import escape.board.coordinate.CoordinateID;
import escape.exception.EscapeException;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.piece.PieceName;
import escape.piece.PieceType;
import escape.rule.*;
import escape.util.EscapeGameInitializer;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * A game factory for making new game managers
 * @version Apr 25, 2020
 */

public class GameManagerFactory {
	private static EscapeGameInitializer gameInitializer;

	/**
	 * Constructor for the game factory
	 * @param gameInitializer
	 */
	public GameManagerFactory(EscapeGameInitializer gameInitializer) {
		this.gameInitializer = gameInitializer;
	}

	/**
	 * This method returns a new game manager based on the game initializer file
	 * @return a new game manager
	 */
	public static GameManager makeGame() {
		Board b = makeBoard();
		return new GameManager(b, initializePieceTypes(b, gameInitializer.getPieceTypes()), setRules(gameInitializer.getRules()));
	}

	/**
	 * Creates a new board for the game manager
	 * @return the new board
	 */
	public static Board makeBoard() {
		BoardFactory boardFactory = new BoardFactory(gameInitializer);
		return boardFactory.makeBoard();
	}

	/**
	 * initializes the  game manager to have all of the piece types
	 * @param the board that goes with the game manager, needed for setting a piece movement pattern to orthogonal
	 * if it is omni AND the square type is orthogonal
	 * @param pieceTypeInitializers the piece type initializers extracted from the xml file
	 * @return a hashmap with all of the piece types
	 */
	public static HashMap<PieceName, PieceType> initializePieceTypes(Board b, PieceTypeInitializer... pieceTypeInitializers) {
		HashMap<PieceName, PieceType> PieceTypeMap = new HashMap<PieceName, PieceType>();
		if(pieceTypeInitializers != null) {
			for(PieceTypeInitializer pti : pieceTypeInitializers) {
				if (pti.getMovementPattern() == null) {
					Exception e = new Exception();
					throw new EscapeException("There's a piece type without a movement pattern!", e);
				}
				if(pti.getPieceName() == null) {
					Exception e = new Exception();
					throw new EscapeException("There's a piece type without a name!", e);
				}
				HashMap<PieceAttributeID, PieceAttribute> attributeMap = new HashMap<PieceAttributeID, PieceAttribute>();
				if((pti.getMovementPattern() != MovementPatternID.ORTHOGONAL && pti.getMovementPattern() != MovementPatternID.DIAGONAL
						&& gameInitializer.getCoordinateType() == CoordinateID.HEX)
						|| (pti.getMovementPattern() != MovementPatternID.DIAGONAL && gameInitializer.getCoordinateType() == CoordinateID.ORTHOSQUARE)
						|| (gameInitializer.getCoordinateType() == CoordinateID.SQUARE)) {
					if (pti.getAttributes() != null) {
						for (PieceAttribute attr : pti.getAttributes()) {
							attributeMap.put(attr.getId(), attr);
						}
					}
				} else {
					throw new EscapeException("This movement pattern is incorrect with this coordinate type!");
				}
				if ((attributeMap.get(PieceAttributeID.DISTANCE) == null
						&& attributeMap.get(PieceAttributeID.FLY) == null)
						|| (attributeMap.get(PieceAttributeID.DISTANCE) != null && attributeMap.get(PieceAttributeID.FLY) != null)) {
					throw new EscapeException("Must have either distance or fly, not none or both!");
				}
				if ((attributeMap.get(PieceAttributeID.DISTANCE) != null
						&& attributeMap.get(PieceAttributeID.DISTANCE).getIntValue() < 0)
						|| (attributeMap.get(PieceAttributeID.FLY) != null
						&& attributeMap.get(PieceAttributeID.FLY).getIntValue() < 0)) {
					throw new EscapeException("Distance and Fly values can't be less than zero!");
				}
				MovementPatternID pattern = (b.getID() == CoordinateID.ORTHOSQUARE && pti.getMovementPattern() == MovementPatternID.OMNI)
						? MovementPatternID.ORTHOGONAL : pti.getMovementPattern();
				PieceType type = new PieceType(pattern, attributeMap);
				PieceTypeMap.put(pti.getPieceName(), type);
			}
		} else {
			throw new EscapeException("No piece rules! Need atleast one.");
		}
		LocationInitializer[] locationList = gameInitializer.getLocationInitializers();
		if (locationList.length != 0) {
			for (int i = 0; i < locationList.length; i++) {
				if (locationList[i].pieceName != null && !PieceTypeMap.containsKey(locationList[i].pieceName)) {
					throw new EscapeException("This piece cannot be initialized without a defined piece type!");
				}
			}
		}
		return PieceTypeMap;
	}
	
	public static HashMap<RuleID, Rule> setRules(Rule ... rules) {
		HashMap<RuleID, Rule> theRules = new HashMap<RuleID, Rule>();
		ArrayList<RuleID> ids = new ArrayList<RuleID>();
		if(rules != null) {
			for(Rule r : rules) {
				theRules.put(r.getId(), r);
				ids.add(r.getId());
			}
			if(ids.contains(RuleID.REMOVE) && ids.contains(RuleID.POINT_CONFLICT)) {
				throw new EscapeException("Cannot have both!");
			}
		}
		return theRules;
	}
}