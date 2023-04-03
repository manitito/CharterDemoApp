package com.charter.charterdemoapp.transactions.rewards;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RewardPointsCalculatorTest {

    private static RewardPointsCalculator calculator;
    private static final List<RewardPointsStrategy> strategies = Arrays.asList(new RewardPointsBetween50And100(), new RewardPointsOver100());

    @BeforeAll
    static void initStrategies() {
        calculator = new RewardPointsCalculator(strategies);
    }

    @Test
    public void testCalculateRewards() {
        assertEquals(252, calculator.calculateRewards(201));
        assertEquals(90, calculator.calculateRewards(120));
        assertEquals(52, calculator.calculateRewards(101));
        assertEquals(50, calculator.calculateRewards(100));
        assertEquals(49, calculator.calculateRewards(99));
        assertEquals(25, calculator.calculateRewards(75));
        assertEquals(1, calculator.calculateRewards(51));
        assertEquals(0, calculator.calculateRewards(49));
    }
}
