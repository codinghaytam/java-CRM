package org.example.crm.models;

import javafx.beans.property.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private StringProperty id = new SimpleStringProperty();
    private StringProperty leadId = new SimpleStringProperty();
    private StringProperty agentId = new SimpleStringProperty();
    private StringProperty loyaltyCardId = new SimpleStringProperty();
    private ObjectProperty<statut> status = new SimpleObjectProperty<>();
    private ObjectProperty<Date> creation_date = new SimpleObjectProperty<>();
    private StringProperty description = new SimpleStringProperty();

    // Constructors that set properties directly
    public Request(String id, String leadId, String agentId, String loyaltyCardId, statut status, Date creation_date, String description) {
        this.id.set(id);
        this.leadId.set(leadId);
        this.agentId.set(agentId);
        this.loyaltyCardId.set(loyaltyCardId);
        this.status.set(status);
        this.creation_date.set(creation_date);
        this.description.set(description);
    }


    // Property access methods for binding (for JavaFX binding purposes)
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty leadIdProperty() {
        return leadId;
    }

    public StringProperty agentIdProperty() {
        return agentId;
    }

    public StringProperty loyaltyCardIdProperty() {
        return loyaltyCardId;
    }

    public ObjectProperty<statut> statusProperty() {
        return status;
    }

    public ObjectProperty<Date> creationDateProperty() {
        return creation_date;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // Getter methods for values (to retrieve actual values, not properties)
    public String getId() {
        return id.get();
    }

    public String getLeadId() {
        return leadId.get();
    }

    public String getAgentId() {
        return agentId.get();
    }

    public String getLoyaltyCardId() {
        return loyaltyCardId.get();
    }

    public statut getStatus() {
        return status.get();
    }

    public Date getCreationDate() {
        return creation_date.get();
    }

    public String getDescription() {
        return description.get();
    }
}
