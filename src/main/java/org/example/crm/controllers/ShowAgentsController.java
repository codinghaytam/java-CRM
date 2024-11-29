package org.example.crm.controllers;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.example.crm.dao.impl.SupervisorDaoImpl;
import org.example.crm.models.AgentCommercial;
import java.net.URL;
import java.util.ResourceBundle;
public class ShowAgentsController implements Initializable {
    @FXML
    private TableView<AgentCommercial> agentTable;
    @FXML
    private TableColumn<AgentCommercial, String> cneColumn;
    @FXML
    private TableColumn<AgentCommercial, String> nameColumn;
    @FXML
    private TableColumn<AgentCommercial, String> surnameColumn;
    @FXML
    private TableColumn<AgentCommercial, String> passwordColumn;
    @FXML
    private TableColumn<AgentCommercial, Void> deleteColumn;
    @FXML
    private Label messageLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAgentData();
    }
    public void loadAgentData() {
        ObservableList<AgentCommercial> agentList = FXCollections.observableArrayList(new SupervisorDaoImpl().showAgents());
        // Set up the cell factory for each column manually
        cneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cneColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getCNE());
        });
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getNom());
        });
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getPrenom());
        });
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getPassword());
        });
        // Set up the delete column with a delete button
        deleteColumn.setCellFactory(param -> new TableCell<AgentCommercial, Void>() {
            private final Button deleteButton = new Button("Delete");
            {
                deleteButton.setOnAction(event -> {
                    AgentCommercial agent = getTableView().getItems().get(getIndex());
                    handleDeleteAction(agent);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });   
        // Populate the table with data
        agentTable.setItems(agentList);
    }
    private void handleDeleteAction(AgentCommercial agent) {
        // Call DAO to delete the agent from the database
        SupervisorDaoImpl supervisorDao = new SupervisorDaoImpl();
        if (supervisorDao.deleteAgent(agent.getCNE())) {
            // Remove the agent from the table
            agentTable.getItems().remove(agent);
            System.out.println("Agent " + agent.getCNE() + " deleted successfully.");
            messageLabel.setText("Agent " + agent.getCNE() + " deleted successfully.");
        } else {
            System.out.println("Error deleting agent " + agent.getCNE());
            messageLabel.setText("Error deleting agent " + agent.getCNE());
        }
    }
}