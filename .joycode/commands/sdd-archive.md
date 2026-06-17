---
name: "SDD: Archive"
description: 归档已完成的变更
category: Workflow
tags: [sdd, workflow, archive]
---

# SDD Archive - 归档变更

归档已完成的变更，从 `dev_logs/` 移至 `dev_logs/archive/`。

---

**输入**: 可选指定变更名称（如 `/sdd-archive bigdecimal-utils`）。如果省略，提示用户选择。

**执行流程**:

1. **选择变更**
   - 扫描 `dev_logs/` 下目录（排除 `archive/`）
   - 列出活跃变更让用户选择

2. **检查任务完成状态**
   - 读取 `dev_logs/<name>/tasks.md` 统计未完成任务
   - 有未完成：显示警告，确认继续

3. **执行归档**
   ```
   mkdir -p dev_logs/archive
   mv dev_logs/<name> dev_logs/archive/YYYY-MM-DD-<name>
   ```

**完成输出**:
```
## 归档完成
**变更:** <name>
**归档至:** dev_logs/archive/YYYY-MM-DD-<name>/
所有任务完成。
```

**护栏**:
- 始终提示变更选择
- 不阻塞在警告上
- 保留所有工件文件
- 显示清晰的操作摘要