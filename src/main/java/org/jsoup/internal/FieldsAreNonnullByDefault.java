package org.jsoup.internal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;

@Documented
@Nonnull
@TypeQualifierDefault(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.CLASS)
public @interface FieldsAreNonnullByDefault {
}
