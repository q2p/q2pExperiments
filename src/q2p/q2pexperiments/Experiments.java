package q2p.q2pexperiments;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import q2p.q2pexperiments.client.ClientPool;
import q2p.q2pexperiments.rings.LightningRing;

// Поджечь, телепортироваться, хил заклинание
public final class Experiments extends JavaPlugin implements Listener {
	public final void onEnable() {
		HubStatus.initilize(this);
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public final void onDisable() {
		HubStatus.deInitilize();
	}
	
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(GuideGenerator.parseCommand(command, sender, args)) return true;
		if(LogicTick.benchmarkCommand(command, sender, args)) return true;
		if(Magic.parseCommand(command, sender, args)) return true;
		
		return true;
	}
	
	// Восстановление здоровья
	@EventHandler
	public void onRegainHealth(EntityRegainHealthEvent event) {
		event.setCancelled(true);
	}
	// Изменение уровня
	@EventHandler
	public void onLevelChange(PlayerLevelChangeEvent event) {
		if(event.getNewLevel() != 0) {
			event.getPlayer().setLevel(0);
		}
	}
	// MOTD
	@EventHandler
	public final void onPing(final ServerListPingEvent event) {
		event.setServerIcon(IconsRenderer.ICONS[Assist.random(IconsRenderer.ICONS.length)]);
		event.setMotd("DanBit's Experiments");
	}
	// Нажатие лкм/пкм
	@EventHandler
	public final void onPlayerInteract(final PlayerInteractEvent event) {
		if(!LightningRing.RING.is(event.getItem()))
			return;
		
		final Action action = event.getAction();

		if(action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
			return;
		
		event.setCancelled(true);
		
		LightningRing.castAim(event.getPlayer());
	}
	// Нажатие пкм
	@EventHandler
	public final void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
		if(LightningRing.RING.is(event.getPlayer().getInventory().getItemInMainHand()))
			LightningRing.castAim(event.getPlayer());
	}
	// Нажатие пкм
	@EventHandler
	public final void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent event) {
		if(LightningRing.RING.is(event.getPlayer().getInventory().getItemInMainHand()))
			LightningRing.castAim(event.getPlayer());
	}
	// При подкулючении к серверу
	@EventHandler
	public final void onLogin(final PlayerLoginEvent event) {
		ClientPool.acceptPlayer(event);
	}
	// Передвижение
	@EventHandler
	public final void onMove(final PlayerMoveEvent event) {
		//TODO: 
	}
	// При входе в игру (без логина)
	@EventHandler
	public final void onJoin(final PlayerJoinEvent event) {
		ClientPool.onJoin(event);
	}
	// Отключение от сервера
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		ClientPool.onExit(event);
	}
	// Запрет крафта
	@EventHandler
	public void onCraft(CraftItemEvent event) {
		event.setCancelled(true);
	}
	// Уничтожение блока
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		
	}
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		
	}
	// Постановка блока
	@EventHandler 
	public void onBlockPlace(BlockPlaceEvent event) {
		
	}
	// Декоративный огонь TODO: может убрать если не нужен?
	@EventHandler
	public void onBurn(BlockBurnEvent event) {
		
	}
	@EventHandler
	public void onIgnite(BlockIgniteEvent event) {
		
	}
	@EventHandler
	public void onFade(BlockFadeEvent event) {
		
	}
	// Смерть
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		// TODO:
		event.setDroppedExp(0);
		event.setKeepInventory(false);
		event.setKeepLevel(false);
		event.setDeathMessage(null);
	}
	// Возрождение
	@EventHandler
	public final void onRespawn(final PlayerRespawnEvent event) {
		// TODO: 
	}
	// Урон
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		// TODO: 
	}
	// Никогда не голоден
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setFoodLevel(20);
	}
	// Заглушка стандартных команд
	private static final String[] blockCommands = new String[] {
		"ban",
		"banlist",
		"help",
		"list",
		"deop",
		"op",
		"pardon",
		"restart",
		"save-all",
		"reload",
		"stop",
		"achievement",
		"ban-ip",
		"banlist",
		"blockdata",
		"clear",
		"clone",
		"debug",
		"defaultgamemode",
		"difficulty",
		"effect",
		"enchant",
		"entitydata",
		"execute",
		"fill",
		"gamemode",
		"gamerule",
		"give",
		"kick",
		"kill",
		"me",
		"pardon-ip",
		"particle",
		"playsound",
		"plugins",
		"replaceitem",
		"save-off",
		"save-on",
		"say",
		"scoreboard",
		"seed",
		"setblock",
		"setidletimeout",
		"setworldspawn",
		"spawnpoint",
		"spreadplayers",
		"stats",
		"stopsound",
		"summon",
		"tell",
		"tellraw",
		"testfor",
		"testforblock",
		"testforblocks",
		"time",
		"timings",
		"title",
		"toggledownfall",
		"tp",
		"tps",
		"trigger",
		"version",
		"weather",
		"whitelist",
		"worldborder",
		"xp"
	};
	@EventHandler
	public final void onCommandPreProcess(final PlayerCommandPreprocessEvent event) {
		String ref = event.getMessage();
		if(ref.startsWith("/")) {
			final int idx = event.getMessage().indexOf(' ', 1);
			ref = ref.substring(1, (idx == -1) ? ref.length() : idx).toLowerCase();
			for(final String command : blockCommands) {
				if(ref.equals(command)) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	// Сообщение в чат
	@EventHandler
	public final void onChat(final AsyncPlayerChatEvent event) {
		//TODO:
	}
	// Взрыв блока самостоятельно
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
		event.setCancelled(true);
	}
	// Взрыв существа или блока TNT
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		
	}
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		
	}
	@EventHandler
	public void onWorldSave(WorldSaveEvent event) {
		//TODO:
	}
	// Сброс предмета
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		
	}
	// Избавление от мусора
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		
	}
	// Постоянная не дождливая погода
	@EventHandler
	public final void onWeatherChange(final WeatherChangeEvent event) {
		
	}
	@EventHandler
	public final void onThunerChange(final ThunderChangeEvent event) {
		
	}
	// Молния
	public final void onLightning(final LightningStrikeEvent event) {
		
	}
	// Перетекание воды и лавы
	public final void onBlockFromTo(final BlockFromToEvent event) {
		
	}
}