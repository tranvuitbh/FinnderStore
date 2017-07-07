package com.example.vutran.finderawsome.Database;

/**
 * Created by VuTran on 6/22/2017.
 */

public class DBModelStore {
    private String Id;
    private String Name;
    private String Address;
    private String Site;
    private String Saved;
    private String IdUser;

    public DBModelStore() {
    }

    public DBModelStore(String id, String name, String address, String site, String saved) {
        Id = id;
        Name = name;
        Address = address;
        Site = site;
        Saved = saved;
    }

    public String getIdUser() {
        return IdUser;
    }

    public DBModelStore(String id, String name, String address, String site, String saved, String idUser) {
        Id = id;
        Name = name;
        Address = address;
        Site = site;
        Saved = saved;
        IdUser = idUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getSaved() {
        return Saved;
    }

    public void setSaved(String saved) {
        Saved = saved;
    }

    public DBModelStore(String id, String name, String address, String site) {
        Id = id;
        Name = name;
        Address = address;
        Site = site;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }
}
