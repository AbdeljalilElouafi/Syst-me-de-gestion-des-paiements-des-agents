package main.java.com.payments.model;

import java.util.Objects;

public class Agent extends Person {
    private Integer id;
    private TypeAgent type;
    private Integer departmentId;

    public Agent(Integer id, String firstName, String lastName, String email, String password, TypeAgent type, Integer departmentId) {
        super(firstName, lastName, email, password);
        this.id = id;
        this.type = type;
        this.departmentId = departmentId;
    }

    public Agent(String firstName, String lastName, String email, String password, TypeAgent type, Integer departmentId) {
        this(null, firstName, lastName, email, password, type, departmentId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeAgent getType() {
        return type;
    }

    public void setType(TypeAgent type) {
        this.type = type;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return String.format("Agent[id=%d, %s %s, type=%s, deptId=%s]",
                id, firstName, lastName, type, departmentId==null?"N/A":departmentId.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return Objects.equals(id, agent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
