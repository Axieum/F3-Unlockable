package me.axieum.mcmod.f3unlockable.capability.f3;

import me.axieum.mcmod.f3unlockable.F3Unlockable;
import me.axieum.mcmod.f3unlockable.Settings;
import me.axieum.mcmod.f3unlockable.api.capability.IF3;
import me.axieum.mcmod.f3unlockable.api.util.F3Aspect;
import me.axieum.mcmod.f3unlockable.capability.CapabilityProviderSerializable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import javax.annotation.Nullable;

public final class CapabilityF3
{
    @CapabilityInject(IF3.class)
    public static final Capability<IF3> F3_CAPABILITY = null;

    public static final EnumFacing DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(F3Unlockable.MOD_ID, "F3");

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IF3.class, new Capability.IStorage<IF3>()
        {
            @Override
            public NBTBase writeNBT(Capability<IF3> capability, IF3 instance, EnumFacing side)
            {
                NBTTagList tag = new NBTTagList();
                for (F3Aspect aspect : instance.getAspects())
                    tag.appendTag(new NBTTagString(aspect.toString()));
                return tag;
            }

            @Override
            public void readNBT(Capability<IF3> capability, IF3 instance, EnumFacing side, NBTBase nbt)
            {
                for (NBTBase aspect : ((NBTTagList) nbt))
                {
                    String str = ((NBTTagString) aspect).getString();
                    if (F3Aspect.has(str))
                        instance.grantAspect(F3Aspect.getByName(str));
                }
            }
        }, () -> new F3(null));
    }

    @Nullable
    public static IF3 getF3(final EntityPlayer entity)
    {
        if (entity != null && entity.hasCapability(F3_CAPABILITY, DEFAULT_FACING))
            return entity.getCapability(F3_CAPABILITY, DEFAULT_FACING);

        return null;
    }

    public static ICapabilityProvider createProvider(final IF3 f3)
    {
        return new CapabilityProviderSerializable<>(F3_CAPABILITY, DEFAULT_FACING, f3);
    }

    @Mod.EventBusSubscriber
    private static class EventHandler
    {
        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event)
        {
            if (event.getObject() instanceof EntityPlayer)
            {
                final F3 f3 = new F3((EntityPlayer) event.getObject());
                event.addCapability(ID, createProvider(f3));
            }
        }

        @SubscribeEvent
        public static void onPlayerClone(final PlayerEvent.Clone event)
        {
            if (event.isWasDeath() && !Settings.PERSIST_DEATH)
                return;

            final IF3 oldF3 = getF3(event.getOriginal());
            final IF3 newF3 = getF3(event.getEntityPlayer());

            if (newF3 != null && oldF3 != null)
            {
                newF3.setAspects(oldF3.getAspects().toArray(new F3Aspect[0]));
                Minecraft.getMinecraft().addScheduledTask(newF3::synchronise);
            }
        }

        @SubscribeEvent
        public static void onPlayerLogin(final PlayerLoggedInEvent event)
        {
            final IF3 f3 = getF3(event.player);

            if (f3 != null)
                f3.synchronise();
        }

        @SubscribeEvent
        public static void onPlayerChangedDimension(final PlayerChangedDimensionEvent event)
        {
            final IF3 f3 = getF3(event.player);

            if (f3 != null)
                f3.synchronise();
        }
    }
}
