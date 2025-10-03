package main.java.com.payments.service;

import main.java.com.payments.exception.NotFoundException;
import main.java.com.payments.model.Agent;
import main.java.com.payments.repository.AgentRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AgentService implements IAgentService {
    private final AgentRepository agentRepo;

    public AgentService(AgentRepository agentRepo) {
        this.agentRepo = agentRepo;
    }

    @Override
    public Agent createAgent(Agent agent) {
        try {
            return agentRepo.save(agent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Agent getAgentById(int id) {
        try {
            Optional<Agent> o = agentRepo.findById(id);
            return o.orElseThrow(() -> new NotFoundException("Agent introuvable: " + id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Agent> listAgents() {
        try {
            return agentRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAgent(Agent a) {
        try {
            agentRepo.update(a);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAgent(int id) {
        try {
            agentRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Agent> authenticate(String email, String password) {
        try {
            return agentRepo.findAll().stream()
                    .filter(a -> a.getEmail().equals(email) && a.getPassword().equals(password))
                    .findFirst();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}


