---
name: "SDD: Apply"
description: 按任务清单逐步实现变更
category: Workflow
tags: [sdd, workflow, apply, implement]
---

# SDD Apply - 实现变更任务

从 `dev_logs/<change-name>/` 读取工件，按照 tasks.md 逐步执行代码变更。

---

**输入**: 可选指定变更名称（如 `/sdd-apply bigdecimal-utils`）。如果省略，从上下文推断或提示选择。

**执行流程**:

1. **选择变更**
   - 提供名称则使用
   - 扫描 `dev_logs/` 只有一个活跃变更则自动选择
   - 模糊时列出 `dev_logs/` 下目录让用户选择

2. **读取上下文**: `dev_logs/<name>/proposal.md`、`design.md`、`tasks.md`

3. **显示进度**: "N/M 任务完成"

4. **逐步实现任务**:
   - 显示正在处理的任务
   - 进行代码变更
   - 在 `dev_logs/<name>/tasks.md` 中标记完成: `- [ ]` → `- [x]`
   - 继续下一个任务

5. **暂停条件**:
   - 任务不清晰 → 询问澄清
   - 发现设计问题 → 建议更新 `dev_logs/<name>/design.md`
   - 遇到阻塞 → 报告并等待

**完成输出**:
```
## 实现完成
**变更:** <name>
**进度:** N/N 任务完成 ✓
所有任务完成！使用 `/sdd-archive` 归档此变更。
```

**护栏**:
- 持续处理直到完成或阻塞
- 开始前读取 `dev_logs/<name>/` 下上下文文件
- 任务模糊时暂停询问
- 保持代码变更最小化
- 完成每个任务后立即更新 tasks.md
- 遇到错误时暂停，不猜测