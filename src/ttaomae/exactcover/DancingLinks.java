package ttaomae.exactcover;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Set;

public class DancingLinks
{
    private ColumnNode header;

    /**
     * Constructs a new DancingLinks with the specified column names and rows.
     *
     * @param names names for columns
     * @param rows
     */
    public DancingLinks(String[] names, Set<BitSet> rows)
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
                throw new IllegalArgumentException("length of each row must not be longer than names");
            }
        }

        this.header = new ColumnNode("header");
        this.header.left = this.header;
        this.header.right = this.header;

        // create column headers
        ColumnNode prev = this.header;
        for (int col = 0; col < names.length; col++) {
            ColumnNode newNode = new ColumnNode(names[col]);

            newNode.left = prev;
            prev.right = newNode;
            this.header.left = newNode;
            newNode.right = this.header;

            newNode.up = newNode;
            newNode.down = newNode;

            prev = newNode;
        }

        for (BitSet row : rows) {
            addRow(row);
        }
    }

    /**
     * Constructs a new DancingLinks with default column names.
     *
     * @param rows
     */
    public DancingLinks(Set<BitSet> rows)
    {
        this(getColumnNames(getMaxColumns(rows)), rows);
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

    private static String[] getColumnNames(int numColumns)
    {
        assert (numColumns >= 0);

        String[] names = new String[numColumns];

        for (int col = 0; col < names.length; col++) {
            names[col] = getColumnName(col);
        }

        return names;
    }

    private static String getColumnName(int col)
    {
        assert (col > 0);

        final int numChars = 1 + ('Z' - 'A');

        if (col < numChars) {
            return String.valueOf((char) ('A' + col));
        }
        else {
            return getColumnName((col / numChars) - 1)
                   + getColumnName(col % numChars);
        }
    }

    public List<Solution> getSolutions()
    {
        List<Solution> solutions = new ArrayList<>();
        return search(0, solutions, new ArrayList<Node>());
    }

    private List<Solution> search(int k,  List<Solution> solutions, List<Node> partialSolution)
    {
        // no columns left; found solution
        if (this.header.right == this.header) {
            List<Node> solution = new ArrayList<>(partialSolution.subList(0, k));
            solutions.add(new Solution(solution));
            return solutions;
        }

        // pick and cover column with least 1s
        ColumnNode c = minColumn();
        cover(c);

        // go down column c
        Node r = c.down;
        while (r != c) {
            // add the row to the partial solution at index k
            if (partialSolution.size() <= k) {
                partialSolution.add(r);
            }
            else {
                partialSolution.set(k, r);
            }

            // go across row r and cover each column with a 1
            Node j = r.right;
            while (j != r) {
                cover(j.column);
                j = j.right;
            }

            // search for next column
            search(k + 1, solutions, partialSolution);

            // uncover each row that we covered from before the recursive search
            r = partialSolution.get(k);
            c = r.column;

            j = r.left;
            while (j != r) {
                uncover(j.column);
                j = j.left;
            }

            r = r.down;
        }

        // uncover the column we chose at the beginning
        uncover(c);

        return solutions;
    }

    private ColumnNode minColumn()
    {
        Node c = this.header.right;
        Node minNode = c;
        int minSize = ((ColumnNode) c).size;
        while (c != this.header) {
            int cSize = ((ColumnNode) c).size;
            if (cSize < minSize) {
                minNode = c;
                minSize = cSize;
            }

            c = c.right;
        }
        return (ColumnNode) minNode;
    }

    private void cover(ColumnNode c)
    {
        c.right.left = c.left;
        c.left.right = c.right;

        // go down column
        Node i = c.down;
        while (i != c) {
            // go across each row
            Node j = i.right;
            while (j != i) {
                // remove j from column
                j.down.up = j.up;
                j.up.down = j.down;

                j.column.size--;

                j = j.right;
            }
            i = i.down;
        }
    }

    private void uncover(ColumnNode c)
    {
        // go up column
        Node i = c.up;
        while (i != c) {
            // go across each row
            Node j = i.left;
            while (j != i) {
                // add j back to column
                j.column.size++;

                j.down.up = j;
                j.up.down = j;

                j = j.left;
            }
            i = i.up;
        }

        c.right.left = c;
        c.left.right = c;
    }

    private void addRow(BitSet row)
    {
        Node header = this.header;
        Node prev = null;
        for (int col = 0; col < row.length(); col++) {
            header = header.right;
            if (row.get(col)) {
                Node newNode = new Node();
                newNode.column = (ColumnNode) header;
                ((ColumnNode) header).size++;

                // add new node to bottom of list
                header.up.down = newNode;
                newNode.down = header;
                newNode.up = header.up;
                header.up = newNode;

                // add to right of list
                if (prev == null) {
                    newNode.left = newNode;
                    newNode.right = newNode;
                    prev = newNode;
                }
                else {
                    newNode.left = prev;
                    newNode.right = prev.right;
                    prev.right.left = newNode;
                    prev.right = newNode;
                }
            }
        }
    }

    private class Node
    {
        protected Node left;
        protected Node right;
        protected Node up;
        protected Node down;
        protected ColumnNode column;
    }

    private class ColumnNode extends Node
    {
        private int size;
        private String name;

        public ColumnNode(String name)
        {
            super();
            super.column = this;
            this.size = 0;
            this.name = name;
        }

        @Override
        public String toString()
        {
            return this.name;
        }
    }

    public class Solution
    {
        List<Node> rows;

        private Solution(List<Node> rows)
        {
            assert (rows != null);
            this.rows = rows;
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            for (Node row : this.rows) {
                sb.append(toRowString(row));
                sb.append("\n");
            }

            return sb.toString();
        }

        private String toRowString(Node n)
        {
            StringBuilder sb = new StringBuilder();

            Node c = n;
            do {
                sb.append(c.column.name);
                c = c.right;
                if (c != n) {
                    sb.append(", ");
                }
            } while (c != n);

            return sb.toString();
        }
    }
}
