package mc.euro.version;

/**
 * The Tester interface is used to optionally pass an additional isEnabled() check to the Version object. <br/><br/>
 * 
 * Notice that if the test returns false, then the compatibility check will stop, (as intended) 
 * because if the plugin is not enabled, then it's not compatible.
 * 
 * @author Nikolai
 */
public interface Tester<T> {
    
    public boolean isEnabled(T t);
    
}
