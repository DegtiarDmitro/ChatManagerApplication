package com.manager.chat.view;


import com.manager.chat.entity.Message;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 */
public class ChatMessageCell extends ListCell<Message> {


    private static DateFormat dateFormat = null;

    static {
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }



    @Override
    public void updateItem(Message msg, boolean empty) {
        super.updateItem(msg, empty);
        if (empty || msg == null) {
            clearContent();
        } else {
            addContent(msg);
        }
    }


    /**
     * ф-ция очищает содержимое ячейки
     */
    private void clearContent() {
        setGraphic(null);
    }


    private void addContent(Message msg) {
        VBox vBox = new VBox();

        //Date
        Label lableTime = new Label();
        lableTime.setText(dateFormat.format(new Date()));
        vBox.getChildren().add(lableTime);

        //Message text
        if(msg.getMessage() != null) {
            Label lableMsg = new Label();
            vBox.getChildren().add(lableMsg);
            lableMsg.setText(msg.getMessage());

        //Message image
        }else if(msg.getFile() != null){
            //try {//(FileInputStream input = new FileInputStream(msg.getFile().getFile())) {
        //else if(msg.getFile() != null){
        //try (FileInputStream input = new FileInputStream(msg.getFile().getFile())) {
                ImageView imgView = new ImageView();
                vBox.getChildren().add(imgView);
                //Image image = new Image(input);
                Image image = new Image(new ByteArrayInputStream( msg.getFile().getFileData() ));
                imgView.setImage(image);
                double _w = image.getWidth();
                double _h = image.getHeight();
                double _k = Math.max(_w, _h);
                double _k2 = 100 / _k;
                imgView.setFitWidth(_w * _k2);
                imgView.setFitHeight(_h * _k2);
            //} 
//            catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        setGraphic(vBox);
    }


}
