package com.viaversion.viabackwards.api.rewriters;

public final class LegacySoundRewriter$SoundData {
    private final int replacementSound;
    private final boolean changePitch;
    private final float newPitch;
    private final boolean added;

    public LegacySoundRewriter$SoundData(int replacementSound, boolean changePitch, float newPitch, boolean added) {
        this.replacementSound = replacementSound;
        this.changePitch = changePitch;
        this.newPitch = newPitch;
        this.added = added;
    }

    public int getReplacementSound() {
        return this.replacementSound;
    }

    public boolean isChangePitch() {
        return this.changePitch;
    }

    public float getNewPitch() {
        return this.newPitch;
    }

    public boolean isAdded() {
        return this.added;
    }
}
