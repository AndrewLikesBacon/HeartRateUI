package application;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.fxml.Initializable;

public class FXMLDocumentController implements Initializable{
	@FXML
    private LineChart<?, ?> LineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("Session.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ArrayList<Integer> list = new ArrayList<>();
        
        int runs = 0;
		try {
			runs = Integer.parseInt(reader.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        for (int i=0; i<runs; i++) {
        	
        	int heartRate = 0;
			try {
				heartRate = Integer.parseInt(reader.readLine());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	list.add(heartRate);
        	
        }
        
        try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	XYChart.Series series = new XYChart.Series();
    	
    	for (int i=0; i<list.size(); i++) {
    		series.getData().add(new XYChart.Data(""+(i+1), list.get(i)));
    	}
    	
    	LineChart.getData().addAll(series);
    	
    }
}
