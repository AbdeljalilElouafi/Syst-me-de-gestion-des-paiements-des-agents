package main.java.com.payments.repository;

import main.java.com.payments.model.Agent;

import java.util.List;
import java.util.Optional;

public interface IAgentRepository {
    Agent save(Agent agent) throws Exception;
    Optional<Agent> findById(int id) throws Exception;
    Optional<Agent> findByEmail(String email) throws Exception;
    List<Agent> findAll() throws Exception;
    void update(Agent agent) throws Exception;
    void deleteById(int id) throws Exception;
}
