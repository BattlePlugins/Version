package mc.euro.version;

import javax.annotation.Nullable;

/**
 * 
 * @author Nikolai
 */
public class AnimalsForTest {
    
    static class Animal {
        protected Object animalAttribute = null;
    }
    
    static class Cat extends Animal {
        Object catAttribute = "notNull";
    }
    
    static class Mouse extends Animal {
        @Nullable
        Object mouseAttribute = null;
        public Mouse() { animalAttribute = "notNull"; }
    }

}
