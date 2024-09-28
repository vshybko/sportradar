package com.sport.radar.util;

import com.sport.radar.dto.Match;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {

    @Override
    public int compare(Match match1, Match t1) {
        if (match1.getTotalScore() == t1.getTotalScore())
        {
            return match1.getStartTime().isAfter(t1.getStartTime()) ? -1 :
                    match1.getStartTime().equals(t1.getStartTime()) ? 0 : 1;
        }
        else {
            return match1.getTotalScore() > t1.getTotalScore() ? -1 : 1;
        }
    }
}
