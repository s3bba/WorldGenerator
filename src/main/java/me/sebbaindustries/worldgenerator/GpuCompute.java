package me.sebbaindustries.worldgenerator;

import com.aparapi.Kernel;
import org.bukkit.World;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

//HERE YOU CAN SEE THE ABOMINATIONS WE TRIED.
public class GpuCompute {
    public static double noise(double xin, double yin, double frequency, double amplitude, World world) {
        int octaves = 8;
        int[] counter = {0};
        double[] result = {0};
        double[] amp = {1};
        double[] freq = {1};
        double[] x = {xin * 0.005D};
        double[] y = {yin * 0.005D};
        NoiseGenerator[] temp = new NoiseGenerator[8];
        for (int j = 0; j < octaves; j++) {
            temp[j] = new SimplexNoiseGenerator(world.getSeed());
        }
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                if (counter[0] == 0) {
                    result[0] += temp[0].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }
                if (counter[0] == 1) {
                    result[0] += temp[1].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }
                if (counter[0] == 2) {
                    result[0] += temp[2].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }
                if (counter[0] == 3) {
                    result[0] += temp[3].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }
                if (counter[0] == 4) {
                    result[0] += temp[4].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }
                if (counter[0] == 5) {
                    result[0] += temp[5].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }
                if (counter[0] == 6) {
                    result[0] += temp[6].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }
                if (counter[0] == 7) {
                    result[0] += temp[7].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }
                if (counter[0] == 8) {
                    result[0] += temp[8].noise(x[0] * freq[0] + y[0] * freq[0]) * amp[0];
                }

                freq[0] *= frequency;
                amp[0] *= amplitude;

                if (result[0] >= 255) {
                    result[0] = 254;
                } else if (result[0] <= 0) {
                    result[0] = 1;
                }
            }
        };
        //kernel.put(result);
        //kernel.put(amp);
        //kernel.put(freq);
        //kernel.put(x);
        //kernel.put(y);

        kernel.setExecutionModeWithoutFallback(Kernel.EXECUTION_MODE.GPU);
        for (int i = 0; i < octaves; i++) {
            counter[0] = i;
            kernel.run();

            //Bukkit.getConsoleSender().sendMessage(String.valueOf(result[0]));
        }
        //kernel.execute(Range.create(8));
        //kernel.get(result);
        //Bukkit.getConsoleSender().sendMessage(String.valueOf(kernel.getTargetDevice()));
        return result[0];
    }
}
