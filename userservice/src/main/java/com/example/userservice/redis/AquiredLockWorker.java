package com.example.userservice.redis;

public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
