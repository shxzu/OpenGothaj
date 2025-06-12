package net.minecraft.client.audio;

import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.util.ResourceLocation;

class SoundHandler$2
implements ISoundEventAccessor<SoundPoolEntry> {
    final ResourceLocation field_148726_a;

    SoundHandler$2(String string, SoundList.SoundEntry soundEntry) {
        this.field_148726_a = new ResourceLocation(string, soundEntry.getSoundEntryName());
    }

    @Override
    public int getWeight() {
        SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
        return soundeventaccessorcomposite1 == null ? 0 : soundeventaccessorcomposite1.getWeight();
    }

    @Override
    public SoundPoolEntry cloneEntry() {
        SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
        return soundeventaccessorcomposite1 == null ? missing_sound : soundeventaccessorcomposite1.cloneEntry();
    }
}
