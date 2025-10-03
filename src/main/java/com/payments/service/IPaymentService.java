package main.java.com.payments.service;

import main.java.com.payments.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface IPaymentService {

    Payment addPayment(Payment p);
    List<Payment> getPaymentsForAgent(int agentId);
    List<Payment> listAllPayments();
    BigDecimal totalForAgent(int agentId);
    BigDecimal averageForAgent(int agentId);
    List<Payment> highestPaymentsForAgent(int agentId, int n);
    List<Payment> unusualPaymentsForAgent(int agentId);

}
