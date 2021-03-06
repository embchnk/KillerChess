package com.killerchess.core.chessboard.points.counter;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.Assert.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PointsCounterTest {

    private static final String DEFAULT_ARRANGEMENT_JSON = "{\"1\":[\"RW\",\"HW\",\"BW\",\"QW\",\"KW\",\"BW\",\"HW\",\"RW\"],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"8\":[\"RB\",\"HB\",\"BB\",\"QB\",\"KB\",\"BB\",\"HB\",\"RB\"]}";
    // White player lost chessmen of 15 pts value
    // Black player lost chessmen of 14 pts value
    private static final String OUTPUT_ARRANGEMENT_JSON = "{\"1\":[\"RW\",\"HW\",\"BW\",\"XX\",\"KW\",\"BW\",\"XX\",\"RW\"],\"2\":[\"PW\",\"XX\",\"PW\",\"PW\",\"XX\",\"PW\",\"PW\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"XX\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"XX\"],\"8\":[\"XX\",\"HB\",\"BB\",\"QB\",\"XX\",\"BB\",\"HB\",\"RB\"]}";

    private PointsCounter pointsCounter;

    @BeforeAll
    void setUp() {
        this.pointsCounter = new PointsCounter(new StateInterpreter());
    }

    @Test
    void whenWhitePlayerGetCapturedChessmenOf15PointsThenHisFinalScoreIs15Points() {
        var whitePlayerPoints = pointsCounter.countWhitePlayerPoints(DEFAULT_ARRANGEMENT_JSON, OUTPUT_ARRANGEMENT_JSON);
        assertEquals(Integer.valueOf(15), whitePlayerPoints);
    }

    @Test
    void whenBlackPlayerGetCapturedChessmenOf14PointsThenHisFinalScoreIs14Points() {
        var blackPlayerPoints = pointsCounter.countBlackPlayerPoints(DEFAULT_ARRANGEMENT_JSON, OUTPUT_ARRANGEMENT_JSON);
        assertEquals(Integer.valueOf(14), blackPlayerPoints);
    }

    @Test
    void whenInputBoardIsSameAsOutputBoardThenFinalScoreOfBothPlayerIsZero() {
        var whitePlayerPoints =
                pointsCounter.countWhitePlayerPoints(DEFAULT_ARRANGEMENT_JSON, DEFAULT_ARRANGEMENT_JSON);
        assertEquals(Integer.valueOf(0), whitePlayerPoints);

        var blackPlayerPoints =
                pointsCounter.countBlackPlayerPoints(DEFAULT_ARRANGEMENT_JSON, DEFAULT_ARRANGEMENT_JSON);
        assertEquals(Integer.valueOf(0), blackPlayerPoints);
    }
}