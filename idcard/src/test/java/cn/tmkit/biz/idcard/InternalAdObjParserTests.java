package cn.tmkit.biz.idcard;

import cn.tmkit.core.io.FileUtil;
import cn.tmkit.core.io.Files;
import cn.tmkit.core.lang.ClassLoaderUtil;
import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Maps;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.support.Console;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 行政区解析
 * <p>通过测试对比，CSV格式和JSON格式查询速度相差约为10倍，JSON格式和KV模式查询速度相差约为10倍</p>
 * <p>CSV格式比JSON格式容量少30KB</p>
 * <p>CSV载入内存占用大小约为：2MB</p>
 * <p>JSON载入内存占用大小约为：500KB</p>
 * <p>KV载入内存占用大小约为：900KB</p>
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class InternalAdObjParserTests {

    private static File dataJson;
    private static File dataCsv;

    private static File dataCsv2;

    private static List<String> lines;

    @BeforeAll
    public static void beforeAll() {
        File rootFile = ClassLoaderUtil.getFile("");
        dataJson = new File(rootFile, "data.json");

        dataCsv = new File(rootFile, "admin-districts.min.csv");

        dataCsv2 = new File(rootFile, "data2.csv");

        File sourceFile = new File(rootFile, "ok_data_level3.csv");
        lines = FileUtil.readLines(sourceFile);
    }

    @Test
    public void convert() {
//        toJson();
//
//         toCsv();

    }

    @Test
    public void jsonBenchmark() {
        List<InternalAdObj> roots = toJson();
        System.out.println("-----------toJson-----------");
        long from = System.currentTimeMillis();
        System.out.println("-------------" + from);
        List<String> searchCodes = Collections.arrayList("110101", "500110", "451229", "469028", "659008");
        AtomicInteger foundCount = new AtomicInteger(0);
        for (int i = 0; i < 10000; i++) {
            String adCode = searchCodes.get(i % 5);
            InternalAdObj province = roots.stream().filter(item -> item.getC().equals(adCode.substring(0, 2)))
                    .findFirst().orElse(null);
            if (province != null) {
                InternalAdObj city = province.getR().stream().filter(item -> item.getC().equals(adCode.substring(0, 4)))
                        .findFirst().orElse(null);
                if (city != null) {
                    city.getR().stream().filter(item -> item.getC().equals(adCode))
                            .findFirst().ifPresent(item -> foundCount.incrementAndGet());
                } else {
                    //Console.error("city {} not found", adCode);
                }
            } else {
                //Console.error("province {} not found", adCode);
            }
//            roots.stream()
//                    .filter(line -> line.getC().equals(adCode.substring(0, 2)))
//                    .findFirst()
//                    .flatMap(line -> line.getR().stream()
//                            .filter(item -> item.getC().equals(adCode.substring(0, 4)))
//                            .findFirst())
//                    .flatMap(item -> item.getR().stream()
//                            .filter(ele -> ele.getC().equals(adCode))
//                            .findFirst())
//                    .ifPresent(found -> foundCount.incrementAndGet());
        }

        long end = System.currentTimeMillis();
        System.out.println("-------------" + end);
        System.out.println("cost time = " + (end - from) + " ms ,foundCount = " + foundCount.get());
        System.out.println("-----------toJson-----------");
    }

    @Test
    public void csvBenchmark() {
        List<String> lines = toCsv();
        List<List<String>> data = lines.stream().map(Strings::split).collect(Collectors.toList());
        Console.error();
        Console.error(ObjectSizeCalculator.getObjectSize(data));
        System.out.println("-----------toCsv-----------");
        long from = System.currentTimeMillis();
        System.out.println("-------------" + from);
        List<String> searchCodes = Collections.arrayList("110101", "500110", "451229", "469028", "659008");
        AtomicInteger foundCount = new AtomicInteger(0);

        for (int i = 0; i < 10000; i++) {
            String adCode = searchCodes.get(i % 5);
            data.stream().filter(ele -> ele.get(0).equals(adCode))
                    .findFirst()
                    .ifPresent(ele -> foundCount.incrementAndGet());
        }

//        for (int i = 0; i < 10000; i++) {
//            String adCode = searchCodes.get(i % 5);
//            lines.stream()
//                    .map(Strings::split)
////                    .map(line -> {
////                        StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
////                        List<String> items = Collections.arrayList(3);
////                        while (stringTokenizer.hasMoreTokens()) {
////                            items.add(stringTokenizer.nextToken());
////                        }
////                        return items;
////                    })
//                    .filter(items -> items.get(0).equals(adCode)).findFirst().ifPresent(found -> foundCount.incrementAndGet());
//        }
        long end = System.currentTimeMillis();
        System.out.println("-------------" + end);
        System.out.println("cost time = " + (end - from) + " ms ,foundCount = " + foundCount.get());

        System.out.println("-----------toCsv-----------");
    }

    @Test
    public void mapBenchmark() {
        Map<String, InternalAdObj> roots = toMap();
        System.out.println("-----------mapBenchmark-----------");
        long from = System.currentTimeMillis();
        System.out.println("-------------" + from);
        List<String> searchCodes = Collections.arrayList("110101", "500110", "451229", "469028", "659008");
        AtomicInteger foundCount = new AtomicInteger(0);

        for (int i = 0; i < 10000; i++) {
            String adCode = searchCodes.get(i % 5);
            if (roots.containsKey(adCode)) {
                foundCount.incrementAndGet();
            } else {
                Console.error("Could not find " + adCode);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("-------------" + end);
        System.out.println("cost time = " + (end - from) + " ms ,foundCount = " + foundCount.get());

        System.out.println("-----------mapBenchmark-----------");
    }

    public Map<String, InternalAdObj> toMap() {
        Map<String, InternalAdObj> roots = Maps.newHashMap(10240);
        int start = 1;
        while (start < lines.size()) {
            String line = lines.get(start++);
            List<String> items = Strings.split(line);
            String id = items.get(0), pid = items.get(1), name = items.get(7);
            // 过滤港澳台
            if (IGNORE_CODES.contains(id)) {
                continue;
            }
            if ("0".equals(pid)) {
                roots.put(id, new InternalAdObj(id, name, pid, null));
            } else if (id.length() == 4) {
                roots.put(id, new InternalAdObj(id, name, pid, null));
            } else if (id.length() == 6) {
                if (pid.length() == 2) {
                    String cid = id.substring(0, 4);
                    roots.put(cid, new InternalAdObj(cid, "\"市辖区\"", pid, null));
                }
                roots.put(id, new InternalAdObj(id, name, pid, null));
            }
        }
        // https://blog.csdn.net/kai161/article/details/114015613
        GraphLayout graphLayout = GraphLayout.parseInstance(roots);
        Console.error("instanceSize = {}", graphLayout.totalSize());
//        System.setProperty("java.vm.name","Java HotSpot(TM) ");
        Console.error();
        Console.error(ObjectSizeCalculator.getObjectSize(roots));
        return roots;
    }

    private List<InternalAdObj> toJson() {
        FileUtil.delete(dataJson);
        FileUtil.touch(dataJson);

        List<InternalAdObj> roots = Collections.arrayList(34);
        int start = 1;
        while (start < lines.size()) {
            String line = lines.get(start++);
            List<String> items = Strings.split(line);
            String id = items.get(0), pid = items.get(1), name = items.get(7);
            if (!pid.equals("0") && id.length() != pid.length() + 2) {
                Console.error("specified {}", line);
            }
            // 过滤港澳台
            if (IGNORE_CODES.contains(id)) {
                continue;
            }
            if ("0".equals(pid)) {
                InternalAdObj root = new InternalAdObj(id, name, Collections.arrayList(128));
                roots.add(root);
            } else if (id.length() == 4) {
                roots.stream().filter(ele -> ele.getC().equals(pid)).findFirst().ifPresent(ele -> {
                    ele.getR().add(new InternalAdObj(id, name, Collections.arrayList()));
                });
            } else if (id.length() == 6) {
                if (pid.length() == 2) {
                    roots.stream().filter(ele -> ele.getC().equals(pid)).findFirst().ifPresent(ele -> {
                        String cid = id.substring(0, 4);
                        InternalAdObj city = ele.getR().stream().filter(item -> item.getC().equals(cid)).findFirst().orElse(null);
                        if (city == null) {
                            city = new InternalAdObj(cid, "\"市辖区\"", Collections.arrayList(128));
                            ele.getR().add(city);
                        }
                        city.getR().add(new InternalAdObj(id, name));
                    });
                } else {
                    roots.stream()
                            .filter(ele -> ele.getC().equals(pid.substring(0, 2)))
                            .findFirst()
                            .flatMap(ele -> ele.getR().stream()
                                    .filter(item -> item.getC().equals(pid))
                                    .findFirst())
                            .ifPresent(item -> item.getR().add(new InternalAdObj(id, name)));
                }
            }
        }
        StringBuilder sb = new StringBuilder(102400);
        sb.append('[');
        for (int i = 0; i < roots.size(); i++) {
            sb.append(roots.get(i));
            if (i + 1 < roots.size()) {
                sb.append(',');
            }
        }
        sb.append(']');
        Files.writeUtf8Str(sb, dataJson);

        GraphLayout graphLayout = GraphLayout.parseInstance(roots);
        Console.error("instanceSize = {}", graphLayout.totalSize());

        return roots;
    }

    private List<String> toCsv() {
        Files.delete(dataCsv);
        Files.touch(dataCsv);

        List<String> newLines = new ArrayList<>(3600);
        newLines.add("id,pid,name");
        for (int i = 1; i < lines.size(); i++) {
            List<String> items = Strings.split(lines.get(i));
            String id = items.get(0), pid = items.get(1), name = items.get(7);
            // 过滤港澳台
            if (IGNORE_CODES.contains(id.substring(0, 2))) {
                continue;
            }
            if (id.length() > 6) {
                // 长度超出6位也忽略
                continue;
            }
            newLines.add(id + "," + pid + "," + Strings.replace(name, "\"", ""));
            Files.writeUtf8Lines(newLines, dataCsv);
        }
        Console.error("newLines = {}", ObjectSizeCalculator.getObjectSize(newLines));

        return newLines;
    }

    @Test
    public void toCsv2() {
        Files.delete(dataCsv2);
        Files.touch(dataCsv2);

        Set<String> keys = Collections.hashSet(3600);
        List<String> newLines = new ArrayList<>(3600);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            List<String> items = Strings.split(line);
            String id = items.get(0), pid = items.get(1), name = Strings.replace(items.get(7), "\"", "");
            // 过滤港澳台
            if (IGNORE_CODES.contains(id)) {
                continue;
            }
            if ("0".equals(pid)) {
                newLines.add(id + "," + name);
                keys.add(id);
            } else if (id.length() == 4) {
                newLines.add(id + "," + name);
                keys.add(id);
            } else if (id.length() == 6) {
                if (pid.length() == 2) {
                    String cid = id.substring(0, 4);
                    if (!keys.contains(cid)) {
                        keys.add(cid);
                        newLines.add(cid + "," + "市辖区");
                    }
                }
                newLines.add(id + "," + name);
            }
        }

        Console.error("newLines = {}", ObjectSizeCalculator.getObjectSize(newLines));
        FileUtil.writeUtf8Lines(newLines, dataCsv2);
    }


    /**
     * 四大直辖市
     */
    private static final List<String> ZHI_XIA_SHI = Collections.arrayList("11", "12", "31", "50");

    /**
     * 港澳台身份证不满足，忽略
     * 国外身份证忽略
     */
    private static final List<String> IGNORE_CODES = Collections.arrayList("71", "81", "82", "91");

}
