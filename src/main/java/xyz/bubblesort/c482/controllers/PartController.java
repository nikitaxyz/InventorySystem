package xyz.bubblesort.c482.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import xyz.bubblesort.c482.models.*;
import xyz.bubblesort.c482.utils.IDGenerator;

/**
 * Controller for Part form views
 *
 * @author Nikita Kukshynsky
 */
public class PartController extends ControllerHelper {
    /**
     * Text field for a part name
     */
    public TextField partName;
    /**
     * Text field for a part min
     */
    public TextField partMin;
    /**
     * Text field for a machine id or company name depending on part type
     */
    public TextField partMachineOrCompany;
    /**
     * Text field for a part id
     */
    public TextField partID;
    /**
     * Text field for a part max
     */
    public TextField partMax;
    /**
     * Text field for a part stock/inv
     */
    public TextField partInv;
    /**
     * Text field for a part price
     */
    public TextField partPrice;
    /**
     * Radio button for an InHouse part type
     */
    public RadioButton radioInHouse;
    /**
     * Radio button for an Outsourced part type
     */
    public RadioButton radioOutsourced;
    /**
     * Label for a machine id or company name depending on part type
     */
    public Label textMachineOrCompany;
    /**
     * Label for displaying errors
     */
    public Label errors;

    private ToggleGroup group;
    private int activeID;

    /**
     * Initializes toggle group
     */
    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        radioInHouse.setToggleGroup(group);
        radioOutsourced.setToggleGroup(group);
        radioInHouse.setSelected(true);
        this.group = group;
    }

    /**
     * Changes to Main form on Cancel button click
     */
    @FXML
    public void onCancel() {
        sceneController.setScene("main");
        MainController mc = (MainController) sceneController.getController("main");
        mc.clearErrors();
        clear();
    }

    /**
     * Sets generated id for a part
     */
    @FXML
    public void setPartID() {
        int newID = IDGenerator.getNextPartID();
        setActiveID(newID);
        partID.setText("Auto Gen - Disabled");
    }

    /**
     * Sets active id
     * @param id id to set as active id
     */
    public void setActiveID(int id) {
        activeID = id;
    }

    /**
     * Switch toggle group
     * @param actionEvent action event of a button click
     */
    public void onRadioClick(ActionEvent actionEvent) {
        String id = ((Node) actionEvent.getSource()).getId();
        if (id.equals("radioInHouse")) {
            textMachineOrCompany.setText("Machine ID");
        } else {
            textMachineOrCompany.setText("Company Name");
        }
    }

    /**
     * Clears all Text Fields
     */
    private void clear() {
        partName.setText("");
        partMin.setText("");
        partMax.setText("");
        partPrice.setText("");
        partMachineOrCompany.setText("");
        partInv.setText("");
        errors.setText("");
    }

    /**
     * Saves a new part if all fields are valid
     */
    private void save() {
        StringBuilder errorsText = new StringBuilder("Exceptions:\n");
        boolean isError = false;

        int ID = activeID;

        String name = "";
        if (partName.getText().isEmpty()) {
            errorsText.append("No data in Name field\n");
            isError = true;
        } else {
            name = partName.getText();
        }

        int inv = -1;
        try {
            inv = Integer.parseInt(partInv.getText());
        } catch (NumberFormatException nfe) {
            errorsText.append("Inventory is not an integer\n");
            isError = true;
        }

        double price = 0;
        try {
            price = Double.parseDouble(partPrice.getText());
        } catch (NumberFormatException nfe) {
            errorsText.append("Price is not a double\n");
            isError = true;
        }

        int max = -1;
        try {
            max = Integer.parseInt(partMax.getText());
        } catch (NumberFormatException nfe) {
            errorsText.append("Max is not an integer\n");
            isError = true;
        }

        int min = -1;
        try {
            min = Integer.parseInt(partMin.getText());
        } catch (NumberFormatException nfe) {
            errorsText.append("Min is not an integer\n");
            isError = true;
        }

        if (max != -1 && min != -1 && inv != -1 && (inv < min || inv > max)) {
            errorsText.append("Inventory must be between Min and Max\n");
            isError = true;
        }

        if (max != -1 && min != -1 && min >= max) {
            errorsText.append("Max must be greater than Min\n");
            isError = true;
        }


        if (((Node) group.getSelectedToggle()).getId().equals("radioInHouse")) {
            int machineID = 0;
            try {
                machineID = Integer.parseInt(partMachineOrCompany.getText());
            } catch (NumberFormatException nfe) {
                errorsText.append("Machine ID is not an integer\n");
                isError = true;
            }
            if (!isError) {
                if (IDGenerator.inHouseIDList.contains(ID)) {
                    InHouse part = (InHouse) Inventory.lookupPart(ID);
                    assert part != null;
                    part.setName(name);
                    part.setStock(inv);
                    part.setPrice(price);
                    part.setMax(max);
                    part.setMin(min);
                    part.setMachineId(machineID);
                } else {
                    if (IDGenerator.outsourcedIDList.contains(ID)) {
                        Inventory.deletePart(Inventory.lookupPart(ID));
                        IDGenerator.outsourcedIDList.remove(ID);
                    }
                    InHouse part = new InHouse(ID, name, price, inv, min, max, machineID);
                    Inventory.addPart(part);
                    IDGenerator.inHouseIDList.add(ID);
                }

            }
        } else {
            String company = partMachineOrCompany.getText();
            if (company.isEmpty()) {
                errorsText.append("No data in Company Name field\n");
                isError = true;
            }
            if (!isError) {
                if (IDGenerator.outsourcedIDList.contains(ID)) {
                    Outsourced part = (Outsourced) Inventory.lookupPart(ID);
                    assert part != null;
                    part.setName(name);
                    part.setStock(inv);
                    part.setPrice(price);
                    part.setMax(max);
                    part.setMin(min);
                    part.setCompanyName(company);
                } else {
                    if (IDGenerator.inHouseIDList.contains(ID)) {
                        Inventory.deletePart(Inventory.lookupPart(ID));
                        IDGenerator.inHouseIDList.remove(ID);
                    }
                    Outsourced part = new Outsourced(ID, name, price, inv, min, max, company);
                    Inventory.addPart(part);
                    IDGenerator.outsourcedIDList.add(ID);
                }

            }
        }

        if (!isError) {
            clear();
            sceneController.setScene("main");
        } else {
            errors.setText(errorsText.toString());
        }
    }

    /**
     * Saves a part and refresh parts table
     */
    public void onSave() {
        save();
        MainController mc = (MainController) sceneController.getController("main");
        mc.partsTable.refresh();
    }

    /**
     * Modify InHouse part
     * @param part part to modify
     */
    public void modifyPart(InHouse part) {
        setCommonParameters(part.getId(), part.getName(), part.getStock(), part.getPrice(), part.getMax(), part.getMin());
        partMachineOrCompany.setText(String.valueOf(part.getMachineId()));
        textMachineOrCompany.setText("Machine ID");
        group.selectToggle(radioInHouse);
        setActiveID(part.getId());
    }

    /**
     * Modify Outsourced part
     * @param part part to modify
     */
    public void modifyPart(Outsourced part) {
        setCommonParameters(part.getId(), part.getName(), part.getStock(), part.getPrice(), part.getMax(), part.getMin());
        partMachineOrCompany.setText(String.valueOf(part.getCompanyName()));
        textMachineOrCompany.setText("Company Name");
        group.selectToggle(radioOutsourced);
        setActiveID(part.getId());
    }

    /**
     * Set common parameters for a part
     * @param id id of a part
     * @param name name of a part
     * @param stock stock of a part
     * @param price price of a part
     * @param max max of a part
     * @param min min of a part
     */
    private void setCommonParameters(int id, String name, int stock, double price, int max, int min) {
        partID.setText(String.valueOf(id));
        partName.setText(name);
        partInv.setText(String.valueOf(stock));
        partPrice.setText(String.valueOf(price));
        partMax.setText(String.valueOf(max));
        partMin.setText(String.valueOf(min));
    }
}
