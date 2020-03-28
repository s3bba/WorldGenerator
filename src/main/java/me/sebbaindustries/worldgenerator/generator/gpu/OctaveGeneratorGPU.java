package me.sebbaindustries.worldgenerator.generator.gpu;

import com.aparapi.Kernel;
import org.jetbrains.annotations.NotNull;

import static me.sebbaindustries.worldgenerator.generator.gpu.NoiseGeneratorGPU.grad;
import static me.sebbaindustries.worldgenerator.generator.gpu.NoiseGeneratorGPU.lerp;

public class OctaveGeneratorGPU extends Kernel {
    @NotNull
    protected final NoiseGeneratorGPU[] octaves;
    protected double xScale = 1.0D;
    protected double yScale = 1.0D;
    protected double zScale = 1.0D;

    protected OctaveGeneratorGPU(@NotNull NoiseGeneratorGPU[] octaves) {
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

    public double noise(double xin, double yin, double zin, double frequency, double amplitude) {
        final double[] result = {0.0D};
        final double[] amp = {1.0D};
        final double[] freq = {1.0D};
        double[] x[ 0] ={
            xin
        } ;
        double[] y[ 0] ={
            yin
        } ;
        double[] z[ 0] ={
            zin
        } ;
        x[0] *= this.xScale;
        y[0] *= this.yScale;
        z[0] *= this.zScale;
        NoiseGeneratorGPU[] var20 = this.octaves;
        int var21 = var20.length;

        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                for (int i = 0; i < var21; ++i) {
                    NoiseGeneratorGPU octave = var20[i];
                    //
                    x[0] *= freq[0];
                    y[0] *= freq[0];
                    z[0] *= freq[0];
                    int[] permGPU = new int[512];
                    int X = ((x[0] >= 0.0D ? (int) x[0] : (int) x[0] - 1) & 255);
                    int Y = ((y[0] >= 0.0D ? (int) y[0] : (int) y[0] - 1) & 255);
                    int Z = ((z[0] >= 0.0D ? (int) z[0] : (int) z[0] - 1) & 255);
                    x[0] -= (x[0] >= 0.0D ? (int) x[0] : (int) x[0] - 1);
                    y[0] -= (y[0] >= 0.0D ? (int) y[0] : (int) y[0] - 1);
                    z[0] -= (z[0] >= 0.0D ? (int) z[0] : (int) z[0] - 1);
                    double fX = (x[0] * x[0] * x[0] * (x[0] * (x[0] * 6.0D - 15.0D) + 10.0D));
                    double fY = (y[0] * y[0] * y[0] * (y[0] * (y[0] * 6.0D - 15.0D) + 10.0D));
                    double fZ = (z[0] * z[0] * z[0] * (z[0] * (z[0] * 6.0D - 15.0D) + 10.0D));
                    int A = permGPU[X] + Y;
                    int AA = permGPU[A] + Z;
                    int AB = permGPU[A + 1] + Z;
                    int B = permGPU[X + 1] + Y;
                    int BA = permGPU[B] + Z;
                    int BB = permGPU[B + 1] + Z;
                    double out = lerp(fZ, lerp(fY, lerp(fX, grad(permGPU[AA], x[0], y[0], z[0]), grad(permGPU[BA], x[0] - 1.0D, y[0], z[0])), lerp(fX, grad(permGPU[AB], x[0], y[0] - 1.0D, z[0]), grad(permGPU[BB], x[0] - 1.0D, y[0] - 1.0D, z[0]))), lerp(fY, lerp(fX, grad(permGPU[AA + 1], x[0], y[0], z[0] - 1.0D), grad(permGPU[BA + 1], x[0] - 1.0D, y[0], z[0] - 1.0D)), lerp(fX, grad(permGPU[AB + 1], x[0], y[0] - 1.0D, z[0] - 1.0D), grad(permGPU[BB + 1], x[0] - 1.0D, y[0] - 1.0D, z[0] - 1.0D))));
//
                    result[0] += out * amp[0];
                    freq[0] *= frequency;
                    amp[0] *= amplitude;
                }
            }
        };


        return result[0];
    }

    @Override
    public void run() {

    }
}
