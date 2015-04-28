package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

	public static List<Map<String,String>> parseInput (String input) {
		List<Map<String,String>> cities = new ArrayList<Map<String,String>>();
		List<String> rawCities = getCities(input);
		for(String city : rawCities) {
			cities.add(JsonParser.getCity(city));
		}
		return cities;
	}

	private static List<String> getCities  (String input) {
		List<String> cities = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		input = input.substring(1,input.length()-1);
		for(int i = 0; i < input.length(); i++) {
			if(counter != 0 || input.charAt(i) != ',')
				sb.append(input.charAt(i));
			if (input.charAt(i) == '{')
				counter ++;
			else if(input.charAt(i) == '}')
				counter --;

			if(counter == 0 && sb.length() > 0) {
				cities.add(sb.toString());
				sb = new StringBuilder();
			}
		}

		return cities;
	}

	private static Map<String,String> getCity (String input) {
		Map<String,String> city = new HashMap<String,String>();
		input = refactorJson(input);
		String[] features = input.split(";");
		for(String feature : features) {
			String[] keyValue = feature.split(":");
			if(keyValue.length > 1) {
				String key = keyValue[0];
				String value = keyValue[1];
				if(!value.equals("delete")) {
					city.put(key, value);
				}
			}
		}
		return city;
	}

	private static String refactorJson(String input) {
		input = input.substring(1,input.length()-1);
		StringBuilder sb1 = new StringBuilder();
		boolean sw = false;
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '"')
				sw = !sw;
			else {
				if(input.charAt(i) == '{') {
					i++;
					sb1.append("delete;");
					boolean sw2 = false;
					while(input.charAt(i) != '}') {
						if(input.charAt(i) == '"')
							sw2 = !sw2;
						else {
							if(input.charAt(i) == ',' && !sw2) 
								sb1.append(';');
							else
								sb1.append(input.charAt(i));
						}
						i++;
					}
				} else {
					if(input.charAt(i) == ',' && !sw) 
						sb1.append(';');
					else
						sb1.append(input.charAt(i));
				}
			}
		}

		return sb1.toString();
	}

}
