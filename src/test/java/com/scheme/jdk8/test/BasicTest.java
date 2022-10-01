package com.scheme.jdk8.test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BasicTest {

    @Test
    public void testIntegerCache() {
        System.out.println("testIntegerCache");
        System.out.println("127");
        Integer a = 127;
        Integer b = 127;
        System.out.println("a == b: " + (a == b));

        Integer c = new Integer(127);
        System.out.println("a == c: " + (a == c));

        Integer d = Integer.valueOf(127);
        System.out.println("a == d: " + (a == d));

        System.out.println("128");
        a = 128;
        b = 128;
        System.out.println("a == b: " + (a == b));

        c = new Integer(128);
        System.out.println("a == c: " + (a == c));

        d = Integer.valueOf(128);
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
        System.out.println("testMapKey");
        Map<MapKey, String> map = new HashMap<>();
        MapKey key1 = new MapKey("1");
        MapKey key2 = new MapKey("2");
        map.put(key1, "1");
        map.put(key2, "2");
        System.out.println("size: " + map.size());
        // Comment out equals or hashcode method in MapKey to see the difference
        System.out.println("get value by old object: " + map.get(key1));
        System.out.println("get value by new object: " + map.get(new MapKey("1")));
    }

    @Test
    public void testDoubleEquals() {
        System.out.println("testDoubleEquals");
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

        double e = 12345678900000.12345d;
        e = e + 1.000001d;
        e = e + 1.000001d;
        e = e + 1.000001d;
        e = e + 1.000001d;
        e = e + 1.000001d;
        double f = 12345678900000.12345d;
        f = f + 1.000001d;
        f = f + 1.000001d;
        f = f + 1.000001d;
        f = f + 1.000001d;
        f = f + 1.000001d;
//      f = f + 1.00001d;
//      f = f + 1.0001d;
//      f = f + 1.001d;
        System.out.println("e == f: " + (e == f));
    }

    @Test
    public void testDoubleToString() {
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

    @Test
    public void testCompareDoubleUsingBigDecimal() {
        double e = 12345678900000.12345d;
        BigDecimal a = new BigDecimal("12345678900000.12345");
        a = a.add(new BigDecimal("1.000001"));
        a = a.add(new BigDecimal("1.000001"));
        a = a.add(new BigDecimal("1.000001"));
        a = a.add(new BigDecimal("1.000001"));
        a = a.add(new BigDecimal("1.000001"));
        BigDecimal b = new BigDecimal("12345678900000.12345");
        b = b.add(new BigDecimal("1.000001"));
        b = b.add(new BigDecimal("1.000001"));
        b = b.add(new BigDecimal("1.000001"));
        b = b.add(new BigDecimal("1.000001"));
        b = b.add(new BigDecimal("1.000001"));
        System.out.println("a == b: " + (a == b));
        System.out.println("a.equals(b): " + (a.equals(b)));
        System.out.println("a.compareTo(b): " + (a.compareTo(b)));

        BigDecimal c = a.setScale(7);
        System.out.println(a);
        System.out.println(c);
        System.out.println("a.scale:" + a.scale());
        System.out.println("c.scale:" + c.scale());
        System.out.println("a.equals(c): " + (a.equals(c)));
        System.out.println("a.compareTo(c): " + (a.compareTo(c)));
    }
}
