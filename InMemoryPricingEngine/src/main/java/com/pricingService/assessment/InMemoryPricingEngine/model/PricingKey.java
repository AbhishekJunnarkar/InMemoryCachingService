package com.pricingService.assessment.InMemoryPricingEngine.model;

public class PricingKey {

	public String  symbol,source;

	public PricingKey( String symbol,String source) {
		this.symbol = symbol;
		this.source = source;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PricingKey)) {
			return false;
		}
		PricingKey refPricingKey = (PricingKey) obj;

		return this.symbol.equals(refPricingKey.symbol) && this.source.equals(refPricingKey.source);
	}

	//^ Bitwise XOR - copies the bit if it is set in one operand but not other 
	@Override
	public int hashCode() {
		return symbol.hashCode() ^ source.hashCode();
	}
	
	@Override
	public String toString() {
		
		return "Symbol = "+this.symbol + " & Source is " + this.source;
	}
}
