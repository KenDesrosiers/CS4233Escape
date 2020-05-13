/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape;


import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import escape.EscapeGameBuilder;
import escape.EscapeGameManager;
import escape.board.LocationType;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.piece.PieceAttributeID;
import escape.piece.PieceName;
import escape.piece.Player;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

import java.util.*;


/**
 * tests the movement of pieces
 * @version Apr 24, 2020
 */
class BetaEscapeGameTests {
	GameObserver o;
	@BeforeEach
	public void setupTest() {
		o = new TestObserver();
	}
    @Test
    void hexTest() throws Exception {
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
    assertNotNull(emg);
    }
    
    @Test
    void OrthoSquareTest() throws Exception {
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleOrthoSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        assertNotNull(emg);
        Map<PieceAttributeID, PieceAttribute> attributeMap = emg.getTypeMap().get(PieceName.HORSE).getAttributes();
        assertTrue(attributeMap.get(PieceAttributeID.JUMP).isBooleanValue());
        assertEquals(attributeMap.get(PieceAttributeID.VALUE).getIntValue(), 10);
        assertEquals(attributeMap.get(PieceAttributeID.FLY).getIntValue(), 6);
    }
    
    @Test
    void noDistanceOrFly() throws Exception{
		String s = "Must have either distance or fly, not none or both!";
		EscapeGameBuilder ebg = new EscapeGameBuilder(new File("config/PieceTypeWithoutFlyOrDistance.xml"));
		EscapeException exception = Assertions.assertThrows(EscapeException.class,
				() -> {
					EscapeGameManager emg = ebg.makeGameManager();
				});
		assertEquals(s, exception.getMessage());
    }
    
    @Test
    void bothDistanceAndFly() throws Exception{
		String s = "Must have either distance or fly, not none or both!";
		EscapeGameBuilder ebg = new EscapeGameBuilder(new File("config/PieceTypeWithFlyAndDistance.xml"));
		EscapeException exception = Assertions.assertThrows(EscapeException.class,
				() -> {
					EscapeGameManager emg = ebg.makeGameManager();
				});
		assertEquals(s, exception.getMessage());
    }
    
    @Test
    void noMovementPattern() throws Exception{
		String s = "There's a piece type without a movement pattern!";
		EscapeGameBuilder ebg = new EscapeGameBuilder(new File("config/PieceTypeWithoutMovementPattern.xml"));
		EscapeException exception = Assertions.assertThrows(EscapeException.class,
				() -> {
					EscapeGameManager emg = ebg.makeGameManager();
				});
		assertEquals(s, exception.getMessage());
    }
    
    @Test
    void noName() throws Exception{
		String s = "There's a piece type without a name!";
		EscapeGameBuilder ebg = new EscapeGameBuilder(new File("config/PieceTypeWithoutName.xml"));
		EscapeException exception = Assertions.assertThrows(EscapeException.class,
				() -> {
					EscapeGameManager emg = ebg.makeGameManager();
				});
		assertEquals(s, exception.getMessage());
    }
    
    @Test
    void noPieceTypes() throws Exception{
		String s = "No piece rules! Need atleast one.";
		EscapeGameBuilder ebg = new EscapeGameBuilder(new File("config/noPieceTypes.xml"));
		EscapeException exception = Assertions.assertThrows(EscapeException.class,
				() -> {
					EscapeGameManager emg = ebg.makeGameManager();
				});
		assertEquals(s, exception.getMessage());
    }
    
    @Test
    void hexDiagonal() throws Exception{
		String s = "This movement pattern is incorrect with this coordinate type!";
		EscapeGameBuilder ebg = new EscapeGameBuilder(new File("config/SampleHexDiagonal.xml"));
		EscapeException exception = Assertions.assertThrows(EscapeException.class,
				() -> {
					EscapeGameManager emg = ebg.makeGameManager();
				});
		assertEquals(s, exception.getMessage());
    }
    
    @Test
    void hexValidCoordinate() throws Exception{
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
    assertNotNull(emg.makeCoordinate(-3, -3));
    }
    
    @Test
    void orthoValid() throws Exception{
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleOrthoSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
    assertNotNull(emg.makeCoordinate(5, 3));
    }
    
    @Test
    void orthoNotValid() throws Exception{
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleOrthoSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
    assertNull(emg.makeCoordinate(6, 9));
    }
    
    @Test
    void squareValid() throws Exception{
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
    assertNotNull(emg.makeCoordinate(19, 20));
    }
    
    @Test
    void squareNotValid() throws Exception{
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
    assertNull(emg.makeCoordinate(21, 1));
    }
    
    @Test
    void getPieceAtNull() throws Exception{
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
    assertNull(emg.getPieceAt(emg.makeCoordinate(3, 3)));
    }
    
    @Test
    void getPieceAtNotNull() throws Exception{
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(EscapePiece.makePiece(Player.PLAYER1, PieceName.FOX), emg.makeCoordinate(3, 3));
    assertNotNull(emg.getPieceAt(emg.makeCoordinate(3, 3)));
    }
    
    @Test
    void getPieceAtInvalid() throws Exception{
        EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
    assertNull(emg.getPieceAt(emg.makeCoordinate(30, 30)));
    }
    
    @Test
    void moveOrtho1() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleOrthoSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1, PieceName.HORSE), emg.makeCoordinate(1, 1));
        assertTrue(emg.move(emg.makeCoordinate(1, 1),emg.makeCoordinate(7, 1)));
    }
    
    @Test
    void moveHex1() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1, PieceName.SNAIL), emg.makeCoordinate(1, 1));
        assertFalse(emg.move(emg.makeCoordinate(1, 1),emg.makeCoordinate(1, 1)));
    }
    
    @Test
    void moveHex2() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().setLocationType(emg.makeCoordinate(0, -3), LocationType.BLOCK);
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1, PieceName.SNAIL), emg.makeCoordinate(1, 1));
        assertFalse(emg.move(emg.makeCoordinate(1, 1),emg.makeCoordinate(0, -3)));
    }
    
    @Test
    void moveHex3() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1, PieceName.FOX), emg.makeCoordinate(0, 0));
        assertTrue(emg.move(emg.makeCoordinate(0, 0),emg.makeCoordinate(5, 0)));
    }
    
    @Test
    void moveSquare1() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.addObserver(o);
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1, PieceName.HORSE), emg.makeCoordinate(1, 1));
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HORSE), emg.makeCoordinate(6, 7));
        assertFalse(emg.move(emg.makeCoordinate(1, 1),emg.makeCoordinate(6, 7)));
    }
    
    @Test
    void moveSquare2() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1, PieceName.HORSE), emg.makeCoordinate(1, 1));
        assertTrue(emg.move(emg.makeCoordinate(1, 1),emg.makeCoordinate(6, 7)));
    }
    @Test
    void getPieceAtInvalidCoord() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        assertNull(emg.getPieceAt(emg.makeCoordinate(0,0)));
    }
    
    @Test
    void moveHex4() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1, PieceName.SNAIL), emg.makeCoordinate(1, 1));
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.SNAIL), emg.makeCoordinate(4, 4));
        assertTrue(emg.move(emg.makeCoordinate(1, 1),emg.makeCoordinate(4, 4)));
    }
    
    @Test
    void pieceOnExit() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.SNAIL), emg.makeCoordinate(4, 4));
        assertNull(emg.getPieceAt(emg.makeCoordinate(4, 4)));
    }
    
    @Test
    void placeNullOnPiece() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.SNAIL), emg.makeCoordinate(5, 4));
        emg.getBoard().putPieceAt(null, emg.makeCoordinate(5, 4));
        assertNull(emg.getPieceAt(emg.makeCoordinate(5, 4)));
    }
    
    void pieceOnExit2() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.SNAIL), emg.makeCoordinate(9, 9));
        assertNull(emg.getPieceAt(emg.makeCoordinate(9, 9)));
    }
    
    @Test
    void placeNullOnPiece2() throws Exception{
    	EscapeGameBuilder egb 
        = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.SNAIL), emg.makeCoordinate(5, 4));
        emg.getBoard().putPieceAt(null, emg.makeCoordinate(5, 4));
        assertNull(emg.getPieceAt(emg.makeCoordinate(5, 4)));
    }
    @Test
    void randomLocationInitializerTest(){
    	LocationInitializer li = new LocationInitializer(1,1,LocationType.CLEAR,Player.PLAYER1,PieceName.FOX);
        assertNotNull(li);
    }
    
    @Test
    void flyTooLong() throws Exception{
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.addObserver(o);
        emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.SNAIL), emg.makeCoordinate(1, 1));
        assertFalse(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(100, 100)));
    }
}
