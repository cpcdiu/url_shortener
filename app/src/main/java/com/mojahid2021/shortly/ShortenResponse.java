package com.mojahid2021.shortly;

public class ShortenResponse {
    private String short_url;
    private String original_url;

    // Getter for short_url
    public String getShortUrl() {
        return short_url;
    }

    public void setShortUrl(String short_url) {
        this.short_url = short_url;
    }

    // Getter for original_url
    public String getOriginalUrl() {
        return original_url;
    }

    public void setOriginalUrl(String original_url) {
        this.original_url = original_url;
    }
}
