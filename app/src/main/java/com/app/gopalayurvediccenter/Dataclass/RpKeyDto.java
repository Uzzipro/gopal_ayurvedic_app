package com.app.gopalayurvediccenter.Dataclass;

public class RpKeyDto {
    private String key_id;
    private String key_secret;


    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getKey_secret() {
        return key_secret;
    }

    public void setKey_secret(String key_secret) {
        this.key_secret = key_secret;
    }

    public RpKeyDto(String key_id, String key_secret) {
        this.key_id = key_id;
        this.key_secret = key_secret;
    }

    public RpKeyDto()
    {

    }
}
