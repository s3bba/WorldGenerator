package me.sebbaindustries.worldgenerator.generator.gpu;

public class PerlinNoiseGeneratorGPU extends NoiseGeneratorGPU {
    protected static final int[][] grad3 = new int[][]{{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}};
    private static final PerlinNoiseGeneratorGPU instance = new PerlinNoiseGeneratorGPU();
    double[] GPU = {0, 0, 0, 0};
    int[] permGPU_tmp;

    protected PerlinNoiseGeneratorGPU() {
        int[] p = new int[]{151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180};

        for (int i = 0; i < 512; ++i) {
            this.perm[i] = p[i & 255];
        }

    }

    public void noise(double x, double y, double z) {
    }

    ;
    /*
        int X = (x >= 0.0D ? (int) x : (int) x - 1) & 255;
        int Y = (y >= 0.0D ? (int) y : (int) y - 1) & 255;
        int Z = (z >= 0.0D ? (int) z : (int) z - 1) & 255;
        x -= (x >= 0.0D ? (int) x : (int) x - 1);
        y -= (y >= 0.0D ? (int) y : (int) y - 1);
        z -= (z >= 0.0D ? (int) z : (int) z - 1);
        double fX = (x * x * x * (x * (x * 6.0D - 15.0D) + 10.0D));
        double fY = (y * y * y * (y * (y * 6.0D - 15.0D) + 10.0D));
        double fZ = (z * z * z * (z * (z * 6.0D - 15.0D) + 10.0D));
        int A = this.perm[X] + Y;
        int AA = this.perm[A] + Z;
        int AB = this.perm[A + 1] + Z;
        int B = this.perm[X + 1] + Y;
        int BA = this.perm[B] + Z;
        int BB = this.perm[B + 1] + Z;
        return lerp(fZ, lerp(fY, lerp(fX, grad(this.perm[AA], x, y, z), grad(this.perm[BA], x - 1.0D, y, z)), lerp(fX, grad(this.perm[AB], x, y - 1.0D, z), grad(this.perm[BB], x - 1.0D, y - 1.0D, z))), lerp(fY, lerp(fX, grad(this.perm[AA + 1], x, y, z - 1.0D), grad(this.perm[BA + 1], x - 1.0D, y, z - 1.0D)), lerp(fX, grad(this.perm[AB + 1], x, y - 1.0D, z - 1.0D), grad(this.perm[BB + 1], x - 1.0D, y - 1.0D, z - 1.0D))));
    }*/
/*   public double noise(double x, double y, double z) {
        GPU = new double[]{x, y, z, 0};
        permGPU_tmp = this.perm;
        this.setExplicit(true);
        this.put(GPU).put(permGPU_tmp).execute(Range.create(1)).get(GPU);
        this.dispose();
        return GPU[3];
    }

    @Override
    public void run() {
        double x = GPU[0];
        double y = GPU[1];
        double z = GPU[2];
        int[] permGPU = permGPU_tmp;
        int X = ((x >= 0.0D ? (int) x : (int) x - 1) & 255);
        int Y = ((y >= 0.0D ? (int) y : (int) y - 1) & 255);
        int Z = ((z >= 0.0D ? (int) z : (int) z - 1) & 255);
        x -= (x >= 0.0D ? (int) x : (int) x - 1);
        y -= (y >= 0.0D ? (int) y : (int) y - 1);
        z -= (z >= 0.0D ? (int) z : (int) z - 1);
        double fX = (x * x * x * (x * (x * 6.0D - 15.0D) + 10.0D));
        double fY = (y * y * y * (y * (y * 6.0D - 15.0D) + 10.0D));
        double fZ = (z * z * z * (z * (z * 6.0D - 15.0D) + 10.0D));
        int A = permGPU[X] + Y;
        int AA = permGPU[A] + Z;
        int AB = permGPU[A + 1] + Z;
        int B = permGPU[X + 1] + Y;
        int BA = permGPU[B] + Z;
        int BB = permGPU[B + 1] + Z;
        GPU[3] = lerp(fZ, lerp(fY, lerp(fX, grad(permGPU[AA], x, y, z), grad(permGPU[BA], x - 1.0D, y, z)), lerp(fX, grad(permGPU[AB], x, y - 1.0D, z), grad(permGPU[BB], x - 1.0D, y - 1.0D, z))), lerp(fY, lerp(fX, grad(permGPU[AA + 1], x, y, z - 1.0D), grad(permGPU[BA + 1], x - 1.0D, y, z - 1.0D)), lerp(fX, grad(permGPU[AB + 1], x, y - 1.0D, z - 1.0D), grad(permGPU[BB + 1], x - 1.0D, y - 1.0D, z - 1.0D))));
    }*/
}
