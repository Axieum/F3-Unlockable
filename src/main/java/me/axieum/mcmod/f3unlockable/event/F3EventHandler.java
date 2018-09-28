package me.axieum.mcmod.f3unlockable.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.axieum.mcmod.f3unlockable.Settings;
import me.axieum.mcmod.f3unlockable.api.capability.IF3;
import me.axieum.mcmod.f3unlockable.api.util.F3Aspect;
import me.axieum.mcmod.f3unlockable.capability.f3.CapabilityF3;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class F3EventHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDebugScreen(RenderGameOverlayEvent.Text event)
    {
        // Bail if debug information is not being shown.
        if (!Minecraft.getMinecraft().gameSettings.showDebugInfo)
            return;

        /// Retrieve the list of aspects that are granted.
        IF3 f3capability = CapabilityF3.getF3(Minecraft.getMinecraft().player);
        if (f3capability == null)
            return; // Highly unlikely scenario, but may prevent an NPE.

        // Aspects to replace:
        HashMap<String, String> replacements = new HashMap<>();

        putXYZ(replacements, f3capability); // X / Y / Z
        putBlock(replacements, f3capability); // Block | Looking at
        putChunk(replacements, f3capability); // Chunk
        putFacing(replacements, f3capability); // Facing
        putDifficultyDay(replacements, f3capability); // Difficulty / Day

        // Obfuscate aspects of the F3 menu.
        event.getLeft().replaceAll((msg) -> {
            for (Map.Entry<String, String> e : replacements.entrySet())
                msg = msg.replaceAll(e.getKey(), e.getValue());
            return msg;
        });
    }

    private static void putBlock(HashMap<String, String> replacements, IF3 cap)
    {
        final String regex = "(Block|Looking at): ([0-9.-]+) ([0-9.-]+) ([0-9.-]+)";
        String replacement = "$1: $2 $3 $4";

        if (!cap.hasAspect(F3Aspect.X) && Settings.REPLACEMENTS.X.length() > 0)
            replacement = replacement.replace("$2", format(Settings.REPLACEMENTS.X));
        if (!cap.hasAspect(F3Aspect.Y) && Settings.REPLACEMENTS.Y.length() > 0)
            replacement = replacement.replace("$3", format(Settings.REPLACEMENTS.Y));
        if (!cap.hasAspect(F3Aspect.Z) && Settings.REPLACEMENTS.Z.length() > 0)
            replacement = replacement.replace("$4", format(Settings.REPLACEMENTS.Z));

        replacements.put(regex, replacement);
    }

    private static void putXYZ(HashMap<String, String> replacements, IF3 cap)
    {
        final String regex = "XYZ: ([0-9.-]+) / ([0-9.-]+) / ([0-9.-]+)";
        String replacement = "XYZ: $1 / $2 / $3";

        if (!cap.hasAspect(F3Aspect.X) && Settings.REPLACEMENTS.X.length() > 0)
            replacement = replacement.replace("$1", format(Settings.REPLACEMENTS.X));
        if (!cap.hasAspect(F3Aspect.Y) && Settings.REPLACEMENTS.Y.length() > 0)
            replacement = replacement.replace("$2", format(Settings.REPLACEMENTS.Y));
        if (!cap.hasAspect(F3Aspect.Z) && Settings.REPLACEMENTS.Z.length() > 0)
            replacement = replacement.replace("$3", format(Settings.REPLACEMENTS.Z));

        replacements.put(regex, replacement);
    }

    private static void putDifficultyDay(HashMap<String, String> replacements, IF3 cap)
    {
        final String regex = "Local Difficulty: ([0-9.-]+) // ([0-9.-]+) \\(Day ([0-9]+)\\)";
        String replacement = "Local Difficulty: $1 // $2 (Day $3)";

        if (!cap.hasAspect(F3Aspect.DIFFICULTY) && Settings.REPLACEMENTS.DIFFICULTY.length() > 0)
            replacement = replacement.replace("$1", format(Settings.REPLACEMENTS.DIFFICULTY))
                                     .replace("$2", format(Settings.REPLACEMENTS.DIFFICULTY));
        if (!cap.hasAspect(F3Aspect.DAY) && Settings.REPLACEMENTS.DAY.length() > 0)
            replacement = replacement.replace("$3", format(Settings.REPLACEMENTS.DAY));

        replacements.put(regex, replacement);
    }

    private static void putFacing(HashMap<String, String> replacements, IF3 cap)
    {
        final String regex = "Facing: ([A-Za-z]+) \\(([A-Za-z ]+)\\) \\(([0-9.-]+) / ([0-9.-]+)\\)";
        String replacement = "Facing: $1 ($2) ($3 / $4)";

        if (!cap.hasAspect(F3Aspect.FACING) && Settings.REPLACEMENTS.FACING.length() > 0)
            replacement = replacement.replace("$1", format(Settings.REPLACEMENTS.FACING))
                                     .replace("$2", format(Settings.REPLACEMENTS.FACING))
                                     .replace("$3", format(Settings.REPLACEMENTS.FACING));

        replacements.put(regex, replacement);
    }

    private static void putChunk(HashMap<String, String> replacements, IF3 cap)
    {
        final String regex = "Chunk: ([0-9.-]+) ([0-9.-]+) ([0-9.-]+) in ([0-9.-]+) ([0-9.-]+) ([0-9.-]+)";
        String replacement = "Chunk: $1 $2 $3 in $4 $5 $6";

        if (!cap.hasAspect(F3Aspect.CHUNK_X) && Settings.REPLACEMENTS.CHUNK_X.length() > 0)
            replacement = replacement.replace("$1", format(Settings.REPLACEMENTS.CHUNK_X))
                                     .replace("$4", format(Settings.REPLACEMENTS.CHUNK_X));
        if (!cap.hasAspect(F3Aspect.CHUNK_Y) && Settings.REPLACEMENTS.CHUNK_Y.length() > 0)
            replacement = replacement.replace("$2", format(Settings.REPLACEMENTS.CHUNK_Y))
                                     .replace("$5", format(Settings.REPLACEMENTS.CHUNK_Y));
        if (!cap.hasAspect(F3Aspect.CHUNK_Z) && Settings.REPLACEMENTS.CHUNK_Z.length() > 0)
            replacement = replacement.replace("$3", format(Settings.REPLACEMENTS.CHUNK_Z))
                                     .replace("$6", format(Settings.REPLACEMENTS.CHUNK_Z));

        replacements.put(regex, replacement);
    }

    private static String format(String msg)
    {
        return msg.replace('&', ChatFormatting.PREFIX_CODE) + ChatFormatting.RESET;
    }
}
