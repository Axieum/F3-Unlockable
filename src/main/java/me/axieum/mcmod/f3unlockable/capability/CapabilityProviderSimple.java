package me.axieum.mcmod.f3unlockable.capability;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class CapabilityProviderSimple<HANDLER> implements ICapabilityProvider
{
    protected final Capability<HANDLER> capability;
    protected final EnumFacing facing;
    protected final HANDLER instance;

    public CapabilityProviderSimple(final Capability<HANDLER> capability, @Nullable final EnumFacing facing, @Nullable final HANDLER instance)
    {
        this.capability = capability;
        this.facing = facing;
        this.instance = instance;
    }

    @Deprecated
    public CapabilityProviderSimple(@Nullable final HANDLER instance, final Capability<HANDLER> capability, @Nullable final EnumFacing facing)
    {
        this(capability, facing, instance);
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, @Nullable final EnumFacing facing)
    {
        return capability == getCapability();
    }

    @Override
    @Nullable
    public <T> T getCapability(final Capability<T> capability, @Nullable final EnumFacing facing)
    {
        if (capability == getCapability())
        {
            return getCapability().cast(getInstance());
        }

        return null;
    }

    public final Capability<HANDLER> getCapability()
    {
        return capability;
    }

    @Nullable
    public EnumFacing getFacing()
    {
        return facing;
    }

    @Nullable
    public final HANDLER getInstance()
    {
        return instance;
    }
}
