package ttaomae.exactcover.polyominoes;

import java.util.ArrayList;
import java.util.Collection;

public final class Tetromino extends Polyomino
{
    private Tetromino()
    {
        super(null);
        // this constructor shouldn't be called
        // use Polyomino constructor directly instead
        assert false;
    }

    public static Polyomino newI()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(0, 2));
        blocks.add(new Block(0, 3));

        return new Polyomino(blocks);
    }

    public static Polyomino newJ()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, 0));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(0, 2));
        blocks.add(new Block(1, 2));

        return new Polyomino(blocks);
    }

    public static Polyomino newL()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(0, 2));
        blocks.add(new Block(1, 2));

        return new Polyomino(blocks);
    }

    public static Polyomino newO()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 0));
        blocks.add(new Block(1, 1));

        return new Polyomino(blocks);
    }

    public static Polyomino newS()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, 0));
        blocks.add(new Block(2, 0));
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 1));

        return new Polyomino(blocks);
    }

    public static Polyomino newT()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(1, 0));
        blocks.add(new Block(2, 0));
        blocks.add(new Block(1, 1));

        return new Polyomino(blocks);
    }

    public static Polyomino newZ()
    {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0));
        blocks.add(new Block(1, 0));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(2, 1));

        return new Polyomino(blocks);
    }

}
