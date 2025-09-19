package com.example.mygame.utils.exceptions;

public class InventorySlotLimitExceededException extends RuntimeException {
    public InventorySlotLimitExceededException(String message) {
        super(message);
    }
}
