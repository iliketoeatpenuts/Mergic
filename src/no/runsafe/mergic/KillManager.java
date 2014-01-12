package no.runsafe.mergic;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class KillManager implements IEntityDamageByEntityEvent
{
	public KillManager(IServer server)
	{
		this.server = server;
	}

	@Override
	public void OnEntityDamageByEntity(RunsafeEntityDamageByEntityEvent event)
	{
		RunsafeEntity entity = event.getEntity();

		if (entity instanceof IPlayer)
		{
			IPlayer victim = (IPlayer) entity;
			RunsafeEntity attackingEntity = event.getDamageActor();

			if (attackingEntity instanceof IPlayer)
			{
				IPlayer attacker = (IPlayer) attackingEntity;
				registerAttack(victim, attacker);
			}
			else if (attackingEntity instanceof RunsafeProjectile)
			{
				RunsafeProjectile projectile = (RunsafeProjectile) attackingEntity;
				IPlayer shooterPlayer = projectile.getShooterPlayer();

				if (shooterPlayer != null)
					registerAttack(victim, shooterPlayer);
			}
		}
	}

	public void wipeAllData()
	{
		lastDamage.clear();
	}

	public void wipePlayerData(IPlayer player)
	{
		lastDamage.remove(player.getName());
	}

	public void registerAttack(IPlayer victim, IPlayer attacker)
	{
		if (!attacker.isVanished() && !victim.isVanished())
			lastDamage.put(victim.getName(), attacker.getName());
	}

	public void OnPlayerKilled(IPlayer player)
	{
		String playerName = player.getName();
		if (lastDamage.containsKey(playerName))
		{
			String killerName = lastDamage.get(playerName);
			IPlayer killer = server.getPlayerExact(killerName);

			if (killer != null)
			{
				int newKills = getPlayerKills(killer) + 1;
				killCount.put(killerName, newKills);
				killer.setLevel(newKills);
			}
		}
	}

	public IPlayer getKiller(IPlayer player)
	{
		String playerName = player.getName();
		if (lastDamage.containsKey(playerName))
			return server.getPlayerExact(lastDamage.get(playerName));

		return null;
	}

	public int getPlayerKills(IPlayer player)
	{
		String playerName = player.getName();
		return killCount.containsKey(playerName) ? killCount.get(playerName) : 0;
	}

	public Map<String, Integer> getScoreList()
	{
		return MapUtil.sortByValue(killCount);
	}

	private final IServer server;
	private HashMap<String, String> lastDamage = new HashMap<String, String>();
	private HashMap<String, Integer> killCount = new HashMap<String, Integer>();
}
