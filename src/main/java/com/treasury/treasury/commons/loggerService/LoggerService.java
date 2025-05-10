package com.treasury.treasury.commons.loggerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerService {

  public void info(Class<?> clazz, String message) {
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.info(message);
  }

  public void error(Class<?> clazz, String message) {
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.error(message);
  }

  public void debug(Class<?> clazz, String message) {
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.debug(message);
  }
}
