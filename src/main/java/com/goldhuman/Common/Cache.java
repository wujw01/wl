//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldhuman.Common;

import java.util.*;
import java.util.Map.Entry;
import java.util.TimerTask;

public class Cache {
    public static int default_size = 100;
    public static int default_timeout = 10;
    private Map cache = new HashMap();
    private Cache.LRU lru = new Cache.LRU();
    private int nitem;
    private int maxsize;
    private int time_stamp;
    private int life_time;
    private int[] key_pos;
    private static Map all_caches = Collections.synchronizedMap(new HashMap());

    private void add(Cache.Item var1) {
        var1.life_time = this.life_time;
        if (this.cache.size() == this.maxsize) {
            this.remove(this.lru.last());
        }

        this.cache.put(var1, var1);
        this.lru.add(var1);
    }

    private void remove(Cache.Item var1) {
        var1.revoke();
        this.cache.remove(var1);
        this.lru.remove(var1);
    }

    private boolean contains(Cache.Item var1) {
        return this.cache.containsKey(var1);
    }

    private Cache(int var1, int[] var2) {
        this.maxsize = default_size;
        this.time_stamp = 0;
        this.life_time = default_timeout;
        this.nitem = var1;
        this.key_pos = var2;
    }

    private Cache(int var1, int[] var2, int var3, int var4) {
        this.maxsize = default_size;
        this.time_stamp = 0;
        this.life_time = default_timeout;
        this.nitem = var1;
        this.key_pos = var2;
        this.maxsize = var3;
        this.life_time = var4;
    }

    public static Cache Create(String var0, int var1, int[] var2) {
        Cache var3 = new Cache(var1, var2);
        all_caches.put(var0, var3);
        return var3;
    }

    public static Cache Create(String var0, int var1, int[] var2, int var3, int var4) {
        Cache var5 = new Cache(var1, var2, var3, var4);
        all_caches.put(var0, var5);
        return var5;
    }

    public static Cache getInstance(String var0) {
        return (Cache) all_caches.get(var0);
    }

    public synchronized int size() {
        return this.cache.size();
    }

    public synchronized Cache.Item find(Cache.Item var1) {
        Cache.Item var2 = (Cache.Item) this.cache.get(var1);
        if (var2 == null) {
            return null;
        } else {
            this.lru.access(var2);
            return (Cache.Item) var2.clone();
        }
    }

    public Cache.Item newItem() {
        return new Cache.Item(this);
    }

    public static void main(String[] var0) {
        Cache var1 = Create("c1", 2, new int[]{0});

        try {
            var1.newItem().set(0, new Integer(1)).set(1, new String("a")).commit();
            var1.newItem().set(0, new Integer(2)).set(1, new String("b")).commit();
            var1.newItem().set(0, new Integer(3)).set(1, new String("c")).commit();
            var1.find(var1.newItem().set(0, new Integer(1)));
            var1.newItem().set(0, new Integer(4)).set(1, new String("d")).commit();
            var1.find(var1.newItem().set(0, new Integer(3)));
            var1.newItem().set(0, new Integer(5)).set(1, new String("e")).commit();
            System.out.println("Size = " + var1.size());
            Thread.sleep(1000L);
            System.out.println("Size = " + var1.size());
            Thread.sleep(1000L);
            System.out.println("Size = " + var1.size());
            Thread.sleep(1000L);
            System.out.println("Size = " + var1.size());
            Thread.sleep(1000L);
            System.out.println("Size = " + var1.size());
            Thread.sleep(1000L);
            System.out.println("Size = " + var1.size());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    static {
        (new Timer(true)).schedule(new TimerTask() {
            public void run() {
                synchronized (Cache.all_caches) {
                    Iterator var2 = Cache.all_caches.entrySet().iterator();

                    while (var2.hasNext()) {
                        Cache var3 = (Cache) ((Entry) var2.next()).getValue();
                        synchronized (var3) {
                            Iterator var5 = var3.cache.entrySet().iterator();

                            while (var5.hasNext()) {
                                Cache.Item var6 = (Cache.Item) ((Entry) var5.next()).getValue();
                                // update access to outerClass's object
//                                if(Cache.Item.access$306(var6) <= 0) {
                                if (var6.time_stamp <= 0) {
                                    var6.revoke();
                                    var5.remove();
                                    var3.lru.remove(var6);
                                }
                            }
                        }
                    }

                }
            }
        }, 0L, 1000L);
    }

    public class Item implements Cloneable {
        private static final int CLEAN = 0;
        private static final int DIRTY = 1;
        private Cache.Item origin;
        private int time_stamp;
        private int life_time;
        private int access_count;
        private int status;
        private Object[] items;
        private Cache owner;

        protected Object clone() {
            try {
                Cache.Item var1 = (Cache.Item) super.clone();
                var1.items = new Object[this.items.length];
                System.arraycopy(this.items, 0, var1.items, 0, this.items.length);
                var1.origin = this;
                return var1;
            } catch (Exception var2) {
                return null;
            }
        }

        private Item(Cache var2) {
            this.access_count = 0;
            this.owner = var2;
            this.items = new Object[var2.nitem];
            this.status = 1;
            // update to outerClass's object
//            this.time_stamp = Cache.access$808(var2);
            this.time_stamp = var2.time_stamp;
        }

        private void revoke() {
        }

        public boolean equals(Object var1) {
            for (int var2 = 0; var2 < this.owner.key_pos.length; ++var2) {
                if (!this.items[Cache.this.key_pos[var2]].equals(((Cache.Item) var1).items[Cache.this.key_pos[var2]])) {
                    return false;
                }
            }

            return true;
        }

        public int hashCode() {
            int var1 = 0;

            for (int var2 = 0; var2 < this.owner.key_pos.length; ++var2) {
                var1 = var1 + this.items[Cache.this.key_pos[var2]].hashCode() * 17 >> 4;
            }

            return var1;
        }

        public void commit() throws RuntimeException {
            if (this.status != 0) {
                Cache var1 = this.owner;
                synchronized (this.owner) {
                    if (this.origin == null) {
                        if (this.owner.contains(this)) {
                            throw new RuntimeException("Duplicate Key");
                        }
                    } else {
                        if (this.origin.time_stamp != this.time_stamp) {
                            throw new RuntimeException("TimeStamp Collision");
                        }

                        if (this.hashCode() != this.origin.hashCode() || !this.equals(this.origin)) {
                            this.owner.remove(this.origin);
                        }
                        // update to outerClass's object
//                        this.time_stamp = this.origin.time_stamp = Cache.access$808(this.owner);
                        this.time_stamp = this.origin.time_stamp = this.owner.time_stamp;
                        this.origin = null;
                    }

                    this.status = 0;
                    this.owner.add(this);
                }
            }
        }

        public Cache.Item set(int var1, Object var2) {
            this.items[var1] = var2;
            this.status = 1;
            return this;
        }

        public Object get(int var1) {
            return this.items[var1];
        }
    }

    private class LRU {
        private TreeMap lru;

        private LRU() {
            this.lru = new TreeMap();
        }

        public void add(Cache.Item var1) {
            Integer var2 = new Integer(var1.access_count);
            LinkedList var3 = (LinkedList) this.lru.get(var2);
            if (var3 == null) {
                this.lru.put(var2, var3 = new LinkedList());
            }

            var3.addLast(var1);
        }

        public void remove(Cache.Item var1) {
            Integer var2 = new Integer(var1.access_count);
            LinkedList var3 = (LinkedList) this.lru.get(var2);
            var3.remove(var1);
            if (var3.size() == 0) {
                this.lru.remove(var2);
            }

        }

        public Cache.Item last() {
            return (Cache.Item) ((LinkedList) this.lru.get(this.lru.firstKey())).getFirst();
        }

        public void access(Cache.Item var1) {
            this.remove(var1);
            // update to outerClass's object
//            Cache.Item.access$608(var1);
            var1.life_time = 0;
            this.add(var1);
        }
    }
}
