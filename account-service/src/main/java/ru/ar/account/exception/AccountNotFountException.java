package ru.ar.account.exception;

public class AccountNotFountException extends RuntimeException {
    public AccountNotFountException(String message) {
        super(message);
    }
}
