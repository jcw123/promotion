package com.im.sky.test.javaparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangchangwei
 * @since 2025/1/8
 */
public class TestClassGenerator {

    public static void main(String[] args) {
        String filePath = "/Users/jiangchangwei/tmp/testjava/Test.java";
        CompilationUnit cu = null;
        try {
            cu = StaticJavaParser.parse(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ClassVisitor classVisitor = new ClassVisitor();
        classVisitor.visit(cu, null);

        String className = classVisitor.getClassName();
        List<String> methodNames = classVisitor.getMethodNames();

        StringBuilder sb = new StringBuilder();
        sb.append("import org.junit.Test;\n");
        sb.append("import static org.junit.Assert.*;\n");
        sb.append("\n");
        sb.append("public class " + className + "Test {\n");
        for (String methodName : methodNames) {
            sb.append("    @Test\n");
            sb.append("    public void test" + methodName + "() {\n");
            sb.append("        // TODO: write test cases\n");
            sb.append("    }\n");
        }
        sb.append("}\n");

        System.out.println(sb.toString());
    }

    private static class ClassVisitor extends VoidVisitorAdapter<Void> {
        private String className;
        private List<String> methodNames;

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            className = n.getNameAsString();
            methodNames = n.getMethods().stream()
                    .map(MethodDeclaration::getNameAsString)
                    .collect(Collectors.toList());
        }

        public String getClassName() {
            return className;
        }

        public List<String> getMethodNames() {
            return methodNames;
        }
    }

}

