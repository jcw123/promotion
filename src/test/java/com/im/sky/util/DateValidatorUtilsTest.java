package com.im.sky.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * {@link DateValidatorUtils} 单元测试。
 *
 * <p>每个 public 方法覆盖：正常用例、{@code null} 入参、边界值、非法格式四类用例。
 *
 * @author jiangchangwei
 */
public class DateValidatorUtilsTest {

    // ==================== 工具类自身规范 ====================

    /**
     * 工具类不可实例化：私有构造器通过反射调用应抛 UnsupportedOperationException。
     */
    @Test
    public void testCannotInstantiate() throws Exception {
        java.lang.reflect.Constructor<DateValidatorUtils> ctor =
                DateValidatorUtils.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        try {
            ctor.newInstance();
            fail("expect UnsupportedOperationException");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof UnsupportedOperationException);
        }
    }

    // ==================== isValidDate ====================

    @Test
    public void testIsValidDate_default() {
        // 正常
        assertTrue(DateValidatorUtils.isValidDate("2024-02-29"));
        assertTrue(DateValidatorUtils.isValidDate("2025-01-01"));
        // null / 空白
        assertFalse(DateValidatorUtils.isValidDate(null));
        assertFalse(DateValidatorUtils.isValidDate(""));
        assertFalse(DateValidatorUtils.isValidDate("   "));
        // 边界 / 非法日期
        assertFalse(DateValidatorUtils.isValidDate("2023-02-29"));
        assertFalse(DateValidatorUtils.isValidDate("2025-02-30"));
        assertFalse(DateValidatorUtils.isValidDate("2025-13-01"));
        // 非法格式
        assertFalse(DateValidatorUtils.isValidDate("2025/01/01"));
        assertFalse(DateValidatorUtils.isValidDate("abc"));
    }

    @Test
    public void testIsValidDate_withPattern() {
        assertTrue(DateValidatorUtils.isValidDate("2025/01/01", "yyyy/MM/dd"));
        assertTrue(DateValidatorUtils.isValidDate("20250101", "yyyyMMdd"));
        assertFalse(DateValidatorUtils.isValidDate("2025-01-01", "yyyy/MM/dd"));
        assertFalse(DateValidatorUtils.isValidDate(null, "yyyy-MM-dd"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidDate_nullPattern() {
        DateValidatorUtils.isValidDate("2025-01-01", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidDate_blankPattern() {
        DateValidatorUtils.isValidDate("2025-01-01", "  ");
    }

    // ==================== isValidDateTime ====================

    @Test
    public void testIsValidDateTime_default() {
        assertTrue(DateValidatorUtils.isValidDateTime("2025-01-01 12:30:45"));
        assertFalse(DateValidatorUtils.isValidDateTime(null));
        assertFalse(DateValidatorUtils.isValidDateTime(""));
        assertFalse(DateValidatorUtils.isValidDateTime("2025-01-01"));
        assertFalse(DateValidatorUtils.isValidDateTime("2025-02-30 12:00:00"));
        assertFalse(DateValidatorUtils.isValidDateTime("2025-01-01 25:00:00"));
    }

    @Test
    public void testIsValidDateTime_withPattern() {
        assertTrue(DateValidatorUtils.isValidDateTime("2025-01-01T12:30:45", "yyyy-MM-dd'T'HH:mm:ss"));
        assertFalse(DateValidatorUtils.isValidDateTime("xxx", "yyyy-MM-dd HH:mm:ss"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidDateTime_nullPattern() {
        DateValidatorUtils.isValidDateTime("2025-01-01 00:00:00", null);
    }

    // ==================== isBetween (LocalDate) ====================

    @Test
    public void testIsBetween_localDate() {
        LocalDate d = LocalDate.of(2025, 6, 15);
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);
        // 正常
        assertTrue(DateValidatorUtils.isBetween(d, start, end));
        // 闭区间端点
        assertTrue(DateValidatorUtils.isBetween(start, start, end));
        assertTrue(DateValidatorUtils.isBetween(end, start, end));
        // 不在区间
        assertFalse(DateValidatorUtils.isBetween(LocalDate.of(2024, 12, 31), start, end));
        assertFalse(DateValidatorUtils.isBetween(LocalDate.of(2026, 1, 1), start, end));
        // null 入参
        assertFalse(DateValidatorUtils.isBetween((LocalDate) null, start, end));
        // null 边界 = 无限
        assertTrue(DateValidatorUtils.isBetween(d, null, end));
        assertTrue(DateValidatorUtils.isBetween(d, start, null));
        assertTrue(DateValidatorUtils.isBetween(d, null, null));
        // start > end 区间非法
        assertFalse(DateValidatorUtils.isBetween(d, end, start));
    }

    @Test
    public void testIsBetween_localDateTime() {
        LocalDateTime t = LocalDateTime.of(2025, 6, 15, 12, 0);
        LocalDateTime s = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime e = LocalDateTime.of(2025, 12, 31, 23, 59);
        assertTrue(DateValidatorUtils.isBetween(t, s, e));
        assertFalse(DateValidatorUtils.isBetween((LocalDateTime) null, s, e));
        assertFalse(DateValidatorUtils.isBetween(t, e, s));
    }

    // ==================== isBefore / isAfter ====================

    @Test
    public void testIsBefore_localDate() {
        LocalDate a = LocalDate.of(2025, 1, 1);
        LocalDate b = LocalDate.of(2025, 12, 31);
        assertTrue(DateValidatorUtils.isBefore(a, b));
        assertFalse(DateValidatorUtils.isBefore(b, a));
        assertFalse(DateValidatorUtils.isBefore(a, a));
        assertFalse(DateValidatorUtils.isBefore(null, b));
        assertFalse(DateValidatorUtils.isBefore(a, null));
    }

    @Test
    public void testIsAfter_localDate() {
        LocalDate a = LocalDate.of(2025, 1, 1);
        LocalDate b = LocalDate.of(2025, 12, 31);
        assertTrue(DateValidatorUtils.isAfter(b, a));
        assertFalse(DateValidatorUtils.isAfter(a, b));
        assertFalse(DateValidatorUtils.isAfter(a, a));
        assertFalse(DateValidatorUtils.isAfter(null, b));
        assertFalse(DateValidatorUtils.isAfter(a, null));
    }

    // ==================== isLeapYear ====================

    @Test
    public void testIsLeapYear_int() {
        // 关键测试用例（来自 tasks.md 与 requirements.md 7.4）
        assertTrue(DateValidatorUtils.isLeapYear(2000));
        assertFalse(DateValidatorUtils.isLeapYear(2100));
        assertTrue(DateValidatorUtils.isLeapYear(2024));
        assertFalse(DateValidatorUtils.isLeapYear(2023));
    }

    @Test
    public void testIsLeapYear_localDate() {
        assertTrue(DateValidatorUtils.isLeapYear(LocalDate.of(2024, 1, 1)));
        assertFalse(DateValidatorUtils.isLeapYear(LocalDate.of(2023, 12, 31)));
        assertFalse(DateValidatorUtils.isLeapYear((LocalDate) null));
    }

    // ==================== isWeekend / isWeekday ====================

    @Test
    public void testIsWeekend() {
        // 2025-05-31 周六、2025-06-01 周日、2025-06-02 周一
        assertTrue(DateValidatorUtils.isWeekend(LocalDate.of(2025, 5, 31)));
        assertTrue(DateValidatorUtils.isWeekend(LocalDate.of(2025, 6, 1)));
        assertFalse(DateValidatorUtils.isWeekend(LocalDate.of(2025, 6, 2)));
        assertFalse(DateValidatorUtils.isWeekend(null));
    }

    @Test
    public void testIsWeekday() {
        assertTrue(DateValidatorUtils.isWeekday(LocalDate.of(2025, 6, 2)));
        assertFalse(DateValidatorUtils.isWeekday(LocalDate.of(2025, 5, 31)));
        assertFalse(DateValidatorUtils.isWeekday(LocalDate.of(2025, 6, 1)));
        assertFalse(DateValidatorUtils.isWeekday(null));
    }

    // ==================== isValidBirthday ====================

    @Test
    public void testIsValidBirthday_localDate() {
        LocalDate today = LocalDate.now();
        // null
        assertFalse(DateValidatorUtils.isValidBirthday((LocalDate) null));
        // 未来
        assertFalse(DateValidatorUtils.isValidBirthday(today.plusDays(1)));
        // 今天合法
        assertTrue(DateValidatorUtils.isValidBirthday(today));
        // 30 年前合法
        assertTrue(DateValidatorUtils.isValidBirthday(today.minusYears(30)));
        // 边界：刚好 150 岁（同月同日）合法
        assertTrue(DateValidatorUtils.isValidBirthday(today.minusYears(150)));
        // 边界：151 岁不合法
        assertFalse(DateValidatorUtils.isValidBirthday(today.minusYears(151)));
    }

    @Test
    public void testIsValidBirthday_string() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertTrue(DateValidatorUtils.isValidBirthday(today.minusYears(20).format(fmt)));
        // 解析失败
        assertFalse(DateValidatorUtils.isValidBirthday("not-a-date"));
        assertFalse(DateValidatorUtils.isValidBirthday((String) null));
        assertFalse(DateValidatorUtils.isValidBirthday(""));
        // 业务非法（未来）
        assertFalse(DateValidatorUtils.isValidBirthday(today.plusDays(1).format(fmt)));
    }

    @Test
    public void testIsValidBirthday_customMaxAge() {
        LocalDate today = LocalDate.now();
        // 18 岁内合法
        assertTrue(DateValidatorUtils.isValidBirthday(today.minusYears(10), 18));
        // 18 岁边界（含）合法
        assertTrue(DateValidatorUtils.isValidBirthday(today.minusYears(18), 18));
        // 19 岁不合法（超过 maxAge=18）
        assertFalse(DateValidatorUtils.isValidBirthday(today.minusYears(19), 18));
        // null
        assertFalse(DateValidatorUtils.isValidBirthday((LocalDate) null, 18));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidBirthday_zeroMaxAge() {
        DateValidatorUtils.isValidBirthday(LocalDate.now(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidBirthday_negativeMaxAge() {
        DateValidatorUtils.isValidBirthday(LocalDate.now(), -1);
    }

    // ==================== isValidRange ====================

    @Test
    public void testIsValidRange_localDate() {
        LocalDate a = LocalDate.of(2025, 1, 1);
        LocalDate b = LocalDate.of(2025, 12, 31);
        assertTrue(DateValidatorUtils.isValidRange(a, b));
        assertTrue(DateValidatorUtils.isValidRange(a, a));
        assertFalse(DateValidatorUtils.isValidRange(b, a));
        assertFalse(DateValidatorUtils.isValidRange((LocalDate) null, b));
        assertFalse(DateValidatorUtils.isValidRange(a, (LocalDate) null));
    }

    @Test
    public void testIsValidRange_localDateTime() {
        LocalDateTime a = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime b = LocalDateTime.of(2025, 1, 1, 0, 1);
        assertTrue(DateValidatorUtils.isValidRange(a, b));
        assertFalse(DateValidatorUtils.isValidRange(b, a));
        assertFalse(DateValidatorUtils.isValidRange((LocalDateTime) null, b));
    }

    @Test
    public void testIsValidRange_string() {
        assertTrue(DateValidatorUtils.isValidRange("2025-01-01", "2025-12-31", "yyyy-MM-dd"));
        assertFalse(DateValidatorUtils.isValidRange("2025-12-31", "2025-01-01", "yyyy-MM-dd"));
        // 解析失败
        assertFalse(DateValidatorUtils.isValidRange("bad", "2025-12-31", "yyyy-MM-dd"));
        assertFalse(DateValidatorUtils.isValidRange("2025-01-01", "bad", "yyyy-MM-dd"));
        // null 字符串
        assertFalse(DateValidatorUtils.isValidRange(null, "2025-12-31", "yyyy-MM-dd"));
        assertFalse(DateValidatorUtils.isValidRange("2025-01-01", null, "yyyy-MM-dd"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidRange_string_nullPattern() {
        DateValidatorUtils.isValidRange("2025-01-01", "2025-12-31", null);
    }

    // ==================== 常量值校验 ====================

    @Test
    public void testConstants() {
        assertEquals("yyyy-MM-dd", DateValidatorUtils.DEFAULT_DATE_PATTERN);
        assertEquals("yyyy-MM-dd HH:mm:ss", DateValidatorUtils.DEFAULT_DATE_TIME_PATTERN);
        assertEquals(150, DateValidatorUtils.DEFAULT_MAX_AGE);
    }
}