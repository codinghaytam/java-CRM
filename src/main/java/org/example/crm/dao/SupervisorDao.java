package org.example.crm.dao;

import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Supervisor;

import java.util.List;

public interface SupervisorDao {
    boolean addAgent(AgentCommercial agent);
    void validateDemande(int demandeId);
    List<String> afficheDemandes();
    boolean deleteAgent(String agentCNE);
    Supervisor getSupervisorByCNE(String CNE);
    List<AgentCommercial> showAgents();
    boolean verifyLogin(String login, String password);
}
