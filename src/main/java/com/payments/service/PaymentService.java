package main.java.com.payments.service;

import main.java.com.payments.exception.NotFoundException;
import main.java.com.payments.exception.ValidationException;
import main.java.com.payments.model.Agent;
import main.java.com.payments.model.Payment;
import main.java.com.payments.model.TypeAgent;
import main.java.com.payments.model.TypePayment;
import main.java.com.payments.repository.AgentRepository;
import main.java.com.payments.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepo;
    private final AgentRepository agentRepo;

    public PaymentService(PaymentRepository paymentRepo, AgentRepository agentRepo) {
        this.paymentRepo = paymentRepo;
        this.agentRepo = agentRepo;
    }

    @Override
    public Payment addPayment(Payment p) {
        try {
            // validations
            if (p.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Montant négatif interdit.");
            }

            // checking if the agent exist
            Optional<Agent> oa = agentRepo.findById(p.getAgentId());
            Agent agent = oa.orElseThrow(() -> new NotFoundException("Agent introuvable: " + p.getAgentId()));

            // handling bonus or indemnity
            if (p.getType() == TypePayment.BONUS || p.getType() == TypePayment.INDEMNITE) {
                if (!(agent.getType() == TypeAgent.RESPONSABLE_DEPARTEMENT || agent.getType() == TypeAgent.DIRECTEUR)) {
                    throw new ValidationException("Le type d'agent n'est pas éligible pour " + p.getType());
                }
                if (!p.isConditionValidee()) {
                    throw new ValidationException("La condition pour ce paiement n'est pas validée.");
                }
            }

            return paymentRepo.save(p);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Payment> getPaymentsForAgent(int agentId) {
        try {
            return paymentRepo.findByAgentId(agentId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Payment> listAllPayments() {
        try {
            return paymentRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal totalForAgent(int agentId) {
        return getPaymentsForAgent(agentId).stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal averageForAgent(int agentId) {
        List<Payment> payments = getPaymentsForAgent(agentId);
        if (payments.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = totalForAgent(agentId);
        return total.divide(BigDecimal.valueOf(payments.size()), BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<Payment> highestPaymentsForAgent(int agentId, int n) {
        return getPaymentsForAgent(agentId).stream()
                .sorted(Comparator.comparing(Payment::getAmount).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> unusualPaymentsForAgent(int agentId) {
        List<Payment> payments = getPaymentsForAgent(agentId);
        if (payments.isEmpty()) return List.of();
        BigDecimal avg = averageForAgent(agentId);
        BigDecimal threshold = avg.multiply(BigDecimal.valueOf(3));
        return payments.stream()
                .filter(p -> p.getAmount().compareTo(threshold) > 0)
                .collect(Collectors.toList());
    }
}