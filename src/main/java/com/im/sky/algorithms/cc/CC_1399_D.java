package com.im.sky.algorithms.cc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-10 22:53
 **/
public class CC_1399_D {

    static FastScanner in = new FastScanner();
    static FastWriter out = new FastWriter();

    public static void main(String[] args) {
        int t = in.nextInt();
        for(int i = 0; i < t; i++) {
            int n = in.nextInt();
            String s = in.next();
            process(n, s);
        }
    }

    private static void process(int n, String s) {
        int[] ans = new int[n];
        int idx = 1;
        PriorityQueue<Integer> oneQ = new PriorityQueue<>();
        PriorityQueue<Integer> zeroQ = new PriorityQueue<>();
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '0') {
                if(oneQ.isEmpty()) {
                    ans[i] = idx;
                    zeroQ.offer(idx);
                    idx++;
                }else {
                    int cur = oneQ.poll();
                    ans[i] = cur;
                    zeroQ.offer(cur);
                }
            }else {
                if(zeroQ.isEmpty()) {
                    ans[i] = idx;
                    oneQ.offer(idx);
                    idx++;
                }else {
                    int cur = zeroQ.poll();
                    ans[i] = cur;
                    oneQ.offer(cur);

                }
            }
        }
        out.println(idx - 1);
        out.println(ans);
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
