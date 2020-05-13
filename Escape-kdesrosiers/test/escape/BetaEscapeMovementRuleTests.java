/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape;


import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

import escape.EscapeGameBuilder;
import escape.EscapeGameManager;
import escape.piece.MovementPatternID;
import escape.rule.movement.*;

/**
* tests the movement rules
* @version Apr 26, 2020
*/

public class BetaEscapeMovementRuleTests {
	
	@Test
	void OrthogonalMovementTrue1() throws Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleOrthoSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule ortho = new MovementRule(MovementPatternID.ORTHOGONAL, emg.getBoard());
		assertTrue(ortho.isValidMoveType(emg.makeCoordinate(1, 1), emg.makeCoordinate(4, 1), null, null));
	}
	
	@Test
	void OrthogonalMovementTrue2() throws Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleOrthoSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule ortho = new MovementRule(MovementPatternID.ORTHOGONAL, emg.getBoard());
		assertTrue(ortho.isValidMoveType(emg.makeCoordinate(1, 1), emg.makeCoordinate(1, 5), null, null));
	}
	
	@Test
	void OrthogonalMovementFalse() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleOrthoSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule ortho = new MovementRule(MovementPatternID.ORTHOGONAL, emg.getBoard());
		assertFalse(ortho.isValidMoveType(emg.makeCoordinate(1, 1), emg.makeCoordinate(5, 5), null, null));
	}
	
	@Test
	void DiagonalMovementTrue1() throws Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule diagonal = new MovementRule(MovementPatternID.DIAGONAL, emg.getBoard());
		assertTrue(diagonal.isValidMoveType(emg.makeCoordinate(1,1), emg.makeCoordinate(5,5), null, null));
	}
	
	@Test
	void DiagonalMovementTrue2() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule diagonal = new MovementRule(MovementPatternID.DIAGONAL, emg.getBoard());
		assertTrue(diagonal.isValidMoveType(emg.makeCoordinate(8,1), emg.makeCoordinate(3,6),null,null));
	}
	
	@Test
	void DiagonalMovementFalse1() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule diagonal = new MovementRule(MovementPatternID.DIAGONAL, emg.getBoard());
		assertFalse(diagonal.isValidMoveType(emg.makeCoordinate(1,1), emg.makeCoordinate(8,1),null,null));
	}
	
	@Test
	void DiagonalMovementFalse2() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule diagonal = new MovementRule(MovementPatternID.DIAGONAL, emg.getBoard());
		assertFalse(diagonal.isValidMoveType(emg.makeCoordinate(6,3), emg.makeCoordinate(6,8),null,null));
	}
	
	@Test
	void LinearHexTrue() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule linear = new MovementRule(MovementPatternID.LINEAR, emg.getBoard());
		assertTrue(linear.isValidMoveType(emg.makeCoordinate(0,0), emg.makeCoordinate(0,-3),emg.makeCoordinate(0, -4), emg.makeCoordinate(0, 1)));
	}
	
	@Test
	void LinearHexFalse() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule linear = new MovementRule(MovementPatternID.LINEAR, emg.getBoard());
		assertFalse(linear.isValidMoveType(emg.makeCoordinate(0,0), emg.makeCoordinate(2,-1), emg.makeCoordinate(1, 1), emg.makeCoordinate(1, 5)));
	}
	
	@Test
	void LinearSquareTrue1() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule linear = new MovementRule(MovementPatternID.LINEAR, emg.getBoard());
		assertTrue(linear.isValidMoveType(emg.makeCoordinate(2,1), emg.makeCoordinate(5,1),emg.makeCoordinate(8, 1), emg.makeCoordinate(1, 1)));
	}

	@Test
	void LinearSquareTrue2() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule linear = new MovementRule(MovementPatternID.LINEAR, emg.getBoard());
		assertTrue(linear.isValidMoveType(emg.makeCoordinate(3,2), emg.makeCoordinate(7,7),emg.makeCoordinate(2, 1), emg.makeCoordinate(8, 7)));
	}
	
	@Test
	void LinearSquareFalse() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule linear = new MovementRule(MovementPatternID.LINEAR, emg.getBoard());
		assertFalse(linear.isValidMoveType(emg.makeCoordinate(2,3), emg.makeCoordinate(2,6),emg.makeCoordinate(1, 2), emg.makeCoordinate(5, 7)));
	}
	
	@Test
	void OmniSquareTrue1() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule omni = new MovementRule(MovementPatternID.OMNI, emg.getBoard());
		assertTrue(omni.isValidMoveType(emg.makeCoordinate(2,1), emg.makeCoordinate(6,7),null, null));
	}
	
	@Test
	void OmniSquareTrue2() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule omni = new MovementRule(MovementPatternID.OMNI, emg.getBoard());
		assertTrue(omni.isValidMoveType(emg.makeCoordinate(3,5), emg.makeCoordinate(6,5),null, null));
	}
	
	@Test
	void OmniSquareTrue3() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule omni = new MovementRule(MovementPatternID.OMNI, emg.getBoard());
		assertTrue(omni.isValidMoveType(emg.makeCoordinate(3,5), emg.makeCoordinate(1,7),null, null));
	}
	
	@Test
	void OmniSquareFalse1() throws  Exception{
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
		MovementRule omni = new MovementRule(MovementPatternID.OMNI, emg.getBoard());
		assertFalse(omni.isValidMoveType(emg.makeCoordinate(3,3), emg.makeCoordinate(3,3),null, null));
	}
}