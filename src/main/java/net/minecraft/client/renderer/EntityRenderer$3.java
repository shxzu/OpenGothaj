package net.minecraft.client.renderer;

import java.util.concurrent.Callable;
import org.lwjgl.input.Mouse;

class EntityRenderer$3
implements Callable<String> {
    private final int val$k1;
    private final int val$l1;

    EntityRenderer$3(int n, int n2) {
        this.val$k1 = n;
        this.val$l1 = n2;
    }

    @Override
    public String call() throws Exception {
        return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", this.val$k1, this.val$l1, Mouse.getX(), Mouse.getY());
    }
}
