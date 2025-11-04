import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Finder
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Amay Srinivasan
 **/

public class Finder {

    private static final String INVALID = "INVALID KEY";
    // large prime number
    private static final int TABLE_SIZE = 200003;
    private List<String>[] keys;
    private List<String>[] vals;
    public Finder() {
        keys = new List[TABLE_SIZE];
        vals = new List[TABLE_SIZE];
        for (int i = 0; i <TABLE_SIZE; i++) {
            keys[i] = new ArrayList<>();
            vals[i] = new ArrayList<>();
        }
    }
    private int hash(String key) {
        long h = 0;
        long p = 31;
        for (int i = 0; i < key.length(); i++) {
            h = (h*p+key.charAt(i)) % TABLE_SIZE;
        }
        return (int) h;
    }
    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException {
        // TODO: Complete the buildTable() function!3
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length <= Math.max(keyCol, valCol)) {
                continue;
            }
            String key = parts[keyCol];
            String val = parts[valCol];
            int index = hash(key);
            keys[index].add(key);
            vals[index].add(val);
        }
        br.close();
    }

    public String query(String key){
        // TODO: Complete the query() function!
        int index = hash(key);
        for (int i = 0; i < keys[index].size(); i++) {
            if (keys[index].get(i).equals(key)) {
                return vals[index].get(i);
            }
        }
        return INVALID;
    }
}