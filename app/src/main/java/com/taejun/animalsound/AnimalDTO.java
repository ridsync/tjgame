package com.taejun.animalsound;

/**
 * Created by arent on 2017. 9. 11..
 */

public class AnimalDTO {

    public AnimalDTO(){

    }

    String name;
    String audio;
    String color;
    String identifier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
