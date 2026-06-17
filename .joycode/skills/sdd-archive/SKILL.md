---
name: sdd-archive
description: SDD(Spec Driven Development) 归档技能 - 归档已完成的变更。当用户想在实现完成后归档变更时使用。
---

# SDD Archive - 规格驱动开发归档

归档已完成的变更，将其从 `dev_logs/` 活跃目录移至归档子目录。

## 使用场景

- 用户完成了所有任务，想要归档变更
- 用户想要清理已完成的变更
- 用户输入 `sdd-archive` 或提到"归档变更"、"完成变更"

## 目录结构

```
dev_logs/
  ├── <active-change>/          ← 活跃变更
  │   ├── proposal.md
  │   ├── design.md
  │   └── tasks.md
  └── archive/                  ← 归档目录
      └── YYYY-MM-DD-<name>/
```

## 工作流程

### 输入

可选指定变更名称。如果省略，提示选择。

### 步骤

1. **选择变更**

   扫描 `dev_logs/` 下的目录（排除 `archive/`），列出活跃变更让用户选择。

   **重要**: 不要猜测或自动选择。始终让用户确认。

2. **检查任务完成状态**

   读取 `dev_logs/<name>/tasks.md`，统计 `- [ ]`（未完成）vs `- [x]`（已完成）。

   **如果有未完成任务：**
   - 显示警告展示未完成任务数量
   - 确认用户是否仍要归档
   - 用户确认后继续

3. **执行归档**

   ```bash
   mkdir -p dev_logs/archive
   mv dev_logs/<name> dev_logs/archive/YYYY-MM-DD-<name>
   ```

   **检查目标是否已存在：**
   - 如果是：提示重命名或使用不同日期
   - 如果否：执行移动

4. **显示摘要**

   ```
   ## 归档完成

   **变更:** <change-name>
   **归档至:** dev_logs/archive/YYYY-MM-DD-<name>/

   所有任务完成。
   ```

## 护栏规则

- 始终提示变更选择
- 不阻塞在警告上 - 只通知并确认
- 保留所有工件文件（随目录移动）
- 显示清晰的操作摘要

## 归档后保鲜：更新 ai-context

归档完成后，检查本次变更是否涉及以下内容，如涉及则更新对应上下文文件：

| 变更内容 | 需要更新的文件 | 更新什么 |
|---------|--------------|---------|
| 新增/修改工具类 | `ai-context/project-context/index.md` 第 10 节 | 工具类能力索引 |
| 新增技术领域模块 | `ai-context/biz-context/index.md` 对应章节 | 已有实现清单 |
| 新增设计决策 | `ai-context/project-context/index.md` 第 12 节 | 决策表 |
| 新增编码约定 | `ai-context/project-context/index.md` 第 9 节 | 禁止事项 |

**执行方式：**
1. 读取本次归档的 `proposal.md` 判断变更类型
2. 对照上表确定是否需要更新
3. 如果需要，追加/修改对应章节
4. 更新文档头部的 `last_updated` 日期
5. 向用户展示更新了什么

**注意**：保鲜是建议性步骤，不阻塞归档操作本身。