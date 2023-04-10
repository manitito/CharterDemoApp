package com.charter.charterdemoapp.transactions.rewards;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RewardPointsOver100Test {

    @Spy
    RewardPointsOver100 points;

    @ParameterizedTest
    @MethodSource("provideTransactionAmountsAndExpectedPoints")
    void shouldReturnCorrectNumberOfPoints(int transactionAmount, int expectedPoints) {
        assertEquals(expectedPoints, points.calculateRewards(transactionAmount));
    }

    static Stream<Arguments> provideTransactionAmountsAndExpectedPoints() {
        return Stream.of(
                Arguments.of(100, 0),
                Arguments.of(101, 2),
                Arguments.of(102, 4),
                Arguments.of(120, 40)
        );
    }
}
