package mc.euro.version.plugin;

import org.spongepowered.api.Sponge;

/**
 *
 * @author Nikolai
 */
public class SpongePlugin implements IPlugin {

    private String name;

    public SpongePlugin(String name) {
        this.name = name;
    }

    @Override
    public boolean isEnabled() {
        return Sponge.getGame().getPluginManager().getPlugin(name).isPresent();
    }

}
