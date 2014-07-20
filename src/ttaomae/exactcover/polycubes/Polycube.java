package ttaomae.exactcover.polycubes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Polycube
{
    private final Set<Cube> cubes;
    private final int xLength;
    private final int yLength;
    private final int zLength;

    public Polycube(Collection<Cube> cubes)
    {
        if (cubes == null) {
            throw new IllegalArgumentException("cubes should not be null");
        }
        if (cubes.isEmpty()) {
            throw new IllegalArgumentException("cubes should not be empty");
        }

        this.cubes = new HashSet<>();

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        for (Cube c : cubes) {
            minX = Math.min(minX, c.x);
            minY = Math.min(minY, c.y);
            minZ = Math.min(minZ, c.z);
            maxX = Math.max(maxX, c.x);
            maxY = Math.max(maxY, c.y);
            maxZ = Math.max(maxZ, c.z);
        }

        this.xLength = 1 + (maxX - minX);
        this.yLength = 1 + (maxY - minY);
        this.zLength = 1 + (maxZ - minZ);

        for (Cube c : cubes) {
            if (minX != 0 || minY != 0 || minZ != 0) {
                this.cubes.add(new Cube(c.x - minX, c.y - minY, c.z - minZ));
            }
            else {
                this.cubes.add(new Cube(c.x, c.y, c.z));
            }
        }
    }

    public Set<Cube> getCubes()
    {
        return Collections.<Cube> unmodifiableSet(this.cubes);
    }

    private Polycube transpose(Axis axis)
    {
        Collection<Cube> newCubes = new ArrayList<>();

        for (Cube c : this.cubes) {
            switch (axis) {
                case X:
                    newCubes.add(new Cube(c.x, c.z, c.y));
                    break;
                case Y:
                    newCubes.add(new Cube(c.z, c.y, c.x));
                    break;
                case Z:
                    newCubes.add(new Cube(c.y, c.x, c.z));
                    break;
            }
        }
        return new Polycube(newCubes);
    }

    /**
     * Returns the Polycube given by reflecting this Polycube across the plane
     * given by the two axes.
     */
    private Polycube reflect(Axis a, Axis b)
    {
        assert (a != b);


        Collection<Cube> newCubes = new ArrayList<>();
        for (Cube c : this.cubes) {
            int newX = (a != Axis.X && b != Axis.X)
                       ? (this.xLength - 1) - c.x
                       : c.x;

            int newY = (a != Axis.Y && b != Axis.Y)
                       ? (this.yLength - 1) - c.y
                       : c.y;

            int newZ = (a != Axis.Z && b != Axis.Z)
                       ? (this.zLength - 1) - c.z
                       : c.z;

            newCubes.add(new Cube(newX, newY, newZ));
        }

        return new Polycube(newCubes);
    }

    public Polycube rotateCW(Axis axis)
    {
        switch (axis) {
            case X:
                return transpose(Axis.X).reflect(Axis.X, Axis.Z);
            case Y:
                return transpose(Axis.Y).reflect(Axis.Y, Axis.X);
            case Z:
                return transpose(Axis.Z).reflect(Axis.Z, Axis.Y);
            default:
                return this;
        }
    }

    public Polycube rotateCCW(Axis axis)
    {
        switch (axis) {
            case X:
                return transpose(Axis.X).reflect(Axis.X, Axis.Y);
            case Y:
                return transpose(Axis.Y).reflect(Axis.Y, Axis.Z);
            case Z:
                return transpose(Axis.Z).reflect(Axis.Z, Axis.X);
            default:
                return this;
        }
    }

    public Set<Polycube> getRotations()
    {
        Set<Polycube> result = new HashSet<>();

        // a subset of the possible orientations
        // one for each "face" aligned with the z-axis
        List<Polycube> subset = new ArrayList<>();
        subset.add(this);
        subset.add(this.rotateCW(Axis.X));
        subset.add(this.rotateCCW(Axis.X));
        subset.add(this.rotateCW(Axis.Y));
        subset.add(this.rotateCCW(Axis.Y));
        subset.add(this.rotateCW(Axis.X).rotateCW(Axis.X));

        // rotate each element of the subset around the z-axis
        for (int i = 0; i < subset.size(); i++) {
            for (int rot = 0; rot < 4; rot++) {
                result.add(subset.get(i));
                subset.set(i, subset.get(i).rotateCW(Axis.Z));
            }
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cubes == null) ? 0 : cubes.hashCode());
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
        Polycube other = (Polycube) obj;
        if (cubes == null) {
            if (other.cubes != null) {
                return false;
            }
        }
        else if (!cubes.equals(other.cubes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        boolean[][][] cubeArray = new boolean[this.zLength][this.yLength][this.xLength];

        for (Cube c : cubes) {
            cubeArray[c.z][c.y][c.x] = true;
        }

        for (int y = 0; y < this.yLength; y++) {
            sb.append("|");
            for (int z = 0; z < this.zLength; z++) {
                for (int x = 0; x < this.xLength; x++) {
                    if (cubeArray[z][y][x]) {
                        sb.append("#");
                    } else {
                        sb.append(" ");
                    }
                }
                sb.append("|");
            }
            sb.append("\n");
        }

        // delete extra newline
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public static class Cube implements Comparable<Cube>
    {
        private final int x;
        private final int y;
        private final int z;

        public Cube(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }

        public int getZ()
        {
            return z;
        }

        @Override
        public String toString()
        {
            return String.format("(%d, %d, %d)", this.x, this.y, this.z);
        }


        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            result = prime * result + z;
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
            Cube other = (Cube) obj;
            if (x != other.x) {
                return false;
            }
            if (y != other.y) {
                return false;
            }
            if (z != other.z) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(Cube o)
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
            else if (this.z < o.z) {
                return -1;
            }
            else if (this.z > o.z) {
                return 1;
            }
            // x, y, and z are equal
            else {
                return 0;
            }
        }
    }

    public enum Axis
    {
        X, Y, Z;
    }
}
