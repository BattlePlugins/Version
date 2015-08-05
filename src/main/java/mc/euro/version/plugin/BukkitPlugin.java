package mc.euro.version.plugin;

import org.bukkit.plugin.Plugin;

/**
 * 
 * @author Nikolai
 */
public class BukkitPlugin extends IPlugin {
    
    Plugin plugin;
    
    public BukkitPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return this.plugin.isEnabled();
    }

}
