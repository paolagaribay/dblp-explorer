import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Error, usage: java Main.java jsonFile keyword int");
            System.exit(1);
        }
        long start = System.currentTimeMillis();

        String path = args[0]; //"C:\\Users\\Paola\\Documents\\COSC\\COSC 4353\\a5\\src\\main\\java\\test.txt"
        String key = args[1]; //"system"
        int n = Integer.parseInt(args[2]); //1

        JSONParser parser = new JSONParser();
        List<JSONObject> jsonList;

        Stream<String>lines = Files.lines(Paths.get(path), Charset.defaultCharset());
        jsonList = lines
                .map(a-> {
                    try {
                        return (JSONObject)parser.parse(a);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        List<JSONObject> keyList = jsonList
                .stream()
                .filter(a -> a.get("title").toString().toLowerCase().contains(key.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Printing titles with keyword "+key+": ");
        keyList.forEach(a->System.out.println(a.get("title").toString()));
        System.out.println("\n"+"Level "+n+" references: ");
        references(keyList, jsonList);

        long stop = System.currentTimeMillis();
        System.out.println("\n"+"Total time taken in ms: " + (stop-start));
        Runtime r = Runtime.getRuntime();
        r.gc();
        long memory = r.totalMemory()-r.freeMemory();
        System.out.println("Memory consumption in bytes: "+ memory);
    }
    private static void references(List<JSONObject> klist, List<JSONObject> jlist) {
        List<JSONObject> refList = new ArrayList<>();
        List<String> r = new ArrayList<>();

        for(JSONObject j: klist) {
            if (j.get("references")!=null) {
                JSONArray ja = (JSONArray) j.get("references");
                for(Object i: ja) {
                    List<JSONObject> o = jlist.stream()
                            .filter(a -> a.get("id").toString().contentEquals(i.toString()))
                            .collect(Collectors.toList());
                    if (!o.isEmpty()) {
                        refList.addAll(o);
                    }
                    else {
                        r.add(i.toString());
                    }
                }
            }
        }
        if (!r.isEmpty()) {
            System.out.println("References not shown in file, displaying ids");
            r.stream().distinct().forEach(System.out::println);
        }
        refList.stream().distinct().sorted(Comparator.comparing(a-> (Integer)a.get("n_citations")))
                .forEach(a-> System.out.println(a.get("title").toString()+", "+a.get("id").toString()));
    }
}