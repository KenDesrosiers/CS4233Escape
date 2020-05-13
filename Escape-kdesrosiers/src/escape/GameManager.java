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
import escape.board.coordinate.CoordinateID;
import escape.board.coordinate.HexCoordinate;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.board.coordinate.SquareCoordinate;
import escape.piece.EscapePiece;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.piece.PieceName;
import escape.piece.PieceType;
import escape.piece.Player;
import escape.rule.Rule;
import escape.rule.RuleID;
import escape.rule.movement.AStar;

/**
 * This class is a concrete implementation of the EscapeGameManager Interface
 * @version Apr 25, 2020
 */

public class GameManager implements EscapeGameManager<Coordinate> {
	private Board b;
	private HashMap<PieceName, PieceType> pieceTypes;
	private HashMap<RuleID, Rule> rules;
	private GameObserver observer;
	private Player current;
	private EscapePiece last;
	private Player winner;
	private int[] scores;
	private boolean firstMove;
	private HashMap<EscapePiece, Integer> pieceValueMap;
	private boolean gameOver;
	private int turns;

	/**
	 * This is the constructor for the Game Manager class
	 * @param b the board for the game
	 */
	public GameManager(Board b, HashMap<PieceName, PieceType> types, HashMap<RuleID, Rule> rules) {
		this.b = b;
		this.pieceTypes = types;
		this.rules = rules;
		this.scores = new int[3];
		this.scores[1] = 0;
		this.scores[2] = 0;
		this.firstMove = true;
		this.pieceValueMap = new HashMap<EscapePiece, Integer>();
		this.gameOver = false;
		turns = 0;
	}


	/**
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to) {
		//if game is over
		if(gameOver) {
			if(scores[1] == scores[2]) {
				notifyObserver("The game is over! It was a tie.");
			} else {
				notifyObserver("Game is over and " + winner.name() + " has won.");
			}
			return false;
		}
		
		boolean answer = false;
		boolean valid = true && b.isCoordinateValid(from) && b.isCoordinateValid(to) && b.getPieceAt(from) != null;
		if(valid) {
			valid = valid && (pieceTypes.get(b.getPieceAt(from).getName()) != null);
		} else {
			notifyObserver("There is an issue with the 'from' or 'to' coordinate!");
			return false;
		}
		if(valid) {
			//set the point values of the coordinates
			setPointValues(from, to);
			//check to see if the first move is player1
			EscapePiece p = b.getPieceAt(from);
			Player player = p.getPlayer();
			PieceName name = p.getName();
			if(firstMove && !player.equals(Player.PLAYER1)) {
				notifyObserver("Player 1 is supposed to play first!");
				return false;
			}
			current = player;
			if(last != null && current.equals(last.getPlayer())) {
				notifyObserver("The other player needs to make a move!");
				return false;
			}
			answer = (pieceTypes.get(name).getAttributes().get(PieceAttributeID.FLY) != null) ? fly(from, to, p) : distance(from, to, p);
			if(answer && firstMove) {
				firstMove = false;
			}
		} else {
			notifyObserver("'from' does not have a piece type associated with it!");
			return false;
		}
		//check if the player escaped
		if(b.getLocationTypeAt(to) == LocationType.EXIT && answer) {
			String str = last.getPlayer().toString().substring(6);
			int playerNum = Integer.parseInt(str);
			int newScore = scores[playerNum] + pieceValueMap.get(last);
			scores[playerNum] = newScore;
		}
		
		//check if game is over
		Player higher = (scores[1] > scores[2] || scores[1] == scores[2]) ? Player.PLAYER1 : Player.PLAYER2;
		int higherNum = (higher.equals(Player.PLAYER1)) ? scores[1] : scores[2];
		if((rules.get(RuleID.TURN_LIMIT) != null && turns == rules.get(RuleID.TURN_LIMIT).getIntValue())
				|| (rules.get(RuleID.SCORE) != null && higherNum >= rules.get(RuleID.SCORE).getIntValue())) {
			//notifyObserver(higher + " wins");
			if(scores[1] != scores[2]) {
				notifyObserver(higher + " wins");
			} else {
				notifyObserver("The game is over! It's a tie.");
			}
			gameOver = true;
			winner = last.getPlayer();
			return true;
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
						if(b.getPieceAt(to) == null) {
							b.removePiece(from);
							b.putPieceAt(p, to);
							last = p;
							turns++;
							return true;
						} else {
							boolean success = combat(from, to);
							if(success) {
								last = p;
								turns++;
								return true;
							}
							notifyObserver("Combat not possible!");
							
						}
					}
				} else {
					if (b.isLinear(from, to) && from.distanceTo(to) <= pieceTypes.get(name).getAttributes()
							.get(PieceAttributeID.FLY).getIntValue()) {
						if(b.getID() == CoordinateID.ORTHOSQUARE && !b.isOrthogonal(from, to)) {
							return false;
						}
						if(b.getPieceAt(to) == null) {
							b.removePiece(from);
							b.putPieceAt(p, to);
							last = p;
							turns++;
							return true;
						} else {
							boolean success = combat(from, to);
							if(success) {
								last = p;
								turns++;
								return true;
							}
							notifyObserver("Combat not possible!");
						}
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
				last = p;
				turns++;
				return true;
			}
			else if(b.getPieceAt(target).getPlayer() != p.getPlayer()) {
				boolean success = combat(from, to);
				if(success) {
					last = p;
					turns++;
					return true;
				}
				notifyObserver("Combat not possible!");
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
	
	/**
	 *@see escape.EscapeGameManager#addObserver(GameObserver)
	 */
	@Override
	public GameObserver addObserver(GameObserver o) {
		this.observer = o;
		return observer;
	}
	
	/**
	 *@see escape.EscapeGameManager#removeObserver(GameObserver)
	 */
	@Override
	public GameObserver removeObserver(GameObserver o) {
		if(!observer.equals(o)) {
			return null;
		}
		this.observer = null;
		return o;
	}
	
	/**
	 * Notifies the observer
	 * @param s the message
	 */
	public void notifyObserver(String s) {
		observer.notify(s);
	}
	
	/**
	 * sets the point values for the from and to 
	 * @param c1 from
	 * @param c2 to
	 */
	public void setPointValues(Coordinate c1, Coordinate c2) {
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		list.add(c1);
		if(b.getPieceAt(c2) != null) {
			list.add(c2);
		}
		for(Coordinate c : list) {
			EscapePiece p = b.getPieceAt(c);
			if(pieceValueMap.get(p) == null) {
				if(pieceTypes.get(p.getName()).getAttributes().get(PieceAttributeID.VALUE) == null) {
					pieceValueMap.put(p, 1);
				} else {
					pieceValueMap.put(p, pieceTypes.get(p.getName()).getAttributes().get(PieceAttributeID.VALUE).getIntValue());
				}
			}
		}
	}
	
	/**
	 * sees whether a piece can engage in combat or not, and if it succeeds
	 * @param from the from coordinate
	 * @param to the to coordinate
	 * @return true if so, false if not
	 */
	public boolean combat(Coordinate from, Coordinate to) {
		EscapePiece fromPiece = b.getPieceAt(from);
		EscapePiece toPiece = b.getPieceAt(to);
		int fromValue = pieceValueMap.get(fromPiece);
		int toValue = pieceValueMap.get(toPiece);
		if(rules.containsKey(RuleID.REMOVE)) {
			b.removePiece(to);
			b.removePiece(from);
			b.putPieceAt(fromPiece, to);
			return true;
		}
		else if(rules.containsKey(RuleID.POINT_CONFLICT)) {
			if(fromValue > toValue) {
				pieceValueMap.put(fromPiece, fromValue - toValue);
				b.removePiece(from);
				b.removePiece(to);
				b.putPieceAt(fromPiece, to);
			}
			else if(fromValue < toValue) {
				pieceValueMap.put(toPiece, toValue = fromValue);
				b.removePiece(from);
			}
			else {
				b.removePiece(to);
				b.removePiece(from);
			}
			return true;
		}
		return false;
	}
}