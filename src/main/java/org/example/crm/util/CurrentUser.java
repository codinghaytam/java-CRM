package org.example.crm.util;

import lombok.Getter;
import lombok.Setter;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Supervisor;

public class CurrentUser {
    @Setter
    @Getter
    private static String loggedInAdmin;

    @Setter
    @Getter
    private static String loggedInCommercial;  // Ajout pour AgentCommercial
}
