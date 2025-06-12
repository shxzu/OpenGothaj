package xyz.cucumber.base.microsoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MicrosoftLogin$GameOwnershipResponse {
    @Expose
    @SerializedName(value="items")
    private Item[] items;

    private MicrosoftLogin$GameOwnershipResponse() {
    }

    private boolean hasGameOwnership() {
        boolean hasProduct = false;
        boolean hasGame = false;
        Item[] itemArray = this.items;
        int n = this.items.length;
        int n2 = 0;
        while (n2 < n) {
            Item item = itemArray[n2];
            if (item.name.equals("product_minecraft")) {
                hasProduct = true;
            } else if (item.name.equals("game_minecraft")) {
                hasGame = true;
            }
            ++n2;
        }
        return hasProduct && hasGame;
    }

    private static class Item {
        @Expose
        @SerializedName(value="name")
        private String name;

        private Item() {
        }
    }
}
