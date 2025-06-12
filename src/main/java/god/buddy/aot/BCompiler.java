package god.buddy.aot;

public @interface BCompiler {
    public AOT aot();

    public static enum AOT {
        AGGRESSIVE,
        NORMAL;

    }
}
