package com.scheme.jdk8.test;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class JavaTest {
    @Test
    public void testIntegerEquals() {
        System.out.println("testIntegerEquals");
        Integer a = 1;
        Integer b = 1;
        System.out.println("a == b: " + (a == b));

        Integer c = new Integer(1);
        System.out.println("a == c: " + (a == c));

        Integer d = Integer.valueOf(1);
        System.out.println("a == d: " + (a == d));
    }

    @Test
    public void testStringEquals() {
        System.out.println("testStringEquals");
        String a = "a";
        String b = "a";
        System.out.println("a == b: " + (a == b));

        String c = new String("a");
        System.out.println("a == c: " + (a == c));

        String d = String.valueOf("a");
        System.out.println("a == d: " + (a == d));
    }

    private static class MapKey {
        private String key;

        private MapKey(String key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof MapKey) {
                return key.equals(((MapKey) obj).key);
            }
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            if (key == null) {
                return 0;
            }
            return key.hashCode();
        }
    }

    @Test
    public void testMapKey() {
        Map<MapKey, String> map = new HashMap<>();
        MapKey key1 = new MapKey("1");
        MapKey key2 = new MapKey("2");
        map.put(key1, "1");
        map.put(key2, "2");
        System.out.println("size: " + map.size());
        System.out.println("get value by old object: " + map.get(key1));
        System.out.println("get value by new object: " + map.get(new MapKey("1")));
    }

    @Test
    public void testDoubleToString() {
        double d1 = 0;
        for (int i = 1; i <= 8; i++) {
            d1 += 0.1;
        }
        double d2 = 0.1 * 8;
        System.out.println("d1:" + d1);
        System.out.println("d2:" + d2);
        System.out.println("d1 == d2:" + (d1 == d2));

        double epsilon = 0.000001d;
        System.out.println("d1 - d2 < epsilon: " + (Math.abs(d1 - d2) < epsilon));

        Double a = Double.valueOf(1000000);
        System.out.println(a);
        Double b = Double.valueOf(9999999);
        System.out.println(b);
        Double c = Double.valueOf(10000000);
        System.out.println(c);
        Double d = Double.valueOf(1000000);
        System.out.println("a == d: " + (a == d));
        System.out.println("a.equals(d): " + (a.equals(d)));
        System.out.println(Double.doubleToLongBits(a));
        System.out.println(Double.doubleToLongBits(1000000));
    }
}
