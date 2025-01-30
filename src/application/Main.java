package application;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		Scanner Scan = new Scanner(System.in);
        
		System.out.println("Do you want to (1)record a new session or (2)read a session from a file");
		int input = Scan.nextInt();
		
		if (input == 1) {
			
			FileWriter writer = new FileWriter("Session.txt");
			
			System.out.println("How many seconds do you want to record your heart rate?");
	        int seconds = Scan.nextInt();
	        
	        //write amount of heart rates to be recorded
	        writer.write(seconds+"\n");
	        
	        for (int i=0; i<seconds; i++) {
	        	
	        	//Wait a second before recording current heart rate
	        	Thread.sleep(1000);
	        	
	        	//Get heart rate using API
	        	URL url = new URL("https://dev.pulsoid.net/api/v1/data/heart_rate/latest?response_mode=text_plain_only_heart_rate");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            conn.setRequestProperty("Authorization","Bearer "+"fbcf5e4b-9e83-4ba8-a0b5-7d58b183b77b");
	            conn.setRequestProperty("Content-Type","application/json");
	            conn.setRequestMethod("GET");

	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String output;

	            StringBuffer response = new StringBuffer();
	            while ((output = in.readLine()) != null) {
	                response.append(output);
	            }

	            in.close();
	            
	            System.out.println("Heart rate: " + response.toString());
	            
	            //write heart rate to file
	            writer.write(response.toString()+"\n");
	        	
	        }
	        
	        writer.close();
			
		}
        
        System.out.println("Reading the file...");
        
        BufferedReader reader = new BufferedReader(new FileReader("Session.txt"));
        
        ArrayList<Integer> list = new ArrayList<>();
        
        int runs = Integer.parseInt(reader.readLine());
        int total = 0;
        int highest = 0;
        int lowest = 0;
        
        for (int i=0; i<runs; i++) {
        	
        	int heartRate = Integer.parseInt(reader.readLine());
        	list.add(heartRate);
        	total += heartRate;
        	
        }
        
        reader.close();
        
        Collections.sort(list);
        
        highest = list.get(list.size()-1);
        lowest = list.get(0);
        
        System.out.println("Average heart rate: " + Math.round((double)total / runs));
        System.out.println("Highest: " + highest);
        System.out.println("Lowest: " + lowest);
        System.out.println("Range: " + (highest - lowest));
        
        int tenPercentNum = (int) Math.round(list.size()/10.0);
        
        if (tenPercentNum <= 0) {
        	tenPercentNum = 1;
        }
        
        System.out.println("10% low: " + list.get(tenPercentNum-1));
        System.out.println("10% high: " + list.get((list.size()-tenPercentNum)));
		
		launch(args);
	}
}
