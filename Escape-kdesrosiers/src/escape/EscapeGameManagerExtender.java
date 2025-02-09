/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape;

import java.util.HashMap;

import escape.board.Board;
import escape.piece.PieceName;
import escape.piece.PieceType;

/**
* Has more methods for GameManagers
* @version Apr 25, 2020
*/

public interface EscapeGameManagerExtender {
	
	/**
	 * gets the board associated with the game manager
	 * @return the board
	 */
	public Board getBoard();
	
	/**
	 * gets the typeMap associated with the game manager
	 * @return the typeMap
	 */
	public HashMap<PieceName, PieceType> getTypeMap();
}