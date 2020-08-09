package base;

import java.io.IOException;

public class Handler {
	private final GraphicalInterface graphicalInterface;
	private final FactFetcher factFetcher;
	
	public static void main(String[] args) {
		new Handler();
	}
	
	private Handler() {
		graphicalInterface = new GraphicalInterface(this);
		factFetcher = new FactFetcher();
	}
	
	//handling methods
	void updateFactBase() {
		try {
			factFetcher.updateFactBase();
		}catch(IOException e) {
			graphicalInterface.signalAnError("Error connecting to fact base");
		}
	}
	
	void requestFact() {
		String fact;
		if( (fact = factFetcher.fetchFact()) != null)
			graphicalInterface.presentFact(fact);
		else
			graphicalInterface.signalAnError("Fact list empty");
	}
}
