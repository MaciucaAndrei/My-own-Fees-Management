package project.licenta;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.apache.deltaspike.core.util.StringUtils;
import project.licenta.entity.Taxes;
import project.licenta.service.TaxesService;
import project.licenta.utils.GetInstance;


import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
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

    @FXML
    private AnchorPane paneContent;


    private String user;
    private PieChart pieChart;
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
                    cmbTime.setVisible(false);
                    paneStartDate.setVisible(true);
                    dtpStartDate.setVisible(true);
                    paneEndDate.setVisible(false);
                    dtpEndDate.setVisible(false);
                    LocalDate l = LocalDate.now();
                    dtpStartDate.setValue(l);
                    break;
                }
                case "One month":
                {
                    paneTime.setVisible(true);
                    cmbTime.setVisible(true);
                    paneStartDate.setVisible(false);
                    dtpStartDate.setVisible(false);
                    paneEndDate.setVisible(false);
                    dtpEndDate.setVisible(false);
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
                    cmbTime.setVisible(true);
                    paneStartDate.setVisible(false);
                    dtpStartDate.setVisible(false);
                    paneEndDate.setVisible(false);
                    dtpEndDate.setVisible(false);
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
                    cmbTime.setVisible(false);
                    paneStartDate.setVisible(true);
                    paneEndDate.setVisible(true);
                    dtpStartDate.setVisible(true);
                    dtpEndDate.setVisible(true);
                    LocalDate l = LocalDate.now();
                    dtpStartDate.setValue(l);
                    dtpEndDate.setValue(l);
                }


            }
        }
    }

    public boolean fieldsValidation(String type,ComboBox<String> time,DatePicker start, DatePicker end)
    {
        if(type.isBlank())
        {
            lblError.setText("Fill in all  the fields");
            javafx.scene.paint.Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            javafx.scene.text.Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(time.isVisible())
        {
            if(time.getValue().isBlank())
            {
                lblError.setText("Fill in all  the fields");
                javafx.scene.paint.Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                javafx.scene.text.Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
        }
        if(start.isVisible())
        {
            Calendar s = Calendar.getInstance();
            s.set(start.getValue().getYear(),start.getValue().getMonthValue()-1,start.getValue().getDayOfMonth());
            if(s.getTimeInMillis()> Calendar.getInstance().getTimeInMillis())
            {
                lblError.setText("The date cannot be in the future");
                javafx.scene.paint.Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                javafx.scene.text.Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
        }
        if(end.isVisible())
        {
            Calendar e = Calendar.getInstance();
            e.set(end.getValue().getYear(),end.getValue().getMonthValue()-1,end.getValue().getDayOfMonth());
            if( e.getTimeInMillis()> Calendar.getInstance().getTimeInMillis())
            {
                lblError.setText("The date cannot be in the future");
                javafx.scene.paint.Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                javafx.scene.text.Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
        }
        return true;
    }

    public PieChart createPieChart(DatePicker start)
    {
        PieChart pie = new PieChart();
        pie.setLayoutX(393);
        pie.setLayoutY(14);
        List<Taxes> all = taxesService.findAll();
        Calendar date = Calendar.getInstance();
        date.set(start.getValue().getYear(),start.getValue().getMonthValue()-1,start.getValue().getDayOfMonth());
        for(Taxes tax : all)
        {
            if(tax.getUser().equals(user) && tax.getPayment_date().get(Calendar.YEAR)== date.get(Calendar.YEAR)
            && tax.getPayment_date().get(Calendar.MONTH)== date.get(Calendar.MONTH) && tax.getPayment_date().get(Calendar.DAY_OF_MONTH)== date.get(Calendar.DAY_OF_MONTH) )
            {
                PieChart.Data data = new PieChart.Data(tax.getPayment_type(),tax.getPayment_amount());
                pie.getData().add(data);
                data.nameProperty().bind(
                        Bindings.concat(data.getName(),": "+data.getPieValue()+" RON")
                );

            }
        }
        if(pie.getData().isEmpty())
        {
              Label label = new Label();
              label.setLayoutX(pie.getLayoutX());
              label.setLayoutY(pie.getLayoutY());
              Font font= new Font("Gadugi",10);
              label.setFont(font);
              Paint paint = Paint.valueOf("red");
              label.setTextFill(paint);
              label.setText("There is no taxes in "+start.getEditor().getText());
              paneContent.getChildren().add(label);

        }else {
            paneContent.getChildren().add(pie);

        }
        return pie;
    }

    public void btnShowOnClick(ActionEvent event)
    {
        switch(cmbType.getValue())
        {
            case "One day":
            {
                if(pieChart==null)
                {
                    pieChart=createPieChart(dtpStartDate);
                }else
                {
                    if(paneContent.getChildren().contains(pieChart))
                    {
                        paneContent.getChildren().remove(pieChart);
                        pieChart= createPieChart(dtpStartDate);
                    }
                }

            }

            default : boolean bool = fieldsValidation(cmbType.getValue(),cmbTime,dtpStartDate,dtpEndDate);
        }

    }
}
