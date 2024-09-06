package com.bloxgaming.primo.entity;

import com.bloxgaming.primo.response.PlayGame;

public class CacheRecord{
    String userId;
    PlayGame playGame;

    boolean isPrime;

    String gameResult;

    public CacheRecord(String userId, PlayGame playGame, boolean isPrime, String gameResult) {
        this.userId = userId;
        this.playGame = playGame;
        this.isPrime = isPrime;
        this.gameResult = gameResult;
    }
}
