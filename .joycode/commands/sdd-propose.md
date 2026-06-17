---
name: "SDD: Propose"
description: 创建新的规格驱动开发提案 - 生成 proposal/design/tasks 工件
category: Workflow
tags: [sdd, workflow, propose]
---

# SDD Propose - 创建变更提案

创建一个新的变更，一步生成所有工件：
- proposal.md（做什么 & 为什么）
- design.md（怎么做）
- tasks.md（实现步骤）

工件存放路径：`dev_logs/<change-name>/`

准备好实现时，运行 `/sdd-apply`

---

**输入**: `/sdd-propose` 后的参数是变更名称（kebab-case），或者对想构建内容的描述。

**执行流程**:

1. 如果没有输入，询问用户想要构建什么
2. 从描述推导 kebab-case 名称
3. 创建 `dev_logs/<name>/` 目录
4. 依次创建 proposal.md → design.md → tasks.md
5. 显示最终状态

**工件创建准则**:
- proposal: 概述、目标、非目标、技术约束、影响范围
- design: 技术选型、类结构、行为规范、设计决策
- tasks: 按依赖顺序的实现步骤，`- [ ]` 标记

**护栏**:
- 创建 proposal → design → tasks 三个完整工件
- 创建 design 前先读取 proposal
- 创建 tasks 前先读取 proposal 和 design
- 上下文不清时询问用户
- 目录已存在时询问继续还是覆盖

**完成后**:
- 显示已创建工件列表
- 提示运行 `/sdd-apply` 开始实现