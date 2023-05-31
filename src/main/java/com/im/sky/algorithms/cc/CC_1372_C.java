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
 * @date 2023-05-10 23:22
 **/
public class CC_1372_C {

    static FastScanner in = new FastScanner();
    static FastWriter out = new FastWriter();

    public static void main(String[] args) {
        int t = in.nextInt();
        for(int i = 0; i < t; i++) {
            int n = in.nextInt();
            int[] arr = in.nextArray(n);
            int count = 0;
            int diffC = 0;
            for(int j = 1; j <= n; j++) {
                if(arr[j - 1] != j) {
                    diffC++;
                }else {
                    if(diffC > 0) {
                        count++;
                        diffC= 0;
                    }
                }
            }
            if(diffC > 0) {
                count++;
            }
            if(count == 0) {
                out.println(0);
            }else if(count == 1) {
                out.println(1);
            }else {
                out.println(2);
            }
        }
        out.flush();
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
