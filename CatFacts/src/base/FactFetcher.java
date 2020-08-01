package base;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class FactFetcher {
	//<fact, was shown>
	private final Map<String, Boolean> factBase = new TreeMap<>();
	
	FactFetcher(){
		
	}
	
	String fetchFact() {
		List<String> factList = new ArrayList<>();
		for(Entry<String, Boolean> fact : factBase.entrySet())
			if(fact.getValue() == false)
					factList.add(fact.getKey());
		
		if(factList.isEmpty())
			return null;
		
		int draw = (int) (Math.random() * (factList.size() - 1));
		
		String drawnFact = factList.get(draw);
		factBase.put(drawnFact, true);
		
		return drawnFact;
	}
	
	void updateFactBase() throws IOException {
		URL url = new URL("https://cat-fact.herokuapp.com/facts");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setReadTimeout(5000);
		httpURLConnection.setConnectTimeout(5000);
		
		if(httpURLConnection.getResponseCode() == 200) {
			InputStream inputStream = httpURLConnection.getInputStream();
			
			JsonValue jsonValue = extractJsonValueFromInputStream(inputStream);
			
			String[] facts = extractFactsFromJsonValue(jsonValue);
			for (String fact : facts)
				factBase.putIfAbsent(fact, false);
		
		}else {
			System.exit(1);
		}
	}
	
	private JsonValue extractJsonValueFromInputStream(InputStream stream) {
		JsonReader jsonReader = Json.createReader(stream);
		
		return jsonReader.readValue();
	}
	
	private String[] extractFactsFromJsonValue(JsonValue jsonValue) {
		JsonArray arrayOfJsonObjectsFacts = jsonValue.asJsonObject()
				.entrySet()
				.iterator()
				.next()
				.getValue()
				.asJsonArray();
		
		List<String> facts = new ArrayList<>();
		arrayOfJsonObjectsFacts.iterator().forEachRemaining(jsonObject -> {
			String fact = jsonObject.asJsonObject().getString("text");
			facts.add(fact);
		});
		
		//TODO return (String[]) facts.toArray();
		//ClassCastException??
		return facts.toArray(new String[10]);
	}
}
