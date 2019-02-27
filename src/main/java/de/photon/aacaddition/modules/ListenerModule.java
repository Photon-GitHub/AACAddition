package de.photon.aacaddition.modules;

import de.photon.aacaddition.AACAddition;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Marks a {@link Module} which utilizes the {@link Listener} functionality.
 */
public interface ListenerModule extends Module, Listener
{
    /**
     * Additional chores needed to enable a {@link ListenerModule}
     */
    static void enable(final ListenerModule module)
    {
        AACAddition.getInstance().registerListener(module);
    }

    /**
     * Additional chores needed to disable a {@link ListenerModule}
     */
    static void disable(final ListenerModule module)
    {
        HandlerList.unregisterAll(module);
    }
}
