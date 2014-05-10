

package core.annotations;
import java.lang.annotation.*;

@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
    boolean CanBeNull() default true;
}
