package project.licenta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.licenta.entity.Semester;
import project.licenta.entity.Subjects;
import project.licenta.service.SemesterService;
import project.licenta.service.SubjectsService;
import project.licenta.utils.GetInstance;
import project.licenta.utils.Timeline;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Timetable {


    @FXML
    private Button btnTimetable;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnBack;
    @FXML
    private TableView<Timeline> tblTimeline;
    @FXML
    private Button btnNew;
    @FXML
    private ComboBox<String> cmbHourStart;
    @FXML
    private ComboBox<String> cmbHourFinish;
    @FXML
    private ComboBox<String> cmbDay;
    @FXML
    private ComboBox<String> cmbSubjectName;
    @FXML
    private ComboBox<String> cmbForm;


    private String user;
    private String semester;
    private List<Timeline> listTimeline = new ArrayList<Timeline>();
    private SemesterService semesterService = GetInstance.of(SemesterService.class);
    private SubjectsService subjectsService = GetInstance.of(SubjectsService.class);


    public void showTimeline(String user,String semester)
    {
        List<Semester> all = semesterService.findAll();
        Menu menu = new Menu();
        for(Semester s : all)
        {
            if(s.getUser().equals(user) && menu.buttonText(s).equals(semester))
            {
                if(s.getTimeline()!=null)
                {
                    HashMap<String,String> timeline= s.getTimeline();
                    for(String key : timeline.keySet())
                    {
                        String[] time = key.split("/");
                        String[] hour= time[1].split("-");
                        for(Timeline t : listTimeline)
                        {
                            String[] exactlyhours=hour[0].split(":");
                            String[] exactlyhourf=hour[1].split(":");
                            for(int i= Integer.parseInt(exactlyhours[0]);i<Integer.parseInt(exactlyhourf[0]);i++) {
                                String text=i+":00-"+(i+1)+":00";
                                if (t.hourProperty().get().equals(text)) {
                                    switch (time[0]) {
                                        case "Monday": t.setMonday(timeline.get(key));break;
                                        case "Tuesday": t.setTuesday(timeline.get(key));break;
                                        case "Wednesday": t.setWednesday(timeline.get(key));break;
                                        case "Thursday": t.setThursday(timeline.get(key));break;
                                        case "Friday": t.setFriday(timeline.get(key));break;
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }


    }

    public void start(String user,String semester)
    {
        this.user=user;
        this.semester=semester;
        Menu menu = new Menu();
        cmbDay.getItems().addAll("Monday","Tuesday","Wednesday","Tuesday","Friday");
        cmbForm.getItems().addAll("Course","Laboratory","Seminar");
        for(int i=8;i<23;i++)
        {

                String text=i+":00";
                cmbHourStart.getItems().add(text);
                cmbHourFinish.getItems().add(text);
        }
        List<Subjects> all= subjectsService.findAll();
        for(Subjects subject : all)
        {
            if(subject.getUser().equals(user)&& subject.getSemester().equals(semester)) {
                cmbSubjectName.getItems().add(subject.getSubject_name());
            }
        }
        TableColumn<Timeline,String> columnHour = new TableColumn<>("Hour");
        columnHour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        TableColumn<Timeline,String> columnMonday = new TableColumn<>("Monday");
        columnMonday.setCellValueFactory(new PropertyValueFactory<>("monday"));
        TableColumn<Timeline,String> columnTuesday = new TableColumn<>("Tuesday");
        columnTuesday.setCellValueFactory(new PropertyValueFactory<>("tuesday"));
        TableColumn<Timeline,String> columnWednesday = new TableColumn<>("Wednesday");
        columnWednesday.setCellValueFactory(new PropertyValueFactory<>("wednesday"));
        TableColumn<Timeline,String> columnThursday = new TableColumn<>("Thursday");
        columnThursday.setCellValueFactory(new PropertyValueFactory<>("thursday"));
        TableColumn<Timeline,String> columnFriday = new TableColumn<>("Friday");
        columnFriday.setCellValueFactory(new PropertyValueFactory<>("friday"));
        tblTimeline.getColumns().add(columnHour);
        tblTimeline.getColumns().add(columnMonday);
        tblTimeline.getColumns().add(columnTuesday);
        tblTimeline.getColumns().add(columnWednesday);
        tblTimeline.getColumns().add(columnThursday);
        tblTimeline.getColumns().add(columnFriday);
        for(int i=8;i<23;i++)
        {
            String text= i+":00"+"-"+(i+1)+":00";
            Timeline t= new Timeline(text,"","","","","");
            tblTimeline.getItems().add(t);
            listTimeline.add(t);
        }
        showTimeline(user,semester);
    }

    public void btnNewOnClick(ActionEvent e)
    {
        List<Semester> all = semesterService.findAll();
        Menu menu = new Menu();
        for(Semester semester : all)
        {
            if(semester.getUser().equals(this.user)&& menu.buttonText(semester).equals(this.semester)) {
                if(semester.getTimeline()==null) {
                    HashMap<String, String> timeline = new HashMap<String, String>();
                    String key = cmbDay.getValue() + "/" + cmbHourStart.getValue() + "-" + cmbHourFinish.getValue();
                    String value = cmbSubjectName.getValue() + "/" + cmbForm.getValue();
                    timeline.put(key,value);
                    semester.setTimeline(timeline);
                    semesterService.save(semester);
                }else
                {
                    HashMap<String, String> timeline = semester.getTimeline();
                    String key = cmbDay.getValue() + "/" + cmbHourStart.getValue() + "-" + cmbHourFinish.getValue();
                    String value = cmbSubjectName.getValue() + "/" + cmbForm.getValue();
                    timeline.put(key,value);
                    semester.setTimeline(timeline);
                    semesterService.save(semester);
                }
            }
        }
        showTimeline(user,semester);
    }

    public void btnTimetableOnClick(ActionEvent e) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("timetable.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Timetable menu = loader.getController();
        menu.start(user,semester);
        stage.show();
    }

    public void btnBackOnClick(ActionEvent event) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("subjects_view.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
       Subjects_view menu = loader.getController();
        menu.start(user,semester);
        stage.show();
    }

    public void btnLogoutOnClick(ActionEvent event) throws IOException
    {
        Stage login= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        login.setScene(scene);
    }


}
