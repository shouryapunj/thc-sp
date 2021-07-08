package dev.shouryapunj.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCartServiceException extends RuntimeException {

    public OrderCartServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
