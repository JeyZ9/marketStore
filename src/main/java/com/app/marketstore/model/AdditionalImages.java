package com.app.marketstore.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AdditionalImages {
    private List<String> additionalImages;

    public AdditionalImages(){
        this.additionalImages = new ArrayList<>();
    }

    public void additionalImage(String imageUrl){
        if (additionalImages.size() < 6){
            additionalImages.add(imageUrl);
        } else{
            throw new IllegalArgumentException("Cannot add more than 6 additional images.");
        }
    }
}
