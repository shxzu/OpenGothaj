package com.viaversion.viarewind.utils.math;

import com.viaversion.viarewind.utils.math.Vector3d;

public class AABB {
    Vector3d min;
    Vector3d max;

    public AABB(Vector3d min, Vector3d max) {
        this.min = min;
        this.max = max;
    }

    public Vector3d getMin() {
        return this.min;
    }

    public Vector3d getMax() {
        return this.max;
    }
}
