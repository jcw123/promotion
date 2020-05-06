package com.im.sky.javacore.reference;

/**
 * @author jiangchangwei
 * @date 2020-4-7 下午 4:43
 **/
public class People {

    public static void main(String[] args) {
        String s = "10000000100000000000000002001100031030100000000000600000000001010000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000200030000001000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        char[] sArray = s.toCharArray();
        sArray[142] = '1';
        System.out.println(new String(sArray));
    }

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("People finalize");
    }
}
