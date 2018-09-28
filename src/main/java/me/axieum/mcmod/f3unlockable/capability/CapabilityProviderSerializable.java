package me.axieum.mcmod.f3unlockable.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class CapabilityProviderSerializable<HANDLER> extends CapabilityProviderSimple<HANDLER> implements INBTSerializable<NBTBase>
{
    public CapabilityProviderSerializable(final Capability<HANDLER> capability, @Nullable final EnumFacing facing)
    {
        this(capability, facing, capability.getDefaultInstance());
    }

    public CapabilityProviderSerializable(final Capability<HANDLER> capability, @Nullable final EnumFacing facing, @Nullable final HANDLER instance)
    {
        super(capability, facing, instance);
    }

    @Nullable
    @Override
    public NBTBase serializeNBT()
    {
        return getCapability().writeNBT(getInstance(), getFacing());
    }

    @Override
    public void deserializeNBT(final NBTBase nbt)
    {
        getCapability().readNBT(getInstance(), getFacing(), nbt);
    }
}
