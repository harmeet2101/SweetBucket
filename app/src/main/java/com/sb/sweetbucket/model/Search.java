package com.sb.sweetbucket.model;

import java.io.Serializable;

/**
 * Created by harmeet on 15-09-2019.
 */

public class Search implements Serializable {


    private String name;
    private String type;

    public Search(String name, String type) {
        this.name = name;
        this.type = type;
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

    @Override
    public String toString() {
        return "Search{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
