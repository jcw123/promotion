package com.im.sky.javacore.classloader.t1;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-03 16:11
 **/
public class ExtCtx {

    public ExtCtx() {
        System.out.println("1:" + this.getClass().getClassLoader());
        ExtObj2 ext = new ExtObj2();
        System.out.println("2:" + ext.getClass().getClassLoader());
        EXT ext1 = new EXT();
        System.out.println(ext1.getClass().getClassLoader());
        System.out.println(ext.ext1);
        System.out.println(ext.ext1.getClass().getClassLoader());
    }
}
