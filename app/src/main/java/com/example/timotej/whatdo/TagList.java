package com.example.timotej.whatdo;

import java.util.ArrayList;

/**
 * Created by Timotej on 3. 03. 2017.
 */

public class TagList {
    ArrayList<Tag> tags;

    public TagList() {
        tags = new ArrayList<>();
        tags.add(new Tag("Hrana"));
        tags.add(new Tag("Pijača"));
        tags.add(new Tag("Kava"));
        tags.add(new Tag("Šport"));
        tags.add(new Tag("Tek"));
        tags.add(new Tag("Izobraževanje"));
        tags.add(new Tag("Šport"));
    }

    @Override
    public String toString() {
        return "TagList{" +
                "tags=" + tags +
                '}';
    }
}
