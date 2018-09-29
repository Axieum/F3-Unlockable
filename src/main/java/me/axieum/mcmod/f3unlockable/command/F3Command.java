package me.axieum.mcmod.f3unlockable.command;

import com.google.common.collect.Lists;
import me.axieum.mcmod.f3unlockable.api.capability.IF3;
import me.axieum.mcmod.f3unlockable.api.util.F3Aspect;
import me.axieum.mcmod.f3unlockable.capability.f3.CapabilityF3;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class F3Command extends CommandBase
{
    @Nonnull
    @Override
    public String getName()
    {
        return "f3";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender)
    {
        return "command.f3.usage";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException
    {
        if (args.length < 3)
            throw new WrongUsageException(getUsage(sender));

        // Ensure first argument is "grant" or "revoke" and correct length.
        if (!"grant".equalsIgnoreCase(args[0]) && !"revoke".equalsIgnoreCase(args[0]))
            throw new WrongUsageException(getUsage(sender));

        // Ensure valid debug aspect was given.
        if (!"all".equalsIgnoreCase(args[1]) && !F3Aspect.has(args[1]))
            throw new CommandException("command.f3.error.aspect", args[1]);

        // Attempt to retrieve the specified player and thus the F3 storage capability.
        EntityPlayer player = getPlayer(server, sender, args[2]);
        IF3 f3capability = CapabilityF3.getF3(player);

        // Determine the aspect(s) to be granted/revoked.
        List<F3Aspect> aspects = new ArrayList<>();
        if ("all".equalsIgnoreCase(args[1]))
            aspects.addAll(Lists.newArrayList(F3Aspect.values()));
        else
            aspects.add(F3Aspect.getByName(args[1]));

        // Grant/Revoke the specified aspect.
        if ("grant".equalsIgnoreCase(args[0]))
        {
            f3capability.grantAspect(aspects.toArray(new F3Aspect[0]));

            sender.sendMessage(new TextComponentTranslation("command.f3.success.aspect.grant",
                                                            args[1].toUpperCase(),
                                                            player.getDisplayName()));
        }
        else if ("revoke".equalsIgnoreCase(args[0]))
        {
            f3capability.revokeAspect(aspects.toArray(new F3Aspect[0]));

            sender.sendMessage(new TextComponentTranslation("command.f3.success.aspect.revoke",
                                                            args[1].toUpperCase(),
                                                            player.getDisplayName()));
        }

        f3capability.synchronise();
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "grant", "revoke");

        if (args.length == 2)
        {
            List<String> possibilities = Lists.newArrayList(F3Aspect.allNames());
            possibilities.add("all");
            return getListOfStringsMatchingLastWord(args, possibilities);
        }

        if (args.length == 3)
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());

        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 2;
    }
}
