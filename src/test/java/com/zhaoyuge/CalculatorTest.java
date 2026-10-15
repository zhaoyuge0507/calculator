package com.zhaoyuge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    Calculator c;

    @BeforeEach
    void setUp() {
        c = new Calculator();
    }

    // ---------- Normal calculations ----------

    @Test
    void addWorks() {
        assertThat(c.eval("2", "+", "2")).isEqualTo(4.0);
    }

    @Test
    void subtractWorks() {
        assertThat(c.eval("5", "-", "2")).isEqualTo(3.0);
    }

    @Test
    void multiplyWorks() {
        assertThat(c.eval("2", "*", "3")).isEqualTo(6.0);
    }

    @Test
    void divideWorks() {
        assertThat(c.eval("10", "/", "5")).isEqualTo(2.0);
    }

    // ---------- Boundary values ----------

    @Test
    void negativeNumbers() {
        assertThat(c.eval("-2", "+", "-3")).isEqualTo(-5.0);
    }

    @Test
    void decimals() {
        assertThat(c.eval("0.1", "+", "0.2"))
                .isCloseTo(0.3, org.assertj.core.data.Offset.offset(1e-9));
    }

    @Test
    void divideByNegative() {
        assertThat(c.eval("10", "/", "-2")).isEqualTo(-5.0);
    }

    // ---------- Parameterized tests (5+ cases) ----------

    @ParameterizedTest
    @CsvSource({
        "1, +, 1, 2",
        "5, -, 2, 3",
        "3, *, 4, 12",
        "10, /, 2, 5",
        "0, +, 0, 0",
        "-1, *, 5, -5"
    })
    void parameterizedTest(String a, String op, String b, double expected) {
        assertThat(c.eval(a, op, b)).isEqualTo(expected);
    }

    // ---------- Exception paths ----------

    @Test
    void blankInputThrows() {
        assertThatThrownBy(() -> c.eval("", "+", "2"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void divideByZeroThrows() {
        assertThatThrownBy(() -> c.eval("1", "/", "0"))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("division by zero");
    }

    @Test
    void unknownOperatorThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> c.eval("1", "?", "2"));
    }
}