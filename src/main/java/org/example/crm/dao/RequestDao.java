package org.example.crm.dao;

import org.example.crm.models.Request;
import java.util.List;

public interface RequestDao {
    List<Request> showRequests();
    boolean acceptRequest(String demandeId, String leadId, String loyaltyCardId);
    boolean rejectRequest(String demandeId, String leadId, String loyaltyCardId);
    boolean addRequest(Request request);
}
