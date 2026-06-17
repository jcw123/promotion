# Project Context - promotion

> **last_updated**: 2026-06-17
> **maintainer**: AI auto-refresh on `/sdd-archive`

## 1. 项目基本信息

| 属性 | 值 |
|------|------|
| GroupId | `com.im.sky` |
| ArtifactId | `promotion` |
| 包根路径 | `com.im.sky` |
| JDK 版本 | 1.8 |
| 构建工具 | Maven |
| 打包方式 | jar |
| 编码 | UTF-8 |

## 2. 技术栈

### 核心框架
- **Spring Boot** 2.7.18
- **MyBatis** 3.4.6
- **Netty** 4.1.32.Final

### 数据存储
- **MySQL** (mysql-connector-java 8.0.33)
- **Redis** (Jedis 3.2.0)
- **ZooKeeper** (Curator 2.12.0)

### 工具库
- **Lombok** 1.18.8
- **Guava** 27.0.1-jre
- **FastJSON** 1.2.62
- **Jackson** 2.13.5
- **Commons-Lang3** 3.5
- **Commons-IO** 2.17.0
- **OkHttp** 3.12.0
- **Gson** 2.9.1

### 其他能力
- **Aviator** 5.4.1 (表达式求值)
- **Easy-Rules** 4.1.0 (规则引擎)
- **Disruptor** 3.4.2 (高性能队列)
- **AspectJ** 1.8.4 (AOP)
- **CGLib** 3.2.10 (动态代理)
- **Protostuff** 1.7.2 (序列化)
- **Reactor** (响应式编程)
- **Quartz** 2.3.2 (定时任务)
- **Hive** 3.1.2 (大数据)

### 测试框架
- **JUnit** 4.12
- **Mockito** 3.11.2
- **AssertJ** 3.11.1
- **Hamcrest** 1.3
- **Reactor-Test**

## 3. 工程结构

```
src/main/java/com/im/sky/
├── algorithms/        # 算法题解（LeetCode、Codeforces 等）
│   ├── cc/            # Codeforces 题解
│   ├── knapsack/      # 背包问题系列
│   ├── oiwiki/        # OI Wiki 数论
│   └── template/      # 算法模板（并查集等）
├── bugs/              # Bug 复现用例
├── collection/        # 集合使用示例
├── datastructure/     # 数据结构实现
│   ├── map/           # 图（邻接表、Dijkstra、Floyd、拓扑排序等）
│   ├── queue/         # 队列（跳表、优先队列、迷宫）
│   ├── search/        # 查找（AVL、BST、哈希）
│   ├── sequencelist/  # 线性表（静态链表、约瑟夫问题）
│   ├── sort/          # 排序（10+种排序算法实现）
│   ├── stack/         # 栈（表达式计算、汉诺塔、递归转非递归）
│   ├── string/        # 字符串匹配（KMP、BM、Sunday）
│   └── tree/          # 树（遍历、线索树、Huffman）
├── ddd/               # DDD 领域建模示例
├── functional/        # 函数式编程探索
├── generator/         # ID 生成器（雪花算法、UUID）
├── guava/             # Guava 工具使用
├── hive/              # Hive UDF
├── io/                # I/O 模型实现
│   ├── common/        # BIO
│   ├── multi/thread/  # 多线程 BIO
│   ├── nio/           # NIO（多版本实现）
│   ├── reactor/       # Reactor 模式
│   └── single/thread/ # 单线程 BIO
├── javacore/          # Java 核心（ClassLoader、Atomic、Proxy 等）
├── lock/              # 锁与并发（JUC、AQS、分布式锁）
├── netty/             # Netty 网络编程实践
├── pattern/           # 设计模式实现
├── reactor/           # Project Reactor 响应式
├── rpc/               # RPC 框架探索
├── spring/            # Spring 源码学习
├── test/              # 各类测试探索
└── util/              # 工具类
    ├── DateValidatorUtils.java
    └── BigDecimalUtils.java
```

## 4. 项目性质

这是一个**个人技术学习与实践项目**，不是生产业务系统。主要用途：

- 算法题解练习与积累
- 数据结构手动实现（教科书级）
- Java 核心技术深度探索（JUC、ClassLoader、代理等）
- 中间件/框架原理学习（Netty、Spring、MyBatis）
- 设计模式实践
- 工具类开发与沉淀

## 5. 编码规范

- 遵循**阿里 Java 开发手册**规范
- 工具类使用 `public final class` + 私有构造器模式
- 禁止使用 `java.util.Date` / `SimpleDateFormat`，统一使用 `java.time`
- 完整 JavaDoc 注释（功能描述、@param、@return、@throws）
- 单元测试使用 JUnit 4

## 6. AI 协作规约

### 开发工作流 (SDD)
项目使用 Spec Driven Development 工作流：
```
/sdd-explore → /sdd-propose → /sdd-apply → /sdd-archive
```

工件存放路径：`dev_logs/<change-name>/`，归档至 `dev_logs/archive/`。

### 代码生成约束
- 不使用任何内部工具类或 SDK
- 不泄漏敏感信息
- 仅依赖 pom.xml 中已有的第三方库或 JDK 标准库
- 新建工具类统一放 `com.im.sky.util` 包下

### 目录约定
| 目录 | 用途 |
|------|------|
| `src/main/java/com/im/sky/util/` | 公共工具类 |
| `src/test/java/com/im/sky/util/` | 工具类单元测试 |
| `dev_logs/` | SDD 变更工件（活跃） |
| `dev_logs/archive/` | SDD 变更工件（已归档） |
| `ai-context/` | AI 协作上下文知识 |
| `.joycode/skills/` | JoyCode 技能定义 |
| `.joycode/commands/` | JoyCode 快捷指令 |
| `docs/` | 技术学习笔记 |

## 7. 新代码放置规则

| 代码类型 | 放置包�径 | 示例 |
|---------|-----------|------|
| 公共工具类 | `com.im.sky.util` | DateValidatorUtils, BigDecimalUtils |
| 算法题解 | `com.im.sky.algorithms` | Solution_xxx.java |
| 数据结构实现 | `com.im.sky.datastructure.<type>` | sort/, tree/, queue/ |
| 设计模式 | `com.im.sky.pattern.<pattern-name>` | command/, strategy/ |
| 并发/锁相关 | `com.im.sky.lock` | JUC, AQS, 分布式锁 |
| Netty 网络 | `com.im.sky.netty` | 编解码器、处理器 |
| Spring 学习 | `com.im.sky.spring` | 源码分析、自定义实现 |
| Java 核心 | `com.im.sky.javacore` | ClassLoader、Proxy |
| I/O 模型 | `com.im.sky.io` | BIO, NIO, Reactor |
| 领域建模(DDD) | `com.im.sky.ddd` | 聚合根、实体 |
| 单元测试 | `src/test/java/` 镜像对应 main 路径 | 与被测类同包名 |

## 8. 代码模板

### 工具类标准骨架

```java
package com.im.sky.util;

/**
 * Xxx 工具类。
 *
 * <p>简要描述该工具类的职责和适用场景。
 *
 * @author jiangchangwei
 */
public final class XxxUtils {

    /**
     * 私有构造器，禁止实例化。
     */
    private XxxUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ═══════════════════════════════════════════════════════════════════
    // 方法分组注释
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 方法描述。
     *
     * @param param 参数说明
     * @return 返回值说明
     */
    public static ReturnType methodName(ParamType param) {
        // 实现
    }
}
```

### 单元测试标准骨架

```java
package com.im.sky.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * XxxUtils 单元测试。
 *
 * @author jiangchangwei
 */
public class XxxUtilsTest {

    @Test
    public void testMethodName_normalCase() {
        // given - when - then
    }

    @Test
    public void testMethodName_nullInput() {
        // null 入参场景
    }

    @Test
    public void testMethodName_boundary() {
        // 边界值场景
    }

    @Test(expected = SomeException.class)
    public void testMethodName_exception() {
        // 异常场景
    }
}
```

## 9. 禁止事项清单 (DO NOT)

| 类别 | 禁止事项 | 替代方案 |
|------|---------|---------|
| 日期 | 使用 `java.util.Date`、`Calendar`、`SimpleDateFormat` | `java.time` 系列 |
| 依赖 | 引入任何内部 SDK 或私有工具类 | 仅用 JDK + pom.xml 已有依赖 |
| 安全 | 代码中硬编码密钥、密码、Token 等 | 使用环境变量或配置文件 |
| 输出 | 使用 `System.out.println` 做日志 | 使用 SLF4J Logger |
| 并发 | 在工具类中使用可变共享状态 | 纯静态无状态方法 |
| 构造 | 工具类允许实例化 | private 构造器 + throw |
| 比较 | 使用 `BigDecimal.equals()` 比较金额 | 使用 `compareTo() == 0` |
| 格式 | 方法超过 80 行不拆分 | 保持方法短小，单一职责 |
| 注释 | public 方法无 JavaDoc | 必须写 @param、@return、@throws |
| 异常 | catch 后吞掉异常不做任何处理 | 至少日志记录或向上传播 |
| 测试 | 测试方法无断言 | 每个 @Test 必须有 assert |

## 10. 已有工具类能力索引

### DateValidatorUtils (`com.im.sky.util`)

| 方法 | 功能 |
|------|------|
| `isValidDate(String, String)` | 按指定格式校验日期字符串合法性 |
| `isValidDate(String)` | 按 yyyy-MM-dd 校验日期 |
| `isValidDateTime(String, String)` | 按指定格式校验日期时间字符串 |
| `isValidDateTime(String)` | 按 yyyy-MM-dd HH:mm:ss 校验 |
| `isBetween(LocalDate, LocalDate, LocalDate)` | 日期是否在闭区间内 |
| `isBetween(LocalDateTime, ...)` | 日期时间是否在闭区间内 |
| `isBefore(LocalDate, LocalDate)` | 日期是否严格早于目标 |
| `isAfter(LocalDate, LocalDate)` | 日期是否严格晚于目标 |
| `isLeapYear(int)` / `isLeapYear(LocalDate)` | 闰年判断 |
| `isWeekend(LocalDate)` | 是否周末 |
| `isWeekday(LocalDate)` | 是否工作日 |
| `isValidBirthday(LocalDate)` | 出生日期合理性校验 |
| `isValidRange(LocalDate, LocalDate)` | 日期区间合法性（start <= end） |

### BigDecimalUtils (`com.im.sky.util`)

| 方法 | 功能 |
|------|------|
| `isNull(BigDecimal)` | 是否为 null |
| `isZero(BigDecimal)` | 是否等于零（忽略精度） |
| `isPositive(BigDecimal)` | 是否正数 |
| `isNegative(BigDecimal)` | 是否负数 |
| `isNullOrZero(BigDecimal)` | 是否 null 或零 |
| `equals(BigDecimal, BigDecimal)` | 数值相等（忽略精度） |
| `gt` / `gte` / `lt` / `lte` | 大小比较（null 视为 ZERO） |
| `add` / `subtract` / `multiply` | 四则运算（null 视为 ZERO） |
| `divide(a, b)` | 除法（默认 scale=2, HALF_UP） |
| `divide(a, b, scale, mode)` | 除法（自定义精度） |
| `defaultIfNull(BigDecimal)` | null 转 ZERO |
| `stripTrailingZeros(BigDecimal)` | 去除尾零 |

## 11. 测试约定

### 命名规范
- 测试类：`被测类名 + Test`（如 `BigDecimalUtilsTest`）
- 测试方法：`test + 方法名 + _场景`（如 `testDivide_byZero`）

### 覆盖要求
每个 public 方法必须覆盖以下场景：
1. **正常用例** - 典型输入，验证正确输出
2. **null 入参** - 所有可 null 参数的 null 场景
3. **边界值** - 零值、极大值、极小值、空字符串
4. **异常场景** - 预期抛出的异常（使用 `@Test(expected = ...)`)

### 断言风格
- 主要使用 JUnit 4 Assert（`assertTrue`、`assertEquals`、`assertNull`）
- 数值比较使用 `compareTo() == 0`，不用 `equals()`
- 复杂断言可使用 AssertJ（`assertThat(...).isEqualTo(...)`）

### 测试原则
- 单元测试不依赖外部服务（数据库、网络、文件系统）
- 每个 @Test 方法只测一个行为点
- 使用 given-when-then 结构组织测试逻辑

## 12. 关键设计决策

| 决策 | 选择 | 原因 |
|------|------|------|
| 日期 API | java.time | 线程安全，替代 SimpleDateFormat |
| BigDecimal null 处理 | 视为 ZERO（宽容模式） | 减少业务 NPE 风险 |
| BigDecimal equals | compareTo（忽略精度） | 避免 1.0 != 1.00 的坑 |
| 除法默认精度 | scale=2, HALF_UP | 金额场景通用规则 |
| 工具类设计 | final + private constructor | 禁止实例化，纯静态方法 |
| 测试框架 | JUnit 4 | 项目已有依赖 |