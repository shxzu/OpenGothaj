package net.minecraft.client.gui;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

class GuiOptions$1
extends GuiButton {
    GuiOptions$1(int $anonymous0, int $anonymous1, int $anonymous2, int $anonymous3, int $anonymous4, String $anonymous5) {
        super($anonymous0, $anonymous1, $anonymous2, $anonymous3, $anonymous4, $anonymous5);
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        SoundEventAccessorComposite soundeventaccessorcomposite = soundHandlerIn.getRandomSoundFromCategories(SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER);
        if (soundeventaccessorcomposite != null) {
            soundHandlerIn.playSound(PositionedSoundRecord.create(soundeventaccessorcomposite.getSoundEventLocation(), 0.5f));
        }
    }
}
