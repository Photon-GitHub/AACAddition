package de.photon.aacaddition.util;

import de.photon.aacaddition.AACAddition;
import de.photon.aacaddition.user.User;
import de.photon.aacaddition.user.UserManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public final class VerboseSender implements Listener
{
    @Getter
    private static final VerboseSender instance = new VerboseSender();

    // Message constants
    private static final String NON_COLORED_PRE_STRING = "[AACAddition] ";
    private static final String PRE_STRING = ChatColor.DARK_RED + NON_COLORED_PRE_STRING + ChatColor.GRAY;

    @Setter
    private boolean allowedToRegisterTasks;

    private final boolean writeToConsole = AACAddition.getInstance().getConfig().getBoolean("Verbose.console");
    private final boolean writeToPlayers = AACAddition.getInstance().getConfig().getBoolean("Verbose.players");

    private VerboseSender()
    {
        allowedToRegisterTasks = true;
        AACAddition.getInstance().registerListener(this);
    }

    /**
     * Sets off a standard verbose message (no console forcing and not flagged as an error).
     *
     * @param s the message that will be sent
     */
    public void sendVerboseMessage(final String s)
    {
        sendVerboseMessage(s, false, false);
    }

    /**
     * This sets off a verbose message.
     *
     * @param s             the message that will be sent
     * @param force_console whether the verbose message should appear in the console even when verbose for console is deactivated.
     * @param error         whether the message should be marked as an error
     */
    public void sendVerboseMessage(final String s, final boolean force_console, final boolean error)
    {
        // Remove color codes
        final String logMessage = ChatColor.stripColor(s);

        if (writeToConsole || force_console) {
            if (error) {
                Bukkit.getLogger().severe(NON_COLORED_PRE_STRING + logMessage);
            }
            else {
                Bukkit.getLogger().info(NON_COLORED_PRE_STRING + logMessage);
            }
        }

        // Prevent errors on disable as of scheduling
        if (allowedToRegisterTasks && writeToPlayers) {
            Bukkit.getScheduler().runTask(AACAddition.getInstance(), () -> {
                for (User user : UserManager.getVerboseUsers()) {
                    user.getPlayer().sendMessage(PRE_STRING + s);
                }
            });
        }
    }
}
