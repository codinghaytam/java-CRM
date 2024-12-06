package org.example.crm.dao;

import org.example.crm.models.AgentCommercial;

import java.util.List;
public interface AgentCommercialDao {
    boolean verifyLogin(String login, String password);
    AgentCommercial getAgentByCNE(String CNE);
}