package org.example.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class lead {
    private String leadId;
    private String Name;
    private String HeadQuarters;
    private String Phone;
    private String Email;
    private String agentId;
}
