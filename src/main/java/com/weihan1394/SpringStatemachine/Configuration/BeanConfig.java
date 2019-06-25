package com.weihan1394.SpringStatemachine.Configuration;

import com.weihan1394.SpringStatemachine.Configuration.Manager.OrderManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public OrderManager orderManager() {
    return new OrderManager();
  }
}
