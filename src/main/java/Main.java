import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.JsonFactory;


public class Main {
    public static void main(String [] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Error, usage: java Main.java jsonFile keyword int");
            System.exit(1);
        }
        long start = System.currentTimeMillis();

        File f = new File(args[0]);
        String key = args[1];
        int n = Integer.parseInt(args[2]);

        List<Article> jsonList = new ArrayList<>();
        List<Article> keyTitles = new ArrayList<>();
        List<Article> refList = new ArrayList<>();

        JsonFactory jf = new JsonFactory();
        JsonParser parser = jf.createJsonParser(f);
        JsonToken token = parser.nextToken();

        while (token != JsonToken.END_ARRAY) {
            String name = parser.getCurrentName();
            Article a = new Article();
            if ("id".equals(name)) {
                token = parser.nextToken();
                a.setId(parser.getText());
            }
            if ("title".equals(name)) {
                token = parser.nextToken();
                a.setTitle(parser.getText());
            }
            if ("n_citations".equals(name)) {
                token = parser.nextToken();
                a.setCitations(Integer.parseInt(parser.getText()));
            }
            if ("references".equals(name)) {
                token = parser.nextToken();
                List<String> ref = new ArrayList<>();
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    ref.add(parser.getText());
                }
                a.setReferences(ref);
            }
            if (token == JsonToken.END_OBJECT) {
                jsonList.add(a);
            }
            token = parser.nextToken();
        }
        // check for keyword - add to list
        try (Stream<String>lines = Files.lines(Paths.get(args[0]), Charset.defaultCharset())) {


        } catch (IOException e) {

        }
        // get ref articles - add to list
        for (int i = 0; i < refList.size(); i++) { // sort by ncitations
            refList.get(i).printArticle();
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
