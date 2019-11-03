import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class Main {
    public static void main(String [] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Error, usage: java Main.java jsonFile keyword int");
            System.exit(1);
        }
        long start = System.currentTimeMillis();
        String key = args[1];
        int n = Integer.parseInt(args[2]);
        JSONParser parser = new JSONParser();
        List<JSONObject> jsonList;

        Stream<String>lines = Files.lines(Paths.get(args[0]), Charset.defaultCharset());
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
                .filter(a -> a.get("title").toString().contains(key))
                .sorted(Comparator.comparing(a ->(Integer)a.get("n_citations")))
                .collect(Collectors.toList());

        List<String> refList = keyList
                .stream()
                .map(a -> a.get("id").toString())
                .collect(Collectors.toList());

        for(String str: refList) {
            refList.stream().forEach(System.out::println);
        }




        // process json file
        // search for keyword
        // get cited articles till level n
        // sort total articles from most cited to least cited
        // print article info
        long stop = System.currentTimeMillis();
        System.out.println("Total time taken in ms: " + (start-stop));
        Runtime r = Runtime.getRuntime();
        r.gc();
        long memory = r.totalMemory()-r.freeMemory();
        System.out.println("Memory consumption in bytes: "+ memory);
    }
}
