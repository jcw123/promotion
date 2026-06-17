---
name: "SDD: Archive"
description: 归档已完成的变更
category: Workflow
tags: [sdd, workflow, archive, openspec]
---

# SDD Archive - 归档变更

归档已完成的变更，从活跃状态移至归档目录。

---

**输入**: 可选指定变更名称（如 `/sdd:archive add-auth`）。如果省略，提示用户选择。

**执行流程**:

1. **选择变更**
   - 运行 `openspec list --json` 获取可用变更
   - 只显示活跃变更（未归档的）
   - 让用户选择（不自动选择）

2. **检查工件完成状态**
   - `openspec status --change "<name>" --json`
   - 未完成工件：显示警告，确认继续

3. **检查任务完成状态**
   - 读取 tasks.md 统计未完成任务
   - 有未完成：显示警告，确认继续

4. **评估 delta spec 同步**
   - 如果 `openspec/changes/<name>/specs/` 有 delta specs
   - 显示同步摘要，提供同步选项

5. **执行归档**
   ```bash
   mkdir -p openspec/changes/archive
   mv openspec/changes/<name> openspec/changes/archive/YYYY-MM-DD-<name>
   ```

**完成输出**:
```
## 归档完成
**变更:** <name>
**归档至:** openspec/changes/archive/YYYY-MM-DD-<name>/
**Specs:** ✓ 已同步
所有工件完成。所有任务完成。
```

**护栏**:
- 始终提示变更选择
- 不阻塞在警告上
- 保留 .openspec.yaml
- 显示清晰的操作摘要