package com.aventador.bicyclerental.settingFragment.list;

import android.graphics.drawable.Drawable;

public class ListObject {

    public String name;
    public String type;
    public Drawable image;
    public Drawable backgroundImage;

    public ListObject(String name, String type, Drawable image, Drawable backgroundImage) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.backgroundImage = backgroundImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Drawable getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Drawable backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
