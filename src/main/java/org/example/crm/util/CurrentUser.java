package org.example.crm.util;

import lombok.Getter;
import lombok.Setter;

public class CurrentUser {
    @Setter
    @Getter
    private static String loggedInAdmin;

    @Setter
    @Getter
    private static String loggedInCommercial;  // Ajout pour AgentCommercial
}