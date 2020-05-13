/************************************************************************
 * This file was created for the CS4233: Object-Oriented Analysis & Design
 * course at Worcester Polytechnic Institute.
 * Kenneth Desrosiers
 ************************************************************************/

package escape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import escape.board.LocationType;
import escape.board.coordinate.Coordinate;
import escape.piece.EscapePiece;
import escape.piece.PieceName;
import escape.piece.Player;
import escape.rule.movement.AStar;

/**
 * tests the A* algorithm
 * @version Apr 29, 2020
 */

public class BetaEscapeAStarTests {

	@Test
	void SquarePath() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 2));
		assertNotNull(foundPath);
	}

	@Test
	void SquarePath2() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 2));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 2), emg.makeCoordinate(6, 7));
		assertNotNull(foundPath);
	}

	@Test
	void SquarePath3() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(8, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HORSE), emg.makeCoordinate(5, 4));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(8, 1), emg.makeCoordinate(4, 5));
		assertNotNull(foundPath);
	}

	@Test
	void SquarePath4() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(8, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HORSE), emg.makeCoordinate(5, 4));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HORSE), emg.makeCoordinate(6, 3));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(8, 1), emg.makeCoordinate(4, 5));
		assertNull(foundPath);
	}

	@Test
	void SquarePath5() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(8, 1));
		emg.getBoard().setLocationType(emg.makeCoordinate(6, 3), LocationType.EXIT);
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(8, 1), emg.makeCoordinate(4, 5));
		assertNull(foundPath);
	}

	@Test
	void HexPath1() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, 0));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(0, 0), emg.makeCoordinate(21, 21));
		assertNull(foundPath);
	}

	@Test
	void HexPath2() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, -3));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(0, -3), emg.makeCoordinate(0, 4));
		assertNotNull(foundPath);
	}

	@Test
	void SquarePath6() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 6));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 6), emg.makeCoordinate(6, 6));
		assertNotNull(foundPath);
		assertEquals(foundPath.contains(emg.makeCoordinate(5, 6)), false);
	}

	@Test
	void SquarePath7() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 6));
		emg.getBoard().setLocationType(emg.makeCoordinate(4, 6), LocationType.BLOCK);
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 6), emg.makeCoordinate(6, 6));
		assertNotNull(foundPath);
	}

	@Test
	void SquarePath8() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 6));
		emg.getBoard().setLocationType(emg.makeCoordinate(4, 6), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(4, 5), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(5, 5), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(4, 7), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(5, 7), LocationType.BLOCK);
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 6), emg.makeCoordinate(6, 6));
		assertNotNull(foundPath);
	}

	@Test
	void SquarePath9() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 6));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(4, 6));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 6), emg.makeCoordinate(6, 6));
		assertNotNull(foundPath);
	}

	@Test
	void SquarePath10() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(5, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(5, 5));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(5, 1), emg.makeCoordinate(5, 8));
		assertNull(foundPath);
	}

	@Test
	void HexPath3() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(1, 0));
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(99, 0));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(99, 0), emg.makeCoordinate(0, 0));
		assertNull(foundPath);
	}

	@Test
	void HexPath4() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(1, 0));
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(7, 0));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(7, 0), emg.makeCoordinate(0, 0));
		assertNotNull(foundPath);
	}
	@Test
	void hexPath5() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(7, 0));
		emg.getBoard().setLocationType(emg.makeCoordinate(-1, 0), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(-1, 1), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(0, 1), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(1, 0), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(1, -1), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(-1, -1), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(1, -2), LocationType.BLOCK);
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(7, 0), emg.makeCoordinate(0, 0));
		assertNotNull(foundPath);
	}

	@Test
	void SquarePath11() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		emg.getBoard().setLocationType(emg.makeCoordinate(4, 1), LocationType.BLOCK);
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 1), emg.makeCoordinate(4, 1));
		assertNull(foundPath);
	}

	@Test
	void OrthoSquarePath1() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SecondSampleOrthoSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 1), emg.makeCoordinate(8, 8));
		assertNotNull(foundPath);
	}

	@Test
	void OrthoSquarePath2() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SecondSampleOrthoSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HUMMINGBIRD);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(2, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(3, 1));
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 1), emg.makeCoordinate(8, 8));
		assertNotNull(foundPath);
	}

	@Test
	void OrthoSquarePath3() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SecondSampleOrthoSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HUMMINGBIRD);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(2, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(3, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(1, 2));
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 1), emg.makeCoordinate(8, 8));
		assertNotNull(foundPath);
	}

	@Test
	void OrthoSquarePath4() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SecondSampleOrthoSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HUMMINGBIRD);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(2, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(3, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(1, 2));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(1, 3));
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 1), emg.makeCoordinate(8, 8));
		assertNull(foundPath);
	}

	@Test
	void OrthoSquarePath5() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SecondSampleOrthoSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.SNAIL);
		emg.getBoard().setLocationType(emg.makeCoordinate(89, 53), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(87, 53), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(88, 52), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(88, 54), LocationType.BLOCK);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 1), emg.makeCoordinate(88, 53));
		assertNotNull(foundPath);
	}

	@Test
	void OrthoSquarePath6() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SecondSampleOrthoSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HUMMINGBIRD);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(2, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(3, 1));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(1, 2));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(1, 3));
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(1, 1));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(1, 1), emg.makeCoordinate(1, 3));
		assertNotNull(foundPath);
	}

	@Test
	void OrthoSquarePath7() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SecondSampleOrthoSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(8, 7));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(7, 8));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(9, 8));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(8, 9));
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(4, 4));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(4, 4), emg.makeCoordinate(8, 8));
		assertNull(foundPath);
	}

	@Test
	void SquarePath12() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleSquareEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(8, 7));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(7, 8));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(9, 8));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER2, PieceName.HUMMINGBIRD), emg.makeCoordinate(8, 9));
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(4, 4));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(4, 4), emg.makeCoordinate(8, 8));
		assertNotNull(foundPath);
	}

	@Test
	void hexPath6() throws Exception{
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, 2));
		emg.getBoard().putPieceAt(new EscapePiece(Player.PLAYER1,  PieceName.FROG), emg.makeCoordinate(0, 1));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(0, 2), emg.makeCoordinate(0, 0));
		assertNull(foundPath);
	}

	@Test
	void hexSameCoordinate() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.HORSE);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, 2));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(0, 2), emg.makeCoordinate(0, 2));
		assertNull(foundPath);
	}

	@Test
	void hexUnreachableTarget() throws Exception {
		EscapeGameBuilder builder = new EscapeGameBuilder(new File("config/SampleHexEscapeGame.xml"));
		EscapeGameManager emg = builder.makeGameManager();
		EscapePiece p = new EscapePiece(Player.PLAYER1, PieceName.FROG);
		emg.getBoard().setLocationType(emg.makeCoordinate(10, 1), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(11, 0), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(11, -1), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(10, -1), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(9, 0), LocationType.BLOCK);
		emg.getBoard().setLocationType(emg.makeCoordinate(9, 1), LocationType.BLOCK);
		emg.getBoard().putPieceAt(p, emg.makeCoordinate(0, 0));
		AStar pathFinder = new AStar(p.getName(), emg.getBoard(), emg.getTypeMap());
		ArrayList<Coordinate> foundPath = pathFinder.findPath(emg.makeCoordinate(0, 0), emg.makeCoordinate(10, 0));
		assertNull(foundPath);
	}
}