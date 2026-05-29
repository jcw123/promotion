package com.im.sky.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * BigDecimal 工具类
 *
 * <p>提供 BigDecimal 常用的安全运算（加、减、乘、除）、比较、四舍五入、格式化等方法，
 * 避免直接使用 {@code double} 进行金额、价格等高精度计算时可能产生的精度丢失问题。
 *
 * <p>设计原则：
 * <ul>
 *     <li>所有方法均为静态方法，工具类不可实例化</li>
 *     <li>对入参进行 null 安全处理，null 视为 {@link BigDecimal#ZERO}</li>
 *     <li>除法默认使用 {@link RoundingMode#HALF_UP}（四舍五入），默认保留 2 位小数</li>
 *     <li>数值比较禁止使用 {@link BigDecimal#equals(Object)}，应使用 {@link BigDecimal#compareTo(BigDecimal)}</li>
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
     * 私有构造方法，禁止实例化
     */
    private BigDecimalUtils() {
        throw new UnsupportedOperationException("BigDecimalUtils cannot be instantiated");
    }

    // ==================== null 安全 ====================

    /**
     * 将可能为 null 的 BigDecimal 转换为 0
     *
     * @param value 待转换值
     * @return 非 null 的 BigDecimal
     */
    public static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * 判断 BigDecimal 是否为 null 或 0
     *
     * @param value 待判断值
     * @return true 表示为 null 或数值等于 0
     */
    public static boolean isNullOrZero(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) == 0;
    }

    // ==================== 加减乘除 ====================

    /**
     * 加法：a + b
     *
     * @param a 加数（null 视为 0）
     * @param b 加数（null 视为 0）
     * @return 和
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return nullToZero(a).add(nullToZero(b));
    }

    /**
     * 多值求和
     *
     * @param values 多个加数（null 视为 0）
     * @return 总和；若 values 为 null 或空数组返回 0
     */
    public static BigDecimal add(BigDecimal... values) {
        if (values == null || values.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            result = result.add(nullToZero(value));
        }
        return result;
    }

    /**
     * 减法：a - b
     *
     * @param a 被减数（null 视为 0）
     * @param b 减数（null 视为 0）
     * @return 差
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return nullToZero(a).subtract(nullToZero(b));
    }

    /**
     * 乘法：a * b
     *
     * @param a 乘数（null 视为 0）
     * @param b 乘数（null 视为 0）
     * @return 积
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return nullToZero(a).multiply(nullToZero(b));
    }

    /**
     * 乘法并按默认精度保留小数：a * b，结果四舍五入保留 2 位
     *
     * @param a 乘数（null 视为 0）
     * @param b 乘数（null 视为 0）
     * @return 积，保留 {@link #DEFAULT_SCALE} 位
     */
    public static BigDecimal multiplyWithScale(BigDecimal a, BigDecimal b) {
        return multiply(a, b).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 除法：a / b，默认保留 {@link #DEFAULT_SCALE} 位小数，四舍五入
     *
     * @param a 被除数（null 视为 0）
     * @param b 除数，不可为 null 或 0
     * @return 商
     * @throws IllegalArgumentException 当除数为 null 或 0
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return divide(a, b, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 除法：a / b，指定小数位数，四舍五入
     *
     * @param a     被除数（null 视为 0）
     * @param b     除数，不可为 null 或 0
     * @param scale 保留小数位数，须 >= 0
     * @return 商
     * @throws IllegalArgumentException 当除数为 null/0 或 scale < 0
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
        return divide(a, b, scale, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 除法：a / b，指定小数位数和舍入模式
     *
     * @param a            被除数（null 视为 0）
     * @param b            除数，不可为 null 或 0
     * @param scale        保留小数位数，须 >= 0
     * @param roundingMode 舍入模式，不可为 null
     * @return 商
     * @throws IllegalArgumentException 当除数为 null/0、scale < 0 或 roundingMode 为 null
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
        if (b == null || b.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("divisor must not be null or zero");
        }
        if (scale < 0) {
            throw new IllegalArgumentException("scale must be >= 0, scale: " + scale);
        }
        Objects.requireNonNull(roundingMode, "roundingMode must not be null");
        return nullToZero(a).divide(b, scale, roundingMode);
    }

    // ==================== 比较 ====================

    /**
     * 是否相等（数值比较，忽略 scale 差异）
     *
     * @param a 值 a（null 视为 0）
     * @param b 值 b（null 视为 0）
     * @return true 表示数值相等
     */
    public static boolean eq(BigDecimal a, BigDecimal b) {
        return nullToZero(a).compareTo(nullToZero(b)) == 0;
    }

    /**
     * a 是否大于 b
     */
    public static boolean gt(BigDecimal a, BigDecimal b) {
        return nullToZero(a).compareTo(nullToZero(b)) > 0;
    }

    /**
     * a 是否大于等于 b
     */
    public static boolean ge(BigDecimal a, BigDecimal b) {
        return nullToZero(a).compareTo(nullToZero(b)) >= 0;
    }

    /**
     * a 是否小于 b
     */
    public static boolean lt(BigDecimal a, BigDecimal b) {
        return nullToZero(a).compareTo(nullToZero(b)) < 0;
    }

    /**
     * a 是否小于等于 b
     */
    public static boolean le(BigDecimal a, BigDecimal b) {
        return nullToZero(a).compareTo(nullToZero(b)) <= 0;
    }

    /**
     * 是否为正数（> 0）
     */
    public static boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 是否为负数（< 0）
     */
    public static boolean isNegative(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 取最大值
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        return ge(a, b) ? nullToZero(a) : nullToZero(b);
    }

    /**
     * 取最小值
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return le(a, b) ? nullToZero(a) : nullToZero(b);
    }

    // ==================== 精度处理 ====================

    /**
     * 四舍五入保留指定位数小数
     *
     * @param value 原值（null 视为 0）
     * @param scale 保留小数位数，须 >= 0
     * @return 处理后的值
     * @throws IllegalArgumentException 当 scale < 0
     */
    public static BigDecimal round(BigDecimal value, int scale) {
        return round(value, scale, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 按指定舍入模式保留小数位数
     *
     * @param value        原值（null 视为 0）
     * @param scale        保留小数位数，须 >= 0
     * @param roundingMode 舍入模式，不可为 null
     * @return 处理后的值
     * @throws IllegalArgumentException 当 scale < 0 或 roundingMode 为 null
     */
    public static BigDecimal round(BigDecimal value, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale must be >= 0, scale: " + scale);
        }
        Objects.requireNonNull(roundingMode, "roundingMode must not be null");
        return nullToZero(value).setScale(scale, roundingMode);
    }

    // ==================== 元/分 转换 ====================

    /**
     * 元转分（金额单位转换：1 元 = 100 分）
     *
     * @param yuan 元金额（null 视为 0）
     * @return 对应的分，向下取整
     */
    public static long yuanToFen(BigDecimal yuan) {
        return nullToZero(yuan).multiply(new BigDecimal(100))
                .setScale(0, RoundingMode.DOWN)
                .longValue();
    }

    /**
     * 分转元
     *
     * @param fen 分金额
     * @return 对应的元，保留 2 位小数
     */
    public static BigDecimal fenToYuan(long fen) {
        return new BigDecimal(fen).divide(new BigDecimal(100), DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    // ==================== 格式化 ====================

    /**
     * 转字符串，保留默认精度（2 位小数，四舍五入）
     *
     * @param value 原值（null 视为 0）
     * @return 字符串，永远非 null
     */
    public static String toPlainString(BigDecimal value) {
        return round(value, DEFAULT_SCALE).toPlainString();
    }

    /**
     * 转字符串，指定保留小数位数（四舍五入）
     *
     * @param value 原值（null 视为 0）
     * @param scale 保留小数位数，须 >= 0
     * @return 字符串，永远非 null
     */
    public static String toPlainString(BigDecimal value, int scale) {
        return round(value, scale).toPlainString();
    }
}