package com.bss.uis.ui.imageSlider;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageSlider {
    // string for our image url.
    private String imgUrl;

    // empty constructor which is
    // required when using Firebase.
    public ImageSlider() {
    }

    // Constructor
    public ImageSlider(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
