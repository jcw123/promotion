# Proposal: BigDecimalUtils 工具类

## 概述

为项目提供一个统一的 `BigDecimalUtils` 工具类，封装 `java.math.BigDecimal` 常用操作，包括判断（null 安全）、比较、加减乘除等运算，避免业务代码中重复且易出错的 BigDecimal 操作。

## 动机

- `BigDecimal` 的 `equals` 方法比较的是值和精度（如 `1.0` != `1.00`），容易导致业务逻辑 Bug
- 加减乘除操作需要频繁处理 null 值和精度设置，代码冗余且容易遗漏
- 业务中常需判断金额是否为零、正数、负数等，缺少统一封装
- 项目已有 `DateValidatorUtils` 的工具类模式，`BigDecimalUtils` 可复用相同风格

## 目标

1. 提供 null 安全的判断方法：`isNull`、`isZero`、`isPositive`、`isNegative`、`isNullOrZero`
2. 提供安全的比较方法：`equals`（语义相等，忽略精度）、`compareTo`、`gt`、`gte`、`lt`、`lte`
3. 提供安全的算术运算：`add`、`subtract`、`multiply`、`divide`（带精度和舍入模式）
4. 提供格式化辅助：`defaultIfNull`（null 转零值）、`stripTrailingZeros`
5. 所有方法为静态、纯函数，线程安全
6. 完整的 JavaDoc 注释，遵循阿里 Java 开发手册规范

## 非目标

- 不实现货币格式化（如加货币符号、千位分隔符）
- 不实现汇率换算等业务逻辑
- 不包装复杂金融计算（如复利、折旧等）
- 不依赖任何第三方库（仅使用 JDK 标准库）

## 技术约束

- JDK 版本：Java 8+
- 包路径：`com.im.sky.util`
- 类设计：`public final class`，私有构造器
- 除法默认精度：`scale = 2`，舍入模式 `RoundingMode.HALF_UP`
- null 处理策略：入参为 null 时视为 `BigDecimal.ZERO`（而非抛异常）

## 影响范围

- 新增文件：`src/main/java/com/im/sky/util/BigDecimalUtils.java`
- 新增测试：`src/test/java/com/im/sky/util/BigDecimalUtilsTest.java`
- 不修改现有代码

## 成功标准

- 所有 public 方法均有完整 JavaDoc
- 单元测试覆盖所有方法的正常、null、边界场景
- 编译通过，测试全部 PASS