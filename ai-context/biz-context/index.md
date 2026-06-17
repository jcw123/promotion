# Business Context - 学习领域知识图谱

> **last_updated**: 2026-06-17
> **maintainer**: AI auto-refresh on `/sdd-archive`

## 1. 项目定位

本项目是**个人技术深度学习与实践平台**，覆盖计算机科学核心知识领域。目标是通过"手写实现"的方式深度掌握原理，而非仅停留在 API 使用层面。

## 2. 知识领域全景

```
┌─────────────────────────────────────────────────────────────┐
│                    技术学习知识图谱                             │
├──────────────┬──────────────┬──────────────┬────────────────┤
│  计算机基础   │   Java 核心   │  中间件/框架  │   工程实践     │
├──────────────┼──────────────┼──────────────┼────────────────┤
│ • 算法       │ • JUC 并发    │ • Netty      │ • 设计模式     │
│ • 数据结构   │ • ClassLoader │ • Spring     │ • DDD 建模     │
│              │ • 动态代理    │ • MyBatis    │ • 工具类沉淀   │
│              │ • 函数式编程  │ • Redis      │ • 规则引擎     │
│              │ • 原子操作    │ • ZooKeeper  │ • 响应式编程   │
│              │ • I/O 模型    │ • Hive       │ • RPC 框架     │
└──────────────┴──────────────┴──────────────┴────────────────┘
```

## 3. 各领域详细说明

### 3.1 算法 (`algorithms/`)

**学习目标**: 掌握常见算法思想，提升编码能力和竞赛水平。

| 子领域 | 内容 | 覆盖范围 |
|--------|------|---------|
| LeetCode | 各难度题解 | 位运算、动态规划、贪心、DFS/BFS |
| Codeforces | 竞赛题解 (cc/) | 构造题、数学题、图论题 |
| 背包问题 | knapsack/ | 01 背包、完全背包、多重背包 |
| 数论 | oiwiki/math/ | 模逆元 |
| 模板 | template/ | 并查集 |

**编码特点**: 每个题解为独立文件，类名含题号，侧重解题思路和复杂度分析。

### 3.2 数据结构 (`datastructure/`)

**学习目标**: 手写经典数据结构，理解底层原理。

| 子领域 | 实现内容 |
|--------|---------|
| 排序 (sort/) | 直接插入、折半插入、链表插入、Shell、冒泡、快排、选择、堆排、归并、基数 |
| 树 (tree/) | 前/中/后序遍历、线索二叉树、Huffman 编码、求最大深度 |
| 图 (map/) | 邻接表/矩阵/十字链表/多重表建图、DFS、BFS、Dijkstra、Floyd、Prim、Kruskal、拓扑排序、关键路径 |
| 查找 (search/) | BST、AVL 树、平衡二叉树、哈希表构造 |
| 队列 (queue/) | 跳表(SkipList)、多级优先队列、迷宫求解 |
| 栈 (stack/) | 进转换、表达式计算、汉诺塔、递归转非递归 |
| 串 (string/) | KMP、BM、Sunday 字符串匹配 |
| 线性表 (sequencelist/) | 静态链表、约瑟夫问题 |

**编码特点**: 教科书级实现，注重算法正确性，通常含 main 方法自测。

### 3.3 Java 并发 (`lock/`, `javacore/`)

**学习目标**: 深入 JUC 源码，掌握并发编程核心。

| 子领域 | 内容 |
|--------|------|
| AQS 体系 | 自定义 CountDownLatch、CyclicBarrier、NonReentrantLock、LimitLock |
| 锁机制 | 自旋锁、FIFO 互斥锁、ReentrantLock/ReadWriteLock 使用 |
| 同步工具 | Semaphore、LockSupport、Unsafe |
| 线程池 | ThreadPoolExecutor 使用与测试 |
| 分布式锁 | 基于 ZooKeeper 的分布式锁实现 |
| 生产者/消费者 | 多种实现方式对比 |
| 原子操作 | AtomicGenerator（原子自增 ID） |
| ClassLoader | 自定义类加载器、双亲委派打破、类隔离 |

**编码特点**: 注重源码级理解，通常有对照 JDK 源码的简化实现。

### 3.4 I/O 模型 (`io/`)

**学习目标**: 从 BIO 到 NIO 到 Reactor，理解网络 I/O 演进。

| 模型 | 实现 |
|------|------|
| BIO 单线程 | `single/thread/` |
| BIO 多线程 | `multi/thread/` |
| NIO | `nio/self/`（多版本迭代） |
| Reactor | `reactor/demo1/` |

**演进路线**: BIO → 多线程 BIO → NIO → Reactor → Netty

### 3.5 Netty (`netty/`)

**学习目标**: 掌握 Netty 架构、编解码、协议设计、RPC 框架搭建。

| 子领域 | 内容 |
|--------|------|
| 基础入门 | Discard、Echo、Time、SimpleChat |
| 编解码 | 自定义协议编解码器（Java序列化、JSON、Hessian、Protobuf、MsgPack） |
| 协议设计 | UQP 自定义协议、消息头/消体分离 |
| RPC 框架 | test2/ - 完整 RPC 框架雏形（Transport → Protocol → Codec → Filter → Invoker） |
| 压缩 | 压缩类型枚举设计 |

**RPC 框架分层**:
```
Client → Transport → Codec → Protocol → Network → Protocol → Codec → Filter → Invoker → Provider
```

### 3.6 Spring (`spring/`)

**学习目标**: 理解 Spring IoC/AOP 核心机制。

| 内容 | 说明 |
|------|------|
| Bean 扫描 | 自定义注解 @BeanScan、注册 BeanDefinition |
| AOP | MethodBeforeAdvice、自定义切面 |
| BeanDefinitionRegistryPostProcessor | 动态注册 Bean |
| Bootstrap | IoC 容器初始化流程 |

### 3.7 设计模式 (`pattern/`)

**学习目标**: 手写 23 种 GoF 设计模式。

| 分类 | 已实现模式 |
|------|-----------|
| 创建型 | Singleton、Factory、Builder、Prototype |
| 结构型 | Adapter、Bridge、Composite、Decorator、Facade、Flyweight、Proxy |
| 行为型 | Command、Iterator、Mediator、Memento、Observer、State、Strategy、Template、Visitor、Interpreter、Responsibility(Chain) |

**覆盖率**: 23/23，全部实现。

### 3.8 工具类 (`util/`)

**学习目标**: 沉淀高质量、可复用的工具方法。

| 工具类 | 职责 |
|--------|------|
| DateValidatorUtils | 日期校验（格式、范围、闰年、周末、生日） |
| BigDecimalUtils | BigDecimal 判断、比较、四则运算（null 安全） |

**扩展方向**: 字符串处理、集合操作、反射工具、JSON 处理等。

### 3.9 其他领域

| 领域 | 包路径 | 内容 |
|------|--------|------|
| DDD | `ddd/` | 领域建模示例（活动聚合根） |
| 函数式编程 | `functional/` | Stream、自定义 Spliterator、BiFunction |
| ID 生成器 | `generator/` | 雪花算法、UUID、数据库自增 |
| Guava | `guava/` | 限流器(RateLimiter)、Objects 工具 |
| Hive | `hive/` | 自定义 UDF |
| 响应式 | `reactor/` | Project Reactor 使用 |
| 规则引擎 | 依赖 Easy-Rules | 声明式规则处理 |

## 4. 学习深度分级

| 等级 | 标准 | 对应模块 |
|------|------|---------|
| L1 - API 使用 | 会调用、能运行 | guava/, hive/, functional/ |
| L2 - 原理理解 | 知道为什么这样设计 | pattern/, datastructure/, algorithms/ |
| L3 - 源码级 | 能读懂并简化复现 | lock/juc/, spring/, io/ |
| L4 - 框架搭建 | 能从零构建完整系统 | netty/test2(RPC), generator/ |

## 5. AI 协作场景

基于以上领域知识，AI 在本项目中典型的协作场景包括：

| 场景 | 示例 |
|------|------|
| 新增工具类 | "实现一个 StringUtils" |
| 算法题解 | "用 Java 解 LeetCode 第 xxx 题" |
| 数据结构实现 | "手写一个红黑树" |
| 设计模式应用 | "用观察者模式重构事件通知" |
| 中间件学习 | "实现一个简单的消息队列" |
| 源码分析 | "分析 AQS 的 acquire 流程" |
| 框架探索 | "在 RPC 框架中加入服务注册发现" |

## 6. 知识演进记录

| 日期 | 变更 | 领域 |
|------|------|------|
| 2026-06-17 | 新增 BigDecimalUtils | 工具类 |
| 2026-06-17 | 新增 DateValidatorUtils | 工具类 |
| - | 23 种设计模式全部实现 | 设计模式 |
| - | RPC 框架雏形完成 | Netty/中间件 |
| - | 10+ 种排序算法实现 | 数据结构 |