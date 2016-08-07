package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

/**
 * Created by Иван on 29.06.2016.
 */
public class ImageBlob {
    private int id;
    private String name;
    //private File file;
    //private Blob imageBlob;
    private Image image;
    private int troubleId;

    private static Connection connection;

    public ImageBlob(String name, Image image, int troubleId) {
        //this.id = id;
        this.name = name;
        //this.file = file;
        this.troubleId = troubleId;
        this.image = image;
    }

    public ImageBlob(int id, String name, Image image, int troubleId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.troubleId = troubleId;
    }

    public ImageBlob(String name, int troubleId) {
        this.name = name;
        this.troubleId = troubleId;
    }

    public ImageBlob(int id, String name, int troubleId) {
        this.id = id;
        this.name = name;
        this.troubleId = troubleId;
    }

    public static void setConnection(Connection connection) {
        ImageBlob.connection = connection;
    }

    public boolean Add(){
        FileInputStream fis = null;
        File file = new File("C:/JavaFX/");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{

            PreparedStatement ps = connection.prepareStatement("insert into image(id, name, content, troubleId) " +
                    "values (0, ?, ?, ?)");
            ps.setString(1, this.getName());
            //ps.setBlob(2, this.getImage());
            fis = new FileInputStream(file);
            ps.setBinaryStream(2, fis, (int)file.length());
            ps.setInt(3, this.getTroubleId());


            ps.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            //ps.close();
            //fis.close();
        }
        return false;
    }
    public boolean Add(File file){
        FileInputStream fis = null;
        try {
            //File file = new File("myPhoto.png");

            PreparedStatement ps = connection.prepareStatement("insert into image(id, name, content, troubleId) " +
                                             "values (0, ?, ?, ?)");
            ps.setString(1, this.getName());
            //ps.setBlob(2, this.getImage());
            fis = new FileInputStream(file);
            ps.setBinaryStream(2, fis, (int)file.length());
            ps.setInt(3, this.getTroubleId());


            ps.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            //ps.close();
            //fis.close();
        }
        return false;
    }

    public ObservableList<Image> Get(int troubleId){
        ObservableList<Image> imList = null;
        FileInputStream fis = null;
        File file = null;
        try {
            //File file = new File("myPhoto.png");

            PreparedStatement ps = connection.prepareStatement(
                    "SELECT content   " +
                    "FROM troubleshooting.image " +
                    "WHERE id = ?;");
            ps.setInt(1, troubleId);
            ResultSet rs = ps.executeQuery();
            /*InputStream is = rs.getBinaryStream(1);
            java.awt.Image im = new java.awt.Image();
            File image = new File();
            FileOutputStream fos = new FileOutputStream(image);
            file.
            blobList = ps.*/

            while(rs.next()){
                InputStream input = new ByteArrayInputStream(rs.getBytes("content"));
                imList.add(new Image(input));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return imList;
    }

    public static ObservableList<ImageBlob> Get(){
        ObservableList<ImageBlob> imList = FXCollections.observableArrayList();
        FileInputStream fis = null;
        File file = null;
        try {
            //File file = new File("myPhoto.png");

            PreparedStatement ps = connection.prepareStatement(
                    "SELECT *   " +
                            "FROM troubleshooting.image ");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                //InputStream input = new ByteArrayInputStream(rs.getBytes("content"));
                System.out.println(rs.getInt("id") +
                        rs.getString("name") + rs.getInt("troubleId"));
                imList.add(new ImageBlob(
                        rs.getInt("id"),
                        rs.getString("name"),
                        new Image(new ByteArrayInputStream(rs.getBytes("content"))),
                        rs.getInt("troubleId")
                    )
                );
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return imList;
    }

    public void Del(){
        try{
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM `troubleshooting`.`image`" +
                            "WHERE `id`= ?;"
            );
            ps.setInt(1, this.getId());
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTroubleId() {
        return troubleId;
    }
    public void setTroubleId(int troubleId) {
        this.troubleId = troubleId;
    }   @Override
    public String toString() {
        return this.getName();
    }
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
}
