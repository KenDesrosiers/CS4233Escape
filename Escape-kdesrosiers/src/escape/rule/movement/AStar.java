/************************************************************************
 * This file was created for the CS4233: Object-Oriented Analysis & Design
 * course at Worcester Polytechnic Institute.
 * Kenneth Desrosiers
 ************************************************************************/

package escape.rule.movement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import escape.board.Board;
import escape.board.LocationType;
import escape.board.coordinate.Coordinate;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.piece.PieceName;
import escape.piece.PieceType;

/**
 * A class for running a modified A* search algorithm that accounts for all possible obstacles while finding a path
 * @version Apr 28, 2020
 */

public class AStar {
	private PieceName name;
	private Board board;
	private HashMap<PieceName, PieceType> map;
	private ArrayList<Coordinate> Q;
	private ArrayList<Coordinate> old;
	private HashMap<Coordinate, Integer>G;
	private MovementRule rule;
	private Coordinate from;
	private Coordinate to;
	private boolean unblock;
	private boolean jump;

	/**
	 * The constructor for the AStar class
	 * @param n the piece name for the 'from' piece
	 * @param b the board to run it on
	 * @param m the attribute hashmap for the game
	 */
	public AStar(PieceName n, Board b, HashMap<PieceName, PieceType> m) {
		this.name = n;
		this.board = b;
		this.map = m;
		this.Q = new ArrayList<Coordinate>();
		this.old = new ArrayList<Coordinate>();
		this.G = new HashMap<Coordinate, Integer>();
		this.rule = new MovementRule(map.get(name).getMovementPattern(), board);
	}

	/**
	 * Runs a modified A* to find a path between the start and end locations,
	 * modified based on pseudocode on: https://en.wikipedia.org/wiki/A*_search_algorithm
	 * @param f the from coordinate
	 * @param t the to coordinate
	 * @return the path if it exists, null if not
	 */
	public ArrayList<Coordinate> findPath(Coordinate f, Coordinate t) {
		this.from = f;
		this.to = t;
		//if the movement pattern is linear but the from and to aren't, can't find path
		if(map.get(name).getMovementPattern() == MovementPatternID.LINEAR && !board.isLinear(f, t)) {
			return null;
		}
		//if the target is a block, can't find path
		if(board.getLocationTypeAt(t) == LocationType.BLOCK) {
			return null;
		}

		//if coordinates are the same
		if(f.equals(t)) {
			return null;
		}
		unblock = (map.get(name).getAttributes().get(PieceAttributeID.UNBLOCK) != null) ? map.get(name).getAttributes().get(PieceAttributeID.UNBLOCK).isBooleanValue() : false;
		jump = (map.get(name).getAttributes().get(PieceAttributeID.JUMP) != null) ? map.get(name).getAttributes().get(PieceAttributeID.JUMP).isBooleanValue() : false;
		int maxDistance = map.get(name).getAttributes().get(PieceAttributeID.DISTANCE).getIntValue();
		HashMap<Coordinate, Coordinate> previous = new HashMap<Coordinate, Coordinate>();
		ArrayList<Coordinate> validNeighbors = new ArrayList<Coordinate>();
		Q.add(from);
		G.put(from, 0);
		while(Q.size() != 0) {
			Collections.sort(Q, new Comparator<Coordinate>() {
				@Override
				public int compare(Coordinate c1, Coordinate c2) {
					Integer first = new Integer(c1.distanceTo(to));
					Integer second = new Integer(c2.distanceTo(to));
					return first.compareTo(second);
					}
				});
			Coordinate current = Q.get(0);
			Q.remove(current);
			old.add(current);
			//if at target
			if(current.equals(to)) {
				ArrayList<Coordinate> path = backTrace(current, previous);
				if(path == null) {
					return null;
				}
				if(path.size() > maxDistance) {
					return null;
				}
				return path;
			}
			//check to see if target is unreachable
			boolean done = isTargetUnreachable();
			if(done) {
				return null;
			}
			validNeighbors = findAllValidNeighbors(current);
			for(Coordinate neighbor : validNeighbors) {
				if(G.get(neighbor) == null) {
					G.put(neighbor, 999999);
				}
				if(!old.contains(neighbor)) {
					previous.put(neighbor, current);
					int newGScore = G.get(current) + 1;
					if(board.getPieceAt(neighbor) != null) {
						boolean clearNext = (neighbor.equals(to)) ? true : jump && findUnblockedValidClearLinearNeighbor(neighbor, current);
						if(!clearNext) {
							if(map.get(name).getMovementPattern() == MovementPatternID.LINEAR) {
								return null;
							}
							G.put(neighbor, -1);
							continue;
						}
					}
					if(Q.contains(neighbor)) {
						if(newGScore < G.get(neighbor) && G.get(neighbor) != -1) {
							G.put(neighbor, newGScore);
							previous.put(neighbor, current);
						}
					} else if (!old.contains(neighbor) && G.get(neighbor) != -1) {
						Q.add(neighbor);
					}
				}
			}
		}
		return null;
	}


	/**
	 * returns all valid neighbors (besides ones that aren't null)
	 * @param current the coordinate to find neighbors of
	 * @return a list of neighbors
	 */
	ArrayList<Coordinate> findAllValidNeighbors(Coordinate current) {
		ArrayList<Coordinate> neighbors = current.getNeighbors();
		neighbors.removeIf(n-> board.isCoordinateValid(n) == false);
		neighbors.removeIf(n-> (rule.isValidMoveType(n, current, from, to) == false));
		neighbors.removeIf(n -> board.getLocationTypeAt(n) == LocationType.EXIT && !n.equals(to));
		neighbors.removeIf(n -> board.getLocationTypeAt(n) == LocationType.BLOCK && !unblock);
		return neighbors;
	}

	/**
	 * finds whether or not an occupied neighbor has an unoccupied neighbor (or target, occupied or unoccupied) that is linear to the current piece,
	 * if so it forces it to go to that linear piece by making the others unreachable
	 * @param neighbor the occupied neighbor to find the neighbors of
	 * @param current the current node who has an occupied neighbor
	 * @return true if neighbor has a valid child coordinate, false if not
	 */
	public boolean findUnblockedValidClearLinearNeighbor(Coordinate neighbor, Coordinate current) {
		boolean answer = false;
		ArrayList<Coordinate> neighbors = findAllValidNeighbors(neighbor);
		neighbors.removeIf(n -> !board.isLinear(n, current));
		if(current.getX() == neighbor.getX()) {
			neighbors.removeIf(n -> n.getX() != current.getX());
		}
		if(current.getY() == neighbor.getY()) {
			neighbors.removeIf(n -> n.getY() != current.getY());
		}
		if(board.isDiagonal(neighbor, current)) {
			neighbors.removeIf(n -> !board.isDiagonal(n, current));
		}
		neighbors.removeIf(n-> board.getPieceAt(n) != null && !n.equals(to));
		neighbors.removeIf(n -> n.equals(current));
		if(neighbors.size() != 0) {
			boolean x = board.getLocationTypeAt(neighbors.get(0)) != LocationType.BLOCK || unblock;
			answer = (board.getPieceAt(neighbors.get(0)) == null || neighbors.get(0).equals(to)) && x;
		}
		if(answer) {
			ArrayList<Coordinate> neighbors2 = findAllValidNeighbors(neighbor);
			for(Coordinate n : neighbors2) {
				if(!neighbors.contains(n) && !n.equals(current)) {
					G.put(n, -1);
				}
			}
		}
		return answer;
	}

	/**
	 * backtraces and adds all coordinates to path except the start
	 * @param current the target node
	 * @param previous the map holding all parent-child relationships established along the way
	 * @return the path if it exits, null if not
	 */
	public ArrayList<Coordinate> backTrace(Coordinate current, HashMap<Coordinate, Coordinate> previous) {
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		path.add(current);
		if(previous.get(current) == null) {
			return null;
		}
		else {
			while (current != from) {
				Coordinate parent = previous.get(current);
				if(parent != from) {
					path.add(parent);
				}
				current = parent;
			}
			return path;
		}
	}

	/**
	 * see whether or not the target coordinate is unreachable (if all its neighbors are not valid to travel to)
	 * @return true if the target is unreachable, false if not
	 */
	public boolean isTargetUnreachable() {
		boolean answer = false;
		ArrayList<Coordinate> toNeighbors = findAllValidNeighbors(to);
		if(toNeighbors.size() == 0) {
			answer = true;
		} else {
			int done = 0;
			for (Coordinate n : toNeighbors) {
				if(G.get(n) != null && G.get(n) == -1) {
					done++;
				}
			}
			if(done == toNeighbors.size()) {
				answer = true;
			}
		}
		return answer;
	}
}