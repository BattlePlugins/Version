package mc.euro.version;

import mc.euro.version.internal.Platform;

/**
 * Handles the construction of new Version objects for BUKKIT & SPONGE.
 * @author Nikolai
 */
public class VersionFactory {
    
    public static final Platform BUKKIT = Platform.BUKKIT;
    public static final Platform SPONGE = Platform.SPONGE;
    
    /**
     * Factory method used when you want to construct a Version object via pluginName. <br/>
     */
    public static Version getPluginVersion(String pluginName) {
        return Platform.getPlatform().getPluginVersion(pluginName);
    }
    
    /**
     * Factory method to conveniently construct a Version object from the Minecraft server. <br/>
     */
    public static Version getMinecraftVersion() {
        return Platform.getPlatform().getMinecraftVersion();
    }
    
    public static Version getApiVersion() {
        return Platform.getPlatform().getApiVersion();
    }
    
    public static Version getImplementationVersion() {
        return Platform.getPlatform().getImplementationVersion();
    }
    
    /**
     * Factory method to get the net.minecraft.server.vX_Y_RZ package.
     * This will return simply "sponge" on the SpongePlatform.
     * @return a String in the form of "vX_Y_Z" where "v" is constant and X, Y, & Z are integers.
     */
    public static String getNmsPackage() {
        return Platform.getPlatform().getNmsPackage();
    }
    
    public static Platform getPlatform() {
        return Platform.getPlatform();
    }
    
}
