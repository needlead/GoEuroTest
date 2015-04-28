package util;

import java.util.Map;

public class MapToCsvConverter {

	public static String convert(Map<String,String> input) {
		StringBuilder sb = new StringBuilder();
		for(String key : Constants.CSV_HEADER){
			sb.append(',').append(input.get(key));
		}
		return sb.toString().substring(1);
	}
	
}
