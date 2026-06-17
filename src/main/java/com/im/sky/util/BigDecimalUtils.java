package com.im.sky.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal 工具类。
 *
 * <p>封装 {@link BigDecimal} 常用操作，包括 null 安全的判断、比较、加减乘除运算，
 * 避免业务代码中重复且易出错的 BigDecimal 操作。
 *
 * <p>设计要点：
 * <ul>
 *     <li>类声明为 {@code final}，私有构造器抛 {@link UnsupportedOperationException}，禁止实例化</li>
 *     <li>所有方法均为静态、纯函数，天然线程安全</li>
 *     <li>null 入参在算术和比较方法中视为 {@link BigDecimal#ZERO}（宽容模式）</li>
 *     <li>使用 {@link BigDecimal#compareTo(BigDecimal)} 比较数值，忽略精度差异</li>
 * </ul>
 *
 * @author jiangchangwei
 */
public final class BigDecimalUtils {

    /**
     * 默认保留小数位数
     */
    public static final int DEFAULT_SCALE = 2;

    /**
     * 默认舍入模式：四舍五入
     */
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * 私有构造器，禁止实例化。
     */
    private BigDecimalUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ═══════════════════════════════════════════════════════════════════
    // 判断方法
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 判断 BigDecimal 是否为 null。
     *
     * @param value 待判断的值
     * @return 如果为 null 返回 true，否则返回 false
     */
    public static boolean isNull(BigDecimal value) {
        return value == null;
    }

    /**
     * 判断 BigDecimal 是否等于零。
     *
     * <p>使用 {@link BigDecimal#compareTo(BigDecimal)} 比较，忽略精度差异。
     * 即 {@code 0.0}、{@code 0.00}、{@code 0} 均视为零。
     *
     * @param value 待判断的值
     * @return 如果值等于零返回 true；如果为 null 返回 false
     */
    public static boolean isZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 判断 BigDecimal 是否为正数（大于零）。
     *
     * @param value 待判断的值
     * @return 如果值大于零返回 true；如果为 null 返回 false
     */
    public static boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断 BigDecimal 是否为负数（小于零）。
     *
     * @param value 待判断的值
     * @return 如果值小于零返回 true；如果为 null 返回 false
     */
    public static boolean isNegative(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 判断 BigDecimal 是否为 null 或等于零。
     *
     * @param value 待判断的值
     * @return 如果为 null 或等于零返回 true，否则返回 false
     */
    public static boolean isNullOrZero(BigDecimal value) {
        return isNull(value) || isZero(value);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 辅助方法
    // ═══════════════════════════════════════════════════════════════════

    /**
     * null 安全转换：如果传入值为 null，返回 {@link BigDecimal#ZERO}。
     *
     * @param value 待转换的值
     * @return 原值或 {@link BigDecimal#ZERO}（如果原值为 null）
     */
    public static BigDecimal defaultIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * 去除 BigDecimal 尾部多余的零。
     *
     * <p>例如 {@code 1.200} 变为 {@code 1.2}，{@code 100.00} 变为 {@code 1E+2}。
     * 如果传入 null，返回 {@link BigDecimal#ZERO}。
     *
     * @param value 待处理的值
     * @return 去除尾零后的值，或 {@link BigDecimal#ZERO}（如果原值为 null）
     */
    public static BigDecimal stripTrailingZeros(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value.stripTrailingZeros();
    }

    // ═══════════════════════════════════════════════════════════════════
    // 比较方法
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 判断两个 BigDecimal 是否数值相等（忽略精度差异）。
     *
     * <p>使用 {@link BigDecimal#compareTo(BigDecimal)} 比较，
     * 因此 {@code 1.0} 和 {@code 1.00} 视为相等。
     *
     * @param a 第一个值
     * @param b 第二个值
     * @return 双方均为 null 返回 true；仅一方为 null 返回 false；否则比较数值
     */
    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.compareTo(b) == 0;
    }

    /**
     * 判断 a 是否大于 b。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     *
     * @param a 第一个值
     * @param b 第二个值
     * @return a > b 返回 true
     */
    public static boolean gt(BigDecimal a, BigDecimal b) {
        return defaultIfNull(a).compareTo(defaultIfNull(b)) > 0;
    }

    /**
     * 判断 a 是否大于等于 b。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     *
     * @param a 第一个值
     * @param b 第二个值
     * @return a >= b 返回 true
     */
    public static boolean gte(BigDecimal a, BigDecimal b) {
        return defaultIfNull(a).compareTo(defaultIfNull(b)) >= 0;
    }

    /**
     * 判断 a 是否小于 b。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     *
     * @param a 第一个值
     * @param b 第二个值
     * @return a < b 返回 true
     */
    public static boolean lt(BigDecimal a, BigDecimal b) {
        return defaultIfNull(a).compareTo(defaultIfNull(b)) < 0;
    }

    /**
     * 判断 a 是否小于等于 b。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     *
     * @param a 第一个值
     * @param b 第二个值
     * @return a <= b 返回 true
     */
    public static boolean lte(BigDecimal a, BigDecimal b) {
        return defaultIfNull(a).compareTo(defaultIfNull(b)) <= 0;
    }

    // ═══════════════════════════════════════════════════════════════════
    // 算术运算
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 加法运算：a + b。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     *
     * @param a 被加数
     * @param b 加数
     * @return a + b 的结果
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return defaultIfNull(a).add(defaultIfNull(b));
    }

    /**
     * 减法运算：a - b。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     *
     * @param a 被减数
     * @param b 减数
     * @return a - b 的结果
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return defaultIfNull(a).subtract(defaultIfNull(b));
    }

    /**
     * 乘法运算：a * b。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     *
     * @param a 被乘数
     * @param b 乘数
     * @return a * b 的结果
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return defaultIfNull(a).multiply(defaultIfNull(b));
    }

    /**
     * 除法运算：a / b，使用默认精度（scale=2）和舍入模式（HALF_UP）。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     * 如果除数为零，抛出 {@link ArithmeticException}。
     *
     * @param a 被除数
     * @param b 除数
     * @return a / b 的结果，保留 2 位小数，四舍五入
     * @throws ArithmeticException 如果除数为零
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return divide(a, b, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 除法运算：a / b，使用自定义精度和舍入模式。
     *
     * <p>null 入参视为 {@link BigDecimal#ZERO}。
     * 如果除数为零，抛出 {@link ArithmeticException}。
     *
     * @param a            被除数
     * @param b            除数
     * @param scale        保留小数位数
     * @param roundingMode 舍入模式
     * @return a / b 的结果
     * @throws ArithmeticException 如果除数为零
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
        BigDecimal divisor = defaultIfNull(b);
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return defaultIfNull(a).divide(divisor, scale, roundingMode);
    }
}