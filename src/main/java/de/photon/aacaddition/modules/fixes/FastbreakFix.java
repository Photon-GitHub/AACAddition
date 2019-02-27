package de.photon.aacaddition.modules.fixes;

import de.photon.aacaddition.modules.ListenerModule;
import de.photon.aacaddition.modules.ModuleType;
import de.photon.aacaddition.user.User;
import de.photon.aacaddition.user.UserManager;
import me.konsolas.aac.api.HackType;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.EnumSet;

public class FastbreakFix implements ListenerModule
{
    private static final EnumSet<Material> AFFECTED_MATERIALS = EnumSet.of(Material.IRON_AXE, Material.DIAMOND_AXE);

    // Lowest priority to process before the break handler of AAC.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(final BlockBreakEvent event)
    {
        if (event.getBlock().getType() == Material.COCOA &&
            AFFECTED_MATERIALS.contains(event.getPlayer().getInventory().getItemInMainHand().getType()))
        {
            final User user = UserManager.getUser(event.getPlayer().getUniqueId());

            // Not bypassed
            if (User.isUserInvalid(user, this.getModuleType())) {
                return;
            }

            user.getFastbreakFixData().updateTimeStamp(0);
        }
    }

    @EventHandler
    public void onViolation(final PlayerViolationEvent event)
    {
        if (event.getHackType() == HackType.FASTBREAK) {
            final User user = UserManager.getUser(event.getPlayer().getUniqueId());

            // Not bypassed
            if (User.isUserInvalid(user, this.getModuleType())) {
                return;
            }

            if (user.getFastbreakFixData().recentlyUpdated(0, 1000)) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public ModuleType getModuleType()
    {
        return ModuleType.FASTBREAK_FIX;
    }
}
