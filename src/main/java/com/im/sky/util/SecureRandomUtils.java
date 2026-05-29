package com.im.sky.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * 安全随机数工具类
 *
 * <p>基于 {@link SecureRandom} 实现，适用于 token、密码、密钥、验证码等
 * 对随机性有安全要求的场景。
 *
 * <p>设计要点：
 * <ul>
 *     <li>使用线程安全的 {@link SecureRandom} 单例（JDK 内部已做并发处理）</li>
 *     <li>优先尝试强随机源 {@code SecureRandom.getInstanceStrong()}，
 *         不可用时回退到默认实现，避免在某些 Linux 环境下熵不足导致阻塞</li>
 *     <li>所有方法对入参做边界校验，非法参数抛 {@link IllegalArgumentException}</li>
 *     <li>不可实例化</li>
 * </ul>
 *
 * <p>注意：本类不适合做高频普通业务随机（性能不如 {@link java.util.concurrent.ThreadLocalRandom}）。
 *
 * @author jiangchangwei
 */
public final class SecureRandomUtils {

    /**
     * 数字字符集
     */
    public static final String DIGITS = "0123456789";

    /**
     * 小写字母字符集
     */
    public static final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 大写字母字符集
     */
    public static final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 字母字符集（大小写）
     */
    public static final String LETTERS = LOWER_LETTERS + UPPER_LETTERS;

    /**
     * 字母+数字字符集
     */
    public static final String ALPHANUMERIC = LETTERS + DIGITS;

    /**
     * URL 安全字符集（字母+数字+ - _ ）
     */
    public static final String URL_SAFE = ALPHANUMERIC + "-_";

    /**
     * 共享 SecureRandom 实例。
     * <p>SecureRandom 自身线程安全，可全局复用，避免反复创建带来的种子初始化开销。
     */
    private static final SecureRandom SECURE_RANDOM = createSecureRandom();

    private SecureRandomUtils() {
        throw new UnsupportedOperationException("SecureRandomUtils cannot be instantiated");
    }

    /**
     * 创建 SecureRandom 实例，优先使用强随机源。
     * <p>{@code getInstanceStrong()} 在某些 Linux 环境（依赖 /dev/random）下可能阻塞，
     * 此处捕获异常回退到默认非阻塞实现。
     */
    private static SecureRandom createSecureRandom() {
        try {
            return SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            return new SecureRandom();
        }
    }

    // ==================== 基础随机数 ====================

    /**
     * 生成 [0, bound) 区间的安全随机 int
     *
     * @param bound 上界（不含），必须 > 0
     * @return 随机整数
     * @throws IllegalArgumentException 当 bound <= 0
     */
    public static int nextInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("bound must be > 0, bound: " + bound);
        }
        return SECURE_RANDOM.nextInt(bound);
    }

    /**
     * 生成 [origin, bound) 区间的安全随机 int
     *
     * @param origin 下界（含）
     * @param bound  上界（不含），必须 > origin
     * @return 随机整数
     * @throws IllegalArgumentException 当 bound <= origin
     */
    public static int nextInt(int origin, int bound) {
        if (bound <= origin) {
            throw new IllegalArgumentException(
                    "bound must be > origin, origin: " + origin + ", bound: " + bound);
        }
        return origin + SECURE_RANDOM.nextInt(bound - origin);
    }

    /**
     * 生成安全随机 long
     */
    public static long nextLong() {
        return SECURE_RANDOM.nextLong();
    }

    /**
     * 生成安全随机 boolean
     */
    public static boolean nextBoolean() {
        return SECURE_RANDOM.nextBoolean();
    }

    // ==================== 随机字节 ====================

    /**
     * 生成指定长度的随机字节数组
     *
     * @param length 字节长度，必须 > 0
     * @return 随机字节数组
     * @throws IllegalArgumentException 当 length <= 0
     */
    public static byte[] nextBytes(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be > 0, length: " + length);
        }
        byte[] bytes = new byte[length];
        SECURE_RANDOM.nextBytes(bytes);
        return bytes;
    }

    // ==================== 验证码 ====================

    /**
     * 生成指定长度的纯数字验证码（首位可能为 0）
     *
     * @param length 验证码长度，必须 > 0
     * @return 数字验证码字符串
     * @throws IllegalArgumentException 当 length <= 0
     */
    public static String nextNumericCode(int length) {
        return nextString(length, DIGITS);
    }

    // ==================== Token / 字符串 ====================

    /**
     * 生成指定长度的字母+数字 token
     *
     * @param length token 长度，必须 > 0
     * @return 随机 token
     * @throws IllegalArgumentException 当 length <= 0
     */
    public static String nextAlphanumeric(int length) {
        return nextString(length, ALPHANUMERIC);
    }

    /**
     * 生成指定长度的 URL 安全字符串（字母+数字+ - _ ）
     *
     * @param length 长度，必须 > 0
     * @return URL 安全字符串
     * @throws IllegalArgumentException 当 length <= 0
     */
    public static String nextUrlSafe(int length) {
        return nextString(length, URL_SAFE);
    }

    /**
     * 从指定字符集中生成随机字符串
     *
     * @param length  目标长度，必须 > 0
     * @param charset 字符集，不可为 null 或空
     * @return 随机字符串
     * @throws IllegalArgumentException 当 length <= 0 或 charset 为空
     */
    public static String nextString(int length, String charset) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be > 0, length: " + length);
        }
        Objects.requireNonNull(charset, "charset must not be null");
        if (charset.isEmpty()) {
            throw new IllegalArgumentException("charset must not be empty");
        }
        char[] chars = new char[length];
        int charsetLen = charset.length();
        for (int i = 0; i < length; i++) {
            chars[i] = charset.charAt(SECURE_RANDOM.nextInt(charsetLen));
        }
        return new String(chars);
    }

    // ==================== 十六进制 / Base64 ====================

    /**
     * 生成指定字节长度的随机十六进制字符串。
     * <p>结果长度为 byteLength * 2，常用于 token、salt 等。
     *
     * @param byteLength 字节长度，必须 > 0
     * @return 十六进制字符串（小写）
     * @throws IllegalArgumentException 当 byteLength <= 0
     */
    public static String nextHex(int byteLength) {
        byte[] bytes = nextBytes(byteLength);
        StringBuilder sb = new StringBuilder(byteLength * 2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >> 4) & 0xF, 16));
            sb.append(Character.forDigit(b & 0xF, 16));
        }
        return sb.toString();
    }

    /**
     * 生成指定字节长度的 URL 安全 Base64 字符串（无 padding）
     *
     * @param byteLength 字节长度，必须 > 0
     * @return Base64 字符串
     * @throws IllegalArgumentException 当 byteLength <= 0
     */
    public static String nextBase64UrlSafe(int byteLength) {
        byte[] bytes = nextBytes(byteLength);
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}