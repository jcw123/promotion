package com.im.sky.leetcode.q_2;

/**
 * @author jiangchangwei
 * @date 2020-10-26 下午 8:57
 **/
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null) {
            return l2;
        }
        if(l2 == null) {
            return l1;
        }
        ListNode p = l1;
        ListNode q = l2;
        ListNode head = null;
        ListNode t = null;
        int carry = 0;
        int v;
        while(p != null && q != null) {
            v = p.val + q.val + carry;
            carry = v / 10;
            v = v % 10;
            ListNode node = new ListNode(v);
            if(head == null) {
                head = node;
                t = node;
            }else {
                t.next = node;
                t = node;
            }
            p = p.next;
            q = q.next;
        }
        p = p == null ? q : p;
        while(p != null) {
            v = carry + p.val;
            carry = v / 10;
            v = v % 10;
            ListNode node = new ListNode(v);
            t.next = node;
            t = node;
            p = p.next;
        }
        if(carry == 1) {
            t.next = new ListNode(1);
        }
        return head;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}