package me.sebbaindustries.worldgenerator.generator.cpu;

import org.jetbrains.annotations.NotNull;

public class OctaveGeneratorCPU {
    @NotNull
    protected final NoiseGeneratorCPU[] octaves;
    protected double xScale = 1.0D;
    protected double yScale = 1.0D;
    protected double zScale = 1.0D;

    protected OctaveGeneratorCPU(@NotNull NoiseGeneratorCPU[] octaves) {
        this.octaves = octaves;
    }

    public void setScale(double scale) {
        this.setXScale(scale);
        this.setYScale(scale);
        this.setZScale(scale);
    }

    public double getXScale() {
        return this.xScale;
    }

    public void setXScale(double scale) {
        this.xScale = scale;
    }

    public double getYScale() {
        return this.yScale;
    }

    public void setYScale(double scale) {
        this.yScale = scale;
    }

    public double getZScale() {
        return this.zScale;
    }

    public void setZScale(double scale) {
        this.zScale = scale;
    }

    public double noise(double x, double y, double frequency, double amplitude) {
        return this.noise(x, y, 0.0D, frequency, amplitude);
    }

    public double noise(double x, double y, double z, double frequency, double amplitude) {
        return this.noise(x, y, z, frequency, amplitude, false);
    }

    public double noise(double x, double y, double z, double frequency, double amplitude, boolean normalized) {
        double result = 0.0D;
        double amp = 1.0D;
        double freq = 1.0D;
        double max = 0.0D;
        x *= this.xScale;
        y *= this.yScale;
        z *= this.zScale;
        NoiseGeneratorCPU[] var20 = this.octaves;
        int var21 = var20.length;

        for (int var22 = 0; var22 < var21; ++var22) {
            NoiseGeneratorCPU octave = var20[var22];
            result += octave.noise(x * freq, y * freq, z * freq) * amp;
            max += amp;
            freq *= frequency;
            amp *= amplitude;
        }

        if (normalized) {
            result /= max;
        }

        return result;
    }
}
