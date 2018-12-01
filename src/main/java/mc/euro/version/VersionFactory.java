package mc.euro.version;

import mc.euro.version.internal.Platform;

/**
 * Handles the construction of new Version objects for BUKKIT & SPONGE.
 * @author Nikolai
 */
public class VersionFactory {

    /**
     * Factory method used when you want to construct a Version object via pluginName. <br/>
     */
    public static Version getPluginVersion(String pluginName) {
        return Platform.getPlatform().getPluginVersion(pluginName);
    }
    
    /**
     * Factory method to conveniently construct a Version object from the Minecraft server. <br/>
     */
    public static Version getServerVersion() {
        return Platform.getPlatform().getServerVersion();
    }
    
    /**
     * Factory method to get the net.minecraft.server.vX_Y_RZ package.
     * Alias for VersionFactory.getNmsVersion().toString();
     * 
     * There are TWO uses for getNmsPackage():
     * 1. you're using it to get your.own.compat packaged classes.
     * 2. you're using it to get 
     *      net.minecraft.server.vX_Y_RZ classes
     *      org.bukkit.craftbukkit.vX_Y_RZ classes
     * Remember that versioned packages start at v1.4.5 for Craftbukkit.
     * So, if you're trying to access these classes,
     *   you'll have to account for both scenarios:
     *   -  1.4.2 example: "org.bukkit.craftbukkit.CraftServer";
     *   -  1.4.5 example: "org.bukkit.craftbukkit.v1_4_5.CraftServer";
     * Which means that you'll always have to do it in a try-catch block:
     * 
     * String NMS = VersionFactory.getNmsPackage();
     * Class clazz;
     * try {
     *     clazz = Class.forName("org.bukkit.craftbukkit.CraftServer");
     * } catch (ClassNotFoundException handled) {
     *     try {
     *         clazz = Class.forName("org.bukkit.craftbukkit." + NMS + ".CraftServer");
     *     } catch (ClassNotFoundException ex) {
     *         Log.printStackTrace(ex);
     *         return null;
     *     }
     * }
     * 
     * If you're using getNmsPackage() for getting your own compatibility classes,
     * and Class.forName() fails,
     * then obviously, the server is updated and your plugin is out-dated:
     * 
     * Example: You support Bukkit v1.2.5 to v1.9 and the server is running v1.10 or v1.11
     * 
     * @return a String in the form of "vX_Y_Z" where "v" is constant and X, Y, & Z are integers.
     */
    public static String getNmsPackage() {
        return Platform.getPlatform().getNmsPackage();
    }
    
    /**
     * Equivalent to new Version("vX_Y_Z") where "vX_Y_Z" comes from 
     * the net.minecraft.server.vX_Y_Z package.
     * 
     * Consider:
     *   String NMS = VersionFactory.getNmsPackage();
     *   Class clazz = Class.forName("com.your.pkg.compat." + NMS + ".YourClass");
     * 
     * You may want to do comparison on the String NMS before passing it Class.forName(),
     * especially if you don't want to support all Minecraft versions.
     * Or, if you want to convert NMS to another "version" like "pre"
     * that handles a group of versions.
     */
    public static Version getNmsVersion() {
        return new Version(getNmsPackage());
    }
    
    /**
     * Platform.BUKKIT, Platform.SPONGE, Platform.UNKNOWN.
     * @return Platform.BUKKIT, Platform.SPONGE, Platform.UNKNOWN.
     */
    public static Platform getPlatform() {
        return Platform.getPlatform();
    }
    
}
