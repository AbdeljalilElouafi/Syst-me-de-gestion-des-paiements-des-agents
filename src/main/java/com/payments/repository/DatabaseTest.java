package main.java.com.payments.repository;

import main.java.com.payments.model.Agent;
import main.java.com.payments.model.TypeAgent;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DatabaseTest {

    public static void main(String[] args) {
        try {
            System.out.println("=== Testing Database Connection and AgentRepository ===\n");

            // Test 1: Database Connection
            System.out.println("1. Testing database connection...");
            AgentRepository repository = new AgentRepository();
            System.out.println("✅ Database connection successful!\n");

            // Test 2: Save a new agent
            System.out.println("2. Testing save operation...");
            Agent newAgent = new Agent("simo", "el", "simo@gmail.com", "0000", TypeAgent.OUVRIER, null);
            newAgent.setFirstName("abdo");
            newAgent.setLastName("el");
            newAgent.setEmail("abdo@gmail.com");
            newAgent.setPassword("0000");
            newAgent.setType(TypeAgent.DIRECTEUR); // or TypeAgent.USER depending on your enum
            newAgent.setDepartmentId(null); // or set a department ID if you have departments

            Agent savedAgent = repository.save(newAgent);
            System.out.println("✅ Agent saved with ID: " + savedAgent.getId() + "\n");

            // Test 3: Find agent by ID
            System.out.println("3. Testing find by ID...");
            Optional<Agent> foundAgent = repository.findById(savedAgent.getId());
            if (foundAgent.isPresent()) {
                System.out.println("✅ Agent found: " + foundAgent.get().getFirstName() + " " + foundAgent.get().getLastName());
            } else {
                System.out.println("❌ Agent not found!");
            }
            System.out.println();

            // Test 4: Find agent by email
            System.out.println("4. Testing find by email...");
            Optional<Agent> foundByEmail = repository.findByEmail("john.doe@test.com");
            if (foundByEmail.isPresent()) {
                System.out.println("✅ Agent found by email: " + foundByEmail.get().getEmail());
            } else {
                System.out.println("❌ Agent not found by email!");
            }
            System.out.println();

            // Test 5: Update agent
            System.out.println("5. Testing update operation...");
            savedAgent.setFirstName("Jane");
            savedAgent.setLastName("Smith");
            repository.update(savedAgent);
            System.out.println("✅ Agent updated successfully!\n");

            // Test 6: Find all agents
            System.out.println("6. Testing find all agents...");
            List<Agent> allAgents = repository.findAll();
            System.out.println("✅ Total agents found: " + allAgents.size());
            for (Agent agent : allAgents) {
                System.out.println("   - " + agent.getId() + ": " + agent.getFirstName() + " " + agent.getLastName());
            }
            System.out.println();

            // Test 7: Delete agent
            System.out.println("7. Testing delete operation...");
            repository.deleteById(savedAgent.getId());
            System.out.println("✅ Agent deleted successfully!\n");

            // Verify deletion
            System.out.println("8. Verifying deletion...");
            Optional<Agent> deletedAgent = repository.findById(savedAgent.getId());
            if (!deletedAgent.isPresent()) {
                System.out.println("✅ Agent successfully deleted!");
            } else {
                System.out.println("❌ Agent still exists!");
            }

            System.out.println("\n=== All tests completed successfully! ===");

        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}