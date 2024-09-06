package com.bloxgaming.primo.services;

import com.bloxgaming.primo.entity.CacheRecord;
import com.bloxgaming.primo.response.PlayGame;

import java.util.*;

public class CacheService {
    private static Map<String, List<CacheRecord>> myCache;

    public CacheService() {
        myCache = Collections.synchronizedMap(new WeakHashMap<>());
    }

    public void storeSpins(String userId, PlayGame playGame, boolean isPrime, String gameResult ) {
        CacheRecord record = new CacheRecord(userId, playGame, isPrime, gameResult );
        if(myCache.containsKey(userId)) {
            List<CacheRecord> history  = myCache.get(userId);
            history.add(record);
        } else {
            List<CacheRecord> history = new ArrayList<>();
            history.add(record);
            myCache.put(userId, history);
        }
    }

    public List<CacheRecord> get(String userId) {
        return myCache.get(userId);
    }
}
