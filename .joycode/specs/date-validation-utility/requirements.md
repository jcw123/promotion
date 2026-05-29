# Requirements Document

## Introduction

本项目旨在为 `promotion` 工程提供一个统一、健壮、线程安全的日期合法性校验工具类 `DateValidatorUtils`，置于 `com.im.sky.util` 包下。工具类基于 JDK 8+ 的 `java.time` API（`LocalDate` / `LocalDateTime` / `DateTimeFormatter`），禁用线程不安全的 `SimpleDateFormat`，统一项目内的日期校验逻辑，避免重复实现与潜在 Bug。

工具类需覆盖：日期字符串格式校验、日期真实性校验、日期范围校验、闰年/周末判断、出生日期合理性校验、日期区间合法性校验等常见业务校验场景，并提供完整的 JavaDoc 与阿里 Java 开发手册规范要求。

## Requirements

### Requirement 1 - 日期字符串格式与真实性校验

**User Story:** 作为后端开发人员，我需要校验用户输入的日期字符串是否同时满足"格式正确"与"日期真实存在"，以便拒绝 `2025-02-30`、`2025-13-01` 等非法输入。

#### Acceptance Criteria

1. 当传入 `dateStr` 为 `null` 或空白字符串时，`isValidDate(dateStr, pattern)` 返回 `false`。
2. 当传入 `pattern` 为 `null` 或空白字符串时，方法抛出 `IllegalArgumentException`。
3. 当 `dateStr` 与 `pattern` 格式不匹配（如 `2025/01/01` 与 `yyyy-MM-dd`）时，返回 `false`。
4. 当 `dateStr` 格式匹配但日期不真实存在（如 `2025-02-30`、`2025-13-01`、`2024-02-30`）时，返回 `false`。
5. 当 `dateStr` 格式与日期均合法时，返回 `true`。
6. 提供 `isValidDate(String dateStr)` 重载，默认使用 `yyyy-MM-dd` 格式。
7. 提供 `isValidDateTime(String dateTimeStr, String pattern)` 用于校验日期时间字符串，规则同上。
8. 提供 `isValidDateTime(String dateTimeStr)` 重载，默认使用 `yyyy-MM-dd HH:mm:ss` 格式。

### Requirement 2 - 日期范围校验

**User Story:** 作为业务开发人员，我需要校验某个日期是否落在指定区间内/之前/之后，以便实现优惠券有效期、活动时间窗口等业务规则。

#### Acceptance Criteria

1. 当 `date` 为 `null` 时，`isBetween` / `isBefore` / `isAfter` 系列方法均返回 `false`。
2. 当 `start` 或 `end` 为 `null` 时，对应边界视为无限（如 `isBetween(date, null, end)` 等价于 `date <= end`）。
3. `isBetween(date, start, end)`：当 `start ≤ date ≤ end` 时返回 `true`，区间为闭区间。
4. 当 `start > end` 时，`isBetween` 返回 `false`（区间非法）。
5. `isBefore(date, target)`：当 `date < target` 时返回 `true`。
6. `isAfter(date, target)`：当 `date > target` 时返回 `true`。
7. 上述方法均提供 `LocalDate` 与 `LocalDateTime` 两类重载。

### Requirement 3 - 闰年与周末/工作日判断

**User Story:** 作为业务开发人员，我需要快速判断某个日期所在年份是否为闰年，以及该日期是周末还是工作日，以便实现日历相关业务。

#### Acceptance Criteria

1. `isLeapYear(int year)`：当 `year` 为闰年时返回 `true`，否则返回 `false`。规则：能被 4 整除且不能被 100 整除，或能被 400 整除。
2. `isLeapYear(LocalDate date)`：当 `date` 为 `null` 时返回 `false`，否则按其所在年份判断。
3. `isWeekend(LocalDate date)`：当 `date` 为 `null` 时返回 `false`；当 `date` 为周六或周日时返回 `true`。
4. `isWeekday(LocalDate date)`：当 `date` 为 `null` 时返回 `false`；当 `date` 为周一至周五时返回 `true`。
5. 周末判断不考虑国家法定假日（如需调休判断需另行扩展，本期不实现）。

### Requirement 4 - 出生日期合法性校验

**User Story:** 作为用户注册功能开发人员，我需要校验用户填写的出生日期是否合理，避免出现未来日期或不合常理的超大年龄。

#### Acceptance Criteria

1. 当 `birthday` 为 `null` 时，`isValidBirthday(birthday)` 返回 `false`。
2. 当 `birthday` 晚于当前系统日期时，返回 `false`（出生日期不可为未来）。
3. 当根据 `birthday` 计算出的年龄大于 `MAX_AGE`（默认 150 岁）时，返回 `false`。
4. 当 `birthday` 合法时，返回 `true`。
5. 提供 `isValidBirthday(String birthdayStr)` 重载，默认按 `yyyy-MM-dd` 格式解析；解析失败返回 `false`。
6. 提供 `isValidBirthday(LocalDate birthday, int maxAge)` 重载，支持自定义最大年龄；当 `maxAge <= 0` 时抛出 `IllegalArgumentException`。

### Requirement 5 - 日期区间合法性校验

**User Story:** 作为开发人员，我需要校验"开始日期 ≤ 结束日期"，以便防止业务录入非法时间段。

#### Acceptance Criteria

1. 当 `start` 或 `end` 任一为 `null` 时，`isValidRange(start, end)` 返回 `false`。
2. 当 `start ≤ end` 时返回 `true`，否则返回 `false`。
3. 提供 `LocalDate` 与 `LocalDateTime` 两类重载。
4. 提供 `isValidRange(String startStr, String endStr, String pattern)` 字符串重载，解析失败或区间非法时返回 `false`。

### Requirement 6 - 工具类设计规范

**User Story:** 作为代码规范的维护者，我希望工具类符合阿里 Java 开发手册要求，避免误用与维护困难。

#### Acceptance Criteria

1. 类必须声明为 `public final class DateValidatorUtils`，禁止被继承。
2. 必须包含私有无参构造方法，且在构造方法中抛出 `UnsupportedOperationException`，禁止实例化。
3. 所有方法必须为 `public static`。
4. 禁止在工具类内部使用 `SimpleDateFormat`、`Date`、`Calendar` 等线程不安全或已过时的 API；必须使用 `java.time` 包下的 API。
5. `DateTimeFormatter` 必须缓存为 `static final` 常量（针对常用格式），自定义格式按需创建。
6. 所有 public 方法必须包含完整 JavaDoc：功能描述、`@param`、`@return`、必要时 `@throws`。
7. 所有 `IllegalArgumentException` 抛出场景必须在 JavaDoc 的 `@throws` 中注明。
8. 不允许吞掉异常；解析类校验方法捕获 `DateTimeParseException` 后返回 `false`，不向上抛出。

### Requirement 7 - 单元测试覆盖

**User Story:** 作为质量保证人员，我希望工具类拥有完整的单元测试，覆盖正常、边界与异常场景，保证后续重构安全。

#### Acceptance Criteria

1. 提供 `DateValidatorUtilsTest` 测试类，置于 `src/test/java/com/im/sky/util/` 目录下。
2. 每个 public 方法至少覆盖：正常用例、`null` 入参、边界值、非法格式四类用例。
3. 测试用例使用 JUnit 4（与项目现有测试框架保持一致；若项目未引入测试框架，可使用 `public static void main` 自验证）。
4. 闰年判断需覆盖：2000（闰）、2100（非闰）、2024（闰）、2023（非闰）四个典型值。
5. 出生日期校验需覆盖：未来日期、150 岁以内、150 岁以外、刚好 150 岁的边界场景。