package project.licenta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.apache.deltaspike.core.util.StringUtils;
import project.licenta.entity.Taxes;
import project.licenta.service.TaxesService;
import project.licenta.utils.GetInstance;


import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Graphic {


    @FXML
    private Button btnBack;

    @FXML
    private Button btnCosts;

    @FXML
    private Button btnLibrary;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnShow;

    @FXML
    private ComboBox<String> cmbTime;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private DatePicker dtpEndDate;

    @FXML
    private DatePicker dtpStartDate;

    @FXML
    private Label lblError;

    @FXML
    private AnchorPane paneMenu;

    @FXML
    private AnchorPane paneTime;

    @FXML
    private AnchorPane paneStartDate;

    @FXML
    private AnchorPane paneEndDate;


    private String user;
    private TaxesService taxesService = GetInstance.of(TaxesService.class);

    public void start(String user)
    {
        this.user=user;
        if(cmbType.getItems().isEmpty())
        {
            cmbType.getItems().addAll("One day", "One month", "One year", "A period of time");
        }
    }

    public void btnCostsOnClick(ActionEvent e) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("costs.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Costs menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnLibraryOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("library.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Library menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnBackOnClick(ActionEvent e) throws IOException, AWTException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Menu menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnLogoutOnClick(ActionEvent event) throws IOException
    {
        Stage logout= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        logout.setScene(scene);
    }

    public void cmbTypeOnChange(ActionEvent event)
    {
        if(StringUtils.isNotEmpty(cmbType.getValue()))
        {
            switch (cmbType.getValue())
            {
                case "One day":
                {
                    paneTime.setVisible(false);
                    paneStartDate.setVisible(true);
                    paneEndDate.setVisible(false);
                    LocalDate l = LocalDate.now();
                    dtpStartDate.setValue(l);
                    break;
                }
                case "One month":
                {
                    paneTime.setVisible(true);
                    paneStartDate.setVisible(false);
                    paneEndDate.setVisible(false);
                    cmbTime.setPromptText("Months");
                    if(!cmbTime.getItems().isEmpty())
                    {
                        cmbTime.getItems().clear();
                    }
                    if(cmbTime.getItems().isEmpty())
                    {
                        cmbTime.getItems().addAll("January",	"February", "March", "April", "May" ,"June", "July"
                                ,"August" ,"September" ,"October","November", "December");
                    }
                    break;
                }
                case "One year":
                {
                    paneTime.setVisible(true);
                    paneStartDate.setVisible(false);
                    paneEndDate.setVisible(false);
                    cmbTime.setPromptText("Years");
                    if(!cmbTime.getItems().isEmpty())
                    {
                        cmbTime.getItems().clear();
                    }
                    if(cmbTime.getItems().isEmpty())
                    {
                        Calendar c=Calendar.getInstance();
                        cmbTime.getItems().add(String.valueOf(c.get(Calendar.YEAR)));
                        List<Taxes> all =taxesService.findAll();
                        for(Taxes tax : all)
                        {
                            if(tax.getUser().equals(user))
                            {
                                if(!cmbTime.getItems().contains(tax.getPayment_date().get(Calendar.YEAR)))
                                {
                                    cmbTime.getItems().add(String.valueOf(tax.getPayment_date().get(Calendar.YEAR)));
                                }
                            }
                        }

                    }
                    break;
                }
                case "A period of time":
                {
                    paneTime.setVisible(false);
                    paneStartDate.setVisible(true);
                    paneEndDate.setVisible(true);
                    LocalDate l = LocalDate.now();
                    dtpStartDate.setValue(l);
                    dtpEndDate.setValue(l);
                }


            }
        }
    }

    public void btnShowOnClick(ActionEvent event)
    {

    }
}
