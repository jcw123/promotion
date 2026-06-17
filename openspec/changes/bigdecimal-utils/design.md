# Design: BigDecimalUtils 工具类

## 1. 技术选型

- **Language / JDK**: Java 8+
- **核心 API**: `java.math.BigDecimal`、`java.math.RoundingMode`
- **测试框架**: JUnit 4（与项目 pom.xml 一致）
- **目标包路径**: `com.im.sky.util`
- **目标文件**:
  - `src/main/java/com/im/sky/util/BigDecimalUtils.java`
  - `src/test/java/com/im/sky/util/BigDecimalUtilsTest.java`

## 2. 类结构设计

```
public final class BigDecimalUtils {
    // ═══════════ 常量 ═══════════
    + {static} DEFAULT_SCALE : int = 2
    + {static} DEFAULT_ROUNDING_MODE : RoundingMode = HALF_UP

    // ═══════════ 构造器 ═══════════
    - BigDecimalUtils()  // 抛 UnsupportedOperationException

    // ═══════════ 判断方法 ═══════════
    + {static} isNull(BigDecimal) : boolean
    + {static} isZero(BigDecimal) : boolean
    + {static} isPositive(BigDecimal) : boolean
    + {static} isNegative(BigDecimal) : boolean
    + {static} isNullOrZero(BigDecimal) : boolean

    // ═══════════ 比较方法 ═══════════
    + {static} equals(BigDecimal, BigDecimal) : boolean
    + {static} gt(BigDecimal, BigDecimal) : boolean
    + {static} gte(BigDecimal, BigDecimal) : boolean
    + {static} lt(BigDecimal, BigDecimal) : boolean
    + {static} lte(BigDecimal, BigDecimal) : boolean

    // ═══════════ 算术运算 ═══════════
    + {static} add(BigDecimal, BigDecimal) : BigDecimal
    + {static} subtract(BigDecimal, BigDecimal) : BigDecimal
    + {static} multiply(BigDecimal, BigDecimal) : BigDecimal
    + {static} divide(BigDecimal, BigDecimal) : BigDecimal
    + {static} divide(BigDecimal, BigDecimal, int, RoundingMode) : BigDecimal

    // ═══════════ 辅助方法 ═══════════
    + {static} defaultIfNull(BigDecimal) : BigDecimal
    + {static} stripTrailingZeros(BigDecimal) : BigDecimal
}
```

## 3. 方法行为规范

### 3.1 判断方法

| 方法 | null 入参行为 | 说明 |
|------|-------------|------|
| `isNull(v)` | 返回 `true` | 判断是否为 null |
| `isZero(v)` | 返回 `false` | 使用 `compareTo(ZERO) == 0` |
| `isPositive(v)` | 返回 `false` | `compareTo(ZERO) > 0` |
| `isNegative(v)` | 返回 `false` | `compareTo(ZERO) < 0` |
| `isNullOrZero(v)` | 返回 `true` | `isNull || isZero` |

### 3.2 比较方法

| 方法 | null 入参行为 | 说明 |
|------|-------------|------|
| `equals(a, b)` | 双 null 返回 true；单 null 返回 false | 使用 `compareTo == 0`，忽略精度 |
| `gt(a, b)` | null 视为 ZERO | `a > b` |
| `gte(a, b)` | null 视为 ZERO | `a >= b` |
| `lt(a, b)` | null 视为 ZERO | `a < b` |
| `lte(a, b)` | null 视为 ZERO | `a <= b` |

### 3.3 算术运算

| 方法 | null 入参行为 | 说明 |
|------|-------------|------|
| `add(a, b)` | null 视为 ZERO | `a + b` |
| `subtract(a, b)` | null 视为 ZERO | `a - b` |
| `multiply(a, b)` | null 视为 ZERO | `a * b` |
| `divide(a, b)` | null 视为 ZERO；除数为 ZERO 抛 `ArithmeticException` | 默认 scale=2, HALF_UP |
| `divide(a, b, scale, mode)` | 同上 | 自定义精度和舍入模式 |

### 3.4 辅助方法

| 方法 | null 入参行为 | 说明 |
|------|-------------|------|
| `defaultIfNull(v)` | 返回 `BigDecimal.ZERO` | null 安全转换 |
| `stripTrailingZeros(v)` | 返回 `BigDecimal.ZERO` | 去除尾零 |

## 4. 设计决策

### D1: null 处理策略 - 宽容模式

**决策**: 算术和比较方法中，null 视为 `BigDecimal.ZERO`，而非抛 NPE。

**原因**: 业务场景中金额字段可能未初始化（数据库 null），宽容模式减少业务侧 null 检查负担，降低 NPE 风险。

### D2: equals 使用 compareTo 而非 BigDecimal.equals

**决策**: `BigDecimalUtils.equals(a, b)` 使用 `a.compareTo(b) == 0`。

**原因**: `BigDecimal.equals` 比较值和精度（`1.0 != 1.00`），在金额比较场景中几乎总是 Bug 根源。`compareTo` 只比较数学值。

### D3: 除法默认精度

**决策**: 默认 `scale = 2`，`RoundingMode.HALF_UP`。

**原因**: 金额场景通常保留两位小数，四舍五入是最常见的业务舍入规则。同时提供重载方法支持自定义。

### D4: 除数为零处理

**决策**: 除数为零时抛出 `ArithmeticException`，不静默返回。

**原因**: 除以零是逻辑错误，应该让开发人员感知并修复，而非掩盖问题。

## 5. 线程安全

- 所有方法为静态纯函数，无共享可变状态
- `BigDecimal` 本身是不可变类
- 天然线程安全，无需同步

## 6. 性能考量

- 避免频繁创建 `BigDecimal.ZERO`：使用 `BigDecimal.ZERO` 常量
- 方法内无循环或递归，时间复杂度 O(1)
- 无内存泄漏风险