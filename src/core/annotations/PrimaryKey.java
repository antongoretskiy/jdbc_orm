
package core.annotations;
import java.lang.annotation.*;

@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
    boolean AutoIncrement() default false;
}
