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
 * @date 2023-04-16 09:20
 **/
public class CC_1868_B {

    static FastScanner in = new FastScanner();
    static FastWriter out = new FastWriter();
    public static void main(String[] args) {
        long k = in.nextLong();
        long  cnt = bs(k);
        int remain = 0;
        while(true) {
            int tmp = remain;
            long total = 1;
            for(int i = 0; i < 10; i++) {
                total *= (cnt + (tmp > 0 ? 1 :0));
                tmp = Math.max(0, tmp - 1);
            }
            if(total >= k) {
                break;
            }
            remain++;
        }

        for(int i = 0; i < 10; i++) {
            long tmp = cnt;
            while(tmp > 0) {
                out.print("codeforces".charAt(i));
                tmp--;
            }
            if(remain > 0) {
                out.print("codeforces".charAt(i));
                remain--;
            }
        }
        out.println();
        out.close();
    }

    static long bs(long k) {
        int s = 1;
        int e = 100;
        int mid;
        while(s <= e) {
            mid = (s + e) / 2;
            if(Math.pow(mid, 10) < k) {
                s = mid + 1;
            }else {
                e = mid - 1;
            }
        }
        return e;
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
