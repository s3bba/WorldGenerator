package me.sebbaindustries.worldgenerator.generator.gpu;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

//HERE YOU CAN SEE THE ABOMINATIONS WE TRIED.
public class SimplexOctaveGeneratorGPU extends OctaveGeneratorGPU {
    protected double wScale = 0;

    public SimplexOctaveGeneratorGPU(@NotNull Random rand, int octaves) {
        super(createOctaves(rand, octaves));
        this.wScale = 1.0D;
    }

    @NotNull
    private static NoiseGeneratorGPU[] createOctaves(@NotNull Random rand, int octaves) {
        NoiseGeneratorGPU[] result = new NoiseGeneratorGPU[octaves];

        for (int i = 0; i < octaves; ++i) {
            result[i] = new SimplexNoiseGeneratorGPU(rand);
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

    @Override
    public void run() {

    }

}
