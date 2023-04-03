package com.charter.charterdemoapp.transactions.rewards;

public interface RewardPointsStrategy {

    int calculateRewards(int amount);

    default int multiplierWithLimit(int pointsMultiplier, int amountDiff, int boundaryDiff) {
        return Math.min(pointsMultiplier * amountDiff, pointsMultiplier * boundaryDiff);
    }

    default int multiplierWithoutLimit(int multiplier, int diff) {
        return multiplier * diff;
    }
}