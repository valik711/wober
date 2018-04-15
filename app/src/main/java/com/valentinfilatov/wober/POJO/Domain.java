package com.valentinfilatov.wober.POJO;

/**
 * Created by valik on 4/14/18.
 */

public class Domain {
    String name;
    String image;

    public Domain() {
    }

    public Domain(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
