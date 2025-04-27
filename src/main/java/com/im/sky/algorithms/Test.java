package com.im.sky.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author jiangchangwei
 * @since 2025/3/20
 */
public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for(int i = 0; i < t; i++){
            int n = scanner.nextInt();
            int[] arr = new int[n];
            Map<Integer, Integer> map = new HashMap<>();
            int max = 0;
            for(int j = 0; j < n; j++){
                arr[j] = scanner.nextInt();
                map.put(arr[j], map.getOrDefault(arr[j], 0) + 1);
                max = Math.max(max, arr[j]);
            }
            max = (max << 1) - 1;
            boolean find = true;
            for(int k = 1; k <= max; k++){
                Map<Integer, Integer> tmpMap = new HashMap<>(map);
                find = true;
                for(int v : arr) {
                    int m = (v ^ k);
                    int b = tmpMap.getOrDefault(m, 0);
                    if(b == 0) {
                        find = false;
                        break;
                    }else {
                        tmpMap.put(m, b - 1);
                    }
                }
                if(find){
                    System.out.println(k);
                    break;
                }
            }
            if(!find) {
                System.out.println(-1);
            }
        }
    }
}
