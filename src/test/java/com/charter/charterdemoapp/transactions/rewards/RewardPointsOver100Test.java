package com.charter.charterdemoapp.transactions.rewards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RewardPointsOver100Test {

    @Spy
    RewardPointsOver100 points;

    @Test
    void shouldReturnCorrectNumberOfPoints() {
        //when
        int noPointsForTransaction = points.calculateRewards(100);
        int minPointsForTransaction = points.calculateRewards(101);
        int regularPointsForTransaction = points.calculateRewards(120);

        //then
        assertEquals(0, noPointsForTransaction);
        assertEquals(2, minPointsForTransaction);
        assertEquals(40, regularPointsForTransaction);
    }
}
