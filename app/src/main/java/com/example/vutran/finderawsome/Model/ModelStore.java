package com.example.vutran.finderawsome.Model;

/**
 * Created by VuTran on 6/2/2017.
 */

public class ModelStore {
    private String Name;
    private String Address;
    private String Id;
    private String IdImage;
    private String Site;
    private String Saved;
    private String IdUser;
    private String Distance;


    public ModelStore(String name, String address, String id, String idImage, String site, String saved, String idUser, String distance) {
        Name = name;
        Address = address;
        Id = id;
        IdImage = idImage;
        Site = site;
        Saved = saved;
        IdUser = idUser;
        Distance = distance;
    }

    public ModelStore() {
    }

    public ModelStore(String name, String address, String id, String site, String saved, String idUser) {
        Name = name;
        Address = address;
        Id = id;
        Site = site;
        Saved = saved;
        IdUser = idUser;
    }

    public ModelStore(String id, String name, String address, String site, String saved) {
        Name = name;
        Address = address;
        Id = id;
        Site = site;
        Saved = saved;
    }

    public ModelStore(String name, String address, String id, String idImage) {
        Name = name;
        Address = address;
        Id = id;
        IdImage = idImage;
    }


    public ModelStore(String name, String address) {
        Name = name;
        Address = address;
    }

    public ModelStore(String name, String address, String id) {
        Name = name;
        Address = address;
        Id = id;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }


    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getSaved() {
        return Saved;
    }

    public void setSaved(String saved) {
        Saved = saved;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIdImage() {
        return IdImage;
    }

    public void setIdImage(String idImage) {
        IdImage = idImage;
    }
}
