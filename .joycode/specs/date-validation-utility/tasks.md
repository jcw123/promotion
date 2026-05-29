# Implementation Tasks

## Task Overview
- Total tasks: 4
- Task categories: 开发任务、测试任务

## Development Tasks

- [x] 1. 创建 DateValidatorUtils 工具类骨架与日期/时间字符串校验能力
  - 在 `src/main/java/com/im/sky/util/` 下新建 `DateValidatorUtils.java`
  - 类声明为 `public final class`，添加私有构造器并抛出 `UnsupportedOperationException`
  - 定义常量：`DEFAULT_DATE_PATTERN`、`DEFAULT_DATE_TIME_PATTERN`、`DEFAULT_MAX_AGE`、`DATE_FORMATTER`、`DATE_TIME_FORMATTER`
  - 实现方法：`isValidDate(String, String)`、`isValidDate(String)`、`isValidDateTime(String, String)`、`isValidDateTime(String)`
  - 严格校验日期真实性（捕获 `DateTimeParseException` 返回 `false`），`pattern` 为空抛 `IllegalArgumentException`
  - 提供完整 JavaDoc（功能描述、`@param`、`@return`、`@throws`）
  - 交付物：`DateValidatorUtils.java`（骨架 + 字符串校验方法）
  - _Requirements: 1.1 1.2 1.3 1.4 1.5 1.6 1.7 1.8 6.1 6.2 6.3 6.4 6.5 6.6 6.7 6.8_

- [x] 2. 实现日期范围比较与日历语义校验
  - 在已存在的 `DateValidatorUtils.java` 上追加方法：
  - `isBetween(LocalDate, LocalDate, LocalDate)`、`isBetween(LocalDateTime, LocalDateTime, LocalDateTime)`：闭区间，`null` 边界视为无限，`start > end` 返回 `false`
  - `isBefore(LocalDate, LocalDate)`、`isAfter(LocalDate, LocalDate)`：`null` 入参返回 `false`
  - `isLeapYear(int)`、`isLeapYear(LocalDate)`：使用 `Year.isLeap` 实现
  - `isWeekend(LocalDate)`、`isWeekday(LocalDate)`：使用 `DayOfWeek` 判断
  - 所有方法配 JavaDoc，保持线程安全
  - 交付物：在 `DateValidatorUtils.java` 中追加 8 个方法
  - _Requirements: 2.1 2.2 2.3 2.4 2.5 2.6 2.7 3.1 3.2 3.3 3.4 3.5_

- [x] 3. 实现出生日期校验与日期区间合法性校验
  - 在已存在的 `DateValidatorUtils.java` 上追加方法：
  - `isValidBirthday(LocalDate)`：`null` 返回 `false`，未来日期返回 `false`，年龄超过 `DEFAULT_MAX_AGE` 返回 `false`
  - `isValidBirthday(String)`：按 `yyyy-MM-dd` 解析失败返回 `false`，再委托给 `LocalDate` 重载
  - `isValidBirthday(LocalDate, int)`：`maxAge <= 0` 抛 `IllegalArgumentException`
  - `isValidRange(LocalDate, LocalDate)`、`isValidRange(LocalDateTime, LocalDateTime)`：`null` 返回 `false`，`start <= end` 返回 `true`
  - `isValidRange(String, String, String)`：解析失败或区间非法返回 `false`
  - 使用 `Period.between` 计算年龄
  - 交付物：在 `DateValidatorUtils.java` 中追加 6 个方法
  - _Requirements: 4.1 4.2 4.3 4.4 4.5 4.6 5.1 5.2 5.3 5.4_

## Testing Tasks

- [x] 4. 编写 DateValidatorUtilsTest 单元测试
  - 在 `src/test/java/com/im/sky/util/` 下新建 `DateValidatorUtilsTest.java`
  - 优先使用 JUnit 4（与项目 `pom.xml` 一致）；若无法使用则提供 `public static void main` 自验证
  - 每个 public 方法覆盖：正常用例、`null` 入参、边界值、非法格式四类用例
  - 闰年用例：2000=true、2100=false、2024=true、2023=false
  - 日期真实性：`2024-02-29`=true、`2023-02-29`=false、`2025-02-30`=false
  - 出生日期：未来日期=false、刚好 150 岁=true、151 岁=false、`null`=false、`maxAge<=0` 抛异常
  - 区间：`null` 边界、`start>end` 行为
  - 交付物：`DateValidatorUtilsTest.java`，所有测试用例通过
  - _Requirements: 7.1 7.2 7.3 7.4 7.5_