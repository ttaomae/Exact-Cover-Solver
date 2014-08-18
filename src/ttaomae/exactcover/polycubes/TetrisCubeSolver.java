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

public class TetrisCubeSolver
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

        if (output != null) {
            output.close();
        }
    }

    private static void printUsage()
    {
        System.err.println("Usage options:");
        System.err.printf("\tjava %s%n", TetrisCubeSolver.class.getName());
        System.err.printf("\tjava %s <nThreads> <depth>%n", TetrisCubeSolver.class.getName());
        System.err.printf("\tjava %s <output_file>%n", TetrisCubeSolver.class.getName());
        System.err.printf("\tjava %s <nThreads> <depth> <output_file>%n", TetrisCubeSolver.class.getName());
    }

    private static List<Solution> solve(int nThreads, int depth)
    {
        Map<String, Polycube> pieces = new HashMap<>();

        pieces.put("F", newF());
        pieces.put("L", newL());
        pieces.put("N", newN());
        pieces.put("P", newP());
        pieces.put("T", newT());
        pieces.put("V", newV());
        pieces.put("V6a", newV6a());
        pieces.put("V6b", newV6b());
        pieces.put("X", newX());
        pieces.put("Y", newY());
        pieces.put("Za", newZa());
        pieces.put("Zb", newZb());

        return new PolycubePuzzle(4, 4, 4, pieces).solve(nThreads, depth);
    }

    private static Polycube newN()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(1, 0, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(1, 2, 0));
        cubes.add(new Cube(0, 2, 0));
        cubes.add(new Cube(0, 3, 0));

        return new Polycube(cubes);
    }

    private static Polycube newV()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(0, 2, 0));
        cubes.add(new Cube(1, 2, 0));
        cubes.add(new Cube(2, 2, 0));

        return new Polycube(cubes);
    }

    private static Polycube newY()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(2, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(2, 1, 0));
        cubes.add(new Cube(3, 1, 0));

        return new Polycube(cubes);
    }

    private static Polycube newP()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 0, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(0, 0, 1));

        return new Polycube(cubes);
    }

    private static Polycube newT()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(1, 0, 0));
        cubes.add(new Cube(2, 0, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(1, 1, 1));

        return new Polycube(cubes);
    }

    private static Polycube newL()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(0, 2, 0));
        cubes.add(new Cube(1, 2, 0));
        cubes.add(new Cube(1, 2, 1));

        return new Polycube(cubes);
    }

    private static Polycube newF()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(1, 0, 0));
        cubes.add(new Cube(2, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(1, 2, 0));
        cubes.add(new Cube(2, 0, 1));

        return new Polycube(cubes);
    }

    private static Polycube newX()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(1, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(2, 1, 0));
        cubes.add(new Cube(1, 1, 1));
        cubes.add(new Cube(1, 2, 1));

        return new Polycube(cubes);
    }

    private static Polycube newZa()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(1, 0, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(1, 1, 1));
        cubes.add(new Cube(2, 1, 1));

        return new Polycube(cubes);
    }

    private static Polycube newZb()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(1, 0, 0));
        cubes.add(new Cube(1, 1, 0));
        cubes.add(new Cube(2, 1, 0));
        cubes.add(new Cube(2, 1, 1));

        return new Polycube(cubes);
    }

    private static Polycube newV6a()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(0, 2, 0));
        cubes.add(new Cube(1, 2, 0));
        cubes.add(new Cube(2, 2, 0));
        cubes.add(new Cube(2, 2, 1));

        return new Polycube(cubes);
    }

    private static Polycube newV6b()
    {
        Collection<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(0, 0, 0));
        cubes.add(new Cube(0, 1, 0));
        cubes.add(new Cube(0, 2, 0));
        cubes.add(new Cube(1, 2, 0));
        cubes.add(new Cube(2, 2, 0));
        cubes.add(new Cube(1, 2, 1));

        return new Polycube(cubes);
    }

}
