package com.pricingService.assessment.InMemoryPricingEngine.service;

public interface PricingEngineService {

	void consumePrice(String symbol, String source,double price);
	
	double getPrice(String symbol);
	
	void clearInMemoryCache();

	int size();
}
