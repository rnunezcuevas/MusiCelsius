package com.chillapps.musicelsius.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

@Component
public class CircuitBreakerCloser {

	private static final Logger logger = LogManager.getLogger(CircuitBreakerCloser.class);
	
    public void closeCircuitBreaker(CircuitBreaker circuitBreaker) {
        new Thread(() -> {
            try {
                Thread.sleep(20000);
                circuitBreaker.reset();
                logger.info("Circuit Breaker " + circuitBreaker.getName() + " closed successfully.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.info("Thread for CircuitBreaker closer has been interrupted.");
            }
        }).start();
    }
}