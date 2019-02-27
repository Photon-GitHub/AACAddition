package de.photon.aacaddition;

import lombok.Getter;
import org.bukkit.permissions.Permissible;

public enum InternalPermission
{
    BYPASS("aac.bypass"),
    VERBOSE("aac.verbose");

    @Getter
    private final String realPermission;

    InternalPermission(final String realPermission)
    {
        this.realPermission = realPermission;
    }

    /**
     * This checks if a {@link Permissible} has a certain permission.
     *
     * @param permissible the {@link Permissible} who should be checked
     * @param permission  the permission that he should be checked for
     *
     * @return true if the player has the permission or is op, otherwise false
     */
    public static boolean hasPermission(final Permissible permissible, final InternalPermission permission)
    {
        return permission == null || hasPermission(permissible, permission.realPermission);
    }

    /**
     * This checks if a {@link Permissible} has a certain permission.
     *
     * @param permissible the {@link Permissible} who should be checked
     * @param permission  the permission that he should be checked for
     *
     * @return true if the player has the permission or is op, otherwise false
     */
    public static boolean hasPermission(final Permissible permissible, final String permission)
    {
        return permission == null || permissible.isOp() || permissible.hasPermission(permission);
    }
}