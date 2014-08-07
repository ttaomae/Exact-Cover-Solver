package ttaomae.exactcover.polycubes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ttaomae.exactcover.DancingLinks.Solution;
import ttaomae.exactcover.polycubes.Polycube.Cube;

public class SomaCubeSolver
{
    public static void main(String[] args) throws FileNotFoundException
    {
        // default values
        int nThreads = 1;
        int depth = 0;
        PrintWriter output = null;

        // parse arguments
        if (args.length == 1) {
            output = new PrintWriter(new File(args[0]));
        }
        else if (args.length == 2 || args.length == 3) {
            try {
                nThreads = Integer.parseInt(args[0]);
                depth = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                printUsage();
                System.exit(1);
            }

            if (args.length == 3) {
                output = new PrintWriter(new File(args[2]));
            }
        }
        else if (args.length != 0) {
            printUsage();
            System.exit(1);
        }

        // find solutions
        List<Solution> solutions = solve(nThreads, depth);
        System.out.printf("Total Solutions: %d%n%n", solutions.size());
        for (Solution s : solutions) {
            if (output == null) {
                System.out.println(s + "\n");
            }
            else {
                output.println(s + "\n");
            }
        }
    }

    private static void printUsage()
    {
        System.err.println("Usage options:");
        System.err.printf("\tjava %s%n", SomaCubeSolver.class.getName());
        System.err.printf("\tjava %s <nThreads> <depth>%n", SomaCubeSolver.class.getName());
        System.err.printf("\tjava %s <output_file>%n", SomaCubeSolver.class.getName());
        System.err.printf("\tjava %s <nThreads> <depth> <output_file>%n", SomaCubeSolver.class.getName());
    }

    private static List<Solution> solve(int nThreads, int depth)
    {
        Map<String, Polycube> pieces = new HashMap<>();

        pieces.put("V", newV());
        pieces.put("L", newL());
        pieces.put("T", newT());
        pieces.put("Z", newZ());
        pieces.put("A", newA());
        pieces.put("B", newB());
        pieces.put("P", newP());

        return new PolycubePuzzle(3, 3, 3, pieces).solve(nThreads, depth);
    }

    private static Polycube newV()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 1, 0));
        return new Polycube(cubes);
    }

    private static Polycube newL()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(0, 2, 0));
        cubes.add(new Cube(1, 2, 0));
        return new Polycube(cubes);
    }

    private static Polycube newT()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(1, 0, 0));
        cubes.add(new Cube(2, 0, 0));
        cubes.add(new Cube(1, 1, 0));
        return new Polycube(cubes);
    }

    private static Polycube newZ()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(1, 2, 0));
        return new Polycube(cubes);
    }

    private static Polycube newA()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(1, 1, 1));
        return new Polycube(cubes);
    }

    private static Polycube newB()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(0, 0, 1));
        return new Polycube(cubes);
    }

    private static Polycube newP()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(0, 1, 1));
        return new Polycube(cubes);
    }
}
