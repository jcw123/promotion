package com.im.sky.javaparser;

import com.alibaba.fastjson.JSON;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author jiangchangwei
 * @since 2025/1/8
 */
public class JavaParserTest {

    @Test
    public void test() throws Exception{
        File sourceFile = new File("/Users/jiangchangwei/tmp/testjava/Test.java");
        CompilationUnit cu = StaticJavaParser.parse(sourceFile);

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {
            System.out.println("Class: " + classDeclaration.getNameAsString());
            List<String> fields = new ArrayList<>();

            // Find all field names
            classDeclaration.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
                fieldDeclaration.getVariables().forEach(variable -> {
                    fields.add(variable.getNameAsString());
                });
            });

            classDeclaration.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
                System.out.println("  Method: " + methodDeclaration.getNameAsString());

                methodDeclaration.findAll(Expression.class).forEach(expression -> {
                    // Process only specific types of expressions
                    if (expression instanceof MethodCallExpr || expression instanceof AssignExpr ||
                            expression instanceof UnaryExpr) {
                        // Check if any of the expression fields match the class level fields
                        List<String> matchedFields = fields.stream().filter(field -> {
                            return expression.getChildNodes().stream().anyMatch((node) -> node.toString().contains(field));
                        }).collect(Collectors.toList());
                        System.out.println("Field access: " + matchedFields);
                    }
                });
            });
        });
    }

    @Test
    public void test2() throws Exception{
        File sourceFile = new File("/Users/jiangchangwei/tmp/testjava/Test.java");
        CompilationUnit cu = StaticJavaParser.parse(sourceFile);
        cu.findAll(ReturnStmt.class).stream()
                .forEach(t -> {
                    System.out.println(t.getExpression().orElse(null));
                });
    }

    @Test
    public void test3() throws Exception{
        String rootDir = "/Users/jiangchangwei/jcwwork/jd_source/order/order-manage";
        File rootFile = new File(rootDir);
        Collection<File> files = FileUtils.listFiles(rootFile, new String[]{"java"}, true);
        for(File file : files){
            CompilationUnit unit = StaticJavaParser.parse(file);
            unit.findAll(ClassOrInterfaceDeclaration.class)
                    .stream()
                    .filter(t -> Objects.equals(t.getNameAsString(), "JimdbClusterConfigs"))
                    .forEach(classDeclaration -> {
                        System.out.println("Class: " + classDeclaration.getNameAsString());
                    });
        }
    }

    @Test
    public void test4() throws Exception{
        String rootDir = "/Users/jiangchangwei/jcwwork/jd_source/order/order-manage";
        File rootFile = new File(rootDir);
        Collection<File> files = FileUtils.listFiles(rootFile, new String[]{"java"}, true);
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        SymbolResolver symbolResolver = new JavaSymbolSolver(new JavaParserTypeSolver("/Users/jiangchangwei/jcwwork/jd_source/order/purchase-sdk-domain"));
        parserConfiguration.setSymbolResolver(symbolResolver);
        JavaParser javaParser = new JavaParser(parserConfiguration);
        for(File file : files){
            CompilationUnit unit = javaParser.parse(file).getResult().orElse(null);
            Set<String> imports = unit.findAll(ImportDeclaration.class).stream().filter(t -> t.getNameAsString().startsWith("com.jd.purchase.domain.old.bean"))
                    .map(t -> t.getNameAsString())
                    .collect(Collectors.toSet());
            if(imports.size() == 0) {
                continue;
            }
//            System.out.println("file:" + file.getName() + ", Imports: " + JSON.toJSONString(imports));
            List<MethodDeclaration> methodDeclarations = unit.findAll(MethodDeclaration.class);
            for(MethodDeclaration methodDeclaration : methodDeclarations){
                List<VariableDeclarationExpr> variableDeclarationExprs = methodDeclaration.findAll(VariableDeclarationExpr.class);
                for(VariableDeclarationExpr variableDeclarationExpr : variableDeclarationExprs){
                    String typeString = variableDeclarationExpr.getVariable(0).getTypeAsString();
                    String nameString = variableDeclarationExpr.getVariable(0).getNameAsString();
//                    System.out.println("type:" + typeString +  ", name:" + nameString);
                }
                List<MethodCallExpr> methodCallExprs = methodDeclaration.findAll(MethodCallExpr.class);
                for(MethodCallExpr methodCallExpr : methodCallExprs){
                    Expression expression = methodCallExpr.getScope().orElse(null);
                    try {
                        System.out.println("callName:" + expression.calculateResolvedType().toDescriptor());
                    }catch (Exception ignored){}
                }
            }
        }
    }

    @Test
    public void test5() throws Exception{
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        SymbolResolver symbolResolver = new JavaSymbolSolver(new JavaParserTypeSolver("/Users/jiangchangwei/jcwwork/jd_source/order/purchase-sdk-domain/src/main/java"));
        parserConfiguration.setSymbolResolver(symbolResolver);
        JavaParser javaParser = new JavaParser(parserConfiguration);
        String codeStr = "package test;\n" +
                "\n" +
                "import com.jd.purchase.domain.old.bean.Order;\n" +
                "\n" +
                "public class MyTest {\n" +
                "        public void test() {\n" +
                "                Order order = new Order();\n" +
                "                long orderId = order.getOrderId();\n" +
                "                System.out.println(orderId);\n" +
                "        }\n" +
                "}";
        File file = new File("/Users/jiangchangwei/tmp/testjava/Test2.java");
        CompilationUnit unit = javaParser.parse(codeStr).getResult().orElse(null);
        Set<String> imports = unit.findAll(ImportDeclaration.class).stream().filter(t -> t.getNameAsString().startsWith("com.jd.purchase.domain.old.bean"))
                .map(t -> t.getNameAsString())
                .collect(Collectors.toSet());
        if(imports.size() == 0) {
            return;
        }
            System.out.println("Imports: " + JSON.toJSONString(imports));
        List<MethodDeclaration> methodDeclarations = unit.findAll(MethodDeclaration.class);
        for(MethodDeclaration methodDeclaration : methodDeclarations){
            List<VariableDeclarationExpr> variableDeclarationExprs = methodDeclaration.findAll(VariableDeclarationExpr.class);
            for(VariableDeclarationExpr variableDeclarationExpr : variableDeclarationExprs){
                String typeString = variableDeclarationExpr.getVariable(0).getTypeAsString();
                String nameString = variableDeclarationExpr.getVariable(0).getNameAsString();
                    System.out.println("type:" + typeString +  ", name:" + nameString);
            }
            List<MethodCallExpr> methodCallExprs = methodDeclaration.findAll(MethodCallExpr.class);
            for(MethodCallExpr methodCallExpr : methodCallExprs){
                Expression expression = methodCallExpr.getScope().orElse(null);
                try {
                    System.out.println("callName:" + expression.calculateResolvedType().describe());
                }catch (Exception ignored){
                }
            }
        }
    }

    /**
     * 方法调用是否需要分析
     * @param methodCallExpr
     * @return
     */
    private boolean isNeedAnalyze(MethodCallExpr methodCallExpr) {
        String methodName = methodCallExpr.getNameAsString();
        if (methodName == null) {
            return false;
        }
        if(methodName.startsWith("set") || methodName.startsWith("add")) {
            return false;
        }
        return true;
    }

    @Test
    public void test6() throws Exception{
        JavaParser javaParser = getJavaParser("/Users/jiangchangwei/jcwwork/jd_source/order/purchase-sdk-domain/src/main/java");
        Set<String> srcClazzNames = obtainSourceClazzNames("/Users/jiangchangwei/jcwwork/jd_source/order/purchase-sdk-domain");
        System.out.println(JSON.toJSONString(srcClazzNames));
//        List<MethodCallExpr> methodCallExprs = obtainDstMethodCall("/Users/jiangchangwei/jcwwork/jd_source/order/ordercenter.base.info", javaParser, t -> t.startsWith("com.jd.purchase.domain.old.bean"));
//        Map<String, Item> itemMap = analyzeResult(srcClazzNames, methodCallExprs);
//        System.out.println(JSON.toJSONString(itemMap));
    }

    private void writeToExcel(Map<String, Item> itemMap, String path) {
        if(itemMap == null || itemMap.isEmpty()) {
            return;
        }
    }

    /**
     * 进行结果分析
     * @param srcClazzNames
     * @param methodCallExprs
     * @return
     * @throws Exception
     */
    private Map<String, Item> analyzeResult(Set<String> srcClazzNames, List<MethodCallExpr> methodCallExprs) throws Exception {
        Map<String, Item> itemMap = new HashMap<>();
        for(MethodCallExpr methodCallExpr : methodCallExprs){
            Expression expression = methodCallExpr.getScope().orElse(null);
            String describe = null;
            try {
                describe = expression.calculateResolvedType().describe();
            }catch (Exception ignored){}
            if(srcClazzNames.contains(describe)){
                Item item = itemMap.get(describe);
                if(item == null){
                    item = new Item();
                    itemMap.put(describe, item);
                }
                item.setClazzName(describe);
                item.addMethodName(methodCallExpr.getNameAsString());
            }
        }
        return itemMap;
    }

    @Data
    private static class Item {

        private String clazzName;

        private Set<String> methodNames = new HashSet<>();

        public void addMethodName(String methodName){
            methodNames.add(methodName);
        }
    }


    /**
     * 获取java工程解析器
     * @param domainPackagePath
     * @return
     */
    private JavaParser getJavaParser(String domainPackagePath){
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        SymbolResolver symbolResolver = new JavaSymbolSolver(new JavaParserTypeSolver(domainPackagePath));
        parserConfiguration.setSymbolResolver(symbolResolver);
        return new JavaParser(parserConfiguration);
    }

    /**
     * 获取目标工程所有的方法调用节点
     * @param dst
     * @param javaParser
     * @return
     * @throws Exception
     */
    private List<MethodCallExpr> obtainDstMethodCall(String dst, JavaParser javaParser, Predicate<String> predicate) throws Exception{
        Collection<File> files = FileUtils.listFiles(new File(dst), new String[]{"java"}, true);
        List<MethodCallExpr> methodCallExprs = new ArrayList<>();
        for(File file : files){
            CompilationUnit compilationUnit = javaParser.parse(file).getResult().orElse(null);
            boolean ok = compilationUnit.findAll(ImportDeclaration.class)
                    .stream()
                    .anyMatch(t -> predicate == null || predicate.test(t.getNameAsString()));
            if(ok) {
                List<MethodCallExpr> itemMethodCallExprs = compilationUnit.findAll(MethodCallExpr.class)
                        .stream()
                        .filter(this::isNeedAnalyze)
                        .collect(Collectors.toList());
                if(itemMethodCallExprs.size() > 0) {
                    methodCallExprs.addAll(itemMethodCallExprs);
                }
            }
        }
        return methodCallExprs;
    }

    /**
     * 获取所有的原路径类文件
     * @param srcPath
     * @return
     */
    private Set<String> obtainSourceClazzNames(String srcPath) throws Exception {
        Collection<File> files = FileUtils.listFiles(new File(srcPath), new String[]{"java"}, true);
        Set<String> sourceClazzNames = new HashSet<>();
        for(File file : files){
            CompilationUnit unit = StaticJavaParser.parse(file);
            unit.findAll(ClassOrInterfaceDeclaration.class)
                    .forEach(t -> {
                        String fullNames = t.getFullyQualifiedName().orElse(null);
                        if(fullNames == null) {
                            return;
                        }
                        if(fullNames.startsWith("com.jd.purchase.domain.old.bean")) {
                            sourceClazzNames.add(fullNames);
                        }
                    });
        }
        return sourceClazzNames;
    }

}
