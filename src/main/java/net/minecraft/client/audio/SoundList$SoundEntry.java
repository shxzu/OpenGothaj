package net.minecraft.client.audio;

public class SoundList$SoundEntry {
    private String name;
    private float volume = 1.0f;
    private float pitch = 1.0f;
    private int weight = 1;
    private Type type = Type.FILE;
    private boolean streaming = false;

    public String getSoundEntryName() {
        return this.name;
    }

    public void setSoundEntryName(String nameIn) {
        this.name = nameIn;
    }

    public float getSoundEntryVolume() {
        return this.volume;
    }

    public void setSoundEntryVolume(float volumeIn) {
        this.volume = volumeIn;
    }

    public float getSoundEntryPitch() {
        return this.pitch;
    }

    public void setSoundEntryPitch(float pitchIn) {
        this.pitch = pitchIn;
    }

    public int getSoundEntryWeight() {
        return this.weight;
    }

    public void setSoundEntryWeight(int weightIn) {
        this.weight = weightIn;
    }

    public Type getSoundEntryType() {
        return this.type;
    }

    public void setSoundEntryType(Type typeIn) {
        this.type = typeIn;
    }

    public boolean isStreaming() {
        return this.streaming;
    }

    public void setStreaming(boolean isStreaming) {
        this.streaming = isStreaming;
    }

    public static enum Type {
        FILE("file"),
        SOUND_EVENT("event");

        private final String field_148583_c;

        private Type(String p_i45109_3_) {
            this.field_148583_c = p_i45109_3_;
        }

        public static Type getType(String p_148580_0_) {
            Type[] typeArray = Type.values();
            int n = typeArray.length;
            int n2 = 0;
            while (n2 < n) {
                Type soundlist$soundentry$type = typeArray[n2];
                if (soundlist$soundentry$type.field_148583_c.equals(p_148580_0_)) {
                    return soundlist$soundentry$type;
                }
                ++n2;
            }
            return null;
        }
    }
}
