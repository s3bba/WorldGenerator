package me.sebbaindustries.worldgenerator;

import com.aparapi.Kernel;
import com.aparapi.Range;
import me.sebbaindustries.worldgenerator.generator.NewChunkGenerator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new NewChunkGenerator();
    }
}
