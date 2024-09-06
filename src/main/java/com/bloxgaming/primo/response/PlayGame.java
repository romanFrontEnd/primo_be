package com.bloxgaming.primo.response;

public class PlayGame {
    private int result;
    private String serverSeed;
    private String nonce;

    public PlayGame(int result, String serverSeed, String nonce) {

        this.result = result;
        this.serverSeed = serverSeed;
        this.nonce = nonce;

    }


}