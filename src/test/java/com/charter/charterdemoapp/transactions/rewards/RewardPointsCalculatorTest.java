package com.charter.charterdemoapp.transactions.rewards;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RewardPointsCalculatorTest {

    private static RewardPointsCalculator calculator;
    private static final List<RewardPointsStrategy> strategies = Arrays.asList(new RewardPointsBetween50And100(), new RewardPointsOver100());

    @BeforeAll
    static void initStrategies() {
        calculator = new RewardPointsCalculator(strategies);
    }

    @ParameterizedTest
    @MethodSource("provideTransactionAmountsAndExpectedPoints")
    public void testCalculateRewards(int transactionAmount, int expectedPoints) {
        assertEquals(expectedPoints, calculator.calculateRewards(transactionAmount));
    }

    static Stream<Arguments> provideTransactionAmountsAndExpectedPoints() {
        return Stream.of(
                Arguments.of(201, 252),
                Arguments.of(120, 90),
                Arguments.of(101, 52),
                Arguments.of(100, 50),
                Arguments.of(99, 49),
                Arguments.of(75, 25),
                Arguments.of(51, 1),
                Arguments.of(50, 0),
                Arguments.of(49, 0)
        );
    }
}
