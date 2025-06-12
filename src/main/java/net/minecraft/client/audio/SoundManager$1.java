package net.minecraft.client.audio;

import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundManager;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemLogger;

class SoundManager$1
implements Runnable {
    SoundManager$1() {
    }

    @Override
    public void run() {
        SoundSystemConfig.setLogger((SoundSystemLogger)new SoundSystemLogger(){

            public void message(String p_message_1_, int p_message_2_) {
                if (!p_message_1_.isEmpty()) {
                    logger.info(p_message_1_);
                }
            }

            public void importantMessage(String p_importantMessage_1_, int p_importantMessage_2_) {
                if (!p_importantMessage_1_.isEmpty()) {
                    logger.warn(p_importantMessage_1_);
                }
            }

            public void errorMessage(String p_errorMessage_1_, String p_errorMessage_2_, int p_errorMessage_3_) {
                if (!p_errorMessage_2_.isEmpty()) {
                    logger.error("Error in class '" + p_errorMessage_1_ + "'");
                    logger.error(p_errorMessage_2_);
                }
            }
        });
        SoundManager soundManager = SoundManager.this;
        soundManager.getClass();
        SoundManager.this.sndSystem = new SoundManager.SoundSystemStarterThread(soundManager, null);
        SoundManager.this.loaded = true;
        SoundManager.this.sndSystem.setMasterVolume(SoundManager.this.options.getSoundLevel(SoundCategory.MASTER));
        logger.info(LOG_MARKER, "Sound engine started");
    }
}
