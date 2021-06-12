package com.playernguyen.lognow.watch;

import com.playernguyen.lognow.LogNow;

public class DebugWatcher {
	private LogNow plugin;

	public DebugWatcher(LogNow plugin) {
		this.plugin = plugin;
	}

	/**
	 * Print any as debug type (prefix)
	 * 
	 * @param object an object to print
	 */
	public void debug(Object object) {
		if (isDebugging()) {
			this.plugin.getLogger().info(String.format("<debug> ~ %s", object.toString()));
		}
	}

	/**
	 * Build a map from iterable and print it out to console
	 * 
	 * @param iterable iterable object to print as map
	 */
	public void map(Iterable<?> iterable) {
		if (isDebugging()) {
			this.plugin.getLogger().info("--- debug map ---");
			int i = 0;
			for (Object object : iterable) {
				this.plugin.getLogger().info(String.format("  %d -> %s", i, object.toString()));
				i++;
			}
		}
	}

	/**
	 * Build a map from iterable with name and print it out to console
	 * 
	 * @param iterable iterable object to print as map.
	 * @param name     name of the title, perform in header title.
	 */
	public void map(Iterable<?> iterable, String name) {
		if (isDebugging()) {
			this.plugin.getLogger().info(String.format("--- debug map: %s ---", name));
			int i = 0;
			for (Object object : iterable) {
				this.plugin.getLogger().info(String.format("  %d -> %s", i, object.toString()));
				i++;
			}
		}
	}

	private boolean isDebugging() {
		return plugin.isDevelopment();
	}
}
