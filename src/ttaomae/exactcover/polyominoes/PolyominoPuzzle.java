package ttaomae.exactcover.polyominoes;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import ttaomae.exactcover.DancingLinks;
import ttaomae.exactcover.DancingLinks.Solution;
import ttaomae.exactcover.ExactCover;
import ttaomae.exactcover.polyominoes.Polyomino.Block;

public class PolyominoPuzzle
{
    private SortedSet<Block> puzzle;
    private SortedMap<String, Polyomino> pieces;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public PolyominoPuzzle(int width, int height, Map<String, Polyomino> pieces)
    {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }

        if (pieces == null) {
            throw new IllegalArgumentException("pieces must not be null");
        }

        this.minX = 0;
        this.minY = 0;
        this.maxX = width;
        this.maxY = height;

        this.pieces = new TreeMap<>(pieces);

        this.puzzle = new TreeSet<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.puzzle.add(new Block(x, y));
            }
        }
    }

    public PolyominoPuzzle(Set<Block> field, Map<String, Polyomino> pieces)
    {
        if (field == null) {
            throw new IllegalArgumentException("field must not be null");
        }
        if (pieces == null) {
            throw new IllegalArgumentException("pieces must not be null");
        }

        this.puzzle = new TreeSet<>(field);
        this.pieces = new TreeMap<>(pieces);

        this.minX = Integer.MAX_VALUE;
        this.minY = Integer.MAX_VALUE;
        this.maxX = Integer.MIN_VALUE;
        this.maxY = Integer.MIN_VALUE;

        for (Block b : this.puzzle) {
            this.minX = Math.min(this.minX, b.getX());
            this.minY = Math.min(this.minY, b.getY());
            this.maxX = Math.max(this.maxX, b.getX());
            this.maxY = Math.max(this.maxY, b.getY());
        }
    }

    public List<Solution> solve()
    {
        List<String> names = new ArrayList<>();

        for (String s : this.pieces.keySet()) {
            names.add(s);
        }
        for (Block b : this.puzzle) {
            names.add(b.toString().replaceAll("\\s", ""));
        }

        Set<BitSet> rows = new HashSet<>();
        for (Entry<String, Polyomino> piece : this.pieces.entrySet()) {
            for (Polyomino transformation : piece.getValue().getFreeTransformations()) {
                for (int x = this.minX; x <= this.maxX; x++) {
                    for (int y = this.minY; y <= this.maxY; y++) {
                        BitSet row = polyominoToBitSet(names, piece.getKey(), transformation, x, y);
                        if (row != null) {
                            rows.add(row);
                        }
                    }
                }
            }
        }

        return new DancingLinks(new ExactCover(names.toArray(new String[0]), rows)).getSolutions();
    }

    private BitSet polyominoToBitSet(List<String> columns, String name, Polyomino p, int x, int y)
    {
        BitSet s = new BitSet();
        // set column for name of piece
        s.set(columnIndex(columns, name));
        for (Block b : p.getBlocks()) {
            // add x and y
            String bs = new Block(b.getX() + x, b.getY() + y).toString().replaceAll("\\s", "");
            int column = columnIndex(columns, bs);
            if (column == -1) {
                return null;
            }
            s.set(column);
        }

        return s;
    }

    private static int columnIndex(List<String> columns, String c)
    {
        return columns.indexOf(c);
    }
}
