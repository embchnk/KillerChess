package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.HashSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KingTest extends TestCase {

    private StateInterpreter stateInterpreter;

    @BeforeAll
    public void setUp() {
        stateInterpreter = new StateInterpreter();
    }

    @Test
    void whenWhiteKingHasNoMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KB\",\"HB\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteKing.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void whenWhiteKingHasSomeMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteKing.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(3, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(4, 2));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhiteKingHasAllMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"KW\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleMoves = whiteKing.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(2, 3));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(3, 2));
        expectedMoves.add(new Pair<>(3, 4));
        expectedMoves.add(new Pair<>(4, 2));
        expectedMoves.add(new Pair<>(4, 3));
        expectedMoves.add(new Pair<>(4, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackKingHasNoMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KB\",\"HB\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackKing.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleMoves.size());

    }

    @Test
    void whenBlackKingHasSomeMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"HB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackKing.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(3, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(4, 2));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenBlackKingHasAllMoves() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"KB\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleMoves = blackKing.getPossibleMoves(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleMoves.size());

        var expectedMoves = new HashSet<Pair<Integer, Integer>>();
        expectedMoves.add(new Pair<>(2, 2));
        expectedMoves.add(new Pair<>(2, 3));
        expectedMoves.add(new Pair<>(2, 4));
        expectedMoves.add(new Pair<>(3, 2));
        expectedMoves.add(new Pair<>(3, 4));
        expectedMoves.add(new Pair<>(4, 2));
        expectedMoves.add(new Pair<>(4, 3));
        expectedMoves.add(new Pair<>(4, 4));
        assertEquals(expectedMoves, possibleMoves);
    }

    @Test
    void whenWhiteKingHasNoCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteKing.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenWhiteKingHasSomeCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"QW\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteKing.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(3, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(3, 4));
        expectedCaptures.add(new Pair<>(4, 3));
        expectedCaptures.add(new Pair<>(4, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenWhiteKingHasAllCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KB\",\"HB\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HB\",\"KW\",\"HB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PB\",\"PB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var whiteKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(whiteKing instanceof King);
        assertEquals(whiteKing.getColour(), ChessmanColourEnum.WHITE);
        var possibleCaptures = whiteKing.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(2, 2));
        expectedCaptures.add(new Pair<>(2, 3));
        expectedCaptures.add(new Pair<>(2, 4));
        expectedCaptures.add(new Pair<>(3, 2));
        expectedCaptures.add(new Pair<>(3, 4));
        expectedCaptures.add(new Pair<>(4, 2));
        expectedCaptures.add(new Pair<>(4, 3));
        expectedCaptures.add(new Pair<>(4, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackKingHasNoCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HB\",\"KB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PB\",\"PB\",\"HB\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackKing.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(0, possibleCaptures.size());
    }

    @Test
    void whenBlackKingHasSomeCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"QB\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HB\",\"KB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PB\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackKing.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(3, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(3, 4));
        expectedCaptures.add(new Pair<>(4, 3));
        expectedCaptures.add(new Pair<>(4, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

    @Test
    void whenBlackKingHasAllCaptures() throws IOException {
        var boardArrangement = "{\"1\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"KW\",\"HW\",\"QW\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"HW\",\"KB\",\"HW\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"PW\",\"PW\",\"HW\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"]}";
        var kingRow = 3;
        var kingCol = 3;
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(boardArrangement);

        var blackKing = chessBoard.getChessmanAt(kingRow, kingCol);
        assertTrue(blackKing instanceof King);
        assertEquals(blackKing.getColour(), ChessmanColourEnum.BLACK);
        var possibleCaptures = blackKing.getPossibleCaptures(chessBoard, new Pair<>(kingRow, kingCol));
        assertEquals(8, possibleCaptures.size());

        var expectedCaptures = new HashSet<Pair<Integer, Integer>>();
        expectedCaptures.add(new Pair<>(2, 2));
        expectedCaptures.add(new Pair<>(2, 3));
        expectedCaptures.add(new Pair<>(2, 4));
        expectedCaptures.add(new Pair<>(3, 2));
        expectedCaptures.add(new Pair<>(3, 4));
        expectedCaptures.add(new Pair<>(4, 2));
        expectedCaptures.add(new Pair<>(4, 3));
        expectedCaptures.add(new Pair<>(4, 4));
        assertEquals(expectedCaptures, possibleCaptures);
    }

}