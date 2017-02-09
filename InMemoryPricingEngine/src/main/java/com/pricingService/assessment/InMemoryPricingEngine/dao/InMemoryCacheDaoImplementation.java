package com.pricingService.assessment.InMemoryPricingEngine.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//import com.pricingService.assessment.InMemoryPricingEngine.model.PricingKey;

/**
 * In memory cache implementation class for the Pricing Engine Dao
 * 
 * @author Abhishek
 *
 */
public final class InMemoryCacheDaoImplementation implements PricingEngineDao {
	private HashMap<PricingKey, Double> pricingTradeDatamap = new HashMap<PricingKey, Double>();

	public void fillPricingData(String symbol, String source, double value) {
		synchronized (pricingTradeDatamap) {
			PricingKey pricingKey = new PricingKey(symbol, source);
			boolean pricingExist = isPricingExistForGivenSymbolAndSource(
					symbol, source);
			if (pricingExist) {
				PricingKey key = getPricingKeyForGivenSymbolAndSource(symbol,
						source);
				double existingPricingValue = pricingTradeDatamap.get(key);
				boolean isValidValue = validateIfPricingMovedMoreThenPointOnePercent(
						existingPricingValue, value);
				if (isValidValue) {
					pricingTradeDatamap.put(pricingKey, value);
				} else {
					// discard update i.e., do nothing for current use case
				}
			} else {
				pricingTradeDatamap.put(pricingKey, value);
			}
		}
	}

	private boolean validateIfPricingMovedMoreThenPointOnePercent(
			double existingPricingValue, double value) {
		if (value > existingPricingValue) {
			if ((value % existingPricingValue) > 0.1d) {
				return true;
			}
		} else {
			if ((existingPricingValue % value) > 0.1d) {
				return true;
			}
		}
		return false;
	}

	public double getPricing(String symbol) {
		synchronized (pricingTradeDatamap) {
			// find all the PricingKey's which have value symbol
			boolean pricingExist = isPricingExistForGivenSymbol(symbol);

			if (pricingExist) {
				PricingKey key = getHighestPricingKeyForGivenSymbol(symbol);
				Double pricingTradeDataValue = pricingTradeDatamap.get(key);
				return pricingTradeDataValue;
			}
			return 0.0;
		}
	}

	private PricingKey getHighestPricingKeyForGivenSymbol(String symbol) {
		Map<PricingKey, Double> resultPricingKeyMap = new HashMap<PricingKey, Double>();
		Set<PricingKey> pricingKeySet = pricingTradeDatamap.keySet();
		for (PricingKey key : pricingKeySet) {
			if (key.symbol == symbol) {
				resultPricingKeyMap.put(key, pricingTradeDatamap.get(key));
			}
		}
		return Collections.max(resultPricingKeyMap.entrySet(),
				Map.Entry.comparingByValue()).getKey();
	}

	private boolean isPricingExistForGivenSymbol(String symbol) {
		synchronized (pricingTradeDatamap) {// To Make sure cleanUp() doesn't
											// causes failure
			while (!pricingTradeDatamap.isEmpty()) {
				Set<PricingKey> pricingKeySet = pricingTradeDatamap.keySet();

				for (PricingKey key : pricingKeySet) {
					if (key.symbol == symbol) {
						return true;
					}
				}
			}
			return false;
		}
	}

	private PricingKey getPricingKeyForGivenSymbolAndSource(String symbol,
			String source) {

		Set<PricingKey> pricingKeySet = pricingTradeDatamap.keySet();
		for (PricingKey key : pricingKeySet) {
			if (key.symbol == symbol && key.source == source) {
				return key;
			}
		}
		return null;
	}

	private boolean isPricingExistForGivenSymbolAndSource(String symbol,
			String source) {
		Set<PricingKey> pricingKeySet = pricingTradeDatamap.keySet();

		for (PricingKey key : pricingKeySet) {
			if (key.symbol == symbol && key.source == source) {
				return true;
			}
		}
		return false;
	}

	public void getPricingTradeDatamapOnConsole() {
		pricingTradeDatamap.forEach((k, v) -> System.out.println("Pricing Key "
				+ k + " Value " + v));
	}

	@Override
	public void remove(String symbol) {
		synchronized (pricingTradeDatamap) {
			pricingTradeDatamap
					.remove(getHighestPricingKeyForGivenSymbol(symbol));
		}
	}

	@Override
	public void clearInMemoryCache() {
		synchronized (pricingTradeDatamap) {
			pricingTradeDatamap.clear();
		}
	}

	@Override
	public int size() {
		return pricingTradeDatamap.size();
	}
}

class PricingKey {

	public String symbol, source;

	public PricingKey(String symbol, String source) {
		this.symbol = symbol;
		this.source = source;

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PricingKey)) {
			return false;
		}
		PricingKey refPricingKey = (PricingKey) obj;

		return this.symbol.equals(refPricingKey.symbol)
				&& this.source.equals(refPricingKey.source);
	}

	// ^ Bitwise XOR - copies the bit if it is set in one operand but not other
	@Override
	public int hashCode() {
		return symbol.hashCode() ^ source.hashCode();
	}

	@Override
	public String toString() {

		return "Symbol = " + this.symbol + " & Source is " + this.source;
	}
}
