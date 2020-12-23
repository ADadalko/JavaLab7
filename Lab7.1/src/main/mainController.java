package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

public class mainController {


    String userName = "";

    @FXML
    RadioButton rb1, rb2, rb3, rb4;

    @FXML
    Button addButton;

    @FXML
    Label yeslabel, nolabel;

    @FXML
    TextArea textarea20;

    @FXML
    TextField textfield20, name1, name2, name3, name31, surname1, surname3, patronymic3, patronymic1, address1, address3, position3, position1;
    @FXML
    DatePicker birthday1, birthday3;
    ToggleGroup group = new ToggleGroup();
    @FXML
    void initialize() {

        // установка группы
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
    }

    public void add(javafx.event.ActionEvent actionEvent) {
        String radio = "Не указано";
        if(rb1.isSelected()) radio = "Мужчина";
        if(rb2.isSelected()) radio = "Женщина";
        if (name1.getText()!="" && surname1.getText()!="" && patronymic1.getText()!="" && address1.getText()!="" && birthday1.getValue()!= null && position1.getText() != "") {
            try {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7" +
                        "?verifyServerCertificate=false" +
                        "&useSSL=false" +
                        "&requireSSL=false" +
                        "&useLegacyDatetimeCode=false" +
                        "&amp" +
                        "&serverTimezone=UTC", "root", "DadalkoArs170750")) {
                    String sql = "INSERT INTO lab7.employee(name, surname, patronymic, sex, birthday, address, position) VALUES (?, ?, ?, ?, ?, ?, ?);";
                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setString(2, name1.getText());
                    ps.setString(1, surname1.getText());
                    ps.setString(3, patronymic1.getText());
                    ps.setString(6, address1.getText());
                    ps.setString(5, birthday1.getValue().toString());
                    ps.setString(4, radio);
                    ps.setString(7, position1.getText());

                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Connection failed...");
                System.out.println(ex);
            }
            name1.setText("");
            surname1.setText("");
            patronymic1.setText("");
            address1.setText("");
            position1.setText("");
            birthday1.setValue(LocalDate.now());
        }
        else System.out.println("Ошибка ввода");
    }

    public void delete(ActionEvent actionEvent) {
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7" +
                    "?verifyServerCertificate=false" +
                    "&useSSL=false" +
                    "&requireSSL=false" +
                    "&useLegacyDatetimeCode=false" +
                    "&amp" +
                    "&serverTimezone=UTC", "root", "DadalkoArs170750")) {
                Statement statement = conn.createStatement();

                int rows = statement.executeUpdate("DELETE FROM lab7.employee WHERE name = '" + name2.getText() + "';");
                if (rows > 0) {
                    yeslabel.setVisible(true);
                    nolabel.setVisible(false);
                }
                else {
                    nolabel.setVisible(true);
                    yeslabel.setVisible(false);
                }
                name2.setText("");
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void edit(ActionEvent actionEvent) {
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7" +
                    "?verifyServerCertificate=false" +
                    "&useSSL=false" +
                    "&requireSSL=false" +
                    "&useLegacyDatetimeCode=false" +
                    "&amp" +
                    "&serverTimezone=UTC", "root", "DadalkoArs170750")) {
                String sql="Update lab7.employee set surname=? where name=?";
                PreparedStatement ps=conn.prepareStatement(sql);
                ps.setString(1, surname3.getText());
                ps.setString(2, userName);
                ps.executeUpdate();
                ps.close();
                sql = "Update lab7.employee set name=? where name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name31.getText());
                ps.setString(2, userName);
                ps.executeUpdate();
                ps.close();
                sql = "Update lab7.employee set patronymic=? where name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, patronymic3.getText());
                ps.setString(2, userName);
                ps.executeUpdate();
                ps.close();
                sql = "Update lab7.employee set sex=? where name=?";
                ps = conn.prepareStatement(sql);
                String radio = "Не указано";
                if (rb3.isSelected()) radio = "Мужчина";
                if (rb4.isSelected()) radio = "Женщина";
                ps.setString(1, radio);
                ps.setString(2, userName);
                ps.executeUpdate();
                ps.close();
                sql = "Update lab7.employee set birthday=? where name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, birthday3.getValue().toString());
                ps.setString(2, userName);
                ps.executeUpdate();
                ps.close();
                sql = "Update lab7.employee set address=? where name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, address3.getText());
                ps.setString(2, userName);
                ps.executeUpdate();
                ps.close();
                sql = "Update lab7.employee set position=? where name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, position3.getText());
                ps.setString(2, userName);
                ps.executeUpdate();
                ps.close();
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void load(ActionEvent actionEvent) {
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7" +
                    "?verifyServerCertificate=false" +
                    "&useSSL=false" +
                    "&requireSSL=false" +
                    "&useLegacyDatetimeCode=false" +
                    "&amp" +
                    "&serverTimezone=UTC", "root", "DadalkoArs170750")) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM lab7.employee WHERE name = '" + name3.getText() + "';");
                while (resultSet.next()) {
                    name31.setText(resultSet.getString(2));
                    surname3.setText(resultSet.getString(3));
                    patronymic3.setText(resultSet.getString(4));
                    String radio = resultSet.getString(5);
                    if (radio.equals("Мужчина")) rb3.setSelected(true);
                    if (radio.equals("Женщина")) rb4.setSelected(true);
                    birthday3.setValue(LocalDate.parse(resultSet.getString(6)));
                    address3.setText(resultSet.getString(7));
                    position3.setText(resultSet.getString(8));
                }
                userName = name3.getText();
                name3.setText("");
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void search(ActionEvent actionEvent) {
        try{
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7" +
                    "?verifyServerCertificate=false" +
                    "&useSSL=false" +
                    "&requireSSL=false" +
                    "&useLegacyDatetimeCode=false" +
                    "&amp" +
                    "&serverTimezone=UTC", "root", "DadalkoArs170750")) {

                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM lab7.employee WHERE name = '" + textfield20.getText() + "';");
                while (resultSet.next()) {
                    textarea20.setText("ID - "+String.valueOf(resultSet.getInt(1))+"\nФамилия - "+resultSet.getString(2)+"\nИмя - "+
                            resultSet.getString(3)+"\nОтчество - "+ resultSet.getString(4)+"\nПол - "+ resultSet.getString(5) +"\nДень рождения - "+
                            resultSet.getString(6)+"\nАдрес проживания - " + resultSet.getString(7) +"\nДолжность - "+ resultSet.getString(8));
                    System.out.println(resultSet.getString(4));
                    System.out.println(resultSet.getString(5));
                    System.out.println(resultSet.getString(6));
                    System.out.println(resultSet.getString(7));
                    System.out.println(resultSet.getString(8));
                }
            }
        }
        catch(Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }
}
