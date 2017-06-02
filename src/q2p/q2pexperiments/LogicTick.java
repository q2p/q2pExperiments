package q2p.q2pexperiments;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import q2p.q2pexperiments.client.ClientPool;

public final class LogicTick implements Runnable {
	private static long last = 0;
	private static long tps = 0;
	private static boolean needBenchmark = false;
	
	public final void run() {
		if(needBenchmark) {
			if(System.currentTimeMillis()-last > 1000) {
				last = System.currentTimeMillis();
				Log.info("Benchmark " + tps+" tps.");
				tps = 0;
			}
			tps++;
		}
		//WorldManager.lobby.setFullTime(6000);
		//TODO:
		//WorldManager.lobby.setFullTime(Assist.worldTimeFromRealTime());
	}
	
	public static final boolean benchmarkCommand(final Command command, final CommandSender sender, final String[] args) {
		if(!command.getName().equals("benchmark") || !ClientPool.isAdmin(sender))
			return false;
		
		needBenchmark = !needBenchmark;
		tps = 0;
		last = System.currentTimeMillis();
		Log.info("Benchmark turned o"+(needBenchmark?"n.":"ff."));
		
		return true;
	}
}