import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        String key = "system";//args[1];
        int n = 1;//Integer.parseInt(args[2]);
        JSONParser parser = new JSONParser();
        List<JSONObject> jsonList;
        List<String> refList = new ArrayList<>();
       // HashMap<String, List<String>> h = new HashMap<>();
        Stream<String>lines = Files.lines(Paths.get("C:\\Users\\Paola\\Documents\\COSC\\COSC 4353\\a5\\src\\main\\java\\test.txt"), Charset.defaultCharset());
        jsonList = lines
                .map(line-> {
                    try {
                        return (JSONObject)parser.parse(line);
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

        keyList.forEach(a->System.out.println(a.get("title").toString()));

        //keyList.stream().forEach(a-> h.put(a.get("title").toString(), refList.addAll(a.get("references"))));
        //List<JSONObject> refList = keyList
          //      .stream()
            //    .sorted(Comparator.comparing(a ->(Integer)a.get("n_citations")))
              //  .collect(Collectors.toList());


        long stop = System.currentTimeMillis();
        System.out.println("Total time taken in ms: " + (stop-start));
        Runtime r = Runtime.getRuntime();
        r.gc();
        long memory = r.totalMemory()-r.freeMemory();
        System.out.println("Memory consumption in bytes: "+ memory);
    }
}