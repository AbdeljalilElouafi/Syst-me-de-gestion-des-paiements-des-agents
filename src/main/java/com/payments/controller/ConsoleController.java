package main.java.com.payments.controller;

import main.java.com.payments.model.*;
import main.java.com.payments.service.AgentService;
import main.java.com.payments.service.DepartmentService;
import main.java.com.payments.service.PaymentService;
import main.java.com.payments.view.ConsoleView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ConsoleController {
    private final ConsoleView view;
    private final AgentService agentService;
    private final DepartmentService departmentService;
    private final PaymentService paymentService;
    private Agent currentUser;

    public ConsoleController(ConsoleView view, AgentService agentService, DepartmentService departmentService, PaymentService paymentService) {
        this.view = view;
        this.agentService = agentService;
        this.departmentService = departmentService;
        this.paymentService = paymentService;
    }

    public void start() {
        while (true) { // application never stops until "Quitter"

            // === LOGIN LOOP ===
            while (currentUser == null) {
                view.showLine();
                String email = view.askString("Email:");
                String pwd = view.askString("Mot de passe:");
                Optional<Agent> user = agentService.authenticate(email, pwd);
                if (user.isPresent()) {
                    currentUser = user.get();
                    view.show("Bienvenue, " + currentUser.getFirstName() + " (" + currentUser.getType() + ")");
                } else {
                    view.show("Identifiants invalides, réessayez.");
                }
            }

            // === MAIN MENU ===
            if (currentUser.getType() == TypeAgent.RESPONSABLE_DEPARTEMENT || currentUser.getType() == TypeAgent.DIRECTEUR) {
                view.show("1) Départements  2) Agents  3) Paiements  4) Déconnexion  0) Quitter");
                int choice = view.askInt("Choix:");
                try {
                    switch (choice) {
                        case 1 -> departmentMenu();
                        case 2 -> agentMenu();
                        case 3 -> paymentMenu();
                        case 4 -> { // Déconnexion
                            logout();
                            // no return, just break to restart login
                        }
                        case 0 -> {
                            view.show("Au revoir !");
                            System.exit(0); // stops whole program
                        }
                        default -> view.show("Choix invalide");
                    }
                } catch (Exception e) {
                    view.show("Erreur: " + e.getMessage());
                }
            } else {
                view.show("1) Mes informations  2) Mes paiements  3) Déconnexion  0) Quitter");
                int choice = view.askInt("Choix:");
                switch (choice) {
                    case 1 -> view.show(currentUser.toString());
                    case 2 -> {
                        List<Payment> list = paymentService.getPaymentsForAgent(currentUser.getId());
                        list.forEach(p -> view.show(p.toString()));
                        view.show("Total: " + paymentService.totalForAgent(currentUser.getId()));
                    }
                    case 3 -> { // Déconnexion
                        logout();
                        // no return, will restart login
                    }
                    case 0 -> {
                        view.show("Au revoir !");
                        System.exit(0);
                    }
                    default -> view.show("Choix invalide");
                }
            }
        }
    }

    private void departmentMenu() {
        view.showLine();
        view.show("DEPARTEMENTS: 1) Lister 2) Ajouter 3) Modifier 4) Supprimer 0) Retour");
        int c = view.askInt("Choix:");
        switch (c) {
            case 1 -> {
                List<Department> ds = departmentService.listDepartments();
                ds.forEach(d -> view.show(d.toString()));
            }
            case 2 -> {
                String name = view.askString("Nom du département:");
                Department d = new Department(name);
                Department saved = departmentService.createDepartment(d);
                view.show("Créé: " + saved);
            }
            case 3 -> {
                int id = view.askInt("Id département à modifier:");
                Department existing = departmentService.getDepartmentById(id);
                String name = view.askString("Nouveau nom (vide = garder):");
                if (!name.isBlank()) existing.setName(name);
                departmentService.updateDepartment(existing);
                view.show("Mis à jour: " + existing);
            }
            case 4 -> {
                int id = view.askInt("Id département à supprimer:");
                departmentService.deleteDepartment(id);
                view.show("Supprimé.");
            }
            case 0 -> {}
            default -> view.show("Choix invalide");
        }
    }

    private void agentMenu() {
        view.showLine();
        view.show("AGENTS: 1) Lister 2) Ajouter 3) Modifier 4) Supprimer 5) Voir détails  0) Retour");
        int c = view.askInt("Choix:");
        switch (c) {
            case 1 -> {
                List<Agent> ags = agentService.listAgents();
                ags.forEach(a -> view.show(a.toString()));
            }
            case 2 -> {
                String fn = view.askString("Prénom:");
                String ln = view.askString("Nom:");
                String email = view.askString("Email:");
                String pwd = view.askString("Mot de passe (simple):");
                String type = view.askString("Type (OUVRIER, RESPONSABLE_DEPARTEMENT, DIRECTEUR, STAGIAIRE):");

                String dept = view.askString("Id département (vide si aucun):");
                int deptId = 0;
                if (!dept.isBlank()) deptId = Integer.parseInt(dept);
                Agent a = new Agent(fn, ln, email, pwd, TypeAgent.valueOf(type), deptId);
                Agent saved = agentService.createAgent(a);
                view.show("Agent créé: " + saved);
            }
            case 3 -> {
                int id = view.askInt("Id agent à modifier:");
                Agent a = agentService.getAgentById(id);
                String fn = view.askString("Prénom (" + a.getFirstName() + "):");
                if (!fn.isBlank()) a.setFirstName(fn);
                String ln = view.askString("Nom (" + a.getLastName() + "):");
                if (!ln.isBlank()) a.setLastName(ln);
                String type = view.askString("Type (" + a.getType() + "):");
                if (!type.isBlank()) a.setType(TypeAgent.valueOf(type));
                String dept = view.askString("Id département (" + a.getDepartmentId() + "):");
                if (!dept.isBlank()) a.setDepartmentId(Integer.parseInt(dept));
                agentService.updateAgent(a);
                view.show("Mis à jour.");
            }
            case 4 -> {
                int id = view.askInt("Id agent à supprimer:");
                agentService.deleteAgent(id);
                view.show("Supprimé.");
            }
            case 5 -> {
                int id = view.askInt("Id agent:");
                Agent a = agentService.getAgentById(id);
                view.show("Agent: " + a);
                view.show("Historique paiements:");
                List<Payment> payments = paymentService.getPaymentsForAgent(id);
                payments.forEach(p -> view.show(p.toString()));
                view.show("Total: " + paymentService.totalForAgent(id));
            }
            case 0 -> {}
            default -> view.show("Choix invalide");
        }
    }

    private void paymentMenu() {
        view.showLine();
        view.show("PAIEMENTS: 1) Ajouter 2) Lister par agent 3) Statistiques agent 0) Retour");
        int c = view.askInt("Choix:");
        switch (c) {
            case 1 -> {
                int agentId = view.askInt("Id agent destinataire:");
                String t = view.askString("Type (SALAIRE, PRIME, BONUS, INDEMNITE):");
                String amount = view.askString("Montant (ex: 1000.50):");
                String date = view.askString("Date yyyy-mm-dd:");
                String motif = view.askString("Motif:");
                boolean cond = false;
                if (t.equals("BONUS") || t.equals("INDEMNITE")) {
                    String cval = view.askString("Condition validée ? (y/n):");
                    cond = cval.equalsIgnoreCase("y");
                }
                Payment p = new Payment(
                        TypePayment.valueOf(t),
                        new BigDecimal(amount),
                        LocalDate.parse(date),
                        motif,
                        cond,
                        agentId
                );
                Payment saved = paymentService.addPayment(p);
                view.show("Paiement enregistré: " + saved);
            }
            case 2 -> {
                int agentId = view.askInt("Id agent:");
                List<Payment> list = paymentService.getPaymentsForAgent(agentId);
                list.forEach(p -> view.show(p.toString()));
            }
            case 3 -> {
                int agentId = view.askInt("Id agent:");
                view.show("Total: " + paymentService.totalForAgent(agentId));
                view.show("Moyenne: " + paymentService.averageForAgent(agentId));
                view.show("Top 3 paiements:");
                paymentService.highestPaymentsForAgent(agentId, 3).forEach(p -> view.show(p.toString()));
                view.show("Paiements inhabituels:");
                paymentService.unusualPaymentsForAgent(agentId).forEach(p -> view.show(p.toString()));
            }
            case 0 -> {}
            default -> view.show("Choix invalide");
        }
    }

    private void logout() {
        currentUser = null;
        view.show("Déconnecté avec succès !");
    }
}
