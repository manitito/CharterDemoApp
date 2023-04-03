package com.charter.charterdemoapp.transactions.rewards;

import org.springframework.stereotype.Component;

@Component
public class RewardPointsBetween50And100 implements RewardPointsStrategy {

    private static final int MIN_BOUNDARY = 50;
    private static final int MAX_BOUNDARY = 100;
    private static final int POINTS_MULTIPLIER = 1;

    @Override
    public int calculateRewards(int amount) {
        int boundaryDiff = MAX_BOUNDARY - MIN_BOUNDARY;
        int amountDiff = amount - boundaryDiff;
        return amount > MIN_BOUNDARY ? multiplierWithLimit(POINTS_MULTIPLIER, amountDiff, boundaryDiff) : 0;
    }
}