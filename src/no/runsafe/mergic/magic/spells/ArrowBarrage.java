package no.runsafe.mergic.magic.spells;

import no.runsafe.framework.minecraft.entity.ProjectileEntity;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.mergic.magic.MagicSchool;
import no.runsafe.mergic.magic.Spell;
import no.runsafe.mergic.magic.SpellHandler;
import no.runsafe.mergic.magic.SpellType;

public class ArrowBarrage implements Spell
{
	@Override
	public int getCooldown()
	{
		return 10;
	}

	@Override
	public String getName()
	{
		return "Arrow Barrage";
	}

	@Override
	public MagicSchool getSchool()
	{
		return MagicSchool.ARCANE;
	}

	@Override
	public SpellType getType()
	{
		return SpellType.PROJECTILE;
	}

	@Override
	public void onCast(final RunsafePlayer player)
	{
		int current = 1;

		// Shoot X arrows at the time provided.
		while (current < 10)
		{
			long time = current * 7;
			SpellHandler.scheduler.startSyncTask(new Runnable() {
				@Override
				public void run() {
					player.Launch(ProjectileEntity.Arrow);
				}
			}, time);
			current++;
		}
	}
}
