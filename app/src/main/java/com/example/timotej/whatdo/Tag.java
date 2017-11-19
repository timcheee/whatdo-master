package com.example.timotej.whatdo;

/**
 * Created by Timotej on 3. 03. 2017.
 */

public class Tag {
    String tagName;

    public  Tag(){};
    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagName='" + tagName + '\'' +
                '}';
    }
}
