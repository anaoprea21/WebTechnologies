package org.example.exception;

public class ArticleNotFound extends BaseRuntimeException{

    public ArticleNotFound(final String message) {
        super(message);
    }
}
