package com.im.sky.pattern.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 6:11
 * @Version 1.0
 *
 * 访问者模式：行为型模式
 *
 * 基本构件：
 * （1）抽象访问者：声明访问者可以访问哪些元素
 * （2）具体访问者：实现抽象访问者中声明的方法
 * （3）抽象元素类：声明接收哪一类访问者访问；同时还包含自己的一些业务逻辑
 * （4）具体元素类：实现抽象元素类所声明的accept方法
 * （5）结构对象：一个元素的容器
 *
 * 适用场景：防止一些不相干的操作干扰元素本身，可以将这些不相干的操作提到访问者中。
 * 如果一个对象结构非常复杂，同时结构不易变化，但需要经常定义基于此结构上的新操作，则
 * 非常适用于访问者模式。比如：复杂的集合对象、XML文档解析、编译器的设计等。
 *
 * 注：核心是结构不易变，而定义在上的操作易变；
 * 行为是在访问者中实现的，元素中主要是保存结构信息。
 *
 * 比如一份静态数据，相当于结构不易变的，但是呢，经常需要变换指标来衡量一个标准，则可以
 * 采用访问者模式
 *
 *
 */
public class VisitorBase {

    public static void main(String[] args) {
        Element element = new Student("s1", 91, 3);
        Element element1 = new Student("s2", 98, 3);
        Element element2 = new Teacher("t1", 101, 10);
        Element element3 = new Teacher("t2", 103, 20);
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.addElement(element)
                .addElement(element1).addElement(element2).addElement(element3);
        ReseacrcherVisitor visitor = new ReseacrcherVisitor();
        objectStructure.accept(visitor);
        System.out.println("select another");
        ScoreVisitor visitor1 = new ScoreVisitor();
        objectStructure.accept(visitor1);
    }

    static class ObjectStructure {
        List<Element> elements = new ArrayList<>();

        public void accept(IVisitor visitor) {
            for(Element element : elements) {
                element.accept(visitor);
            }
        }

        public ObjectStructure addElement(Element element) {
            elements.add(element);
            return this;
        }

        public ObjectStructure removeElement(Element element) {
            elements.remove(element);
            return this;
        }
    }

    static interface IVisitor {
        void visit(Teacher teacher);

        void visit(Student student);
    }

    static class ReseacrcherVisitor implements IVisitor {
        @Override
        public void visit(Teacher teacher) {
            if(teacher.paperCount > 6) {
                System.out.println("老师论文数达到了6篇，入选优秀工作者");
            }
        }

        @Override
        public void visit(Student student) {
            if(student.paperCount > 2) {
                System.out.println("学生论文数达到了2篇，入选优秀工作者");
            }
        }
    }

    static class ScoreVisitor implements IVisitor {
        @Override
        public void visit(Teacher teacher) {
            if(teacher.score > 100) {
                System.out.println("老师分数达到了100，入选优秀工作者");
            }
        }

        @Override
        public void visit(Student student) {
            if(student.score > 90) {
                System.out.println("学生分数达到了90，入选优秀工作者");
            }
        }
    }

    static interface Element {
        void accept(IVisitor visitor);
    }

    static class Teacher implements Element {

        private String name;

        private int score;

        private int paperCount;

        public Teacher(String name, int score, int paperCount) {
            this.name = name;
            this.score = score;
            this.paperCount = paperCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getPaperCount() {
            return paperCount;
        }

        public void setPaperCount(int paperCount) {
            this.paperCount = paperCount;
        }

        @Override
        public void accept(IVisitor visitor) {
            visitor.visit(this);
        }
    }

    static class Student implements Element {

        private String name;

        private int score;

        private int paperCount;

        public Student(String name, int score, int paperCount) {
            this.name = name;
            this.score = score;
            this.paperCount = paperCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getPaperCount() {
            return paperCount;
        }

        public void setPaperCount(int paperCount) {
            this.paperCount = paperCount;
        }

        @Override
        public void accept(IVisitor visitor) {
            visitor.visit(this);
        }
    }
}
