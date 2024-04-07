package fr.ensim.charme_quartier.pixel_war.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public class AuthentifierToken {
    public String access_token;

    private int expire_in;

    private LocalDateTime GenerateTime = LocalDateTime.now();


    public String getAccess_token() {
        return access_token;
    }

    public int getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(int expire_in) {
        this.expire_in = expire_in-10;
    }

    public LocalDateTime getExpireTime() {
        return GenerateTime.plusSeconds(expire_in);
    }
}
