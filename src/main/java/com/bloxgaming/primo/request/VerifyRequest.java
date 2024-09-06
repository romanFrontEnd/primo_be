package com.bloxgaming.primo.request;

import com.google.gson.annotations.SerializedName;

public class VerifyRequest {
    @SerializedName("nonce")
    private String nonce;

    public String getNonce() {
        return nonce;
    }

    @SerializedName("serverSeed")
    String serverSeed;

    public String getServerSeed() {
        return serverSeed;
    }

    @SerializedName("clientSeed")
    String clientSeed;

    public String getClientSeed() {
        return clientSeed;
    }


}
