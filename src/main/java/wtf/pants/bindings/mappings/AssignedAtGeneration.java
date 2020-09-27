package wtf.pants.bindings.mappings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark which variables are assigned only when the generation has begun for clarity.
 * This happens because some attributes such as static and inheritance are not present in obfuscation mappings.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface AssignedAtGeneration {
}
