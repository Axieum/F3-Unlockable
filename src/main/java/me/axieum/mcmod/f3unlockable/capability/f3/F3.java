package me.axieum.mcmod.f3unlockable.capability.f3;

import com.google.common.collect.Lists;
import me.axieum.mcmod.f3unlockable.F3Unlockable;
import me.axieum.mcmod.f3unlockable.api.capability.IF3;
import me.axieum.mcmod.f3unlockable.api.util.F3Aspect;
import me.axieum.mcmod.f3unlockable.network.MessageF3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class F3 implements IF3
{
    private final EntityPlayer entity;
    private List<F3Aspect> aspects = new ArrayList<>();

    public F3(@Nullable final EntityPlayer entity)
    {
        this.entity = entity;
    }

    @Override
    public List<F3Aspect> getAspects()
    {
        return this.aspects;
    }

    @Override
    public boolean hasAspect(F3Aspect... aspects)
    {
        if (aspects == null)
            return false;

        return this.aspects.containsAll(Lists.newArrayList(aspects));
    }

    @Override
    public void setAspects(F3Aspect... aspects)
    {
        if (aspects == null || aspects.length < 1)
            this.clearAspects();
        else
            this.aspects = Lists.newArrayList(aspects);
    }

    @Override
    public void clearAspects()
    {
        this.aspects.clear();
    }

    @Override
    public void grantAspect(F3Aspect... aspects)
    {
        for (F3Aspect aspect : aspects)
            if (!this.hasAspect(aspect))
                this.aspects.add(aspect);
    }

    @Override
    public void revokeAspect(F3Aspect... aspects)
    {
        this.aspects.removeAll(Lists.newArrayList(aspects));
    }

    @Override
    public void synchronise()
    {
        if (entity == null || entity.getEntityWorld().isRemote)
            return;

        F3Unlockable.network.sendTo(new MessageF3(aspects.toArray(new F3Aspect[0])), (EntityPlayerMP) entity);
    }
}
