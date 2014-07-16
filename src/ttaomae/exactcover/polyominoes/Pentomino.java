package ttaomae.exactcover.polyominoes;

import java.util.ArrayList;
import java.util.Collection;

public final class Pentomino extends Polyomino
{
    private Pentomino()
    {
        super(null);
        // this constructor shouldn't be called
        // use Polyomino constructor directly instead
        assert false;
    }

    public static Polyomino newF()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, 0));
        blocks.add(new Block(2, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(1, 2));

        return new Polyomino(blocks);
    }

    public static Polyomino newI()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 1));
        blocks.add(new Block(0, 2));
        blocks.add(new Block(0, 3));
        blocks.add(new Block(0, 4));
        blocks.add(new Block(0, 5));

        return new Polyomino(blocks);
    }

    public static Polyomino newL()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 1));
        blocks.add(new Block(0, 2));
        blocks.add(new Block(0, 3));
        blocks.add(new Block(0, 4));
        blocks.add(new Block(2, 4));

        return new Polyomino(blocks);
    }

    public static Polyomino newN()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, 0));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(1, 2));
        blocks.add(new Block(0, 2));
        blocks.add(new Block(0, 3));

        return new Polyomino(blocks);
    }

    public static Polyomino newP()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 0));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(0, 2));

        return new Polyomino(blocks);
    }

    public static Polyomino newT()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(1, 0));
        blocks.add(new Block(2, 0));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(1, 2));

        return new Polyomino(blocks);
    }

    public static Polyomino newU()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(2, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(2, 1));

        return new Polyomino(blocks);
    }

    public static Polyomino newV()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(0, 2));
        blocks.add(new Block(1, 2));
        blocks.add(new Block(2, 2));

        return new Polyomino(blocks);
    }

    public static Polyomino newW()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(1, 2));
        blocks.add(new Block(2, 2));

        return new Polyomino(blocks);
    }

    public static Polyomino newX()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(2, 1));
        blocks.add(new Block(1, 2));

        return new Polyomino(blocks);
    }

    public static Polyomino newY()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(2, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(2, 1));
        blocks.add(new Block(3, 1));

        return new Polyomino(blocks);
    }

    public static Polyomino newZ()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(1, 0));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(1, 2));
        blocks.add(new Block(2, 2));

        return new Polyomino(blocks);
    }

}
