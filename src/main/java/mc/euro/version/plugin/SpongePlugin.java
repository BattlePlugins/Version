package mc.euro.version.plugin;

import org.spongepowered.common.Sponge;

/**
 *
 * @author Nikolai
 */
public class SpongePlugin extends IPlugin {

    String name;

    public SpongePlugin(String name) {
        this.name = name;
    }

    @Override
    public boolean isEnabled() {
        return Sponge.getGame().getPluginManager().getPlugin(name).isPresent();
    }

}
