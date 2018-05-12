package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Chessman {

    private static final Integer QUEEN_VALUE = 9;
    private static final Character QUEEN_SYMBOL = 'Q';

    public Queen(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return QUEEN_SYMBOL;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        var possibleMoves = new HashSet<Pair<Integer, Integer>>();
        var rookRow = position.getKey();
        var rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToMove;

        var upperLeftBlockade = false;
        var upperRightBlockade = false;
        var bottomLeftBlockade = false;
        var bottomRightBlockade = false;
        var middleLeftBlockade = false;
        var middleRightBlockade = false;


        for (int row = rookRow + 1; row < 8; row++) {
            if (!upperRightBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol + (row - rookRow));
                upperRightBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, upperRightBlockade);
            }
            if (!bottomRightBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol - (row - rookRow));
                bottomRightBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, bottomRightBlockade);
            }
            if (!middleRightBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol);
                middleRightBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, middleRightBlockade);
            }
        }
        for (int row = rookRow - 1; row >= 0; row--) {
            if (!upperLeftBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol + (rookRow - row));
                upperLeftBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, upperLeftBlockade);
            }
            if (!bottomLeftBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol - (rookRow - row));
                bottomLeftBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, bottomLeftBlockade);
            }
            if (!middleLeftBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol);
                middleLeftBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, middleLeftBlockade);
            }
        }
        for (int col = rookCol + 1; col < 8; col++) {
            possibleFieldToMove = new Pair<>(rookRow, col);
            if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
            } else {
                break;
            }
        }
        for (int col = rookCol - 1; col >= 0; col--) {
            possibleFieldToMove = new Pair<>(rookRow, col);
            if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
            } else {
                break;
            }
        }

        return possibleMoves;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        var colorToCapture = (getColour().equals(ChessmanColourEnum.BLACK)) ?
                ChessmanColourEnum.WHITE : ChessmanColourEnum.BLACK;

        var possibleCaptures = new HashSet<Pair<Integer, Integer>>();
        var rookRow = position.getKey();
        var rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToCapture;

        var upperLeftBlockade = false;
        var upperRightBlockade = false;
        var bottomLeftBlockade = false;
        var bottomRightBlockade = false;
        var middleLeftBlockade = false;
        var middleRightBlockade = false;


        for (int row = rookRow + 1; row < 8; row++) {
            if (!upperRightBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol + (row - rookRow));
                upperRightBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, upperRightBlockade, colorToCapture);
            }
            if (!bottomRightBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol - (row - rookRow));
                bottomRightBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, bottomRightBlockade, colorToCapture);
            }
            if (!middleRightBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol);
                middleRightBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, middleRightBlockade, colorToCapture);
            }
        }
        for (int row = rookRow - 1; row >= 0; row--) {
            if (!upperLeftBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol + (rookRow - row));
                upperLeftBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, upperLeftBlockade, colorToCapture);
            }
            if (!bottomLeftBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol - (rookRow - row));
                bottomLeftBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, bottomLeftBlockade, colorToCapture);
            }
            if (!middleLeftBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol);
                middleLeftBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, middleLeftBlockade, colorToCapture);
            }
        }
        for (int col = rookCol + 1; col < 8; col++) {
            possibleFieldToCapture = new Pair<>(rookRow, col);
            if (!isFieldEmpty(chessBoard, possibleFieldToCapture)) {
                if (chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
                    possibleCaptures.add(possibleFieldToCapture);
                break;
            }
        }
        for (int col = rookCol - 1; col >= 0; col--) {
            possibleFieldToCapture = new Pair<>(rookRow, col);
            if (!isFieldEmpty(chessBoard, possibleFieldToCapture)) {
                if (chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
                    possibleCaptures.add(possibleFieldToCapture);
                break;
            }
        }

        return possibleCaptures;
    }

    @Override
    public Integer getPointsValue() {
        return QUEEN_VALUE;
    }
}
