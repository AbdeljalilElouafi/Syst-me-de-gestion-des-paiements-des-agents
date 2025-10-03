package main.java.com.payments;

import main.java.com.payments.controller.ConsoleController;
import main.java.com.payments.repository.*;
import main.java.com.payments.service.*;
import main.java.com.payments.view.ConsoleView;

public class App {
    public static void main(String[] args) {
        try {
            // instantiate repositories (JDBC singletons managed by DatabaseConnection)
            AgentRepository agentRepo = new AgentRepository();
            DepartmentRepository deptRepo = new DepartmentRepository();
            PaymentRepository paymentRepo = new PaymentRepository();

            // services
            var agentService = new AgentService(agentRepo);
            var deptService = new DepartmentService(deptRepo);
            var paymentService = new PaymentService(paymentRepo, agentRepo);

            // view + controller
            var view = new ConsoleView();
            var controller = new ConsoleController(view, agentService, deptService, paymentService);
            controller.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur démarrage: " + e.getMessage());
        }
    }
}

