package mc.euro.version;

import mc.euro.version.plugin.IPlugin;

/**
 * Provides for easy construction of IPlugin Testers. <br/><br/>
 * 
 * @author Nikolai
 */
public class TesterFactory {
    
    public static Tester<IPlugin> getNewTester(IPlugin iplugin) {

        if (iplugin == null) {
            return getShortCircuitTester();
        }
        
        Predicate predicate = new Predicate<IPlugin>() {

            @Override
            public boolean test(IPlugin t) {
                return t.isEnabled();
            }
        };

        return new Tester(predicate, iplugin);
    }

    /**
     * The default Tester always succeeds (returns true).
     * 
     * @return A new Tester object where its test() method always returns true.
     */
    public static Tester getDefaultTester() {
        return new Tester(Predicate.TRUE, null);
    }
    
    /**
     * The ShortCircuit Tester always fails (returns false).
     * 
     * @return A new Tester object where its test() method always returns false;
     */
    public static Tester getShortCircuitTester() {
        return new Tester(Predicate.FALSE, null);
    }
    
}
