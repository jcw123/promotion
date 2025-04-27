package com.im.sky.javassist;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangchangwei
 * @since 2025/1/8
 */
public class MethodCallAnalyzer extends VoidVisitorAdapter<Void> {
    private final Map<Integer, List<String>> methodCallsByLine = new HashMap<>();

    @Override
    public void visit(MethodCallExpr methodCall, Void arg) {
        super.visit(methodCall, arg);
        System.out.println("Method Name: " + methodCall.getNameAsString());
    }

    private String getQualifiedMethodName(MethodCallExpr methodCall) {
        StringBuilder qualifiedMethodName = new StringBuilder();
        Node node = methodCall;
        while (!(node instanceof TypeDeclaration || node instanceof ObjectCreationExpr)) {
            if (node instanceof MethodCallExpr) {
                qualifiedMethodName.insert(0, ((MethodCallExpr) node).getNameAsString() + ".");
            } else if (node instanceof FieldAccessExpr) {
                qualifiedMethodName.insert(0, ((FieldAccessExpr) node).getNameAsString() + ".");
            }
        }
        if (node instanceof TypeDeclaration) {
            qualifiedMethodName.insert(0, ((TypeDeclaration) node).getNameAsString() + ".");
        } else if (node instanceof ObjectCreationExpr) {
            qualifiedMethodName.insert(0, ((ObjectCreationExpr) node).getTypeAsString() + ".");
        }
        qualifiedMethodName.append(methodCall.getNameAsString());
        return qualifiedMethodName.toString();
    }

    public static void main(String[] args) throws IOException {
        CompilationUnit cu = StaticJavaParser.parse(new File("/Users/jiangchangwei/tmp/testjava/Test.java"));
        MethodCallAnalyzer methodVisitor = new MethodCallAnalyzer();
        methodVisitor.visit(cu, null);
    }
}
