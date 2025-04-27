package com.im.sky.test.javaparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Modifier;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @author jiangchangwei
 * @since 2025/1/8
 */
public class ClassModifier {
    public static void main(String[] args) throws Exception {
        // Load the class file
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("HelloWorld");

        // Modify the class using Javaparser and Javassist
        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(new File("HelloWorld.java")));
        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
        for (MethodDeclaration method : methods) {
            if (method.getName().getIdentifier().equals("add")) {
                method.getBody().ifPresent(body -> {
                    Expression left = new IntegerLiteralExpr(10);
                    Expression right = new IntegerLiteralExpr(20);
                    BinaryExpr binaryExpr = new BinaryExpr(left, right, BinaryExpr.Operator.PLUS);
                    body.addStatement(binaryExpr);
                });
            }
        }
        ctClass.defrost();
        ctClass.setModifiers(Modifier.PUBLIC);
        ctClass.setInterfaces(new CtClass[] { pool.get("java.lang.Cloneable") });

        // Load the modified class into the JVM
        ctClass.writeFile();
        ctClass.toClass();

        // Use the modified class
        HelloWorld helloWorld = (HelloWorld) ctClass.toClass().newInstance();
        System.out.println(helloWorld.add());
    }

    class HelloWorld {
        public int add() {
            return 0;
        }
    }
}
