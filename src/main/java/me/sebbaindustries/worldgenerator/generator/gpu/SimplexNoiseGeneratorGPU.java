package me.sebbaindustries.worldgenerator.generator.gpu;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

//HERE YOU CAN SEE THE ABOMINATIONS WE TRIED.
public class SimplexNoiseGeneratorGPU extends PerlinNoiseGeneratorGPU {
    protected static final double SQRT_3 = Math.sqrt(3.0D);
    protected static final double SQRT_5 = Math.sqrt(5.0D);
    protected static final double F2;
    protected static final double G2;
    protected static final double G22;
    protected static final double F4;
    protected static final double G4;
    protected static final double G42;
    protected static final double G43;
    protected static final double G44;
    protected static final int[][] grad4;
    protected static final int[][] simplex;
    private static final SimplexNoiseGeneratorGPU instance;

    static {
        F2 = 0.5D * (SQRT_3 - 1.0D);
        G2 = (3.0D - SQRT_3) / 6.0D;
        G22 = G2 * 2.0D - 1.0D;
        F4 = (SQRT_5 - 1.0D) / 4.0D;
        G4 = (5.0D - SQRT_5) / 20.0D;
        G42 = G4 * 2.0D;
        G43 = G4 * 3.0D;
        G44 = G4 * 4.0D - 1.0D;
        grad4 = new int[][]{{0, 1, 1, 1}, {0, 1, 1, -1}, {0, 1, -1, 1}, {0, 1, -1, -1}, {0, -1, 1, 1}, {0, -1, 1, -1}, {0, -1, -1, 1}, {0, -1, -1, -1}, {1, 0, 1, 1}, {1, 0, 1, -1}, {1, 0, -1, 1}, {1, 0, -1, -1}, {-1, 0, 1, 1}, {-1, 0, 1, -1}, {-1, 0, -1, 1}, {-1, 0, -1, -1}, {1, 1, 0, 1}, {1, 1, 0, -1}, {1, -1, 0, 1}, {1, -1, 0, -1}, {-1, 1, 0, 1}, {-1, 1, 0, -1}, {-1, -1, 0, 1}, {-1, -1, 0, -1}, {1, 1, 1, 0}, {1, 1, -1, 0}, {1, -1, 1, 0}, {1, -1, -1, 0}, {-1, 1, 1, 0}, {-1, 1, -1, 0}, {-1, -1, 1, 0}, {-1, -1, -1, 0}};
        simplex = new int[][]{{0, 1, 2, 3}, {0, 1, 3, 2}, {0, 0, 0, 0}, {0, 2, 3, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 2, 3, 0}, {0, 2, 1, 3}, {0, 0, 0, 0}, {0, 3, 1, 2}, {0, 3, 2, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 3, 2, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 2, 0, 3}, {0, 0, 0, 0}, {1, 3, 0, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {2, 3, 0, 1}, {2, 3, 1, 0}, {1, 0, 2, 3}, {1, 0, 3, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {2, 0, 3, 1}, {0, 0, 0, 0}, {2, 1, 3, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {2, 0, 1, 3}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {3, 0, 1, 2}, {3, 0, 2, 1}, {0, 0, 0, 0}, {3, 1, 2, 0}, {2, 1, 0, 3}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {3, 1, 0, 2}, {0, 0, 0, 0}, {3, 2, 0, 1}, {3, 2, 1, 0}};
        instance = new SimplexNoiseGeneratorGPU();
    }

    protected double offsetW;

    protected SimplexNoiseGeneratorGPU() {
    }

    public SimplexNoiseGeneratorGPU(@NotNull Random rand) {
        this.offsetW = rand.nextDouble() * 256.0D;
    }

    @NotNull
    public static SimplexNoiseGeneratorGPU getInstance() {
        return instance;
    }

}
