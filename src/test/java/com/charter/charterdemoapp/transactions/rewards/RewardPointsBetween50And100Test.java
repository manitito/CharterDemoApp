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
public class RewardPointsBetween50And100Test {

    @Spy
    RewardPointsBetween50And100 points;

    @ParameterizedTest
    @MethodSource("provideTransactionAmountsAndExpectedPoints")
    void shouldReturnCorrectNumberOfPoints(int transactionAmount, int expectedPoints) {
        assertEquals(expectedPoints, points.calculateRewards(transactionAmount));
    }

    static Stream<Arguments> provideTransactionAmountsAndExpectedPoints() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(20, 0),
                Arguments.of(49, 0),
                Arguments.of(50, 0),
                Arguments.of(51, 1),
                Arguments.of(99, 49),
                Arguments.of(100, 50),
                Arguments.of(101, 50),
                Arguments.of(1000, 50)
        );
    }
}
