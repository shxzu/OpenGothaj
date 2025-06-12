package net.minecraft.client.audio;

public enum SoundList$SoundEntry$Type {
    FILE("file"),
    SOUND_EVENT("event");

    private final String field_148583_c;

    private SoundList$SoundEntry$Type(String p_i45109_3_) {
        this.field_148583_c = p_i45109_3_;
    }

    public static SoundList$SoundEntry$Type getType(String p_148580_0_) {
        SoundList$SoundEntry$Type[] typeArray = SoundList$SoundEntry$Type.values();
        int n = typeArray.length;
        int n2 = 0;
        while (n2 < n) {
            SoundList$SoundEntry$Type soundlist$soundentry$type = typeArray[n2];
            if (soundlist$soundentry$type.field_148583_c.equals(p_148580_0_)) {
                return soundlist$soundentry$type;
            }
            ++n2;
        }
        return null;
    }
}
