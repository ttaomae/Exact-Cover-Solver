package ttaomae.exactcover.polycubes;

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
import ttaomae.exactcover.polycubes.Polycube.Cube;

public class PolycubePuzzle
{
    private SortedSet<Cube> puzzle;
    private SortedMap<String, Polycube> pieces;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int minZ;
    private int maxZ;

    public PolycubePuzzle(int x, int y, int z, Map<String, Polycube> pieces)
    {
        if (x < 0 || y < 0 || z < 0) {
            throw new IllegalArgumentException(
                    "puzzle dimensions must be positive");
        }

        if (pieces == null) {
            throw new IllegalArgumentException("pieces must not be null");
        }


        this.minX = 0;
        this.minY = 0;
        this.minZ = 0;
        this.maxX = x;
        this.maxY = y;
        this.maxZ = z;

        this.pieces = new TreeMap<>(pieces);

        this.puzzle = new TreeSet<>();
        for (int xx = 0; xx < x; xx++) {
            for (int yy = 0; yy < y; yy++) {
                for (int zz = 0; zz < z; zz++) {
                    this.puzzle.add(new Cube(xx, yy, zz));
                }
            }
        }
    }

    public List<Solution> solve(int nThreads, int depth)
    {
        if (nThreads < 1) {
            throw new IllegalArgumentException("numThreads must be positive");
        }
        if (depth < 0) {
            throw new IllegalArgumentException("depth must be non-negative");
        }

        List<String> names = new ArrayList<>();

        for (String s : this.pieces.keySet()) {
            names.add(s);
        }
        for (Cube c : this.puzzle) {
            names.add(c.toString().replaceAll("\\s", ""));
        }

        Set<BitSet> rows = new HashSet<>();

        for(Entry<String, Polycube> piece : this.pieces.entrySet()) {
            for (Polycube rotation : piece.getValue().getRotations()) {
                for (int x = this.minX; x <= this.maxX; x++) {
                    for (int y = this.minY; y <= this.maxY; y++) {
                        for (int z = this.minZ; z <= this.maxZ; z++) {
                            BitSet row = polycubeToBitSet(names, piece.getKey(), rotation, x, y, z);
                            if (row != null) {
                                rows.add(row);
                            }
                        }
                    }
                }
            }
        }

        if (nThreads == 1) {
            return new DancingLinks(
                        new ExactCover(names.toArray(new String[0]), rows))
                        .getSolutions();
        }
        else {
            return new DancingLinks(
                        new ExactCover(names.toArray(new String[0]), rows))
                        .getSolutions(nThreads, depth);
        }
    }

    public List<Solution> solve()
    {
        return solve(1, 0);
    }

    private BitSet polycubeToBitSet(List<String> columns, String name, Polycube p, int x, int y, int z)
    {
        BitSet s = new BitSet();
        // set column for name of piece
        s.set(columnIndex(columns, name));
        for (Cube c : p.getCubes()) {
            String cs = new Cube(c.getX() + x, c.getY() + y, c.getZ() + z).toString().replaceAll("\\s", "");
            int column = columnIndex(columns, cs);
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
