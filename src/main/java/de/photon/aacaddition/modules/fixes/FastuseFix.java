package de.photon.aacaddition.modules.fixes;

import de.photon.aacaddition.modules.ListenerModule;
import de.photon.aacaddition.modules.ModuleType;
import me.konsolas.aac.api.HackType;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class FastuseFix implements ListenerModule
{
    private final Map<UUID, Long> kelpMap = new WeakHashMap<>();

    @EventHandler
    public void onViolation(final PlayerViolationEvent event)
    {
        if (event.getHackType() == HackType.FASTUSE &&
            (System.currentTimeMillis() - kelpMap.getOrDefault(event.getPlayer().getUniqueId(), 0L)) < 20)
        {
            event.setCancelled(true);
            // Clear the map.
            kelpMap.remove(event.getPlayer().getUniqueId());
        }
    }

    // LOWEST priority to handle before AAC
    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemConsume(final PlayerItemConsumeEvent event)
    {
        if (event.getItem().getType() == Material.DRIED_KELP) {
            kelpMap.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
    }

    @Override
    public ModuleType getModuleType()
    {
        return ModuleType.FASTUSE_FIX;
    }
}
