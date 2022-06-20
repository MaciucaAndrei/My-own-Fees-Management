package project.licenta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.deltaspike.core.util.StringUtils;
import project.licenta.entity.Taxes;
import project.licenta.service.TaxesService;
import project.licenta.utils.GetInstance;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
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
    private BarChart<String, Number> barChart;
    private final TaxesService taxesService = GetInstance.of(TaxesService.class);

    public void start(String user) {
        this.user = user;
        if (cmbType.getItems().isEmpty()) {
            cmbType.getItems().addAll("One day", "One month", "One year", "A period of time");
        }
        for (Node node : paneMenu.getChildren()) {
            if (node.getClass().equals(Button.class)) {
                node.setOnMouseEntered(e -> {
                    node.setStyle("-fx-background-color: transparent;-fx-text-fill:#000080;");
                    node.setTranslateX(5);
                });
                node.setOnMouseExited(e -> {
                    node.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2;");
                    node.setTranslateX(-5);
                });
            }
        }
    }

    public void btnCostsOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("costs.fxml"));
        Stage stage = (Stage) btnCosts.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Costs menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnLibraryOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("library.fxml"));
        Stage stage = (Stage) btnLibrary.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Library menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnBackOnClick() throws IOException, AWTException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Menu menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnLogoutOnClick() throws IOException {
        File path = FileUtils.getUserDirectory().getAbsoluteFile();
        File file = new File(path.getAbsolutePath() + File.separator + "user.txt");
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.write(user + ";" + "false");
        writer.close();
        Stage logout = (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        logout.setScene(scene);
    }

    public void cmbTypeOnChange() {
        if (StringUtils.isNotEmpty(cmbType.getValue())) {
            switch (cmbType.getValue()) {
                case "One day": {
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
                case "One month": {
                    paneTime.setVisible(true);
                    cmbTime.setVisible(true);
                    paneStartDate.setVisible(false);
                    dtpStartDate.setVisible(false);
                    paneEndDate.setVisible(false);
                    dtpEndDate.setVisible(false);
                    cmbTime.setPromptText("Months");
                    if (!cmbTime.getItems().isEmpty()) {
                        cmbTime.getItems().clear();
                    }
                    if (cmbTime.getItems().isEmpty()) {
                        cmbTime.getItems().addAll("January", "February", "March", "April", "May", "June", "July"
                                , "August", "September", "October", "November", "December");
                    }
                    break;
                }
                case "One year": {
                    paneTime.setVisible(true);
                    cmbTime.setVisible(true);
                    paneStartDate.setVisible(false);
                    dtpStartDate.setVisible(false);
                    paneEndDate.setVisible(false);
                    dtpEndDate.setVisible(false);
                    cmbTime.setPromptText("Years");
                    if (!cmbTime.getItems().isEmpty()) {
                        cmbTime.getItems().clear();
                    }
                    if (cmbTime.getItems().isEmpty()) {
                        Calendar c = Calendar.getInstance();
                        cmbTime.getItems().add(String.valueOf(c.get(Calendar.YEAR)));
                        List<Taxes> all = taxesService.findAll();
                        for (Taxes tax : all) {
                            if (tax.getUser().equals(user)) {
                                if (!cmbTime.getItems().contains(String.valueOf(tax.getPayment_date().get(Calendar.YEAR)))) {
                                    cmbTime.getItems().add(String.valueOf(tax.getPayment_date().get(Calendar.YEAR)));
                                }
                            }
                        }

                    }
                    break;
                }
                case "A period of time": {
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

    public boolean fieldsValidation(String type, ComboBox<String> time, DatePicker start, DatePicker end) {
        if (type == null) {
            lblError.setText("Fill in all  the fields");
            javafx.scene.paint.Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            javafx.scene.text.Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if (time.isVisible()) {
            if (time.getValue().isBlank()) {
                lblError.setText("Fill in all  the fields");
                javafx.scene.paint.Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                javafx.scene.text.Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
        }
        if (start.isVisible()) {
            Calendar s = Calendar.getInstance();
            s.set(start.getValue().getYear(), start.getValue().getMonthValue() - 1, start.getValue().getDayOfMonth());
            if (s.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
                lblError.setText("The date cannot be in the future");
                javafx.scene.paint.Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                javafx.scene.text.Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
        }
        if (end.isVisible()) {
            Calendar e = Calendar.getInstance();
            Calendar s = Calendar.getInstance();
            e.set(end.getValue().getYear(), end.getValue().getMonthValue() - 1, end.getValue().getDayOfMonth());
            s.set(start.getValue().getYear(), start.getValue().getMonthValue() - 1, start.getValue().getDayOfMonth());
            if (e.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
                lblError.setText("The date cannot be in the future");
                javafx.scene.paint.Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                javafx.scene.text.Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
            if (s.getTimeInMillis() > e.getTimeInMillis()) {
                lblError.setText("The end date cannot be earlier than the start date ");
                Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
            if (s.getTimeInMillis() == e.getTimeInMillis()) {
                lblError.setText("The end date cannot be the same as the start date ");
                Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
        }
        return true;
    }

    public int MonthValue(String month) {
        switch (month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;
        }
    }

    public String MonthName(int m) {
        switch (m) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "Wrong";
        }
    }


    public PieChart createPieChart(DatePicker start) {
        PieChart pie = new PieChart();
        pie.setLayoutX(393);
        pie.setLayoutY(14);
        pie.setPrefWidth(500);
        pie.setPrefHeight(400);
        pie.setLegendVisible(false);
        pie.setLabelsVisible(false);
        List<Taxes> all = taxesService.findAll();
        Calendar date = Calendar.getInstance();
        date.set(start.getValue().getYear(), start.getValue().getMonthValue() - 1, start.getValue().getDayOfMonth());
        for (Taxes tax : all) {
            if (tax.getUser().equals(user) && tax.getPayment_date().get(Calendar.YEAR) == date.get(Calendar.YEAR)
                    && tax.getPayment_date().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                    tax.getPayment_date().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                PieChart.Data data = new PieChart.Data(tax.getPayment_type(), tax.getPayment_amount());
                pie.getData().add(data);
            }
        }
        if (pie.getData().isEmpty()) {
            lblError.setText("There is no taxes in " + start.getEditor().getText());
            javafx.scene.paint.Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            javafx.scene.text.Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
        } else {
            pie.getData().forEach(data -> {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getName() + ": " + data.getPieValue() + "RON");
                Tooltip.install(data.getNode(), tooltip);
                data.pieValueProperty().addListener((observable, oldValue, newValue) ->
                        tooltip.setText(data.getName() + ": " + newValue + "RON"));
            });
            paneContent.getChildren().add(pie);
        }
        return pie;
    }

    @SuppressWarnings("rawtypes")
    public BarChart<String, Number> createBarChart(String type, String time, DatePicker start, DatePicker end) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> bar = new BarChart<>(xAxis, yAxis);
        xAxis.setTickLabelFill(Paint.valueOf("#d9e9f2"));
        yAxis.setTickLabelFill(Paint.valueOf("#d9e9f2"));
        bar.setLayoutX(393);
        bar.setLayoutY(14);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("All taxes in that period");
        HashMap<String, Double> values = new HashMap<>();
        switch (type) {
            case "One month": {
                List<Taxes> all = taxesService.findAll();
                for (Taxes tax : all) {
                    if (tax.getUser().equals(user) && tax.getPayment_date().get(Calendar.MONTH) + 1 == MonthValue(time)
                            && tax.getPayment_date().get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
                        String text = tax.getPayment_date().get(Calendar.DAY_OF_MONTH) + " " +
                                MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1);
                        if (values.containsKey(text)) {
                            values.replace(text, values.get(text) + tax.getPayment_amount());

                        } else {
                            values.put(text, tax.getPayment_amount());
                        }


                    }
                }
                if (values.isEmpty()) {

                    lblError.setText("There is no taxes in " + time);
                    javafx.scene.paint.Paint paint = Paint.valueOf("red");
                    lblError.setTextFill(paint);
                    javafx.scene.text.Font font = new Font("Gadugi", 10);
                    lblError.setFont(font);
                } else {
                    for (String key : values.keySet()) {
                        series.getData().add(new XYChart.Data<>(key, values.get(key)));
                    }
                    series.getData().sort(new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            String[] date1 = ((XYChart.Data<String, Double>) o1).getXValue().split(" ");
                            String[] date2 = ((XYChart.Data<String, Double>) o2).getXValue().split(" ");
                            return Integer.compare(Integer.parseInt(date1[0]), Integer.parseInt(date2[0]));
                        }
                    });
                    bar.getData().add(series);
                    paneContent.getChildren().add(bar);
                }
                return bar;
            }
            case "One year": {
                List<Taxes> all = taxesService.findAll();
                for (Taxes tax : all) {
                    if (tax.getUser().equals(user) && tax.getPayment_date().get(Calendar.YEAR) == Integer.parseInt(time)) {
                        if (values.containsKey(MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1))) {
                            values.replace(MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1), values.get(MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1))
                                    + tax.getPayment_amount());

                        } else {
                            values.put(MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1), tax.getPayment_amount());
                        }


                    }
                }
                if (values.isEmpty()) {
                    lblError.setText("There is no taxes in " + time);
                    javafx.scene.paint.Paint paint = Paint.valueOf("red");
                    lblError.setTextFill(paint);
                    javafx.scene.text.Font font = new Font("Gadugi", 10);
                    lblError.setFont(font);
                } else {
                    for (String key : values.keySet()) {
                        series.getData().add(new XYChart.Data<>(key, values.get(key)));
                    }
                    series.getData().sort((Comparator) (o1, o2) -> Integer.compare(MonthValue(((XYChart.Data<String, Double>) o1).getXValue()), MonthValue(((XYChart.Data<String, Double>) o2).getXValue())));
                    bar.getData().add(series);
                    paneContent.getChildren().add(bar);
                }
                return bar;
            }
            case "A period of time": {
                List<Taxes> all = taxesService.findAll();
                Calendar s = Calendar.getInstance();
                s.set(start.getValue().getYear(), start.getValue().getMonthValue() - 1, start.getValue().getDayOfMonth());
                Calendar e = Calendar.getInstance();
                e.set(end.getValue().getYear(), end.getValue().getMonthValue() - 1, end.getValue().getDayOfMonth());

                for (Taxes tax : all) {
                    if (tax.getUser().equals(user) && s.getTimeInMillis() < tax.getPayment_date().getTimeInMillis()
                            && tax.getPayment_date().getTimeInMillis() < e.getTimeInMillis()) {
                        if (values.containsKey(MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1))) {
                            values.replace(MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1), values.get(MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1))
                                    + tax.getPayment_amount());

                        } else {
                            values.put(MonthName(tax.getPayment_date().get(Calendar.MONTH) + 1), tax.getPayment_amount());
                        }


                    }
                }
                if (values.isEmpty()) {
                    lblError.setText("There is no taxes between " + dtpStartDate.getEditor().getText() + " and " + dtpEndDate.getEditor().getText());
                    javafx.scene.paint.Paint paint = Paint.valueOf("red");
                    lblError.setTextFill(paint);
                    javafx.scene.text.Font font = new Font("Gadugi", 10);
                    lblError.setFont(font);
                } else {
                    for (String key : values.keySet()) {
                        series.getData().add(new XYChart.Data<>(key, values.get(key)));
                    }
                    series.getData().sort((Comparator) Comparator.comparingInt(o -> MonthValue(((XYChart.Data<String, Double>) o).getXValue())));
                    bar.getData().add(series);
                    paneContent.getChildren().add(bar);
                }
                return bar;
            }
            default:
                return bar;
        }

    }

    public void btnShowOnClick() {
        lblError.setText("");
        if (pieChart != null) {
            paneContent.getChildren().remove(pieChart);

        }
        if (barChart != null) {
            paneContent.getChildren().remove(barChart);

        }
        if (fieldsValidation(cmbType.getValue(), cmbTime, dtpStartDate, dtpEndDate)) {
            if (cmbType.getValue().equals("One day")) {
                pieChart = createPieChart(dtpStartDate);

            } else {

                barChart = createBarChart(cmbType.getValue(), cmbTime.getValue(), dtpStartDate, dtpEndDate);
            }

        }

    }
}
