package com.bloxgaming.primo.entity;

public class User {

    private String id;
    private String serverSeed;

    public User(String id, String serverSeed) {
        this.id = id;
        this.serverSeed = serverSeed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerSeed() {
        return serverSeed;
    }

    public void setServerSeed(String serverSeed) {
        this.serverSeed = serverSeed;
    }
}