package com.im.sky.leetcode.q_1642;

import java.util.PriorityQueue;

/**
 * @author jiangchangwei
 * @date 2020-11-12 下午 8:04
 *
 * 1 <= heights.length <= 105
 * 1 <= heights[i] <= 106
 * 0 <= bricks <= 109
 * 0 <= ladders <= heights.length
 **/
class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        return maxDistance(heights, 0, heights.length - 1, bricks, ladders, 0);
    }

    private int maxDistance(int[] heights, int start, int len, int bricks, int ladders, int distance) {
        if(start >= len) {
            return distance;
        }
        int max = distance;
        if(heights[start] >= heights[start + 1]) {
            return maxDistance(heights, start + 1, len, bricks, ladders, distance + 1);
        }else {
            int diff = heights[start + 1] - heights[start];
            if(bricks >= diff) {
                max = Math.max(max, maxDistance(heights, start + 1, len, bricks - diff, ladders, distance + 1));
            }
            if(ladders > 0) {
                max = Math.max(max, maxDistance(heights, start + 1, len, bricks, ladders - 1, distance + 1));
            }
            return max;
        }
    }

    private int maxDistance2(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int i = 0;
        int diff;
        while(i < heights.length - 1) {
            diff = heights[i + 1] - heights[i];
            if(diff > 0) {
                queue.offer(diff);
            }
            if(queue.size() > ladders) {
                diff = queue.poll();
                if(bricks >= diff) {
                    bricks -= diff;
                }else {
                    break;
                }
            }
            i++;
        }
        return i;
    }
}
