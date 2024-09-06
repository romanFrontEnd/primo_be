package com.bloxgaming.primo.response;

public class GameResult {
    private boolean isPrime;
    private int result;
    private String gameResult;

    public GameResult(boolean isPrime, int result, String gameResult) {
        this.isPrime = isPrime;
        this.result = result;
        this.gameResult = gameResult;
    }
}
