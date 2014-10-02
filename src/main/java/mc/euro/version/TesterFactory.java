package mc.euro.version;

import org.bukkit.plugin.Plugin;

/**
 * Provides less typing for the construction of Plugin Testers. <br/><br/>
 * 
 * @author Nikolai
 */
public class TesterFactory {
    
    public static Tester getNewTester(Plugin plugin) {
        if (plugin == null) {
            return new Tester<Plugin>() {

                @Override
                public boolean isEnabled(Plugin t) {
                    return false;
                }
            };
        } else {
            return new Tester<Plugin>() {

                @Override
                public boolean isEnabled(Plugin t) {
                    return t.isEnabled();
                }
            };
        }
    }

    public static Tester getDefaultTester() {
        return new Tester() {

            @Override
            public boolean isEnabled(Object t) {
                return true;
            }
        };
    }
    
}
