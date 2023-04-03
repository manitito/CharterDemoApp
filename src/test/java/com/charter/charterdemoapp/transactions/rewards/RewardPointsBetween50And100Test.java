package com.charter.charterdemoapp.transactions.rewards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RewardPointsBetween50And100Test {

    @Spy
    RewardPointsBetween50And100 points;

    @Test
    void shouldReturnCorrectNumberOfPoints() {
        //when
        int noPointsForTransaction0 = points.calculateRewards(0);
        int noPointsForTransaction20 = points.calculateRewards(20);
        int noPointsForTransaction49 = points.calculateRewards(49);
        int noPointsForTransaction50 = points.calculateRewards(50);
        int minPointsForTransaction51 = points.calculateRewards(51);
        int minPointsForTransaction99 = points.calculateRewards(99);
        int maxPointsForTransaction100 = points.calculateRewards(100);
        int maxPointsForTransaction101 = points.calculateRewards(101);
        int maxPointsForTransaction1000 = points.calculateRewards(1000);

        //then
        assertEquals(0, noPointsForTransaction0);
        assertEquals(0, noPointsForTransaction20);
        assertEquals(0, noPointsForTransaction49);
        assertEquals(0, noPointsForTransaction50);
        assertEquals(1, minPointsForTransaction51);
        assertEquals(50, maxPointsForTransaction100);
        assertEquals(49, minPointsForTransaction99);
        assertEquals(50, maxPointsForTransaction101);
        assertEquals(50, maxPointsForTransaction1000);
    }
}
