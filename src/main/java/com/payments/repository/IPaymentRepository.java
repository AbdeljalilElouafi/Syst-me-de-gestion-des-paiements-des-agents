package main.java.com.payments.repository;

import main.java.com.payments.model.Payment;

import java.util.List;
import java.util.Optional;

public interface IPaymentRepository {
    Payment save(Payment payment) throws Exception;
    Optional<Payment> findById(int id) throws Exception;
    List<Payment> findAll() throws Exception;
    List<Payment> findByAgentId(int agentId) throws Exception;
    void deleteById(int id) throws Exception;
}
