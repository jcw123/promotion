---
name: "SDD: Propose"
description: 创建新的规格驱动开发提案 - 生成 proposal/design/tasks 工件
category: Workflow
tags: [sdd, workflow, propose, openspec]
---

# SDD Propose - 创建变更提案

创建一个新的变更，一步生成所有工件：
- proposal.md（做什么 & 为什么）
- design.md（怎么做）
- tasks.md（实现步骤）

准备好实现时，运行 `/sdd:apply`

---

**输入**: `/sdd:propose` 后的参数是变更名称（kebab-case），或者对想构建内容的描述。

**执行流程**:

1. 如果没有输入，询问用户想要构建什么
2. 从描述推导 kebab-case 名称
3. 创建变更目录: `openspec new change "<name>"`
4. 获取构建顺序: `openspec status --change "<name>" --json`
5. 按依赖顺序创建工件直到实现就绪
6. 显示最终状态

**工件创建准则**:
- 遵循 `openspec instructions` 中每种工件类型的指导
- 读取依赖工件作为上下文
- 使用 `template` 作为输出文件结构
- `context` 和 `rules` 是约束条件，不是文件内容

**护栏**:
- 创建实现所需的所有工件
- 始终读取依赖工件
- 上下文不清时询问用户
- 同名变更已存在时询问继续还是新建

**完成后**:
- 显示已创建工件列表
- 提示运行 `/sdd:apply` 开始实现