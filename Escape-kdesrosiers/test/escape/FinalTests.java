/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import escape.board.LocationType;
import escape.piece.*;



/**
* tests game with rules
* @version May 10, 2020
*/
public class FinalTests {
	GameObserver o;
	@BeforeEach
	public void setupTest() {
		o = new TestObserver();
	}
	
	@Test
	void hexMove1() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/Hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, 0));
		assertTrue(emg.move(emg.makeCoordinate(0, 0), emg.makeCoordinate(10, 0)));
		assertTrue(emg.move(emg.makeCoordinate(3, 4), emg.makeCoordinate(3, 0)));
	}
	
	@Test
	void hexNoCombat1() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/Hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, 0));
		assertFalse(emg.move(emg.makeCoordinate(0, 0), emg.makeCoordinate(3, 4)));
		assertTrue(((TestObserver) o).sameMessage("Combat not possible!"));
	}
	
	@Test
	void squareWinByLimit() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SquareRemove.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		EscapePiece p2 = new EscapePiece(Player.PLAYER2, PieceName.FROG);
		emg.getBoard().putPieceAt(p2, emg.makeCoordinate(10, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertTrue(emg.move(emg.makeCoordinate(10, 1), emg.makeCoordinate(9, 1)));
		assertTrue(emg.move(emg.makeCoordinate(2, 1), emg.makeCoordinate(3, 1)));
		assertTrue(emg.move(emg.makeCoordinate(9, 1), emg.makeCoordinate(8, 1)));
		assertTrue(emg.move(emg.makeCoordinate(3, 1), emg.makeCoordinate(4, 1)));
		emg.getBoard().setLocationType(emg.makeCoordinate(7, 1), LocationType.EXIT);
		assertTrue(emg.move(emg.makeCoordinate(8, 1), emg.makeCoordinate(7, 1)));
		assertTrue(((TestObserver) o).sameMessage("PLAYER2 wins"));
		emg.move(emg.makeCoordinate(4, 1), emg.makeCoordinate(5, 1));
		assertTrue(((TestObserver) o).sameMessage("Game is over and PLAYER2 has won."));
	}
	
	@Test
	void squareWinByScore() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SquareRemove.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		EscapePiece p2 = new EscapePiece(Player.PLAYER2, PieceName.FROG);
		emg.getBoard().putPieceAt(p2, emg.makeCoordinate(10, 1));
		emg.getBoard().setLocationType(emg.makeCoordinate(2, 1), LocationType.EXIT);
		emg.getBoard().setLocationType(emg.makeCoordinate(9, 1), LocationType.EXIT);
		emg.getBoard().setLocationType(emg.makeCoordinate(4, 1), LocationType.EXIT);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1, PieceName.FROG), emg.makeCoordinate(3, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertTrue(emg.move(emg.makeCoordinate(10, 1), emg.makeCoordinate(9, 1)));
		assertTrue(emg.move(emg.makeCoordinate(3, 1), emg.makeCoordinate(4, 1)));
		assertTrue(((TestObserver) o).sameMessage("PLAYER1 wins"));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.FROG), emg.makeCoordinate(10, 2));
		emg.move(emg.makeCoordinate(10, 2), emg.makeCoordinate(10, 3));
		assertTrue(((TestObserver) o).sameMessage("Game is over and PLAYER1 has won."));
	}
	
	@Test
	void squareWinByLimitTie() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SquareRemove.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		EscapePiece p2 = new EscapePiece(Player.PLAYER2, PieceName.HORSE);
		emg.getBoard().putPieceAt(p2, emg.makeCoordinate(10, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertTrue(emg.move(emg.makeCoordinate(10, 1), emg.makeCoordinate(9, 1)));
		assertTrue(emg.move(emg.makeCoordinate(2, 1), emg.makeCoordinate(3, 1)));
		assertTrue(emg.move(emg.makeCoordinate(9, 1), emg.makeCoordinate(8, 1)));
		assertTrue(emg.move(emg.makeCoordinate(3, 1), emg.makeCoordinate(4, 1)));
		assertTrue(emg.move(emg.makeCoordinate(8, 1), emg.makeCoordinate(7, 1)));
		assertTrue(((TestObserver) o).sameMessage("The game is over! It's a tie."));
		emg.move(emg.makeCoordinate(4, 1), emg.makeCoordinate(5, 1));
		assertTrue(((TestObserver) o).sameMessage("The game is over! It was a tie."));
	}
	
	@Test
	void HexFromNull() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/Hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		assertFalse(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertTrue(((TestObserver) o).sameMessage("There is an issue with the 'from' or 'to' coordinate!"));
	}
	
	@Test
	void HexPlayer2First() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/Hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		EscapePiece p2 = new EscapePiece(Player.PLAYER2, PieceName.HORSE);
		emg.getBoard().putPieceAt(p2, emg.makeCoordinate(10, 1));
		assertFalse(emg.move(emg.makeCoordinate(10, 1), emg.makeCoordinate(2, 1)));
		assertTrue(((TestObserver) o).sameMessage("Player 1 is supposed to play first!"));
	}
	
	@Test
	void twiceInARow() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/Hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertFalse(emg.move(emg.makeCoordinate(2, 1), emg.makeCoordinate(3, 1)));
		assertTrue(((TestObserver) o).sameMessage("The other player needs to make a move!"));
	}
	
	@Test
	void noPieceType() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/Hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HUMMINGBIRD);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		assertFalse(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertTrue(((TestObserver) o).sameMessage("'from' does not have a piece type associated with it!"));
	}
	
	@Test
	void squareRemove() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SquareRemove.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.FROG), emg.makeCoordinate(2, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertNull(emg.getBoard().getPieceAt(emg.makeCoordinate(1, 1)));
	}
	
	@Test
	void squareConflictFromWins() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SquarePointConflict.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HORSE), emg.makeCoordinate(2, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertNull(emg.getBoard().getPieceAt(emg.makeCoordinate(1, 1)));
		assertEquals(emg.getBoard().getPieceAt(emg.makeCoordinate(2, 1)), p);
	}
	
	@Test
	void squareConflictToWins() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SquarePointConflict.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HORSE), emg.makeCoordinate(2, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertNull(emg.getBoard().getPieceAt(emg.makeCoordinate(1, 1)));
		assertNotEquals(emg.getBoard().getPieceAt(emg.makeCoordinate(2, 1)), p);
	}
	
	@Test
	void squareConflictBothLose() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SquarePointConflict.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.FROG), emg.makeCoordinate(2, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertNull(emg.getBoard().getPieceAt(emg.makeCoordinate(1, 1)));
		assertNull(emg.getBoard().getPieceAt(emg.makeCoordinate(2, 1)));
	}
	
	@Test
	void hexRemove1() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/HexWithRules.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.FROG), emg.makeCoordinate(2, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertNull(emg.getBoard().getPieceAt(emg.makeCoordinate(1, 1)));
	}
	
	@Test
	void hexRemove2() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/HexWithRules.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.SNAIL);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.FROG), emg.makeCoordinate(2, 1));
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 1)));
		assertNull(emg.getBoard().getPieceAt(emg.makeCoordinate(1, 1)));
	}
	
	@Test
	void hexNoCombat2() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/Hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, 0));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.FROG), emg.makeCoordinate(1, 0));
		assertFalse(emg.move(emg.makeCoordinate(0, 0), emg.makeCoordinate(1, 0)));
		assertTrue(((TestObserver) o).sameMessage("Combat not possible!"));
	}
	
	@Test
	void hexNoCombat3() throws Exception{
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/Hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		emg.addObserver(o);
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.SNAIL);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, 0));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.FROG), emg.makeCoordinate(1, 0));
		assertFalse(emg.move(emg.makeCoordinate(0, 0), emg.makeCoordinate(1, 0)));
		assertTrue(((TestObserver) o).sameMessage("Combat not possible!"));
	}
}