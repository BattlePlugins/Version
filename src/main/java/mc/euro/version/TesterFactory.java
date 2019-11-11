package mc.euro.version;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import mc.euro.version.plugin.IPlugin;

import javax.annotation.Nullable;

/**
 * Provides for easy construction of IPlugin Testers. <br/><br/>
 * 
 * @author Nikolai
 */
public class TesterFactory {
    
    /**
     * Creates a new Tester object of Type IPlugin that checks if the IPlugin.isEnabled().
     */
    public static Tester<IPlugin> getNewTester(IPlugin iplugin) {
        if (iplugin == null) {
            return getShortCircuitTester();
        }
        
        return new Tester<>(IPlugin::isEnabled, iplugin);
    }

    /**
     * The default Tester always succeeds (returns true).
     * 
     * @return A new Tester object where its test() method always returns true.
     */
    public static Tester getDefaultTester() {
        return new Tester<>(Predicate.TRUE, null);
    }
    
    /**
     * The ShortCircuit Tester always fails (returns false).
     * 
     * @return A new Tester object where its test() method always returns false;
     */
    public static Tester getShortCircuitTester() {
        return new Tester<>(Predicate.FALSE, null);
    }
    
    /**
     * A Tester that checks if the fields of T are not null.
     * This method was added because Admins were installing broken plugins.
     * These plugins would cause errors during onEnable() which meant that 
     * their fields weren't fully initialized.
     * 
     * So merely checking that the plugin is installed & version checking is not enough:
     * We should also verify that they're not broken (fields are not null).
     * @param <T>
     * @param t
     * @return -- true if all fields are initialized (not null), false if any field is null.
     * @since v3.0.0-SNAPSHOT
     */
    public static <T> Tester<T> getUnitTester(T t) {
        return new Tester<>(getFieldTester(), t);
    }

    public static <T> Predicate<T> getFieldTester() {
        return testee -> {
            if (testee == null)
                return false;

            return isInitialized(testee.getClass(), testee);
        };
    }
    
    /**
     * This method checks ALL fields, including inherited fields.
     * @param <T> The object Type.
     * @param t The object whose fields will be tested for null.
     * @return A Tester object whose test() method will return true is all fields are not null.
     *         And test() will return false if any fields are null.
     * @since v3.0.0-SNAPSHOT
     */
    public static <T> Tester<T> getInheritanceTester(T t) {
        return new Tester<>(getSuperFieldTester(t), t);
    }
    
    /**
     * This method checks ALL fields, including inherited fields from the Superclass.
     * @param <T>
     * @param t
     * @return False if the object or any of its fields are null.
     *         True if the object is fully initialized (does not have any null fields).
     * Null fields labeled with the @Nullable annotation are deemed okay.
     * @since v3.0.0-SNAPSHOT
     */
    public static <T> Predicate<T> getSuperFieldTester(T t) {
        return testee -> {
            if (testee == null)
                return false;

            Class<?> clazz = testee.getClass();
            while (clazz != null) {
                if (hasNullFields(clazz, testee))
                    return false;
                clazz = clazz.getSuperclass();
            }
            return true;
        };
    }
    
    /**
     * @return -- true if all fields are initialized (not null), false if any field is null.
     * @since v4.0.1
     */
    private static <T> boolean isInitialized(Class c, T object) {
        return !hasNullFields(c, object);
    }
    
    /**
     * @return -- true if all fields are initialized (not null), false if any field is null.
     * @since v4.0.1
     */
    private static <T> boolean hasNullFields(Class c, T object) {
        return !getNullFields(c, object).isEmpty();
    }
    
    /**
     * This will return all the null fields of a Class with the exception of 
     * fields labeled with the @Nullable annotation.
     * @since v3.0.0-SNAPSHOT
     */
    private static <T> Collection<String> getNullFields(Class c, T object) {
        Collection<String> nullFields = new ArrayList<>();
        if (object == null) return nullFields;
        String name = c.getName();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(object) == null && !field.isAnnotationPresent(Nullable.class)) {
                    nullFields.add(field.getName());
                    return nullFields;
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(name).log(Level.SEVERE, null, ex);
            }
        }
        return nullFields;
    }
    
    private static StringBuilder toString(Collection<String> nullFields) {
        StringBuilder msg = new StringBuilder();
        msg.append(" has ").append(nullFields.size()).append(" null fields: ");
        for (String field : nullFields) {
            msg.append(field).append(", ");
        }
        msg.deleteCharAt(msg.length() - 1);
        msg.deleteCharAt(msg.length() - 1);
        return msg;
    }

}
