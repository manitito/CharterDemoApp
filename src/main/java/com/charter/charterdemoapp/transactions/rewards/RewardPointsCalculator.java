package com.charter.charterdemoapp.transactions.rewards;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardPointsCalculator {

    private final List<RewardPointsStrategy> strategies;

    public int calculateRewards(int amount) {
        int rewards = 0;
        for (RewardPointsStrategy strategy : strategies) {
            rewards += strategy.calculateRewards(amount);
        }
        return rewards;
    }
}
