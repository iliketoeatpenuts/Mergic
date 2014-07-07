package no.runsafe.mergic.magic.spells;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.mergic.KillManager;
import no.runsafe.mergic.magic.MagicSchool;

public class EarthBolt extends Bolt
{
    public EarthBolt(IScheduler scheduler, KillManager manager)
    {
        super(Item.BuildingBlock.Stone, scheduler, manager);
    }

    @Override
    public String getName()
    {
        return "Earth Bolt";
    }

    @Override
    public MagicSchool getSchool()
    {
        return MagicSchool.EARTH;
    }
}
