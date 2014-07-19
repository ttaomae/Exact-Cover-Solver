package ttaomae.exactcover.polyominoes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Polyomino
{
    private final Set<Block> blocks;
    private final int width;
    private final int height;

    /**
     * Constructs a new polyomino with the specified blocks shifted towards
     * (0,0) if necessary, such that the minimum x-coordinate and minimum
     * y-coordinate of any block is 0.
     *
     * @param blocks
     */
    protected Polyomino(Collection<Block> blocks)
    {
        if (blocks == null) {
            throw new IllegalArgumentException("blocks should not be null");
        }
        if (blocks.isEmpty()) {
            throw new IllegalArgumentException("blocks should not be empty");
        }

        this.blocks = new HashSet<>();

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Block b : blocks) {
            minX = Math.min(minX, b.x);
            minY = Math.min(minY, b.y);
            maxX = Math.max(maxX, b.x);
            maxY = Math.max(maxY, b.y);
        }
        this.width = 1 + (maxX - minX);
        this.height = 1 + (maxY - minY);

        for (Block b : blocks) {
            if (minX != 0 || minY != 0) {
                this.blocks.add(new Block(b.x - minX, b.y - minY));
            }
            else {
                this.blocks.add(new Block(b.x, b.y));
            }
        }
    }

    public Set<Block> getBlocks()
    {
        return Collections.<Block>unmodifiableSet(this.blocks);
    }

    private Polyomino transpose()
    {
        Collection<Block> newBlocks = new ArrayList<>();
        for (Block b : this.blocks) {
            newBlocks.add(new Block(b.y, b.x));
        }

        return new Polyomino(newBlocks);
    }

    public Polyomino flipVerticalAxis()
    {
        Collection<Block> newBlocks = new ArrayList<>();
        for (Block b : this.blocks) {
            newBlocks.add(new Block((this.width - 1) - b.x, b.y));
        }

        return new Polyomino(newBlocks);
    }

    public Polyomino flipHorizontalAxis()
    {
        Collection<Block> newBlocks = new ArrayList<>();
        for (Block b : this.blocks) {
            newBlocks.add(new Block(b.x, (this.height - 1) - b.y));
        }

        return new Polyomino(newBlocks);
    }

    public Polyomino rotateCW()
    {
        return this.transpose().flipVerticalAxis();
    }

    public Polyomino rotateCCW()
    {
        return this.transpose().flipHorizontalAxis();
    }

    public Set<Polyomino> getFreeTransformations()
    {
        Set<Polyomino> result = new HashSet<>();

        result.addAll(this.getOneSidedTransformations());
        result.addAll(this.flipHorizontalAxis().getOneSidedTransformations());

        return result;
    }

    public Set<Polyomino> getOneSidedTransformations()
    {
        Set<Polyomino> result = new HashSet<>();

        Polyomino original = this;

        for (int rot = 0; rot < 4; rot++) {
            result.add(original);

            original = original.rotateCW();
        }

        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        boolean[][] blockArray = new boolean[this.height][this.width];

        for (Block b : blocks) {
            blockArray[b.y][b.x] = true;
        }

        for (boolean[] row : blockArray) {
            for (boolean col : row) {
                if (col) {
                    sb.append("#");
                }
                else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        // delete extra newline
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            System.out.println(getClass());
            System.out.println(obj.getClass());
            return false;
        }
        Polyomino other = (Polyomino) obj;
        if (blocks == null) {
            if (other.blocks != null) {
                return false;
            }
        }
        else if (!blocks.equals(other.blocks)) {
            return false;
        }
        return true;
    }

    public static class Block implements Comparable<Block>
    {
        private final int x;
        private final int y;

        public Block(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public int getX()
        {
            return this.x;
        }

        public int getY()
        {
            return this.y;
        }

        @Override
        public String toString()
        {
            return String.format("(%d, %d)", this.x, this.y);
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Block other = (Block) obj;
            if (x != other.x) {
                return false;
            }
            if (y != other.y) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(Block o)
        {
            if (this.x < o.x) {
                return -1;
            }
            else if (this.x > o.x) {
                return 1;
            }
            // this.x == o.x
            else if (this.y < o.y) {
                return -1;
            }
            else if (this.y > o.y) {
                return 1;
            }
            // x and y are equal
            else {
                return 0;
            }
        }
    }
}
