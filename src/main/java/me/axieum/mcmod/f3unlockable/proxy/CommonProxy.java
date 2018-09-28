package me.axieum.mcmod.f3unlockable.proxy;

import me.axieum.mcmod.f3unlockable.F3Unlockable;
import me.axieum.mcmod.f3unlockable.Settings;
import me.axieum.mcmod.f3unlockable.capability.f3.CapabilityF3;
import me.axieum.mcmod.f3unlockable.network.MessageF3;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        CapabilityF3.register();

        F3Unlockable.network.registerMessage(MessageF3.Handler.class, MessageF3.class, 1, Side.CLIENT);
    }

    public void init(FMLInitializationEvent event)
    {
        Settings.sync();
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        //
    }

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(F3Unlockable.MOD_ID))
            Settings.sync();
    }
}
