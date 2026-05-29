#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Java 代码规范校验工具（极速版）
参考阿里、Google、百度、京东、字节等主流公司 Java 开发规范
全量校验，TopN 展示
优化策略：正则预编译 + 单次遍历 + 字符串缓存 + 跳过注释
"""

import argparse
import re
import sys
from dataclasses import dataclass
from typing import List
from enum import Enum


class Severity(Enum):
    ERROR = "错误"
    WARNING = "警告"
    INFO = "提示"


@dataclass
class LintIssue:
    category: str
    file: str
    line: int
    message: str
    reason: str
    suggestion: str
    severity: Severity = Severity.WARNING

    def to_report(self, rank: int) -> str:
        return f"【Top {rank}: {self.category} - {self.message.split(':')[0]}】\n位置：{self.file}:{self.line}\n问题：{self.message}\n原因：{self.reason}\n修复建议：{self.suggestion}\n"


class JavaLintChecker:
    """Java 代码规范检查器（极速版）"""

    PATTERNS = {
        'const_decl': re.compile(r'public\s+static\s+final\s+\w+'),
        'const_name': re.compile(r'public\s+static\s+final\s+\w+\s+(\w+)'),
        'const_upper': re.compile(r'^[A-Z][A-Z0-9_]*$'),
        'class_decl': re.compile(r'(?:public\s+)?(?:abstract\s+)?(?:final\s+)?class\s+(\w+)'),
        'class_name': re.compile(r'^[A-Z][a-zA-Z0-9]*$'),
        'method_decl': re.compile(r'\s*(?:public|private|protected)?\s*(?:static)?\s*\w+\s+(\w+)\s*\('),
        'method_name': re.compile(r'^[a-z][a-zA-Z0-9]*$'),
        'return_types': re.compile(r'\s*(?:public\s+)?(?:static\s+)?(?:void|int|boolean|String|long|double|float|char|byte|short|class)\s+'),
        'var_assign': re.compile(r'\b(\w+)\s*=\s*'),
        'for_loop': re.compile(r'\bfor\b'),
        'package_import_comment': re.compile(r'\s*(?:package\s+|import\s+|/\*|//|package\s+)'),
        'public_class': re.compile(r'\s*public\s+class'),
        'empty_method': re.compile(r'\)\s*\{\s*\}'),
        'catch_exception': re.compile(r'catch\s*\(\s*Exception\s+\w+\s*\)'),
        'catch_general': re.compile(r'catch\s*\([^)]+\)\s*\{'),
        'io_stream': re.compile(r'new\s+(?:FileInputStream|FileOutputStream|BufferedReader|BufferedWriter|InputStreamReader|OutputStreamReader)\s*\('),
        'collection_new': re.compile(r'new\s+(?:ArrayList|HashMap)\s*<>'),
        'collection_new_simple': re.compile(r'new\s+(?:ArrayList|HashMap)\s*\(\s*\)'),
        'foreach': re.compile(r'for\s*\(\s*\w+\s+\w+\s*:\s*\w+'),
        'public_method': re.compile(r'(?:public\s+)?(?:static\s+)?(?:void|int|boolean|String|long|double|float|char|byte|short|\w+)\s+\w+\s*\('),
        'javadoc': re.compile(r'/\*\*'),
        'todo': re.compile(r'TODO|FIXME'),
        'tab_char': re.compile(r'\t'),
        'synchronized_block': re.compile(r'synchronized\s+\w+\s*\('),
        'new_thread': re.compile(r'new\s+Thread\s*\('),
        'volatile_kw': re.compile(r'\bvolatile\b'),
        'instanceof_kw': re.compile(r'\binstanceof\b'),
        'magic_value': re.compile(r'[<>=!]\s*\d+'),
        'magic_extract': re.compile(r'([<>=!])\s*(\d+)'),
        'comment_start': re.compile(r'\s*/\*'),
        'comment_line': re.compile(r'\s*//'),
    }

    ALLOWED_SINGLE_CHARS = frozenset(('i', 'j', 'k', 'x', 'y', 'z'))
    ALLOWED_MAGIC_VALUES = frozenset(('0', '1', '-1'))
    MODIFY_KEYWORDS = frozenset(('remove(', 'add(', 'put('))

    def __init__(self, top_n: int = 10):
        self.top_n = top_n
        self.all_issues: List[LintIssue] = []

    def check_file(self, file_path: str) -> List[LintIssue]:
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                lines = f.readlines()
        except Exception as e:
            print(f"读取文件失败：{e}", file=sys.stderr)
            return []

        self._fast_check(lines, file_path)
        self.all_issues.sort(key=lambda x: (0 if x.severity == Severity.ERROR else 1 if x.severity == Severity.WARNING else 2, x.line))
        return self.all_issues[:self.top_n]

    def _add_issue(self, category: str, file: str, line: int, message: str,
                   reason: str, suggestion: str, severity: Severity = Severity.WARNING):
        self.all_issues.append(LintIssue(category, file, line, message, reason, suggestion, severity))

    def _fast_check(self, lines: List[str], file_path: str):
        """极速检查：单次遍历 + 跳过注释 + 字符串缓存"""
        patterns = self.PATTERNS
        in_class = False
        class_start = 0
        brace_count = 0
        in_comment = False

        # 缓存 stripped 结果
        stripped_cache = {}

        for line_num, line in enumerate(lines, 1):
            if line_num not in stripped_cache:
                stripped_cache[line_num] = line.strip()
            stripped = stripped_cache[line_num]

            # 跳过注释块
            if in_comment:
                if '*/' in stripped:
                    in_comment = False
                continue

            if patterns['comment_start'].search(stripped) and '*/' not in stripped:
                in_comment = True
                continue

            # 跳过纯注释行
            if patterns['comment_line'].match(stripped) or stripped.startswith('*'):
                continue

            # === 命名规范检查 ===
            if patterns['const_decl'].match(stripped):
                match = patterns['const_name'].search(stripped)
                if match:
                    name = match.group(1)
                    if not patterns['const_upper'].match(name):
                        self._add_issue("命名规范", file_path, line_num,
                                        f"常量 '{name}' 未使用全大写下划线分隔格式",
                                        "阿里规范强制规定：常量必须使用 UPPER_SNAKE_CASE 格式",
                                        f"将 '{name}' 改为 '{name.upper()}'", Severity.ERROR)

            class_match = patterns['class_decl'].match(stripped)
            if class_match:
                class_name = class_match.group(1)
                if not patterns['class_name'].match(class_name):
                    self._add_issue("命名规范", file_path, line_num,
                                    f"类名 '{class_name}' 不符合大驼峰命名规范",
                                    "阿里规范规定：类名使用 UpperCamelCase 风格",
                                    f"将 '{class_name}' 改为 PascalCase 格式", Severity.ERROR)
                in_class = True
                class_start = line_num

            method_match = patterns['method_decl'].match(stripped)
            if method_match and not patterns['return_types'].search(stripped):
                method_name = method_match.group(1)
                if not patterns['method_name'].match(method_name):
                    self._add_issue("命名规范", file_path, line_num,
                                    f"方法名 '{method_name}' 不符合小驼峰命名规范",
                                    "阿里规范规定：方法名使用 lowerCamelCase 风格",
                                    f"将 '{method_name}' 改为 camelCase 格式", Severity.WARNING)

            var_match = patterns['var_assign'].search(stripped)
            if var_match:
                var_name = var_match.group(1)
                if len(var_name) == 1 and var_name not in self.ALLOWED_SINGLE_CHARS:
                    if not patterns['for_loop'].search(stripped):
                        self._add_issue("命名规范", file_path, line_num,
                                        f"变量名 '{var_name}' 过于简短",
                                        "阿里规范建议：变量名应见名知意，避免单字符命名",
                                        "使用具有描述性的变量名", Severity.INFO)

            # === 类结构检查 ===
            if in_class:
                brace_count += stripped.count('{') - stripped.count('}')
                if brace_count == 0 and '{' in line:
                    in_class = False

            if line_num == 1 and not patterns['package_import_comment'].match(stripped):
                if patterns['public_class'].match(stripped):
                    self._add_issue("类结构", file_path, line_num,
                                    "类声明前缺少 package 声明",
                                    "阿里规范规定：所有类必须显式声明包名",
                                    "在文件顶部添加 package 声明", Severity.WARNING)

            # === 方法结构检查 ===
            if patterns['empty_method'].search(stripped):
                self._add_issue("方法设计", file_path, line_num,
                                "发现空方法体",
                                "空方法可能导致代码意图不清晰，Google 规范建议至少添加注释说明",
                                "添加 @Override 注释或说明此方法 intentionally empty 的注释", Severity.INFO)

            # === 异常处理检查 ===
            if patterns['catch_exception'].match(stripped):
                self._add_issue("异常处理", file_path, line_num,
                                "捕获了通用的 Exception 异常",
                                "阿里规范强制规定：避免捕获通用异常，应捕获具体异常类型",
                                "根据实际可能抛出的异常类型，捕获具体的异常类", Severity.ERROR)

            if patterns['catch_general'].match(stripped):
                if line_num < len(lines):
                    next_stripped = stripped_cache.get(line_num + 1, lines[line_num].strip())
                    if next_stripped == '}' or next_stripped == '':
                        self._add_issue("异常处理", file_path, line_num,
                                        "发现空 catch 块",
                                        "字节规范强制：禁止空 catch 块，异常必须被处理或记录",
                                        "在 catch 块中添加日志记录或异常处理逻辑", Severity.ERROR)
                    elif line_num + 2 < len(lines):
                        block = stripped_cache.get(line_num, '') + stripped_cache.get(line_num+1, '') + stripped_cache.get(line_num+2, '')
                        if 'printStackTrace' in block:
                            self._add_issue("异常处理", file_path, line_num,
                                            "使用 printStackTrace() 输出异常",
                                            "阿里规范规定：生产环境禁止使用 printStackTrace，应使用日志框架",
                                            "使用 logger.error() 等日志框架记录异常", Severity.WARNING)

            # === 资源管理检查 ===
            if patterns['io_stream'].search(stripped):
                has_try = False
                for i in range(max(0, line_num - 4), line_num - 1):
                    if 'try(' in stripped_cache.get(i + 1, lines[i].strip()):
                        has_try = True
                        break
                if not has_try:
                    self._add_issue("资源管理", file_path, line_num,
                                    "IO 流资源未使用 try-with-resources 语法",
                                    "阿里规范推荐：Java7+ 应使用 try-with-resources 自动关闭资源",
                                    "将资源声明放入 try 的括号中，实现自动关闭", Severity.WARNING)

            if 'finally' in stripped and line_num + 5 <= len(lines):
                has_close = False
                for i in range(line_num, min(line_num + 5, len(lines))):
                    if 'close()' in stripped_cache.get(i + 1, lines[i].strip()):
                        has_close = True
                        break
                if not has_close:
                    self._add_issue("资源管理", file_path, line_num,
                                    "finally 块中可能未关闭资源",
                                    "百度规范规定：IO 资源必须在 finally 块中关闭或使用 try-with-resources",
                                    "确保在 finally 块中调用 close() 方法", Severity.WARNING)

            # === 集合使用检查 ===
            if patterns['collection_new'].match(stripped) or patterns['collection_new_simple'].match(stripped):
                self._add_issue("集合使用", file_path, line_num,
                                "集合初始化未指定初始容量",
                                "阿里规范推荐：明确指定集合初始容量，避免频繁扩容",
                                "根据实际数据量指定初始容量，如 new ArrayList<>(16)", Severity.INFO)

            if patterns['foreach'].search(stripped) and line_num + 3 <= len(lines):
                block = ''
                for i in range(line_num, min(line_num + 3, len(lines))):
                    block += stripped_cache.get(i + 1, lines[i].strip())
                if any(kw in block for kw in self.MODIFY_KEYWORDS):
                    self._add_issue("集合使用", file_path, line_num,
                                    "在 foreach 循环中修改集合",
                                    "阿里规范强制：foreach 循环中禁止修改集合，会导致 ConcurrentModificationException",
                                    "使用 Iterator 的 remove 方法或 Java8+ 的 removeIf 方法", Severity.ERROR)

            # === 注释风格检查 ===
            if patterns['public_method'].match(stripped):
                prev_stripped = stripped_cache.get(line_num - 2, '') if line_num >= 2 else ''
                if not patterns['javadoc'].match(prev_stripped) and 'public' in stripped:
                    self._add_issue("注释规范", file_path, line_num,
                                    "公共方法缺少 Javadoc 注释",
                                    "Google 规范规定：公共 API 必须使用 Javadoc 注释",
                                    "添加 /** */ Javadoc 注释，说明方法功能、参数和返回值", Severity.INFO)

            if patterns['todo'].search(stripped):
                self._add_issue("代码质量", file_path, line_num,
                                f"发现 TODO/FIXME 注释：{stripped[:50]}",
                                "TODO 标记表示未完成的工作，应及时处理",
                                "完成 TODO 标记的功能或转换为正式任务", Severity.INFO)

            # === 代码格式检查 ===
            line_len = len(line.rstrip())
            if line_len > 120:
                self._add_issue("代码格式", file_path, line_num,
                                f"行长度超过 120 字符 ({line_len} 字符)",
                                "阿里规范规定：单行字符数限制不超过 120 个",
                                "将长行拆分为多行，使用换行和缩进提高可读性", Severity.WARNING)

            if '\t' in line:
                self._add_issue("代码格式", file_path, line_num,
                                "使用了 Tab 缩进",
                                "阿里规范规定：使用空格缩进，每个缩进级别使用 4 个空格",
                                "将 Tab 替换为 4 个空格", Severity.WARNING)

            # === 并发编程检查 ===
            if patterns['synchronized_block'].match(stripped):
                self._add_issue("并发编程", file_path, line_num,
                                "使用 synchronized 同步代码块",
                                "京东规范建议：优先使用 ReentrantLock 等更灵活的锁机制",
                                "考虑使用 ReentrantLock 或并发工具类", Severity.INFO)

            if patterns['new_thread'].match(stripped):
                self._add_issue("并发编程", file_path, line_num,
                                "直接创建 Thread 对象",
                                "阿里规范强制：线程池不允许使用 new Thread() 创建，应通过 ThreadPoolExecutor 创建",
                                "使用 ThreadPoolExecutor 或 Executors 创建线程池", Severity.ERROR)

            if patterns['volatile_kw'].search(stripped):
                self._add_issue("并发编程", file_path, line_num,
                                "使用 volatile 关键字",
                                "volatile 只能保证可见性不能保证原子性，需确认使用场景正确",
                                "确认是否需要原子性操作，必要时使用 Atomic 类或 Lock", Severity.INFO)

            # === OOP 设计检查 ===
            if patterns['instanceof_kw'].search(stripped):
                self._add_issue("OOP 设计", file_path, line_num,
                                "使用 instanceof 进行类型判断",
                                "字节规范建议：过多的 instanceof 可能违反开闭原则，考虑使用多态",
                                "考虑使用多态或策略模式替代类型判断", Severity.INFO)

            if patterns['magic_value'].search(stripped):
                match = patterns['magic_extract'].search(stripped)
                if match:
                    value = match.group(2)
                    if value not in self.ALLOWED_MAGIC_VALUES:
                        self._add_issue("代码质量", file_path, line_num,
                                        f"发现魔法值：{value}",
                                        "阿里规范强制：代码中不能出现魔法值，必须定义为常量",
                                        f"将值 {value} 提取为具有语义的常量", Severity.WARNING)

    def generate_report(self, top_issues: List[LintIssue]) -> str:
        if not top_issues:
            return "✅ 未发现代码规范问题"

        report = "=== Java 代码规范校验报告 ===\n\n"
        for rank, issue in enumerate(top_issues, 1):
            report += issue.to_report(rank) + "\n"

        report += f"\n总计发现 {len(self.all_issues)} 个问题，展示 Top {len(top_issues)}"
        return report


def main():
    parser = argparse.ArgumentParser(description='Java 代码规范校验工具（极速版）')
    parser.add_argument('--file', required=True, help='要检查的 Java 文件路径')
    parser.add_argument('--output', help='结果输出文件路径')
    parser.add_argument('--top', type=int, default=10, help='输出的问题数量，默认 10')

    args = parser.parse_args()

    checker = JavaLintChecker(top_n=args.top)
    top_issues = checker.check_file(args.file)
    report = checker.generate_report(top_issues)

    if args.output:
        with open(args.output, 'w', encoding='utf-8') as f:
            f.write(report)
        print(f"报告已保存到：{args.output}")
    else:
        print(report)


if __name__ == '__main__':
    main()
