package xyz.cucumber.base.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    public String name();

    public String description();

    public int key() default 0;

    public Category category();

    public ArrayPriority priority() default ArrayPriority.MEDIUM;
}
