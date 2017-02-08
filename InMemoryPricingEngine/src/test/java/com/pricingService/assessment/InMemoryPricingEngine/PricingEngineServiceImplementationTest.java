/**
 * 
 */
package com.pricingService.assessment.InMemoryPricingEngine;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pricingService.assessment.InMemoryPricingEngine.service.PricingEngineServiceImplementation;

public class PricingEngineServiceImplementationTest {

	// This should go in before method
	PricingEngineServiceImplementation impl;

	@Before
	public void setUp() {
		impl = new PricingEngineServiceImplementation();
	}

	@After
	public void tearDown() {
		System.out.println("Size of cache is " + impl.size());
		impl.clearInMemoryCache();
	}
	@Test
	public void WhenAddingNewTradeInMemoryCacheIsUpdatedWithNewValue() {
		impl.consumePrice("PQR", "P1", 450);
		assertEquals(450, impl.getPrice("PQR"),0.0);
	}
	
	@Test
	public void WhenUpdatingExistingTradeUpdateHappensOnlyWhenPriceRecievedIsMoreThenPointOnePercent() {
		impl.consumePrice("AAA", "A1", 20.0);
		impl.consumePrice("AAA", "A1", 25.0);
		assertEquals(25.0, impl.getPrice("AAA"), 0.0);
	}
	@Test
	public void WhenUpdatingExistingTradeInMemoryCacheIsDiscardedWhenPriceRecievedIsLessThenPointOnePercent() {
		impl.consumePrice("AAA", "A1", 25);
		impl.consumePrice("AAA", "A1", 25.024);
		assertEquals(25.0, impl.getPrice("AAA"), 0.0);
	}
}
