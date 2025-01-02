package org.example.crm.dao;

import org.example.crm.models.AgentCommercial;

public interface AgentCommercialDao {
    boolean verifyLogin(String login, String password);

    AgentCommercial getAgentByCNE(String CNE);
}
