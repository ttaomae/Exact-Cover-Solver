package ttaomae.exactcover;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ExactCover
{
    private final String[] names;
    private final Set<BitSet> rows;

    public ExactCover(String[] names, Set<BitSet> rows)
    {
        if (names == null || rows == null) {
            throw new IllegalArgumentException("arguments must not be null");
        }
        if (names.length == 0) {
            throw new IllegalArgumentException("names must have at least one column");
        }

        if (rows.size() == 0) {
            throw new IllegalArgumentException("there must be at least one row");
        }
        for (BitSet row : rows) {
            if (row.length() > names.length) {
                throw new IllegalArgumentException("a row cannot be longer than names");
            }
        }

        this.names = Arrays.copyOf(names, names.length);
        this.rows = new HashSet<>(rows);
    }

    public ExactCover(Set<BitSet> rows)
    {
        this(generateColumnNames(rows), rows);
    }

    public ExactCover(String[] names, String sets) throws DataFormatException
    {
        this.names = Arrays.copyOf(names, names.length);
        this.rows = stringToBitSets(sets);
    }

    public ExactCover(String sets) throws DataFormatException
    {
        this.rows = stringToBitSets(sets);
        this.names = generateColumnNames(this.rows);
    }

    public ExactCover(File file) throws FileNotFoundException, IOException,
            DataFormatException
    {
        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }

        this.rows = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine().trim();

            if (line == null) {
                throw new DataFormatException("file is empty");
            }

            if (line.startsWith("#")) {
                // remove #, trim, then split on whitespace
                this.names = line.substring(1).trim().split("\\s+");

                while ((line = br.readLine()) != null) {
                    this.rows.add(stringToBitSet(line.trim()));
                }
            }
            else {
                // we have already read one line so use do-while
                do {
                    this.rows.add(stringToBitSet(line.trim()));
                } while ((line = br.readLine()) != null);

                this.names = generateColumnNames(this.rows);
            }
        }

        if (this.rows.size() == 0) {
            throw new DataFormatException("no data");
        }
    }

    private static int getMaxColumns(Set<BitSet> rows)
    {
        assert (rows.size() > 0);

        int max = 0;
        for (BitSet row : rows) {
            max = Math.max(max, row.length());
        }

        return max;
    }

    private static String[] generateColumnNames(Set<BitSet> rows)
    {
        int numColumns = getMaxColumns(rows);
        assert (numColumns > 0);

        String[] names = new String[numColumns];

        for (int col = 0; col < names.length; col++) {
            names[col] = getColumnName(col);
        }

        return names;
    }

    private static String getColumnName(int col)
    {
        assert (col >= 0);

        final int numChars = 1 + ('Z' - 'A');

        if (col < numChars) {
            return String.valueOf((char) ('A' + col));
        }
        else {
            return getColumnName((col / numChars) - 1)
                   + getColumnName(col % numChars);
        }
    }

    private static Set<BitSet> stringToBitSets(String str) throws DataFormatException
    {
        String[] rows = str.split("\\r?\\n");
        Set<BitSet> bitSets = new HashSet<>();

        for (int i = 0; i < rows.length; i++) {
            bitSets.add(stringToBitSet(rows[i]));
        }

        return bitSets;
    }

    private static BitSet stringToBitSet(String str) throws DataFormatException
    {
        BitSet s = new BitSet();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                s.set(i);
            }
            else if (str.charAt(i) != '0') {
                throw new DataFormatException("data should only contains '1's and '0's: " + str);
            }
        }

        return s;
    }

    /**
     * @return the names
     */
    public String[] getNames()
    {
        return Arrays.copyOf(this.names, this.names.length);
    }

    /**
     * @return the rows
     */
    public Set<BitSet> getRows()
    {
        return Collections.unmodifiableSet(this.rows);
    }


}
