package org.example.crm.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Lead {
    protected String entrepriseId;
    protected String entrepriseName;
    protected String headquarters;
    protected String phone;
    protected String email;
    private String agent_CNE;
    public Lead(Lead lead) {
    	this.entrepriseId = lead.entrepriseId;
    	this.entrepriseName = lead.entrepriseName;
    	this.headquarters = lead.headquarters;
    	this.phone = lead.phone;
    	this.email = lead.email;
    	this.agent_CNE = lead.agent_CNE;
    }
}
