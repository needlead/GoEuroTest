package core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import util.Constants;
import util.JsonParser;
import util.MapToCsvConverter;

public class CityApiTest {
	
	public static void main (String[] args) {
		if(args.length == 0) {
			System.out.println("Error - no command line parameters. The program will now exit.");
		} else {
			for(String cityName :args)
				try {
					URL url = new URL(Constants.API_URL+cityName);
					BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
					String line;
					String input = "";
					while ((line = reader.readLine()) != null) {
						input += line;
					}
					reader.close();
					
					List<Map<String,String>> cities = JsonParser.parseInput(input);
					for(Map<String,String> city : cities) {
						System.out.println(MapToCsvConverter.convert(city));
					}


				} catch (MalformedURLException e) {
					// ...
				} catch (IOException e) {
					// ...
				}
		}
	}
}
