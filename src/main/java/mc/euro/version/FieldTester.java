package mc.euro.version;

/**
 * Testing fields for null.
 * 
 * @author Nikolai
 * @since v3.0.0-SNAPSHOT
 */
public class FieldTester {

    /**
     * Are all the fields initialized ? (not null). This does not include
     * inherited fields. Watch out for lazy-loading.
     * @since v3.0.0-SNAPSHOT
     */
    public static <T> boolean isInitialized(T t) {
        return TesterFactory.<T>getUnitTester(t).test();
    }

    /**
     * Are all the fields initialized ? (not null). This is including inherited
     * fields. Watch out for lazy-loading. Do not use on org.bukkit.Plugin 
     * because their JavaPlugin implementation has null fields (intentionally).
     * For instances of org.bukkit.Plugin, use isInitialized() instead.
     * @since v3.0.0-SNAPSHOT
     */
    public static <T> boolean isFullyInitialized(T t) {
        return TesterFactory.<T>getInheritanceTester(t).test();
    }

}
