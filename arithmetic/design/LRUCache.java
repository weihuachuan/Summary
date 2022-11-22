package design;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    class DlinkedNode{
        private int key;
        private int value;
        DlinkedNode pre;
        DlinkedNode next;
        public DlinkedNode(){};
        public DlinkedNode(int key,int value){
            this.key = key;
            this.value = value;
        }
    }
    Map<Integer,DlinkedNode> cache = new HashMap<Integer,DlinkedNode>();
    private int size;
    private int capacity;
    private DlinkedNode head;
    private DlinkedNode tail;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        head = new DlinkedNode();
        tail = new DlinkedNode();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        DlinkedNode node = cache.get(key);
        if(node == null){
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DlinkedNode node = cache.get(key);
        if(node == null){
            DlinkedNode newNode = new DlinkedNode(key,value);
            //先存入内存中
            cache.put(key,newNode);
            //再存入链表中
            addToHead(newNode);
            size++;
            if(size > capacity){
                DlinkedNode tail =removeTail();
                cache.remove(tail.key);
                size--;
            }
        }else{
            moveToHead(node);
            node.value = value;
        }
    }
    public void moveToHead(DlinkedNode node){
        removeNode(node);
        addToHead(node);
    }
    public void addToHead(DlinkedNode node){
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }
    public void removeNode(DlinkedNode node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }
    public DlinkedNode removeTail(){
        DlinkedNode res =tail.pre;
        removeNode(res);
        return res;
    }
}
