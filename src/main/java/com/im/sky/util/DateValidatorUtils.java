package com.im.sky.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 日期合法性校验工具类。
 *
 * <p>基于 JDK 8+ 的 {@code java.time} API（{@link LocalDate} / {@link LocalDateTime} /
 * {@link DateTimeFormatter}）实现，统一项目内的日期校验逻辑，避免 {@code SimpleDateFormat}
 * 在并发场景下的线程安全问题。
 *
 * <p>设计要点：
 * <ul>
 *     <li>类声明为 {@code final}，私有构造器抛 {@link UnsupportedOperationException}，禁止实例化</li>
 *     <li>所有方法均为静态、纯函数，天然线程安全</li>
 *     <li>{@link DateTimeFormatter} 默认严格解析，可识别 {@code 2025-02-30} 等不真实日期</li>
 *     <li>入参为 {@code null} 或空白字符串时返回 {@code false}（语义：非法输入即校验失败）</li>
 *     <li>{@code pattern} 入参为 {@code null} 或空白时抛 {@link IllegalArgumentException}（编码错误，需开发感知）</li>
 *     <li>解析异常 {@link DateTimeParseException} 在工具类内部捕获，不向上扩散</li>
 * </ul>
 *
 * <p>本任务仅提供工具类骨架与字符串校验能力，范围比较、闰年/周末、出生日期、
 * 区间合法性等校验在后续任务中提供。
 *
 * @author jiangchangwei
 */
public final class DateValidatorUtils {

    /**
     * 默认日期格式：{@code yyyy-MM-dd}
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 默认日期时间格式：{@code yyyy-MM-dd HH:mm:ss}
     */
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认最大年龄（用于出生日期校验，由后续任务使用）
     */
    public static final int DEFAULT_MAX_AGE = 150;

    /**
     * 默认日期 formatter（缓存），对应 {@link #DEFAULT_DATE_PATTERN}
     */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

    /**
     * 默认日期时间 formatter（缓存），对应 {@link #DEFAULT_DATE_TIME_PATTERN}
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

    /**
     * 私有构造器，禁止实例化。
     *
     * @throws UnsupportedOperationException 总是抛出，工具类不可实例化
     */
    private DateValidatorUtils() {
        throw new UnsupportedOperationException("DateValidatorUtils cannot be instantiated");
    }

    // ==================== 日期字符串校验 ====================

    /**
     * 按指定格式校验日期字符串是否合法（同时校验格式与日期真实性）。
     *
     * <p>例如：当 {@code pattern = "yyyy-MM-dd"} 时：
     * <ul>
     *     <li>{@code "2024-02-29"} -> {@code true}（2024 年是闰年）</li>
     *     <li>{@code "2023-02-29"} -> {@code false}（2023 年非闰年）</li>
     *     <li>{@code "2025-02-30"} -> {@code false}（日期不真实存在）</li>
     *     <li>{@code "2025/01/01"} -> {@code false}（格式不匹配）</li>
     * </ul>
     *
     * @param dateStr 日期字符串，{@code null} 或空白返回 {@code false}
     * @param pattern 日期格式，不可为 {@code null} 或空白
     * @return 当字符串满足格式且日期真实存在时返回 {@code true}，否则 {@code false}
     * @throws IllegalArgumentException 当 {@code pattern} 为 {@code null} 或空白，
     *                                  或 {@code pattern} 自身不是合法的格式串
     */
    public static boolean isValidDate(String dateStr, String pattern) {
        checkPattern(pattern);
        if (isBlank(dateStr)) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /**
     * 按默认格式 {@link #DEFAULT_DATE_PATTERN}（{@code yyyy-MM-dd}）校验日期字符串。
     *
     * @param dateStr 日期字符串，{@code null} 或空白返回 {@code false}
     * @return 校验结果
     */
    public static boolean isValidDate(String dateStr) {
        if (isBlank(dateStr)) {
            return false;
        }
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /**
     * 按指定格式校验日期时间字符串是否合法（同时校验格式与日期时间真实性）。
     *
     * @param dateTimeStr 日期时间字符串，{@code null} 或空白返回 {@code false}
     * @param pattern     日期时间格式，不可为 {@code null} 或空白
     * @return 当字符串满足格式且日期时间真实存在时返回 {@code true}，否则 {@code false}
     * @throws IllegalArgumentException 当 {@code pattern} 为 {@code null} 或空白，
     *                                  或 {@code pattern} 自身不是合法的格式串
     */
    public static boolean isValidDateTime(String dateTimeStr, String pattern) {
        checkPattern(pattern);
        if (isBlank(dateTimeStr)) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime.parse(dateTimeStr, formatter);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /**
     * 按默认格式 {@link #DEFAULT_DATE_TIME_PATTERN}（{@code yyyy-MM-dd HH:mm:ss}）校验日期时间字符串。
     *
     * @param dateTimeStr 日期时间字符串，{@code null} 或空白返回 {@code false}
     * @return 校验结果
     */
    public static boolean isValidDateTime(String dateTimeStr) {
        if (isBlank(dateTimeStr)) {
            return false;
        }
        try {
            LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    // ==================== 日期范围比较 ====================

    /**
     * 校验 {@link LocalDate} 是否落在闭区间 {@code [start, end]} 内。
     *
     * <p>规则：
     * <ul>
     *     <li>{@code date} 为 {@code null} 返回 {@code false}</li>
     *     <li>{@code start} 为 {@code null} 视为下界无限（仅校验 {@code date <= end}）</li>
     *     <li>{@code end} 为 {@code null} 视为上界无限（仅校验 {@code date >= start}）</li>
     *     <li>当 {@code start > end} 时（区间非法）返回 {@code false}</li>
     *     <li>采用闭区间，即 {@code date == start} 或 {@code date == end} 均返回 {@code true}</li>
     * </ul>
     *
     * @param date  待校验日期
     * @param start 区间下界（含），可为 {@code null}
     * @param end   区间上界（含），可为 {@code null}
     * @return 当 date 落在区间内时返回 {@code true}
     */
    public static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null) {
            return false;
        }
        if (start != null && end != null && start.isAfter(end)) {
            return false;
        }
        if (start != null && date.isBefore(start)) {
            return false;
        }
        if (end != null && date.isAfter(end)) {
            return false;
        }
        return true;
    }

    /**
     * 校验 {@link LocalDateTime} 是否落在闭区间 {@code [start, end]} 内。
     *
     * <p>规则同 {@link #isBetween(LocalDate, LocalDate, LocalDate)}。
     *
     * @param dateTime 待校验日期时间
     * @param start    区间下界（含），可为 {@code null}
     * @param end      区间上界（含），可为 {@code null}
     * @return 当 dateTime 落在区间内时返回 {@code true}
     */
    public static boolean isBetween(LocalDateTime dateTime, LocalDateTime start, LocalDateTime end) {
        if (dateTime == null) {
            return false;
        }
        if (start != null && end != null && start.isAfter(end)) {
            return false;
        }
        if (start != null && dateTime.isBefore(start)) {
            return false;
        }
        if (end != null && dateTime.isAfter(end)) {
            return false;
        }
        return true;
    }

    /**
     * 校验 {@code date} 是否严格早于 {@code target}。
     *
     * @param date   待校验日期，{@code null} 返回 {@code false}
     * @param target 比较目标日期，{@code null} 返回 {@code false}
     * @return 当 {@code date < target} 时返回 {@code true}
     */
    public static boolean isBefore(LocalDate date, LocalDate target) {
        if (date == null || target == null) {
            return false;
        }
        return date.isBefore(target);
    }

    /**
     * 校验 {@code dateTime} 是否严格早于 {@code target}。
     *
     * @param dateTime 待校验日期时间，{@code null} 返回 {@code false}
     * @param target   比较目标日期时间，{@code null} 返回 {@code false}
     * @return 当 {@code dateTime < target} 时返回 {@code true}
     */
    public static boolean isBefore(LocalDateTime dateTime, LocalDateTime target) {
        if (dateTime == null || target == null) {
            return false;
        }
        return dateTime.isBefore(target);
    }

    /**
     * 校验 {@code date} 是否严格晚于 {@code target}。
     *
     * @param date   待校验日期，{@code null} 返回 {@code false}
     * @param target 比较目标日期，{@code null} 返回 {@code false}
     * @return 当 {@code date > target} 时返回 {@code true}
     */
    public static boolean isAfter(LocalDate date, LocalDate target) {
        if (date == null || target == null) {
            return false;
        }
        return date.isAfter(target);
    }

    /**
     * 校验 {@code dateTime} 是否严格晚于 {@code target}。
     *
     * @param dateTime 待校验日期时间，{@code null} 返回 {@code false}
     * @param target   比较目标日期时间，{@code null} 返回 {@code false}
     * @return 当 {@code dateTime > target} 时返回 {@code true}
     */
    public static boolean isAfter(LocalDateTime dateTime, LocalDateTime target) {
        if (dateTime == null || target == null) {
            return false;
        }
        return dateTime.isAfter(target);
    }

    // ==================== 日历语义判断 ====================

    /**
     * 判断指定年份是否为闰年。
     *
     * <p>规则：能被 4 整除且不能被 100 整除，或能被 400 整除。
     *
     * @param year 年份，支持任意 {@code int} 值（含负数表示公元前年份）
     * @return 闰年返回 {@code true}
     */
    public static boolean isLeapYear(int year) {
        return Year.isLeap(year);
    }

    /**
     * 判断指定日期所在年份是否为闰年。
     *
     * @param date 待判断日期，{@code null} 返回 {@code false}
     * @return 所在年份为闰年时返回 {@code true}
     */
    public static boolean isLeapYear(LocalDate date) {
        if (date == null) {
            return false;
        }
        return Year.isLeap(date.getYear());
    }

    /**
     * 判断指定日期是否为周末（周六或周日）。
     *
     * <p>本方法不考虑国家法定假日与调休规则。
     *
     * @param date 待判断日期，{@code null} 返回 {@code false}
     * @return 当日期为周六或周日时返回 {@code true}
     */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    /**
     * 判断指定日期是否为工作日（周一至周五）。
     *
     * <p>本方法不考虑国家法定假日与调休规则。
     *
     * @param date 待判断日期，{@code null} 返回 {@code false}
     * @return 当日期为周一至周五时返回 {@code true}
     */
    public static boolean isWeekday(LocalDate date) {
        if (date == null) {
            return false;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    // ==================== 出生日期校验 ====================

    /**
     * 校验出生日期是否合理（默认最大年龄 {@link #DEFAULT_MAX_AGE} 岁）。
     *
     * <p>校验规则：
     * <ul>
     *     <li>{@code birthday} 为 {@code null} 返回 {@code false}</li>
     *     <li>{@code birthday} 晚于当前系统日期返回 {@code false}（不能为未来）</li>
     *     <li>根据 {@code birthday} 计算的年龄大于 {@link #DEFAULT_MAX_AGE} 返回 {@code false}</li>
     * </ul>
     *
     * @param birthday 出生日期
     * @return 出生日期合法返回 {@code true}
     */
    public static boolean isValidBirthday(LocalDate birthday) {
        return isValidBirthday(birthday, DEFAULT_MAX_AGE);
    }

    /**
     * 校验出生日期字符串是否合法。
     *
     * <p>按 {@link #DEFAULT_DATE_PATTERN}（{@code yyyy-MM-dd}）解析；解析失败返回 {@code false}，
     * 解析成功后再委托给 {@link #isValidBirthday(LocalDate)} 进行业务规则校验。
     *
     * @param birthdayStr 出生日期字符串
     * @return 字符串可解析且符合业务规则时返回 {@code true}
     */
    public static boolean isValidBirthday(String birthdayStr) {
        if (isBlank(birthdayStr)) {
            return false;
        }
        try {
            LocalDate birthday = LocalDate.parse(birthdayStr, DATE_FORMATTER);
            return isValidBirthday(birthday, DEFAULT_MAX_AGE);
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /**
     * 校验出生日期是否合理（自定义最大年龄）。
     *
     * <p>年龄计算使用 {@link Period#between(LocalDate, LocalDate)} 的 {@code getYears()}，
     * 即按"周岁"计算（未到生日的当年不算）。例如：今天为 2026-05-29，
     * 出生于 1876-05-30 仍算 149 岁，1876-05-29 算 150 岁，1876-05-28 算 150 岁但 +1 天即超 150。
     *
     * <p>判定语义：年龄 ≤ {@code maxAge} 视为合法。
     *
     * @param birthday 出生日期，{@code null} 返回 {@code false}
     * @param maxAge   允许的最大年龄（含），必须 {@code > 0}
     * @return 出生日期合法返回 {@code true}
     * @throws IllegalArgumentException 当 {@code maxAge <= 0}
     */
    public static boolean isValidBirthday(LocalDate birthday, int maxAge) {
        if (maxAge <= 0) {
            throw new IllegalArgumentException("maxAge must be > 0, maxAge: " + maxAge);
        }
        if (birthday == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        if (birthday.isAfter(today)) {
            return false;
        }
        int years = Period.between(birthday, today).getYears();
        return years <= maxAge;
    }

    // ==================== 日期区间合法性校验 ====================

    /**
     * 校验日期区间是否合法（开始 ≤ 结束）。
     *
     * @param start 开始日期，{@code null} 返回 {@code false}
     * @param end   结束日期，{@code null} 返回 {@code false}
     * @return 当 {@code start <= end} 时返回 {@code true}
     */
    public static boolean isValidRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return false;
        }
        return !start.isAfter(end);
    }

    /**
     * 校验日期时间区间是否合法（开始 ≤ 结束）。
     *
     * @param start 开始时间，{@code null} 返回 {@code false}
     * @param end   结束时间，{@code null} 返回 {@code false}
     * @return 当 {@code start <= end} 时返回 {@code true}
     */
    public static boolean isValidRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return false;
        }
        return !start.isAfter(end);
    }

    /**
     * 按指定格式解析两端字符串后校验区间合法性。
     *
     * <p>解析失败或区间非法（{@code start > end}）均返回 {@code false}，
     * 解析异常 {@link DateTimeParseException} 在工具类内部捕获，不向上扩散。
     *
     * @param startStr 开始日期字符串
     * @param endStr   结束日期字符串
     * @param pattern  日期格式，不可为 {@code null} 或空白
     * @return 字符串可解析且 {@code start <= end} 时返回 {@code true}
     * @throws IllegalArgumentException 当 {@code pattern} 为 {@code null} 或空白，
     *                                  或 {@code pattern} 自身不是合法的格式串
     */
    public static boolean isValidRange(String startStr, String endStr, String pattern) {
        checkPattern(pattern);
        if (isBlank(startStr) || isBlank(endStr)) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate start = LocalDate.parse(startStr, formatter);
            LocalDate end = LocalDate.parse(endStr, formatter);
            return !start.isAfter(end);
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    // ==================== 内部工具方法 ====================

    /**
     * 判断字符串是否为 {@code null} 或全空白字符。
     *
     * <p>未使用 JDK 11 的 {@code String#isBlank()}，以兼容 JDK 8。
     *
     * @param str 待判断字符串
     * @return 当字符串为 {@code null} 或 trim 后为空时返回 {@code true}
     */
    private static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 校验 pattern 是否非空。
     *
     * @param pattern 日期格式串
     * @throws IllegalArgumentException 当 pattern 为 null 或空白
     */
    private static void checkPattern(String pattern) {
        if (isBlank(pattern)) {
            throw new IllegalArgumentException("pattern must not be null or blank");
        }
    }
}