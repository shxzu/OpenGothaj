package fr.litarvan.openauth.microsoft;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class LoginFrame$1
extends WindowAdapter {
    LoginFrame$1() {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        LoginFrame.this.future.completeExceptionally(new MicrosoftAuthenticationException("User closed the authentication window"));
    }
}
