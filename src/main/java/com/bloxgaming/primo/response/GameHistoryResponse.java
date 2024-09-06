package com.bloxgaming.primo.response;

import com.bloxgaming.primo.entity.CacheRecord;

import java.util.List;

public class GameHistoryResponse{
        private String userId;

        private List<CacheRecord> history;
        public GameHistoryResponse(
                String userId,
                List<CacheRecord> history)
        {
            this.userId = userId;
            this.history = history;
        }
    }
