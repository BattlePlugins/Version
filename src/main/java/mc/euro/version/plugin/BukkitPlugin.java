package mc.euro.version.plugin;

import org.bukkit.plugin.Plugin;

/**
 * 
 * @author Nikolai
 */
public class BukkitPlugin implements IPlugin {
    
    private Plugin plugin;
    
    public BukkitPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return (plugin != null) && (plugin.isEnabled());
    }

}
