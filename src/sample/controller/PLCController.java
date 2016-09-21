package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.model.PLC;

/**
 * Created by VAN on 14.09.2016.
 */
public class PLCController {
    @FXML TableView<PLC> plcTable;
    @FXML TableColumn<PLC, String> plcTableName;
    @FXML TableColumn<PLC, String> plcTablePass;
}
