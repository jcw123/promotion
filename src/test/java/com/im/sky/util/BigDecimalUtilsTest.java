package com.im.sky.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

/**
 * BigDecimalUtils 单元测试。
 *
 * @author jiangchangwei
 */
public class BigDecimalUtilsTest {

    // ═══════════════════════════════════════════════════════════════════
    // 判断方法测试
    // ═══════════════════════════════════════════════════════════════════

    @Test
    public void testIsNull() {
        assertTrue(BigDecimalUtils.isNull(null));
        assertFalse(BigDecimalUtils.isNull(BigDecimal.ZERO));
        assertFalse(BigDecimalUtils.isNull(new BigDecimal("1.5")));
    }

    @Test
    public void testIsZero() {
        assertTrue(BigDecimalUtils.isZero(BigDecimal.ZERO));
        assertTrue(BigDecimalUtils.isZero(new BigDecimal("0.0")));
        assertTrue(BigDecimalUtils.isZero(new BigDecimal("0.00")));
        assertFalse(BigDecimalUtils.isZero(null));
        assertFalse(BigDecimalUtils.isZero(new BigDecimal("1")));
        assertFalse(BigDecimalUtils.isZero(new BigDecimal("-1")));
    }

    @Test
    public void testIsPositive() {
        assertTrue(BigDecimalUtils.isPositive(new BigDecimal("1")));
        assertTrue(BigDecimalUtils.isPositive(new BigDecimal("0.01")));
        assertFalse(BigDecimalUtils.isPositive(null));
        assertFalse(BigDecimalUtils.isPositive(BigDecimal.ZERO));
        assertFalse(BigDecimalUtils.isPositive(new BigDecimal("-1")));
    }

    @Test
    public void testIsNegative() {
        assertTrue(BigDecimalUtils.isNegative(new BigDecimal("-1")));
        assertTrue(BigDecimalUtils.isNegative(new BigDecimal("-0.01")));
        assertFalse(BigDecimalUtils.isNegative(null));
        assertFalse(BigDecimalUtils.isNegative(BigDecimal.ZERO));
        assertFalse(BigDecimalUtils.isNegative(new BigDecimal("1")));
    }

    @Test
    public void testIsNullOrZero() {
        assertTrue(BigDecimalUtils.isNullOrZero(null));
        assertTrue(BigDecimalUtils.isNullOrZero(BigDecimal.ZERO));
        assertTrue(BigDecimalUtils.isNullOrZero(new BigDecimal("0.00")));
        assertFalse(BigDecimalUtils.isNullOrZero(new BigDecimal("1")));
        assertFalse(BigDecimalUtils.isNullOrZero(new BigDecimal("-1")));
    }

    // ═══════════════════════════════════════════════════════════════════
    // 辅助方法测试
    // ═══════════════════════════════════════════════════════════════════

    @Test
    public void testDefaultIfNull() {
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.defaultIfNull(null));
        BigDecimal value = new BigDecimal("3.14");
        assertSame(value, BigDecimalUtils.defaultIfNull(value));
    }

    @Test
    public void testStripTrailingZeros() {
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.stripTrailingZeros(null));
        assertEquals(0, new BigDecimal("1.2").compareTo(
                BigDecimalUtils.stripTrailingZeros(new BigDecimal("1.200"))));
        assertEquals(0, new BigDecimal("100").compareTo(
                BigDecimalUtils.stripTrailingZeros(new BigDecimal("100.00"))));
    }

    // ═══════════════════════════════════════════════════════════════════
    // 比较方法测试
    // ═══════════════════════════════════════════════════════════════════

    @Test
    public void testEquals() {
        // 双 null
        assertTrue(BigDecimalUtils.equals(null, null));
        // 单 null
        assertFalse(BigDecimalUtils.equals(null, BigDecimal.ONE));
        assertFalse(BigDecimalUtils.equals(BigDecimal.ONE, null));
        // 忽略精度差异
        assertTrue(BigDecimalUtils.equals(new BigDecimal("1.0"), new BigDecimal("1.00")));
        assertTrue(BigDecimalUtils.equals(new BigDecimal("0"), new BigDecimal("0.0")));
        // 不相等
        assertFalse(BigDecimalUtils.equals(new BigDecimal("1"), new BigDecimal("2")));
    }

    @Test
    public void testGt() {
        assertTrue(BigDecimalUtils.gt(new BigDecimal("2"), new BigDecimal("1")));
        assertFalse(BigDecimalUtils.gt(new BigDecimal("1"), new BigDecimal("1")));
        assertFalse(BigDecimalUtils.gt(new BigDecimal("0"), new BigDecimal("1")));
        // null 视为 ZERO
        assertTrue(BigDecimalUtils.gt(new BigDecimal("1"), null));
        assertFalse(BigDecimalUtils.gt(null, new BigDecimal("1")));
        assertFalse(BigDecimalUtils.gt(null, null));
    }

    @Test
    public void testGte() {
        assertTrue(BigDecimalUtils.gte(new BigDecimal("2"), new BigDecimal("1")));
        assertTrue(BigDecimalUtils.gte(new BigDecimal("1"), new BigDecimal("1")));
        assertFalse(BigDecimalUtils.gte(new BigDecimal("0"), new BigDecimal("1")));
        // null 视为 ZERO
        assertTrue(BigDecimalUtils.gte(null, null));
        assertTrue(BigDecimalUtils.gte(new BigDecimal("0"), null));
    }

    @Test
    public void testLt() {
        assertTrue(BigDecimalUtils.lt(new BigDecimal("1"), new BigDecimal("2")));
        assertFalse(BigDecimalUtils.lt(new BigDecimal("1"), new BigDecimal("1")));
        assertFalse(BigDecimalUtils.lt(new BigDecimal("2"), new BigDecimal("1")));
        // null 视为 ZERO
        assertTrue(BigDecimalUtils.lt(null, new BigDecimal("1")));
        assertFalse(BigDecimalUtils.lt(new BigDecimal("1"), null));
    }

    @Test
    public void testLte() {
        assertTrue(BigDecimalUtils.lte(new BigDecimal("1"), new BigDecimal("2")));
        assertTrue(BigDecimalUtils.lte(new BigDecimal("1"), new BigDecimal("1")));
        assertFalse(BigDecimalUtils.lte(new BigDecimal("2"), new BigDecimal("1")));
        // null 视为 ZERO
        assertTrue(BigDecimalUtils.lte(null, null));
        assertTrue(BigDecimalUtils.lte(null, new BigDecimal("0")));
    }

    // ═══════════════════════════════════════════════════════════════════
    // 算术运算测试
    // ═══════════════════════════════════════════════════════════════════

    @Test
    public void testAdd() {
        assertEquals(0, new BigDecimal("3").compareTo(
                BigDecimalUtils.add(new BigDecimal("1"), new BigDecimal("2"))));
        // null 视为 ZERO
        assertEquals(0, new BigDecimal("5").compareTo(
                BigDecimalUtils.add(new BigDecimal("5"), null)));
        assertEquals(0, new BigDecimal("5").compareTo(
                BigDecimalUtils.add(null, new BigDecimal("5"))));
        assertEquals(0, BigDecimal.ZERO.compareTo(
                BigDecimalUtils.add(null, null)));
    }

    @Test
    public void testSubtract() {
        assertEquals(0, new BigDecimal("1").compareTo(
                BigDecimalUtils.subtract(new BigDecimal("3"), new BigDecimal("2"))));
        // null 视为 ZERO
        assertEquals(0, new BigDecimal("5").compareTo(
                BigDecimalUtils.subtract(new BigDecimal("5"), null)));
        assertEquals(0, new BigDecimal("-5").compareTo(
                BigDecimalUtils.subtract(null, new BigDecimal("5"))));
    }

    @Test
    public void testMultiply() {
        assertEquals(0, new BigDecimal("6").compareTo(
                BigDecimalUtils.multiply(new BigDecimal("2"), new BigDecimal("3"))));
        // null 视为 ZERO
        assertEquals(0, BigDecimal.ZERO.compareTo(
                BigDecimalUtils.multiply(new BigDecimal("5"), null)));
        assertEquals(0, BigDecimal.ZERO.compareTo(
                BigDecimalUtils.multiply(null, new BigDecimal("5"))));
    }

    @Test
    public void testDivide() {
        assertEquals(0, new BigDecimal("2.50").compareTo(
                BigDecimalUtils.divide(new BigDecimal("5"), new BigDecimal("2"))));
        assertEquals(0, new BigDecimal("3.33").compareTo(
                BigDecimalUtils.divide(new BigDecimal("10"), new BigDecimal("3"))));
        // null 被除数视为 ZERO
        assertEquals(0, BigDecimal.ZERO.compareTo(
                BigDecimalUtils.divide(null, new BigDecimal("5"))));
    }

    @Test
    public void testDivideWithCustomScale() {
        assertEquals(0, new BigDecimal("3.3333").compareTo(
                BigDecimalUtils.divide(new BigDecimal("10"), new BigDecimal("3"),
                        4, RoundingMode.HALF_UP)));
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        BigDecimalUtils.divide(new BigDecimal("10"), BigDecimal.ZERO);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByNull() {
        // null 视为 ZERO，应抛 ArithmeticException
        BigDecimalUtils.divide(new BigDecimal("10"), null);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 边界测试
    // ═══════════════════════════════════════════════════════════════════

    @Test
    public void testLargeNumbers() {
        BigDecimal large = new BigDecimal("99999999999999999999.99");
        assertTrue(BigDecimalUtils.isPositive(large));
        assertFalse(BigDecimalUtils.isZero(large));
        assertEquals(0, large.compareTo(BigDecimalUtils.add(large, null)));
    }

    @Test
    public void testVerySmallNumbers() {
        BigDecimal small = new BigDecimal("0.000000001");
        assertTrue(BigDecimalUtils.isPositive(small));
        assertFalse(BigDecimalUtils.isZero(small));
        assertFalse(BigDecimalUtils.isNullOrZero(small));
    }
}