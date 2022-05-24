package com.example.userservice.service;

public interface MqService {
    Object simpleSend(String message);

    Object workQueueSend(String message);

    Object fanOutSend(String message);

    Object sendTopic(String message);

    Object routeSend(String message);

    Object deadSend(String message);
}
