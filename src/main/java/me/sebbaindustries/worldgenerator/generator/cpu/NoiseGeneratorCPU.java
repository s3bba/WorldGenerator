package me.sebbaindustries.worldgenerator.generator.cpu;

public abstract class NoiseGeneratorCPU {
    protected final int[] perm = new int[512];
    protected double offsetX;
    protected double offsetY;
    protected double offsetZ;

    public NoiseGeneratorCPU() {
    }

    public static int floor(double x) {
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

    public abstract double noise(double var1, double var3, double var5);

}
