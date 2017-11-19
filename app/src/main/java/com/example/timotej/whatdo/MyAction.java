package com.example.timotej.whatdo;

import java.util.*;

/**
 * Created by Timotej on 3. 03. 2017.
 */

public class MyAction {
    public String idAction;
    String name, description;
    Location loc;

    TagList tags;
    long startDate, endDate;
    String startTime, endTime;

    public MyAction(){};

    public MyAction(String name, String description, Location loc, TagList tags, long startDate, long endDate, String startTime, String endTime) {
        this.idAction = UUID.randomUUID().toString().replaceAll("-", "");
        this.name = name;
        this.description = description;
        this.loc = loc;
        this.tags = tags;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getIdAction() {
        return idAction;
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public TagList getTags() {
        return tags;
    }

    public void setTags(TagList tags) {
        this.tags = tags;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "\nMyAction{" +
                "idAction='" + idAction + '\'' +
                ", name='" + name + '\'' +
                ", loc=" + loc +
                ", tags=" + tags +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
