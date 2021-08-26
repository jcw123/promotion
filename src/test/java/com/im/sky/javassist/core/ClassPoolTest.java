package com.im.sky.javassist.core;

import com.im.sky.javassist.classloader.DefaultClassloader;
import javassist.*;
import javassist.bytecode.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class ClassPoolTest {

    @Test
    public void addMethodTest() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.makeClass("Joy");

        CtMethod m = CtNewMethod.make("public void say() {System.out.println(\"hello, world\");}", ctClass);

        ctClass.addMethod(m);

        Class<?> clz = ctClass.toClass();

        Object o = clz.newInstance();

        Method method = clz.getDeclaredMethod("say");

        method.invoke(o);
    }

    @Test
    public void insertSourceTextTest() throws Exception {
        DefaultClassloader classloader = new DefaultClassloader(new String[]{"/Users/jiangchangwei/tmp/classfile"});
        ClassPool pool = new ClassPool();
        pool.appendClassPath(new LoaderClassPath(classloader));
        CtClass ctClass = pool.get("Test");
        CtMethod ctMethod = ctClass.getDeclaredMethod("test");
        ctMethod.instrument(new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if(m.getMethodName().equalsIgnoreCase("println")) {
                    m.replace("System.out.println(\"msg:\" + $1);");
                }
            }
        });
        ctClass.writeFile();
        Class<?> clz = ctClass.toClass();
        clz.getDeclaredMethod("test", String.class).invoke(clz.newInstance(), "hello");
    }

    @Test
    public void createClassTest() throws Exception {
        ClassFile cf = new ClassFile(false, "test.M", null);
        cf.setInterfaces(new String[]{"java.lang.Cloneable"});
        FieldInfo f = new FieldInfo(cf.getConstPool(), "width", "I");
        f.setAccessFlags(AccessFlag.PUBLIC);
        cf.addField(f);

        cf.write(new DataOutputStream(new FileOutputStream("M.class")));

        DefaultClassloader classloader = new DefaultClassloader(new String[]{"/Users/jiangchangwei/tmp/classfile"});
        DataInputStream dataInputStream = new DataInputStream(Objects.requireNonNull(classloader.getResourceAsStream("Test.class")));
        ClassFile classFile = new ClassFile(dataInputStream);

        MethodInfo methodInfo = classFile.getMethod("test");
        CodeAttribute ca = methodInfo.getCodeAttribute();
        CodeIterator iterator = ca.iterator();
        while(iterator.hasNext()) {
            int index = iterator.next();
            int op = iterator.byteAt(index);
            System.out.println(Mnemonic.OPCODE[op]);
        }
    }
}
