package de.photon.aacaddition.modules;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum ModuleType
{
    // Fixes
    FASTBREAK_FIX("FastbreakFix"),
    FASTUSE_FIX("FastuseFix");

    @Getter
    @Setter(AccessLevel.PACKAGE)
    private boolean enabled;

    private final String configString;

    ModuleType(final String configString)
    {
        this.configString = configString;
    }
}