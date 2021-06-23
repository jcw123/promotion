package com.im.sky.algorithms;

class LCP03 {

    public static void main(String[] args) {
        LCP03 lcp03 = new LCP03();
        System.out.println(lcp03.robot("UURRUUU", new int[][]{{4, 5}, {6, 1}, {7, 10}, {9, 1}, {1, 1}, {5, 0}, {2, 8}}
                , 946,2365));
    }

    public boolean robot(String command, int[][] obstacles, int x, int y) {
        int uLen = 0;
        int rLen = 0;
        for(char c : command.toCharArray()) {
            if(c == 'R') {
                rLen++;
            }else {
                uLen++;
            }
        }
        int r = 0;
        int u = 0;
        boolean mayArrive = false;
        int times1 = x / rLen;
        int times2 = y / uLen;
        int min = Math.min(times1, times2);
        for(int i = 0; i <= command.length(); i++) {
            if(i != 0) {
                if (command.charAt(i-1) == 'R') {
                    r++;
                } else {
                    u++;
                }
            }
            if(x - min * rLen == r && y - min * uLen == u) {
                mayArrive = true;
                break;
            }
        }
        if(x - min * rLen == 0 && y - min * uLen == 0) {
            mayArrive = true;
        }
        if(!mayArrive) {
            return false;
        }
        r = 0;
        u = 0;
        for(int i = 0; i <= command.length(); i++) {
            if(i != 0) {
                if (command.charAt(i-1) == 'R') {
                    r++;
                } else {
                    u++;
                }
            }
            for(int j = 0; j < obstacles.length; j++) {
                int x1 = obstacles[j][0];
                int y1 = obstacles[j][1];
                times1 = x1 / rLen;
                times2 = x1 / uLen;
                min = Math.min(times1, times2);
                if(x1 - min * rLen == r && y1 - min * uLen == u && !isLarge(x1,y1,x,y)) {
                    return false;
                }
            }
        }
        return true;

    }

    private boolean isLarge(int x1, int y1, int x2, int y2) {
        return x1 > x2 || y1 > y2;
    }
}
