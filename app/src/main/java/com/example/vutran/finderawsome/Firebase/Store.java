package com.example.vutran.finderawsome.Firebase;

/**
 * Created by VuTran on 7/22/2017.
 */

public class Store {
    public String name;
    public String address;
    public String id;

    public Store() {
    }

    public Store(String name, String address, String id) {
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
