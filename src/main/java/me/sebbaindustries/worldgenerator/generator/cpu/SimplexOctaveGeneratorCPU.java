package me.sebbaindustries.worldgenerator.generator.cpu;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

//HERE YOU CAN SEE THE ABOMINATIONS WE TRIED.
public class SimplexOctaveGeneratorCPU extends OctaveGeneratorCPU {
    protected double wScale = 0;

    public SimplexOctaveGeneratorCPU(@NotNull Random rand, int octaves) {
        super(createOctaves(rand, octaves));
        this.wScale = 1.0D;
    }

    @NotNull
    private static NoiseGeneratorCPU[] createOctaves(@NotNull Random rand, int octaves) {
        NoiseGeneratorCPU[] result = new NoiseGeneratorCPU[octaves];

        for (int i = 0; i < octaves; ++i) {
            result[i] = new SimplexNoiseGeneratorCPU(rand);
        }

        return result;
    }

    public void setScale(double scale) {
        super.setScale(scale);
        this.setWScale(scale);
    }

    public void setWScale(double scale) {
        this.wScale = scale;
    }
}
