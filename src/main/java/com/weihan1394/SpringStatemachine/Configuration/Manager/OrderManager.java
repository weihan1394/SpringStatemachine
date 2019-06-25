package com.weihan1394.SpringStatemachine.Configuration.Manager;

import com.weihan1394.SpringStatemachine.Configuration.OrderEvents;
import com.weihan1394.SpringStatemachine.Configuration.OrderStates;
import com.weihan1394.SpringStatemachine.Entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.transition.Transition;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
  @Autowired
  StateMachineFactory<OrderStates, OrderEvents> factory;

  public StateMachine<OrderStates, OrderEvents> newOrder(Order order) {
    StateMachine<OrderStates, OrderEvents> stateMachine = factory.getStateMachine("PO-" + order.getId());
    stateMachine.getExtendedState().getVariables().put("order", order);
    stateMachine.start();
    return stateMachine;
  }

  public boolean fire(StateMachine<OrderStates, OrderEvents> stateMachine, OrderEvents event) {
    return stateMachine.sendEvent(event);
  }

  @SuppressWarnings("unchecked")
  public List<Transition<OrderStates, OrderEvents>> getTransitions(StateMachine<OrderStates, OrderEvents> stateMachine) {
    List<Transition<OrderStates, OrderEvents>> transitions = new ArrayList<>();

    for (Object objTrans : stateMachine.getTransitions()) {
      Transition<OrderStates, OrderEvents> transition = (Transition<OrderStates, OrderEvents>) objTrans;

      if (transition.getSource().getId().equals(stateMachine.getState().getId())) {
        transitions.add(transition);
      }
    }

    return transitions;
  }
}
