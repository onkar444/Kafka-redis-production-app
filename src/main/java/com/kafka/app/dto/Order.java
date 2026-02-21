package com.kafka.app.dto;

public record Order(String orderId,String product, double amount) {
}
