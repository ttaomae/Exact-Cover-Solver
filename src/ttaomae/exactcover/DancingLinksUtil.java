package ttaomae.exactcover;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public final class DancingLinksUtil
{
    private DancingLinksUtil()
    {
        // should not be called
        assert false;
    }

    public static Set<BitSet> stringToSets(String input)
    {
        String[] lines = input.split("\\r?\\n");
        Set<BitSet> sets = new HashSet<BitSet>();

        for (int i = 0; i < lines.length; i++) {
            BitSet s = new BitSet();
            for (int j = 0; j < lines[i].length(); j++) {
                if (lines[i].charAt(j) == '1') {
                    s.set(j);
                }
            }
            sets.add(s);
        }

        return sets;
    }

    public static DancingLinks newDancingLinksFromFile(File file)
            throws IOException, FileFormatException
    {
        String[] names = null;
        Set<BitSet> rows = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine().trim();

            if (line == null) {
                throw new FileFormatException("file is empty");
            }

            if (line.startsWith("#")) {
                names = line.substring(1).trim().split("\\s+");
                line = br.readLine();
                if (line == null) {
                    throw new FileFormatException("no data");
                }
            }

            do {
                rows.add(stringToBitSet(line.trim()));
            } while ((line = br.readLine()) != null);
        }

        if (names == null || names.length == 0) {
            return new DancingLinks(rows);
        }
        else {
            return new DancingLinks(names, rows);
        }
    }

    private static BitSet stringToBitSet(String str) throws FileFormatException
    {
        BitSet s = new BitSet();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                s.set(i);
            }
            else if (str.charAt(i) != '0') {
                throw new FileFormatException("data should only contains '1's and '0's: " + str);
            }
        }

        return s;
    }
}
