package org.drools.solver.examples.travelingtournament.persistence.smart;

import java.io.File;
import java.util.List;

import org.drools.solver.examples.travelingtournament.domain.Match;
import org.drools.solver.examples.travelingtournament.domain.Team;
import org.drools.solver.examples.travelingtournament.domain.TravelingTournament;
import org.drools.solver.examples.travelingtournament.persistence.TravelingTournamentInputConvertor;

/**
 * @author Geoffrey De Smet
 */
public class SmartTravelingTournamentInputConvertor extends TravelingTournamentInputConvertor {

    private static final File outputDir = new File(
            "data/travelingtournament/smart/unsolved/");

    public static void main(String[] args) {
        new SmartTravelingTournamentInputConvertor().convert();
    }

    protected File getOutputDir() {
        return outputDir;
    }

    /**
     * Canonical pattern initialization (see papers).
     * @param travelingTournament the traveling tournament
     */
    protected void initializeMatchDays(TravelingTournament travelingTournament) {
        int n = travelingTournament.getN();
        for (int i = 0; i < (n - 1); i++) {
            initializeMatchPairs(travelingTournament, (n - 1), i, i);
        }
        for (int startA = 1, startB = (n - 2); startA < (n - 1); startA += 2, startB -= 2) {
            for (int i = 0; i < (n - 1); i++) {
                int a = (startA + i) % (n - 1);
                int b = (startB + i) % (n - 1);
                initializeMatchPairs(travelingTournament, a, b, i);
            }
        }
    }

    private void initializeMatchPairs(TravelingTournament travelingTournament, int a, int b, int i) {
        if ((i % 6) >= 3) { // Might not be a 100% the canonical pattern
            // Swap them
            int oldA = a;
            a = b;
            b = oldA;
        }
        Team aTeam = travelingTournament.getTeamList().get(a);
        Team bTeam = travelingTournament.getTeamList().get(b);
        Match m1 = findMatch(travelingTournament.getMatchList(), aTeam, bTeam);
        m1.setDay(travelingTournament.getDayList().get(i));
        Match m2 = findMatch(travelingTournament.getMatchList(), bTeam, aTeam);
        m2.setDay(travelingTournament.getDayList().get(i + travelingTournament.getN() - 1));
    }

    private Match findMatch(List<Match> matchList, Team homeTeam, Team awayTeam) {
        for (Match match : matchList) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                return match;
            }
        }
        throw new IllegalStateException("Nothing found for: " + homeTeam + " and " + awayTeam);
    }

}
