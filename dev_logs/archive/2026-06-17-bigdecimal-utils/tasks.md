# Implementation Tasks

## Task Overview
- Total tasks: 3
- Task categories: 开发任务、测试任务

## Development Tasks

- [x] 1. 创建 BigDecimalUtils 类骨架与判断/辅助方法
  - 在 `src/main/java/com/im/sky/util/` 下新建 `BigDecimalUtils.java`
  - 类声明为 `public final class`，添加私有构造器并抛出 `UnsupportedOperationException`
  - 定义常量：`DEFAULT_SCALE = 2`、`DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP`
  - 实现判断方法：`isNull`、`isZero`、`isPositive`、`isNegative`、`isNullOrZero`
  - 实现辅助方法：`defaultIfNull`、`stripTrailingZeros`
  - 提供完整 JavaDoc（功能描述、`@param`、`@return`）
  - 交付物：`BigDecimalUtils.java`（骨架 + 判断 + 辅助方法）

- [x] 2. 实现比较方法和算术运算方法
  - 在已存在的 `BigDecimalUtils.java` 上追加方法：
  - 比较方法：`equals(a, b)` 使用 compareTo 忽略精度，`gt`、`gte`、`lt`、`lte`，null 入参视为 ZERO
  - 算术运算：`add`、`subtract`、`multiply`，null 入参视为 ZERO
  - 除法运算：`divide(a, b)` 默认 scale=2/HALF_UP，`divide(a, b, scale, mode)` 自定义精度
  - 除数为 null 视为 ZERO 后抛 `ArithmeticException`（除以零）
  - 所有方法配 JavaDoc，保持线程安全
  - 交付物：在 `BigDecimalUtils.java` 中追加比较和算术方法

## Testing Tasks

- [x] 3. 编写 BigDecimalUtilsTest 单元测试
  - 在 `src/test/java/com/im/sky/util/` 下新建 `BigDecimalUtilsTest.java`
  - 使用 JUnit 4
  - 每个 public 方法覆盖：正常用例、null 入参、边界值
  - 判断方法测试：null、零值、正数、负数
  - 比较方法测试：相等（不同精度 1.0 vs 1.00）、大小比较、null 入参
  - 算术运算测试：正常运算、null 入参、除以零抛异常
  - 辅助方法测试：null 转 ZERO、去除尾零
  - 交付物：`BigDecimalUtilsTest.java`，所有测试用例通过