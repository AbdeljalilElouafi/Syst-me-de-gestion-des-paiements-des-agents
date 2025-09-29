package main.java.com.payments.model;

public class Department {
    private Integer id;
    private String name;
    private Integer responsibleAgentId;

    public Department(Integer id, String name, Integer responsibleAgentId) {
        this.id = id;
        this.name = name;
        this.responsibleAgentId = responsibleAgentId;
    }

    public Department(String name) {
        this(null, name, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResponsibleAgentId() {
        return responsibleAgentId;
    }

    public void setResponsibleAgentId(Integer responsibleAgentId) {
        this.responsibleAgentId = responsibleAgentId;
    }

    @Override
    public String toString() {
        return String.format("Department[id=%d, name=%s, responsibleAgentId=%s]",
                id, name, responsibleAgentId==null?"N/A":responsibleAgentId.toString());
    }
}
