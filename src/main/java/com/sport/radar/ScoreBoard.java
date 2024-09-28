package com.sport.radar;

import com.sport.radar.dto.Match;
import com.sport.radar.exceptions.MatchAlreadyExists;
import com.sport.radar.exceptions.MatchDoesNotExist;
import com.sport.radar.util.MatchComparator;

import java.util.List;
import java.util.LinkedList;

/**
 * ScoreBoard class is aimed to manage sport matches and return all registered matches list.
 * The class is thread safe.
 */
public class ScoreBoard {

    private final List<Match> orderedMatches;
    private final MatchComparator comparator;

    public ScoreBoard() {
        this.orderedMatches = new LinkedList<>();
        this.comparator = new MatchComparator();
    }

    /**
     * Starts match for given teams
     * @param homeTeam home team name
     * @param awayTeam away team name
     * @return match between two teams
     * @throws MatchAlreadyExists thrown in case if match for given teams already exist
     */
    public synchronized Match startMatch(String homeTeam, String awayTeam) throws MatchAlreadyExists
    {
        Match match = new Match(homeTeam, awayTeam);
        if (orderedMatches.contains(match))
        {
            throw new MatchAlreadyExists("Match between %s and %s already exists".formatted(
                    match.getHomeTeam(), match.getAwayTeam()));
        } else
        {
            orderedMatches.add(match);
            orderedMatches.sort(comparator);
            return match;
        }
    }

    /**
     * Deletes match from the storage
     * @param match match to be deleted
     * @return returns true in case if match was successfully deleted
     * @throws MatchDoesNotExist is throws when match doesn't exist in the storage
     */
    public synchronized boolean finishMatch(Match match) throws MatchDoesNotExist
    {
        if (orderedMatches.contains(match)) {
            orderedMatches.remove(match);
            orderedMatches.sort(comparator);
            return true;
        } else {
            throw new MatchDoesNotExist("Match between %s and %s doesn't exist.".formatted(
                    match.getHomeTeam(), match.getAwayTeam()));
        }
    }

    /**
     * Updates match scores for teams
     * @param match match instance to be updated
     * @param homeTeamScore home team score value
     * @param awayTeamScore away team score value
     * @return return updated match
     * @throws MatchDoesNotExist is thrown when match doesn't exist
     */
    public synchronized Match updateScore(Match match, Integer homeTeamScore, Integer awayTeamScore) throws MatchDoesNotExist
    {
        if (orderedMatches.contains(match)) {
            Match foundMatch = orderedMatches.get(orderedMatches.indexOf(match));
            foundMatch.setAwayTeamScore(awayTeamScore);
            foundMatch.setHomeTeamScore(homeTeamScore);
            orderedMatches.sort(comparator);
            return match;
        } else {
            throw new MatchDoesNotExist("Match between %s and %s doesn't exist.".formatted(
                    match.getHomeTeam(), match.getAwayTeam()));
        }
    }

    /**
     * Provides list of matches arranged by total score in descending order.
     * For matches with the same total score the order mostly recently started match is on the top
     * @return ordered matches list.
     */
    public synchronized List<Match> getMatches()
    {
        return orderedMatches;
    }
}
