package me.sebbaindustries.worldgenerator.generator;

import com.aparapi.Kernel;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Random;

/**
 * Creates simplex noise through unbiased octaves
 */
public class SimplexOctaveGenerator extends OctaveGenerator {
    private double wScale = 1;

    /**
     * Creates a simplex octave generator for the given world
     *
     * @param world   World to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public SimplexOctaveGenerator(World world, int octaves) {
        this(new Random(world.getSeed()), octaves);
    }

    /**
     * Creates a simplex octave generator for the given world
     *
     * @param seed    Seed to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public SimplexOctaveGenerator(long seed, int octaves) {
        this(new Random(seed), octaves);
    }

    /**
     * Creates a simplex octave generator for the given {@link Random}
     *
     * @param rand    Random object to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public SimplexOctaveGenerator(Random rand, int octaves) {
        super(createOctaves(rand, octaves));
    }

    private static NoiseGenerator[] createOctaves(Random rand, int octaves) {
        NoiseGenerator[] result = new NoiseGenerator[octaves];
        int[] counter = {0};

        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                if (counter[0] == 0) {
                    result[0] = new SimplexNoiseGenerator(rand);
                }
                if (counter[0] == 1) {
                    result[1] = new SimplexNoiseGenerator(rand);
                }
                if (counter[0] == 2) {
                    result[2] = new SimplexNoiseGenerator(rand);
                }
                if (counter[0] == 3) {
                    result[3] = new SimplexNoiseGenerator(rand);
                }
                if (counter[0] == 4) {
                    result[4] = new SimplexNoiseGenerator(rand);
                }
                if (counter[0] == 5) {
                    result[5] = new SimplexNoiseGenerator(rand);
                }
                if (counter[0] == 6) {
                    result[6] = new SimplexNoiseGenerator(rand);
                }
                if (counter[0] == 7) {
                    result[7] = new SimplexNoiseGenerator(rand);
                }
            }
        };

        for (int i = 0; i < octaves; i++) {
            counter[0] = i;
            kernel.setExecutionModeWithoutFallback(Kernel.EXECUTION_MODE.GPU);
            kernel.run();
        }

        return result;
    }

    @Override
    public void setScale(double scale) {
        super.setScale(scale);
        setWScale(scale);
    }

    /**
     * Gets the scale used for each W-coordinates passed
     *
     * @return W scale
     */
    public double getWScale() {
        return wScale;
    }

    /**
     * Sets the scale used for each W-coordinates passed
     *
     * @param scale New W scale
     */
    public void setWScale(double scale) {
        wScale = scale;
    }

    /**
     * Generates noise for the 3D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x         X-coordinate
     * @param y         Y-coordinate
     * @param z         Z-coordinate
     * @param w         W-coordinate
     * @param frequency How much to alter the frequency by each octave
     * @param amplitude How much to alter the amplitude by each octave
     * @return Resulting noise
     */
    public double noise(double x, double y, double z, double w, double frequency, double amplitude) {
        return noise(x, y, z, w, frequency, amplitude, false);
    }

    /**
     * Generates noise for the 3D coordinates using the specified number of
     * octaves and parameters
     *
     * @param xin        X-coordinate
     * @param yin        Y-coordinate
     * @param zin        Z-coordinate
     * @param win        W-coordinate
     * @param frequency  How much to alter the frequency by each octave
     * @param amplitude  How much to alter the amplitude by each octave
     * @param normalized If true, normalize the value to [-1, 1]
     * @return Resulting noise
     */
    public double noise(double xin, double yin, double zin, double win, double frequency, double amplitude, boolean normalized) {
        double[] result = {0};
        double[] amp = {1};
        double[] freq = {1};
        double[] max = {0};
        double[] x = {xin};
        double[] y = {yin};
        double[] z = {zin};
        double[] w = {win};
        x[0] *= xScale;
        y[0] *= yScale;
        z[0] *= zScale;
        w[0] *= wScale;
        for (NoiseGenerator octave : octaves) {

            Kernel kernel = new Kernel() {
                @Override
                public void run() {
                    result[0] += ((SimplexNoiseGenerator) octave).noise(x[0] * freq[0], y[0] * freq[0], z[0] * freq[0], w[0] * freq[0]) * amp[0];
                    max[0] += amp[0];
                    freq[0] *= frequency;
                    amp[0] *= amplitude;
                }
            };
            Bukkit.getConsoleSender().sendMessage(String.valueOf(kernel.isRunningCL()));
            Bukkit.getConsoleSender().sendMessage(String.valueOf(kernel.getTargetDevice()));
            //kernel.run();
        }

        if (normalized) {
            result[0] /= max[0];
        }

        return result[0];
    }
}
