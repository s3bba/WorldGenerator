package me.sebbaindustries.worldgenerator.generator;

import com.aparapi.Kernel;
import com.aparapi.Range;
import me.sebbaindustries.worldgenerator.generator.gpu.SimplexOctaveGeneratorGPU;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class NewChunkGeneratorGPU extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        SimplexOctaveGeneratorGPU generator = new SimplexOctaveGeneratorGPU(new Random(world.getSeed()), 8); //GPU
        ChunkData chunk = createChunkData(world);
        generator.setScale(0.005D);
        int[][] currentHeight = new int[16][16];
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                for (int Xl = 0; Xl < 16; Xl++) {
                    for (int Zl = 0; Zl < 16; Zl++) {
                        currentHeight[Xl][Zl] = (int) generator.noise(chunkX * 16 + Xl, chunkZ * 16 + Zl, 0.5D, 0.5D);// ZA GPU
                    }
                }
            }
        };
        kernel.execute(Range.create(1));

      /*  {
            chunk.setBlock(X, currentHeight, Z, Material.GRASS_BLOCK);
            chunk.setBlock(X, currentHeight - 1, Z, Material.DIRT);
            for (int i = currentHeight - 2; i > 0; i--)
                chunk.setBlock(X, i, Z, Material.STONE);
            chunk.setBlock(X, 0, Z, Material.BEDROCK);
        }*/

        return chunk;
    }
}