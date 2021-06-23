package com.im.sky.algorithms;

import java.util.Arrays;
import java.util.PriorityQueue;

class Solution_1882 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(assignTasks(new int[]{3,3,2}, new int[]{1,2,3,2,1,2})));
    }

    public static int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m  = tasks.length;
        PriorityQueue<Server> queue = new PriorityQueue<>((o1, o2) -> {
            if(o1.weight > o2.weight) {
                return 1;
            }else if(o1.weight < o2.weight) {
                return -1;
            }else {
                if(o1.index > o2.index) {
                    return 1;
                }else if(o1.index < o2.index) {
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        PriorityQueue<Server> busyServerQueue = new PriorityQueue<>((o1, o2) -> {
            if(o1.idleTime < o2.idleTime) {
                return -1;
            }else if(o1.idleTime > o2.idleTime) {
                return 1;
            }else {
                return 0;
            }
        });
        for(int i = 0; i < n; i++) {
            Server server = new Server(i, servers[i], 0);
            queue.offer(server);
        }
        int[] res = new int[m];
        for(int i = 0; i < m; i++) {
            while(!busyServerQueue.isEmpty()) {
                if(busyServerQueue.peek().idleTime <= i) {
                    queue.offer(busyServerQueue.poll());
                }else {
                    break;
                }
            }
            if(queue.isEmpty()) {
                Server server = busyServerQueue.poll();
                queue.offer(server);
                while(!busyServerQueue.isEmpty() && busyServerQueue.peek().idleTime == server.idleTime) {
                    queue.offer(busyServerQueue.poll());
                }
            }
            Server server = queue.poll();
            server.idleTime += tasks[i];
            res[i] = server.index;
            busyServerQueue.offer(server);
        }
        return res;
    }

    private static class Server {
        int index;
        int weight;
        int idleTime;
        public Server(int index, int weight, int idleTime) {
            this.index = index;
            this.weight = weight;
            this.idleTime = idleTime;
        }
    }
}
