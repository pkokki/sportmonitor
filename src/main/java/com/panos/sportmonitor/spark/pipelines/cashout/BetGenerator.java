package com.panos.sportmonitor.spark.pipelines.cashout;

import org.apache.commons.math3.util.Precision;
import scala.Tuple2;

import java.util.*;

public class BetGenerator {
    private static final Random random = new Random(new Date().getTime());

    public static Tuple2<List<Bet>, List<BetSelection>> createBetSlips(List<SelectionEvent> odds, long count) {
        List<Bet> bets = new ArrayList<>();
        List<BetSelection> betSelections = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Tuple2<Bet, List<BetSelection>> t = createBetSlip(odds, i % 2 == 0 ? 1 : random.nextInt(5) + 1);
            if (t._2.size() > 0) {
                bets.add(t._1);
                betSelections.addAll(t._2);
            }
        }
        return new Tuple2<>(bets, betSelections);
    }

    private static Tuple2<Bet, List<BetSelection>> createBetSlip(List<SelectionEvent> odds, int numBets) {
        String betId = UUID.randomUUID().toString();

        List<BetSelection> selections = new ArrayList<>();
        int actualSelections = Math.min(numBets, odds.size());
        Iterator<Integer> it= getRandomIndexes(actualSelections, odds.size());
        float totalOdd = 1;
        long betStamp = Long.MAX_VALUE;
        while (it.hasNext()) {
            SelectionEvent se = odds.get(it.next());
            betStamp = Long.min(betStamp, se.getTimestamp());
            selections.add(new BetSelection(betId, se.getSelectionId(), se.getDescription(), se.getCurrentPrice()));
            totalOdd *= se.getCurrentPrice();
        }

        float cashOutRatio =  0.75F;
        Bet bet = new Bet();
        bet.setBetId(betId);
        bet.setBetStamp(betStamp);
        bet.setSelections(actualSelections);
        bet.setInitialPrice(Precision.round(totalOdd, 2));
        bet.setCashOutPrice(Precision.round(totalOdd * cashOutRatio, 2));
        return new Tuple2<>(bet, selections);
    }

    private static Iterator<Integer> getRandomIndexes(int actualSelections, int bound) {
        Set<Integer> set = new LinkedHashSet<>();
        while (set.size() < actualSelections) {
            set.add(random.nextInt(bound));
        }
        return set.iterator();
    }
}
