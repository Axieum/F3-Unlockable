package me.axieum.mcmod.f3unlockable.api.capability;

import me.axieum.mcmod.f3unlockable.api.util.F3Aspect;

import java.util.List;

public interface IF3
{
    /**
     * Get the granted aspects.
     *
     * @return The list of granted aspects
     */
    List<F3Aspect> getAspects();

    /**
     * Is aspect(s) granted?
     *
     * @param aspects The aspects to query
     * @return Whether all queried aspects are present
     */
    boolean hasAspect(final F3Aspect... aspects);

    /**
     * Set the granted aspects.
     *
     * @param aspects The granted aspects
     */
    void setAspects(final F3Aspect... aspects);

    /**
     * Clears the granted aspects (revokes all).
     */
    void clearAspects();

    /**
     * Grant aspect(s).
     *
     * @param aspects The aspect(s) to grant
     */
    void grantAspect(final F3Aspect... aspects);

    /**
     * Revoke aspect(s).
     *
     * @param aspects The aspect(s) to revoke
     */
    void revokeAspect(final F3Aspect... aspects);

    /**
     * Synchronise the aspects to watching clients.
     */
    void synchronise();
}
