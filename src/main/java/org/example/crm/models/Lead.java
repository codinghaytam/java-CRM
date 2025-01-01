package org.example.crm.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Lead {
    private String leadId;
    private String Name;
    private String HeadQuarters;
    private String Phone;
    private String Email;
    private String agentId;
}
