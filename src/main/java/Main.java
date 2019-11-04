import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class Main {

    public static void main(String [] args) throws IOException {
        /*if (args.length < 3) {
            System.out.println("Error, usage: java Main.java jsonFile keyword int");
            System.exit(1);
        }*/
        long start = System.currentTimeMillis();
        String path = "C:\\Users\\Paola\\Documents\\COSC\\COSC 4353\\a5\\src\\main\\java\\test.txt";//args[0]
        String key = "system";//args[1]
        int n = 1;//Integer.parseInt(args[2])
        JSONParser parser = new JSONParser();
        List<JSONObject> jsonList;
        List<String> refList = new ArrayList<>();
        HashMap<String, JSONArray> h = new HashMap<>();
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

        keyList.stream().filter(a->a.get("references")!=null).forEach(a-> getRef(a, refList));
        //refList.stream().distinct().forEach(a->setHash(a, ));

        if(n==1) {
            System.out.println("\n"+"Article ids for level 1: ");
            refList.stream().distinct().forEach(System.out::println);
        }

        long stop = System.currentTimeMillis();
        System.out.println("\n"+"Total time taken in ms: " + (stop-start));
        Runtime r = Runtime.getRuntime();
        r.gc();
        long memory = r.totalMemory()-r.freeMemory();
        System.out.println("Memory consumption in bytes: "+ memory);
    }
    private static void getRef(JSONObject a, List<String> l) {
        JSONArray j = (JSONArray) a.get("references");
        for (Object i: j) {
            l.add(i.toString());
        }
    }
}