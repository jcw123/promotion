package com.im.sky.javacore.classloader.t1;

public class Test {

    public static void main(String[] args) throws Exception {
        JokeClassLoader jokeClassLoader = new JokeClassLoader();
        Class cls1 = jokeClassLoader.loadClass("People");
        Object people = cls1.newInstance();
        cls1.getMethod("say").invoke(people);

        JokeClassLoader jokeClassLoader1 = new JokeClassLoader();
        Class cls2 = jokeClassLoader1.loadClass("People");
        Object people2 = cls2.newInstance();
        cls2.getMethod("say").invoke(people2);
        System.out.println(cls1 == cls2);

    }
}
