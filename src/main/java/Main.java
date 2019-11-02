import java.io.File;

public class Main {
    public static void main(String [] args) {
        if (args.length < 3) {
            System.out.println("Error, usage: java Main.java jsonFile keyword int");
            System.exit(1);
        }
        File j = new File(args[0]);
        String key = args[1];
        int n = Integer.parseInt(args[2]);

        long start = System.currentTimeMillis();
        // process json file
        // search for keyword
        // get cited articles till level n
        // sort total articles from most cited to least cited
        // print article info
        long stop = System.currentTimeMillis();
        System.out.println("Total time taken: " + (start-stop));
        // get mem consumption
    }
}
