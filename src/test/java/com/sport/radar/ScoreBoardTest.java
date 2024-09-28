package com.sport.radar;

import com.sport.radar.dto.Match;
import com.sport.radar.exceptions.MatchAlreadyExists;
import com.sport.radar.exceptions.MatchDoesNotExist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp()
    {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void testStartMatch() throws MatchAlreadyExists
    {
        Match returnedMatch = scoreBoard.startMatch("homeTeam", "awayTeam");

        assertEquals("homeTeam", returnedMatch.getHomeTeam());
        assertEquals("awayTeam", returnedMatch.getAwayTeam());
    }

    @Test
    void testStartMatchWhenItExist() throws MatchAlreadyExists
    {
        scoreBoard.startMatch("homeTeam", "awayTeam");

        assertThrows(MatchAlreadyExists.class, () -> scoreBoard.startMatch("homeTeam", "awayTeam"));
    }

    @Test
    void testFinishMatch() throws MatchDoesNotExist, MatchAlreadyExists
    {
        Match returnedMatch = scoreBoard.startMatch("homeTeam", "awayTeam");

        assertTrue(scoreBoard.finishMatch(returnedMatch));
    }

    @Test
    void testFinishMatchWhenItNotExist() throws MatchAlreadyExists
    {
        scoreBoard.startMatch("homeTeam", "awayTeam");

        assertThrows(MatchDoesNotExist.class, () -> scoreBoard.finishMatch(new Match("homeTeam1", "awayTeam")));
    }

    @Test
    void testUpdateScore() throws MatchDoesNotExist, MatchAlreadyExists
    {
        Integer newHomeTeamScore = 1;
        Integer newAwayTeamScore = 2;
        Match startMatch = scoreBoard.startMatch("homeTeam", "awayTeam");

        Match returnMatch = scoreBoard.updateScore(startMatch, newHomeTeamScore, newAwayTeamScore);

        assertEquals(newHomeTeamScore, returnMatch.getHomeTeamScore());
        assertEquals(newAwayTeamScore, returnMatch.getAwayTeamScore());
    }

    @Test
    void testUpdateScoreWhenMatchNotExist() throws MatchAlreadyExists
    {
        Integer newHomeTeamScore = 1;
        Integer newAwayTeamScore = 2;
        scoreBoard.startMatch("homeTeam", "awayTeam");

        assertThrows(MatchDoesNotExist.class, () -> scoreBoard.updateScore(
                new Match("homeTeam1", "awayTeam"), newHomeTeamScore, newAwayTeamScore));
    }

    @Test
    void testGetMatches() throws MatchAlreadyExists
    {
        scoreBoard.startMatch("Mexico", "Canada");

        List<Match> returnedMatches = scoreBoard.getMatches();

        assertEquals(1, returnedMatches.size());
    }

    @Test
    void testGetMatchesInProperOrder() throws MatchDoesNotExist, MatchAlreadyExists
    {
        Match mexCan = scoreBoard.startMatch("Mexico", "Canada");
        Match spaBra = scoreBoard.startMatch("Spain", "Brazil");
        Match gerFra = scoreBoard.startMatch("Germany", "France");
        Match uruIta = scoreBoard.startMatch("Uruguay", "Italy");
        Match argAus = scoreBoard.startMatch("Argentina", "Australia");
        scoreBoard.updateScore(mexCan, 0, 5);
        scoreBoard.updateScore(spaBra, 10, 2);
        scoreBoard.updateScore(gerFra, 2, 2);
        scoreBoard.updateScore(uruIta, 6, 6);
        scoreBoard.updateScore(argAus, 3, 1);

        List<Match> returnedMatches = scoreBoard.getMatches();

        assertEquals(5, returnedMatches.size());
        assertEquals(uruIta, returnedMatches.get(0));
        assertEquals(spaBra, returnedMatches.get(1));
        assertEquals(mexCan, returnedMatches.get(2));
        assertEquals(argAus, returnedMatches.get(3));
        assertEquals(gerFra, returnedMatches.get(4));
    }
}
