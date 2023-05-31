package com.im.sky.algorithms.cc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-14 00:44
 * https://codeforces.com/problemset/problem/1542/B
 **/
public class CC_1542_B {

    static FastScanner in = new FastScanner();
    static FastWriter out = new FastWriter();

    public static void main(String[] args) {
        long m = in.nextLong();
        for(int i = 0; i < m; i++) {
            long n= in.nextLong();
            long a = in.nextLong();
            long b = in.nextLong();
            process(a, b, n);
        }
        out.close();
    }

    private static void process(long a, long b, long n) {
        if(n==1||b==1||(n-1)%b==0){
            out.println("Yes");
            return;
        }else if(a==1||(n<a&&n<b)){
            out.println("No");
            return;
        }
        long t = 1;
        boolean find = false;
        while(t <= n) {
            if(t % b == n % b) {
                find = true;
                break;
            }
            t *= a;
        }
        if(find) {
            out.println("Yes");
        }else {
            out.println("No");
        }
    }

    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        String next() {
            while (!st.hasMoreTokens())
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] nextArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }
        long[] nextArray(long n) {
            long[] a = new long[(int) n];
            for (int i = 0; i < n; i++) a[i] = nextLong();
            return a;
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class FastWriter extends PrintWriter {
        FastWriter(){
            super(System.out);
        }
        void println(int[] array) {
            for (int value : array) {
                print(value + " ");
            }
            println();
        }
        void println(long [] array) {
            for (long l : array) {
                print(l + " ");
            }
            println();
        }
    }
}
