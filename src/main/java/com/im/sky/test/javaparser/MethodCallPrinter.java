package com.im.sky.test.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;

/**
 * @author jiangchangwei
 * @since 2025/1/9
 */
public class MethodCallPrinter extends VoidVisitorAdapter< Void > {
    @Override
    public void visit(MethodCallExpr n, Void arg) {
        System.out.println("Method Call: " + n.getNameAsString());
        System.out.println("Caller: " + n.getScope().orElse(null));
        n.getScope().ifPresent(t -> System.out.println(t.calculateResolvedType().toDescriptor()));
    }

    public static void main(String[] args) throws Exception {
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        SymbolResolver symbolResolver = new JavaSymbolSolver(new JavaParserTypeSolver("/Users/jiangchangwei/jcwwork/jd_source/order/purchase-sdk-domain"));
        String javaSource = "package test; public class MyClass { public void myMethod() { this.anotherMethod(); } public void anotherMethod() { } }";
        parserConfiguration.setSymbolResolver(symbolResolver);
        JavaParser javaParser = new JavaParser(parserConfiguration);
        CompilationUnit cu = javaParser.parse(javaSource).getResult().orElse(null);
        MethodCallPrinter printer = new MethodCallPrinter();
        printer.visit(cu, null);
    }
}