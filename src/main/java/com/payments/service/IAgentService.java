package main.java.com.payments.service;

import main.java.com.payments.model.Agent;

import java.util.List;

public interface IAgentService {

    Agent createAgent(Agent agent);
    Agent getAgentById(int id);
    List<Agent> listAgents();
    void updateAgent(Agent agent);
    void deleteAgent(int id);

}
