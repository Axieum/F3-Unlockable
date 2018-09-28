package me.axieum.mcmod.f3unlockable;

import me.axieum.mcmod.f3unlockable.command.F3Command;
import me.axieum.mcmod.f3unlockable.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = F3Unlockable.MOD_ID,
     name = F3Unlockable.MOD_NAME,
     version = F3Unlockable.MOD_VERSION,
     dependencies = F3Unlockable.MOD_DEPENDENCIES,
     useMetadata = true)
public class F3Unlockable
{
    public static final String MOD_ID = "f3unlockable";
    public static final String MOD_NAME = "F3 Unlockable";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "";

    @Mod.Instance(F3Unlockable.MOD_ID)
    public static F3Unlockable instance;

    @SidedProxy(clientSide = "me.axieum.mcmod.f3unlockable.proxy.ClientProxy",
                serverSide = "me.axieum.mcmod.f3unlockable.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new F3Command());
    }
}
