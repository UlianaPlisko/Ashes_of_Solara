package com.example.mygame.exceptions;

public class InventorySlotLimitExceededException extends RuntimeException {
    public InventorySlotLimitExceededException(String message) {
        super(message);
    }
}
