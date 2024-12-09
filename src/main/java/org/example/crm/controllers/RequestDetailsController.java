package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.example.crm.dao.impl.RequestDaoImpl;
import org.example.crm.models.Request;

public class RequestDetailsController {

    @FXML private Label labelRequestId;
    @FXML private Label labelAgentId;
    @FXML private Label labelLeadId;
    @FXML private Label labelLoyaltyCardId;
    @FXML private TextArea textAreaDescription;

    public void setRequest(Request request) {
        labelRequestId.setText(request.getId());
        labelAgentId.setText(request.getAgentId());
        labelLeadId.setText(request.getLeadId());
        labelLoyaltyCardId.setText(request.getLoyaltyCardId());
        textAreaDescription.setText(request.getDescription());
    }

    @FXML
    public void handleAcceptRequest(){
        String requestId = labelRequestId.getText();
        String leadId = labelLeadId.getText();
        String loyaltyCardId = labelLoyaltyCardId.getText();

        RequestDaoImpl req = new RequestDaoImpl();
        if(req.acceptRequest(requestId,leadId,loyaltyCardId)){
            System.out.println("Accepted request");
        }
        else{
            System.out.println("error in accepting request");
        }
    }

    @FXML
    public void handleRejectRequest(){
        String requestId = labelRequestId.getText();
        String leadId = labelLeadId.getText();
        String loyaltyCardId = labelLoyaltyCardId.getText();

        RequestDaoImpl req = new RequestDaoImpl();
        if(req.rejectRequest(requestId,leadId,loyaltyCardId)){
            System.out.println("Request Rejected");
        }
        else{
            System.out.println("error in rejecting request");
        }
    }

}
