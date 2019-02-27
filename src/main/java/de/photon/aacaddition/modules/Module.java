package de.photon.aacaddition.modules;

import de.photon.aacaddition.AACAddition;
import de.photon.aacaddition.util.VerboseSender;

public interface Module
{
    /**
     * This enables the check according to its interfaces.
     */
    static void enableModule(final Module module)
    {
        try {

            // Enabled
            if (!AACAddition.getInstance().getConfig().getBoolean(module.getConfigString() + ".enabled")) {
                sendNotice(module, module.getConfigString() + " was chosen not to be enabled.");
                return;
            }

            if (module instanceof ListenerModule) {
                ListenerModule.enable((ListenerModule) module);
            }

            if (module instanceof PacketListenerModule) {
                PacketListenerModule.enable((PacketListenerModule) module);
            }
            module.enable();

            sendNotice(module, module.getConfigString() + " has been enabled.");
        } catch (final Exception e) {
            VerboseSender.getInstance().sendVerboseMessage(module.getConfigString() + " could not be enabled.", true, true);
            e.printStackTrace();
        }
    }

    /**
     * This disables the check according to its interfaces.
     */
    static void disableModule(final Module module)
    {
        try {
            if (module instanceof ListenerModule) {
                ListenerModule.disable((ListenerModule) module);
            }

            if (module instanceof PacketListenerModule) {
                PacketListenerModule.disable((PacketListenerModule) module);
            }

            module.disable();

            sendNotice(module, module.getConfigString() + " has been disabled.");
        } catch (final Exception e) {
            VerboseSender.getInstance().sendVerboseMessage(module.getConfigString() + " could not be disabled.", true, true);
            e.printStackTrace();
        }
    }

    /**
     * Sends a message if {@link Module#shouldNotify()} returns true.
     */
    static void sendNotice(final Module module, final String message)
    {
        if (module.shouldNotify()) {
            VerboseSender.getInstance().sendVerboseMessage(message, true, false);
        }
    }

    /**
     * All additional chores during enabling that are not handled by the {@link Module} - subinterfaces.
     */
    default void enable() {}

    /**
     * All additional chores during disabling that are not handled by the {@link Module} - subinterfaces.
     */
    default void disable() {}

    /**
     * Whether or not there are messages regarding this module when enabled/disabled.
     */
    default boolean shouldNotify()
    {
        return true;
    }

    /**
     * Gets the direct path representing this module in the config.
     */
    default String getConfigString()
    {
        return this.getModuleType().getConfigString();
    }

    /**
     * Gets the {@link ModuleType} of this {@link Module}
     */
    ModuleType getModuleType();
}
