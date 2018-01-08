package com.manager.chat;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class Main extends Application {

	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {

		stage = primaryStage;
		stage.setTitle("ChatApp");
		stage.setMinHeight(500);
		stage.setMinWidth(500);
		showLoginScene();
		primaryStage.show();
	}

	private static void showLoginScene(){
		try {
			Parent root = FXMLLoader.load(Main.class.getResource("/fxml/login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/css/login.css");
			stage.setMaxWidth(500);
			stage.setMaxHeight(500);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void showMainScene(){
		try {
			Parent root = FXMLLoader.load(Main.class.getResource("/fxml/main.fxml"));
			stage.setMaxWidth(Double.MAX_VALUE);
			stage.setMaxHeight(Double.MAX_VALUE);
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		copyDbFile();
		createDataFile();
		launch(args);
	}
	
	
	/**
	 * 
	 */
	private static void copyDbFile() {
		
		String outerDB = "./CHAT_SYSTEM_DB.db";
		
		File f = new File(outerDB);
		if(!f.exists() || f.isDirectory()) { 
			InputStream in;
			OutputStream out;
			try {
				in = Main.class.getResourceAsStream("/CHAT_SYSTEM_DB.db");
				out = new FileOutputStream(outerDB);
		        byte[] buf = new byte[1024];
		        int len;
		        try {
					while ((len = in.read(buf)) > 0) {
					    out.write(buf, 0, len);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						in.close();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	private static void createDataFile() {
		File dataFile = new File("./data.txt");
		if(!dataFile.exists() || dataFile.isDirectory()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
}
