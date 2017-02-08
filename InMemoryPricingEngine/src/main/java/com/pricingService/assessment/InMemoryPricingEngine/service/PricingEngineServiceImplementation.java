package com.pricingService.assessment.InMemoryPricingEngine.service;

import com.pricingService.assessment.InMemoryPricingEngine.dao.InMemoryCacheDaoImplementation;
import com.pricingService.assessment.InMemoryPricingEngine.dao.PricingEngineDao;

public final class PricingEngineServiceImplementation implements
		PricingEngineService {

	PricingEngineDao pricingEngineDao = new InMemoryCacheDaoImplementation();

	@Override
	public void consumePrice(String symbol, String source, double price) {

		if (validatePricingData(symbol, source, price)) {
			pricingEngineDao.fillPricingData(symbol, source, price);
		}

	}

	@Override
	public double getPrice(String symbol) {
		return pricingEngineDao.getPricing(symbol);
	}

	private final boolean validatePricingData(String source, String symbol,
			double price) {
		if (symbol != null && symbol != "" && symbol instanceof String) {
			if (source != null && source != "" && source instanceof String) {
				if (price >= 0.0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void clearInMemoryCache() {
		pricingEngineDao.clearInMemoryCache();
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return pricingEngineDao.size();
	}
}
