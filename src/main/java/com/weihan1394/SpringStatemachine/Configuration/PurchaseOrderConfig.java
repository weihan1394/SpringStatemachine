package com.weihan1394.SpringStatemachine.Configuration;

import com.weihan1394.SpringStatemachine.Entity.Order;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.math.BigDecimal;
import java.util.Date;

@Configuration
@EnableStateMachineFactory
public class PurchaseOrderConfig extends
    EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

  // state action
  @Override
  public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states)
      throws Exception {
    states
        .withStates()
        .initial(OrderStates.CREATED, entryAction())
        .state(OrderStates.APPROVED, approveAction())
        .end(OrderStates.DENIED)
        .end(OrderStates.CANCELLED)
        .end(OrderStates.PROCESSED);
  }

  // state configuration to let it auto start
  @Override
  public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config)
      throws Exception {
    config
        .withConfiguration()
        .autoStartup(true);
  }

  // state flow
  @Override
  public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions)
      throws Exception {
    transitions
        .withExternal()
        .source(OrderStates.CREATED)
        .target(OrderStates.APPROVED)
        .event(OrderEvents.APPROVE)
        .and()
        .withExternal()
        .source(OrderStates.CREATED)
        .target(OrderStates.DENIED)
        .event(OrderEvents.DENY)
        .and()
        .withExternal()
        .source(OrderStates.APPROVED)
        .target(OrderStates.PROCESSED)
        .event(OrderEvents.PROCESS)
        .and()
        .withExternal()
        .source(OrderStates.APPROVED)
        .target(OrderStates.CANCELLED)
        .event(OrderEvents.CANCEL);
  }

  //------------------------------------------------------------------------------------------------------
  // Actions
  //------------------------------------------------------------------------------------------------------

  public Action<OrderStates, OrderEvents> approveAction() {
    return context -> {
      System.out.println("approve action");
      Order order = findOrder(context.getExtendedState());
      if (order != null) {
        order.setNumber((int) (Math.random() * 100000));
        order.setApprovalDate(new Date());
      }
    };
  }

  public Action<OrderStates, OrderEvents> denyAction() {
    return context -> {
      System.out.println("deny action");
      Order order = findOrder(context.getExtendedState());
      if (order != null) {
        order.setCancelDate(new Date());
      }
    };
  }

  public Action<OrderStates, OrderEvents> entryAction() {
    return context -> {
      System.out.println("entry action");
      System.out.println("Entering state " + context.getTarget().getId());
    };
  }

  public Action<OrderStates, OrderEvents> exitAction() {
    return context -> System.out.println("Exiting state " + context.getSource().getId());
  }

  //------------------------------------------------------------------------------------------------------
  // Guards
  //------------------------------------------------------------------------------------------------------
  public Guard<OrderStates, OrderEvents> budgetGuard(BigDecimal limit) {
    return context -> {
      Order order = findOrder(context.getExtendedState());
      if (order != null) {
        return order.getBudget().compareTo(limit) == -1;
      }
      return true;
    };
  }

  //------------------------------------------------------------------------------------------------------
  private Order findOrder(ExtendedState extendedState) {
    for (Object obj : extendedState.getVariables().values()) {
      if (obj instanceof Order) {
        return (Order) obj;
      }
    }
    return null;
  }
}
