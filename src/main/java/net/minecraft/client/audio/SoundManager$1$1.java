// ERROR: Unable to apply inner class name fixup
package net.minecraft.client.audio;

import paulscode.sound.SoundSystemLogger;

class SoundManager.1
extends SoundSystemLogger {
    SoundManager.1() {
    }

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
}
