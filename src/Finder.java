import java.io.BufferedReader;
import java.io.IOException;

/**
 * Finder
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 * <p>
 * Completed by: Amay Srinivasan
 **/

public class Finder {

    private static final String INVALID = "INVALID KEY";
    private static final int DEFAULT_TABLE_SIZE = 1024;
    private int tableSize = DEFAULT_TABLE_SIZE;
    private int numPairs = 0;
    // Declaring parallel arrays for the keys and values (key-value pairs)
    private String[] keys = new String[tableSize];
    private String[] values = new String[tableSize];

    public Finder() {
    }

    // Uses Horner's method to hash, and then the modulos is tableSize, as tableSize changes
    private int hash(String key) {
        long h = 0;
        for (int i = 0; i < key.length(); i++) {
            h = (h * 31 + key.charAt(i)) % tableSize;
        }
        return (int) h;
    }

    // Doubles the table size, and then re=inserts everything for linear probing
    private void resize() {
        int oldSize = tableSize;
        String[] oldKeys = keys;
        String[] oldValues = values;
        tableSize *= 2;
        keys = new String[tableSize];
        values = new String[tableSize];
        numPairs = 0;
        // Rehashes just the non-null entries
        for (int i = 0; i < oldSize; i++) {
            if (oldKeys[i] != null) {
                add(oldKeys[i], oldValues[i]);
            }
        }
    }

    // Inserts key-value pairs using linear probing
    private void add(String key, String value) {
        // Makes sure the load factor is less than 0.5 for speed efficiency, otherwise, resize (Double the tableSize)
        double loadFactor = (double) numPairs / tableSize;
        if (loadFactor >= 0.5) {
            resize();
        }
        int index = hash(key);
        // Probes forward until it finds an empty spot or match
        while (keys[index] != null) {
            // If the key already exists, write over it
            if (keys[index].equals(key)) {
                values[index] = value;
                return;
            }
            index = (index + 1) % tableSize;
        }
        // Inserts a new pair
        keys[index] = key;
        values[index] = value;
        numPairs++;
    }

    // Gets a value using linear probing
    private String get(String key) {
        int index = hash(key);
        // Only stop if we hit an empty cell
        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                return values[index];
            }
            index = (index + 1) % tableSize;
        }
        return INVALID;
    }

    // Goes through each value and then pulls out the key-value columns for each row
    // The invalid rows are ignored, and then each pair is inserted into the hashtable
    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            // Splits the line into columns
            String[] parts = line.split(",");
            // Skips over the incomplete lines
            if (parts.length <= Math.max(keyCol, valCol)) {
                continue;
            }
            String key = parts[keyCol];
            // Skips the empty keys
            if (key == null || key.isEmpty()) {
                continue;
            }
            String val = parts[valCol];
            // Inserts it into the hashtable
            add(key, val);
        }
        br.close();
    }

    // Essentially just runs the get function (but this is called in tester)
    public String query(String key) {
        return get(key);
    }
}