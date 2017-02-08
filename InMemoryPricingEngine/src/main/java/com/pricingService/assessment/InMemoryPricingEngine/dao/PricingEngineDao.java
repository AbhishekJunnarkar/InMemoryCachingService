package com.pricingService.assessment.InMemoryPricingEngine.dao;

public interface PricingEngineDao {

	public void fillPricingData(String symbol, String source, double value);
	
	public double getPricing(String symbol);
	
	public void remove(String symbol);

	void clearInMemoryCache();

	int size();

}
