package mc.euro.version;

/**
 * The Predicate preforms tests on objects of type T and returns true or false.
 * 
 * @author Nikolai
 * @param <T> The type of objects to be tested.
 */
public interface Predicate<T> {
    
    boolean test(T t);
    
    /**
     * Always returns true.
     */
    Predicate<?> TRUE = t -> true;
    
    /**
     * Always returns false.
     */
    Predicate<?> FALSE = t -> false;
}
