package mc.euro.version;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

/**
 * Handles the construction of new Version objects specific to Craftbukkit.
 * @author Nikolai
 */
public class VersionFactory {
    
    /**
     * Factory method used when you want to construct a Version object via a Plugin object. <br/>
     */
    public static Version getPluginVersion(Plugin plugin) {
        String version = (plugin == null) ? null : plugin.getDescription().getVersion();
        Tester<Plugin> tester = TesterFactory.getNewTester(plugin);
        return new Version<Plugin>(version, tester, plugin);
    }
    
    /**
     * Factory method used when you want to construct a Version object via pluginName. <br/>
     */
    public static Version getPluginVersion(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        String version = (plugin == null) ? null : plugin.getDescription().getVersion();
        Tester<Plugin> tester = TesterFactory.getNewTester(plugin);
        return new Version<Plugin>(version, tester, plugin);
    }
    
    /**
     * Factory method to conveniently construct a Version object of the server. <br/>
     */
    public static Version getServerVersion() {
        String version = Bukkit.getServer().getBukkitVersion();
        return new Version(version);
    }
    
    /**
     * Factory method to conveniently construct a Version object from net.minecraft.server.vX_Y_RZ package. <br/>
     * <b>These return values should NOT be used for comparison.</b><br/>
     * This should only be used to reliably access your own .compat. packages.
     * Really, the return value never should have been a Version object
     * because it just gets immediately converted to a String anyways.
     * @return - "pre" if the server is less than v1.4.5, otherwise returns "vX_Y_Z"
     * @deprecated - Scheduled for deletion in favor of getNmsPackage() + getCompatPackage().
     */
    @Deprecated
    public static Version getNmsVersion() {
        String NMS = null;
            try {
                // This fails for versions less than 1.4.5
                NMS = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            } catch (ArrayIndexOutOfBoundsException ex) {
                NMS = createCompatPackage();
                // Do what I say, not what I do :P
                // 1.4.5+ should never reach this point, thus comparison is safe here.
                // This adds backwards compatibility while future proofing
                // against possible changes in Spigot.
                // Note that comparison of 1.4.7 (v1_4_R1) to 1.4.6 (v1_4_6) would normally fail.
                if (new Version(NMS).compareTo("1.4.5") < 0) {
                    return new Version("pre");
                }
            }
        return new Version(NMS);
    }
    
    /**
     * If net.minecraft.server.vX_Y_RZ package exists, then use that, 
     * otherwise, call getCompatPackage() to construct vX_Y_Z based on 
     * the server version that's running.
     * @return a String in the form of "vX_Y_Z" where "v" is constant and X, Y, & Z are integers.
     */
    private static String getNmsPackage() {
        return getNmsVersion().toString();
    }
    
    /**
     * getNmsPackage() is preferable because it will check the server to see 
     * if net.minecraft.server.vX_Y_RZ exists and return that. 
     * getNmsPackage() will also construct vX_Y_Z if it fails (if the package 
     * n.m.s.v does not exist). However, use getCompatPackage() if you want 
     * to ignore the existence of the n.m.s.v package. Note that
     * the n.m.s.v package does not exist in really old versions of Craftbukkit.
     * Also, it may not exist in future versions of Spigot.
     * @return a String in the form of "vX_Y_Z" where "v" is constant and X, Y, & Z are integers.
     */
    private static String getCompatPackage() {
        return createCompatPackage();
    }
    
    /**
     * Creates a String for compat packages.
     * <pre>
     * For example, if the server is running 1.2.5-R0.1-SNAPSHOT, 
     * then the return value will be "v1_2_5".
     * Which can be used to get classes from the package:
     * com.name.project.compat.v1_2_5;
     * </pre>
     * @return a String in the form of "vX_Y_Z" where "v" is constant and X, Y, & Z are integers.
     */
    private static String createCompatPackage() {
        String fullVersion = Bukkit.getBukkitVersion();
        String[] parts = fullVersion.split("[-]");
        String version = parts[0];
        version = version.replace(".", "_");
        version = "v" + version;
        return version;
    }
    
}
