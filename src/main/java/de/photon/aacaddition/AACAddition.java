package de.photon.aacaddition;

import de.photon.aacaddition.modules.Module;
import de.photon.aacaddition.modules.fixes.FastbreakFix;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AACAddition extends JavaPlugin
{
    private final Module fastbreakFix = new FastbreakFix();

    /**
     * This will get the object of the plugin registered on the server.
     *
     * @return the active instance of this plugin on the server.
     */
    public static AACAddition getInstance()
    {
        return AACAddition.getPlugin(AACAddition.class);
    }

    // Cache the config for better performance
    private FileConfiguration cachedConfig;

    /**
     * Registers a new {@link Listener} for AACAddition.
     *
     * @param listener the {@link Listener} which should be registered in the {@link org.bukkit.plugin.PluginManager}
     */
    public void registerListener(final Listener listener)
    {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public FileConfiguration getConfig()
    {
        if (cachedConfig == null) {
            this.saveDefaultConfig();
            cachedConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml"));
        }

        return cachedConfig;
    }

    @Override
    public void onEnable()
    {
        Module.enableModule(fastbreakFix);
    }

    @Override
    public void onDisable()
    {
        Module.disableModule(fastbreakFix);
    }
}
