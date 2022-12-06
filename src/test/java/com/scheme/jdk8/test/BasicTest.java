package com.scheme.jdk8.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.*;

public class BasicTest {
    private static final Logger log = LogManager.getLogger(BasicTest.class.getName());

    @Test
    public void testIntegerCache() {
        // 判等问题 - Integer内部缓存
        System.out.println("testIntegerCache");
        System.out.println("127");
        Integer a = 127;
        Integer b = 127;
        // 内存地址是同一个吗？
        System.out.println("a == b: " + (a == b));

        // new 的话，会使用缓存吗？
        Integer c = new Integer(127);
        System.out.println("a == c: " + (a == c));

        // valueOf 会使用缓存吗？
        Integer d = Integer.valueOf(127);
        System.out.println("a == d: " + (a == d));

        // 128 会使用缓存吗？
        System.out.println("128");
        a = 128;
        b = 128;
        // 内存地址是同一个吗？
        System.out.println("a == b: " + (a == b));

        c = new Integer(128);
        System.out.println("a == c: " + (a == c));

        d = Integer.valueOf(128);
        System.out.println("a == d: " + (a == d));
    }

    @Test
    public void testStringEquals() {
        // 字符串常量池
        System.out.println("testStringEquals");
        String a = "a";
        String b = "a";
        System.out.println("a == b: " + (a == b));

        // 是同一个对象吗？
        String c = new String("a");
        System.out.println("a == c: " + (a == c));

        String d = String.valueOf("a");
        // 是同一个对象吗？
        System.out.println("a == d: " + (a == d));
        // 调试查看 aa, ba 两个元素的内存存储
        String aa = "aa";
        String ba = "aa";

        // 是同一个对象吗？
        System.out.println("aa == ba: " + (aa == ba));
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
        // equals hashCode
        System.out.println("testMapKey");
        Map<MapKey, String> map = new HashMap<>();
        MapKey key1 = new MapKey("1");
        MapKey key2 = new MapKey("2");
        map.put(key1, "1");
        map.put(key2, "2");
        System.out.println("size: " + map.size());
        // 注释 MapKey 中的 equals 或 hashcode 方法，看看结果会有什么不同
        System.out.println("get value by old object: " + map.get(key1));
        // 能取出原来存储的值吗？
        System.out.println("get value by new object: " + map.get(new MapKey("1")));
    }

    @Test
    public void testDoubleEquals() {
        // - 浮点数精度问题
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
        // 注释下一行，然后分别反注释后面三行，看看结果会有什么不同
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
        // 注意c的变化
        Double c = Double.valueOf(10000000);
        // 观察 toString 变成什么了？
        System.out.println(c);
        Double d = Double.valueOf(1000000);
        System.out.println("a == b: " + (a == b));
        System.out.println("a.equals(b): " + (a.equals(b)));
        System.out.println("a == d: " + (a == d));
        // 观察 Double.toString 方法是怎么实现的
        System.out.println("a.equals(d): " + (a.equals(d)));
        System.out.println(Double.doubleToLongBits(a));
        System.out.println(Double.doubleToLongBits(1000000));
    }

    @Test
    public void testCompareDoubleUsingBigDecimal() {
        // 使用 BigDecimal 进行比较
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

        // equals 还会比较 scale
        BigDecimal c = a.setScale(7);
        System.out.println(a);
        System.out.println(c);
        System.out.println("a.scale:" + a.scale());
        System.out.println("c.scale:" + c.scale());
        // 还相等吗？
        System.out.println("a.equals(c): " + (a.equals(c)));
        // 这里会返回什么？
        System.out.println("a.compareTo(c): " + (a.compareTo(c)));
    }

    @Test
    public void testDateFormat() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = sdf.parse("20160901");
        System.out.println(date);
        // 使用更严格的解析
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        TemporalAccessor parsed = formatter.parse("201609");
        System.out.println(parsed);
        TemporalAccessor parsed2 = formatter.parse("20160901");
        System.out.println(parsed2);
    }

    @Test
    public void testBadCatchExceptionMode() throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            formatter.parse("20160901");
        } catch (DateTimeParseException e) {
            // 这样好吗？
            log.error(e);
            // 这样好吗？
            throw new Exception("解析日期异常");
        } finally {
            throw new Exception("finally 异常");
        }
    }

    @Test
    public void testJoinStrings() throws Exception {
        String[] arrays = new String[] {"1", "2", "3"};
        // 这种方案会有什么问题？
        StringBuilder sb = new StringBuilder();
        for (String ele : arrays) {
            sb.append(ele);
            sb.append(",");
        }
        System.out.println(sb);
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);

        // 上面方案的问题，下面的方案解决了吗？
        StringBuilder sb2 = new StringBuilder();
        boolean first = true;
        for (String ele : arrays) {
            if (first) {
                first = false;
            } else {
                sb2.append(",");
            }
            sb2.append(ele);
        }
        System.out.println(sb2);
    }

    @Test
    public void testArraysAsList() throws Exception {
        int[] array = new int[] {1, 2, 3};
        List<int[]> ints = Arrays.asList(array);
        System.out.println(ints.size());
        System.out.println(ints);

        String[] strs = new String[] {"1", "2", "3"};
        List<String> strings = Arrays.asList(strs);
        System.out.println(strings.size());
        System.out.println(strings);
        try {
            strings.add("4");
        } catch (Exception e) {
            log.error("failed to add element to array list");
        }
        try {
            strings.remove(0);
        } catch (Exception e) {
            log.error("failed to remove element from array list");
        }
        strs[0] = "2";
        // 观察对原来值的影响
        System.out.println(strings);
    }

    @Test
    public void testThreadPool() throws ExecutionException, InterruptedException {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        // 一个核心线程，队列最大为1，最大线程数也是1.拒绝策略是DiscardPolicy
        // 注意各个参数是什么意思
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                1,
                1,
                1L,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        Future f1 = executorService.submit(()-> {
            System.out.println("线程：" + Thread.currentThread().getName() + "：提交任务1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Future f2 = executorService.submit(()->{
            System.out.println("线程：" + Thread.currentThread().getName() + "：提交任务2");
        });

        Future f3 = executorService.submit(()->{
            System.out.println("线程：" + Thread.currentThread().getName() + "：提交任务3");
        });

        System.out.println("当前线程：" + Thread.currentThread().getName());
        // 任务1会完成吗？
        System.out.println("任务1完成： " + f1.get());// 等待任务1执行完毕
        // 任务2会完成吗？
        System.out.println("任务2完成： " + f2.get());// 等待任务2执行完毕
        // 任务3会完成吗？
        System.out.println("任务3完成： " + f3.get());// 等待任务3执行完毕

        executorService.shutdown();// 关闭线程池，阻塞直到所有任务执行完毕
    }
}
