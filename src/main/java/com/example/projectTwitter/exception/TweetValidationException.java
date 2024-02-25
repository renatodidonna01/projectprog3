package com.example.projectTwitter.exception;

public class TweetValidationException extends RuntimeException {
    public TweetValidationException(String message) {
        super(message);
    }
}
