package com.weihan1394.SpringStatemachine;

import com.weihan1394.SpringStatemachine.Configuration.BeanConfig;
import com.weihan1394.SpringStatemachine.Configuration.Manager.OrderManager;
import com.weihan1394.SpringStatemachine.Configuration.OrderEvents;
import com.weihan1394.SpringStatemachine.Configuration.OrderStates;
import com.weihan1394.SpringStatemachine.Configuration.PurchaseOrderConfig;
import com.weihan1394.SpringStatemachine.Entity.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;

import java.math.BigDecimal;

@SpringBootApplication
@EnableStateMachine
public class SpringStatemachineApplication implements CommandLineRunner {


  public static void main(String[] args) {
    SpringApplication.run(SpringStatemachineApplication.class, args);
  }

  private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PurchaseOrderConfig.class,
          BeanConfig.class);
  private OrderManager orderManager = context.getBean(OrderManager.class);

  @Override
  public void run(String... args) {
    System.out.println("running");

    // Laptop PO
    Order laptop = new Order(1L, "Laptop", BigDecimal.valueOf(1000));
    StateMachine<OrderStates, OrderEvents> laptopStateMachine = orderManager.newOrder(laptop);

    System.out.println("Laptop: " + laptopStateMachine.getState().getId());
    orderManager.fire(laptopStateMachine, OrderEvents.APPROVE);

    System.out.println("Laptop: " + laptopStateMachine.getState().getId());

    // Mouse PO
    Order mouse = new Order(2L, "Mouse", BigDecimal.valueOf(50));
    StateMachine<OrderStates, OrderEvents> mouseStateMachine = orderManager.newOrder(mouse);

    System.out.println("Mouse State: " + mouseStateMachine.getState().getId());
    orderManager.fire(mouseStateMachine, OrderEvents.APPROVE);

    System.out.println("Mouse State: " + mouseStateMachine.getState().getId());
    System.out.println(mouseStateMachine.getExtendedState().getVariables().get("order"));


    System.out.println("Mouse Machine Completed:" + mouseStateMachine.isComplete());
    orderManager.fire(mouseStateMachine, OrderEvents.PROCESS);
    System.out.println("Mouse State: " + mouseStateMachine.getState().getId());
    System.out.println("Mouse Machine Completed:" + mouseStateMachine.isComplete());
  }
}
