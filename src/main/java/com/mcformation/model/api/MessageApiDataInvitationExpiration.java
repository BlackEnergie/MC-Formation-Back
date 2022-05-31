package com.mcformation.model.api;

import java.sql.Timestamp;

public class MessageApiDataInvitationExpiration extends MessageApi {

    private Timestamp expiration;

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }
}
