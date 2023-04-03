package com.charter.charterdemoapp.transactions.rewards;

import org.springframework.stereotype.Component;

@Component
public class RewardPointsOver100 implements RewardPointsStrategy {

    private static final int MIN_BOUNDARY = 100;
    private static final int POINT_MULTIPLIER = 2;

    @Override
    public int calculateRewards(int amount) {
        int amountDiff = amount - MIN_BOUNDARY;
        return amount > MIN_BOUNDARY ? multiplierWithoutLimit(POINT_MULTIPLIER, amountDiff) : 0;
    }
}