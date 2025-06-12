package net.minecraft.client.main;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

class Main$1
extends Authenticator {
    private final String val$s1;
    private final String val$s2;

    Main$1(String string, String string2) {
        this.val$s1 = string;
        this.val$s2 = string2;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.val$s1, this.val$s2.toCharArray());
    }
}
