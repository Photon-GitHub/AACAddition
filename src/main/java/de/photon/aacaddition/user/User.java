package de.photon.aacaddition.user;

import de.photon.aacaddition.InternalPermission;
import de.photon.aacaddition.modules.ModuleType;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class User
{
    private Player player;
    private TimeData fastbreakFixData = new TimeData(this, 0);

    public User(final Player player)
    {
        this.player = player;
        UserManager.setVerbose(this, InternalPermission.hasPermission(player, InternalPermission.VERBOSE));
    }

    public boolean isBypassed(ModuleType moduleType)
    {
        return InternalPermission.hasPermission(this.player, InternalPermission.BYPASS.getRealPermission() + '.' + moduleType.getConfigString().toLowerCase());
    }

    /**
     * @return true if the {@link User} is null or bypassed.
     */
    public static boolean isUserInvalid(final User user, final ModuleType moduleType)
    {
        return user == null || user.isBypassed(moduleType) || user.getPlayer() == null;
    }

    // Help the gc
    void unregister()
    {
        this.player = null;

        fastbreakFixData.unregister();
        fastbreakFixData = null;
    }
}
