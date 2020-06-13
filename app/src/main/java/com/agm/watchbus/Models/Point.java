package com.agm.watchbus.Models;

import java.util.UUID;

public class Point {
    public UUID id;
    public String name;

    public Point(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
