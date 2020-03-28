package me.sebbaindustries.worldgenerator.generator.gpu;

import com.aparapi.Kernel;

public abstract class NoiseGeneratorGPU extends Kernel {
    protected final int[] perm = new int[512];

    public NoiseGeneratorGPU() {
    }

    public static int floorGPU(double x) {
        return (x >= 0.0D ? (int) x : (int) x - 1);
    }

    protected static double fade(double x) {
        return x * x * x * (x * (x * 6.0D - 15.0D) + 10.0D);
    }

    protected static double lerp(double x, double y, double z) {
        return y + x * (z - y);
    }

    protected static double grad(int hash, double x, double y, double z) {
        hash &= 15;
        double u = hash < 8 ? x : y;
        double v = hash < 4 ? y : (hash != 12 && hash != 14 ? z : x);
        return ((hash & 1) == 0 ? u : -u) + ((hash & 2) == 0 ? v : -v);
    }

    public abstract void noise(double var1, double var3, double var5);

    @Override
    public void run() {

    }

}
