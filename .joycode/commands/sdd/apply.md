---
name: "SDD: Apply"
description: 按任务清单逐步实现变更
category: Workflow
tags: [sdd, workflow, apply, implement, openspec]
---

# SDD Apply - 实现变更任务

从 OpenSpec 变更中实现任务。按照 proposal/design/tasks 工件的规格逐步执行代码变更。

---

**输入**: 可选指定变更名称（如 `/sdd:apply add-auth`）。如果省略，从上下文推断或提示选择。

**执行流程**:

1. **选择变更**
   - 提供名称则使用
   - 只有一个活跃变更则自动选择
   - 模糊时运行 `openspec list --json` 让用户选择

2. **检查状态**: `openspec status --change "<name>" --json`

3. **获取实现指令**: `openspec instructions apply --change "<name>" --json`
   - 如果 `state: "blocked"`: 建议使用 `/sdd:propose`
   - 如果 `state: "all_done"`: 建议使用 `/sdd:archive`

4. **读取上下文文件**: 读取 `contextFiles` 中所有文件

5. **逐步实现任务**:
   - 显示正在处理的任务
   - 进行代码变更
   - 标记完成: `- [ ]` → `- [x]`
   - 继续下一个任务

6. **暂停条件**:
   - 任务不清晰 → 询问澄清
   - 发现设计问题 → 建议更新工件
   - 遇到阻塞 → 报告并等待

**完成输出**:
```
## 实现完成
**变更:** <name>
**进度:** N/N 任务完成 ✓
所有任务完成！使用 `/sdd:archive` 归档此变更。
```

**护栏**:
- 持续处理直到完成或阻塞
- 开始前读取上下文文件
- 任务模糊时暂停询问
- 保持代码变更最小化
- 完成每个任务后立即更新复选框
- 遇到错误时暂停，不猜测

**流式工作流**:
- 随时可调用
- 允许交替使用 `/sdd:explore` 思考问题
- 如果发现设计问题可以更新工件