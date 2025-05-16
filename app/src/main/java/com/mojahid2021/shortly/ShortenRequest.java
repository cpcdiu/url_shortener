package com.mojahid2021.shortly;

public class ShortenRequest {
    private String alias;
    private String original_url;

    // Constructor
    public ShortenRequest(String alias, String original_url) {
        this.alias = alias;
        this.original_url = original_url;
    }

    // Getters and Setters
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOriginalUrl() {
        return original_url;
    }

    public void setOriginalUrl(String original_url) {
        this.original_url = original_url;
    }
}
