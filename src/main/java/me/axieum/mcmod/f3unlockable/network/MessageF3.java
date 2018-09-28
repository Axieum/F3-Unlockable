package me.axieum.mcmod.f3unlockable.network;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import me.axieum.mcmod.f3unlockable.api.util.F3Aspect;
import me.axieum.mcmod.f3unlockable.capability.f3.CapabilityF3;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;

public class MessageF3 implements IMessage
{
    private List<F3Aspect> aspects;

    public MessageF3()
    {
        this.aspects = new ArrayList<>();
    }

    public MessageF3(F3Aspect... aspects)
    {
        this.aspects = Lists.newArrayList(aspects);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        aspects.clear();
        final int aspectCount = buf.readInt();
        for (int i = 0; i < aspectCount; i++)
        {
            String str = ByteBufUtils.readUTF8String(buf);
            if (F3Aspect.has(str))
                aspects.add(F3Aspect.getByName(str));
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(aspects.size());
        for (F3Aspect aspect : aspects)
            ByteBufUtils.writeUTF8String(buf, aspect.toString());
    }

    public static class Handler implements IMessageHandler<MessageF3, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageF3 message, final MessageContext ctx)
        {
            Minecraft.getMinecraft()
                     .addScheduledTask(() -> CapabilityF3.getF3(Minecraft.getMinecraft().player)
                                                         .setAspects(message.aspects.toArray(new F3Aspect[0])));

            return null;
        }
    }
}
