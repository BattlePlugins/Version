package mc.euro.version.plugin;

/**
 * What is IPlugin ?
 * 
 * IPlugin is an abstraction of Plugin for the purpose of determining isEnabled().
 * Once constructed, this object should be passed to a Predicate Tester, 
 * which will call isEnabled().
 * 
 * @author Nikolai
 */
public interface IPlugin {
    
    boolean isEnabled();

}
