package me.axieum.mcmod.f3unlockable;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

@Config(modid = F3Unlockable.MOD_ID)
public class Settings
{
    @Config.LangKey("config.persistDeath")
    public static boolean PERSIST_DEATH = true;

    @Config.LangKey("config.category.replacements")
    public static Replacements REPLACEMENTS = new Replacements();

    public static class Replacements
    {
        @Config.LangKey("config.replacements.x")
        public String X = "&7&kxxxxx";

        @Config.LangKey("config.replacements.y")
        public String Y = "&7&kxxxxx";

        @Config.LangKey("config.replacements.z")
        public String Z = "&7&kxxxxx";

        @Config.LangKey("config.replacements.chunk.x")
        public String CHUNK_X = "&7&kxxx";

        @Config.LangKey("config.replacements.chunk.y")
        public String CHUNK_Y = "&7&kxxx";

        @Config.LangKey("config.replacements.chunk.z")
        public String CHUNK_Z = "&7&kxxx";

        @Config.LangKey("config.replacements.facing")
        public String FACING = "&7&kxxxxx";

        @Config.LangKey("config.replacements.day")
        public String DAY = "&7&kxxx";

        @Config.LangKey("config.replacements.difficulty")
        public String DIFFICULTY = "&7&kxxx";
    }

    public static void sync()
    {
        ConfigManager.sync(F3Unlockable.MOD_ID, Config.Type.INSTANCE);
    }
}
