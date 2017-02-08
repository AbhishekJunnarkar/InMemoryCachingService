package com.pricingService.assessment.InMemoryPricingEngine.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InMemoryCacheDaoImplementationTest {

	// This should go in before method
	InMemoryCacheDaoImplementation impl;

	@Before
	public void setUp() {
		impl = new InMemoryCacheDaoImplementation();
	}

	@After
	public void tearDown() {
		System.out.println("Size of cache is " + impl.size());
		impl.getPricingTradeDatamapOnConsole();
		impl.clearInMemoryCache();
	}

	@Test
	public void WhenAddingNewTradeStoreNewValue() {
		impl.fillPricingData("AAA", "A1", 20.0);
		assertEquals(20.0, impl.getPricing("AAA"), 0.0);

	}

	@Test
	public void WhenUpdatingExistingTradeStoreWhenPriceRecievedIsMoreThenPointOnePercent() {
		impl.fillPricingData("AAA", "A1", 20.0);
		impl.fillPricingData("AAA", "A1", 25.0);
		assertEquals(25.0, impl.getPricing("AAA"), 0.0);

	}

	@Test
	public void WhenUpdatingExistingTradeDoNotStoreWhenPriceRecievedIsLessThenPointOnePercent() {
		impl.fillPricingData("AAA", "A1", 25);
		impl.fillPricingData("AAA", "A1", 25.024);
		assertEquals(25.0, impl.getPricing("AAA"), 0.0);

	}

	@Test
	public void WhenAddingNewTradeWithDifferentSourceStoreNewValue() {
		impl.fillPricingData("AAA", "A1", 25);
		impl.fillPricingData("AAA", "A2", 80.0);
		assertEquals(80.0, impl.getPricing("AAA"), 0.0);
	}

	@Test
	public void WhenAddingMultipleTradeWithDifferentSymbols() {

		impl.fillPricingData("BBB", "B1", 30);
		impl.fillPricingData("CCC", "C1", 40);
		impl.fillPricingData("DDD", "D1", 50);
		impl.fillPricingData("EEE", "E1", 60);
		impl.fillPricingData("FFF", "F1", 70);

		assertEquals(30.0, impl.getPricing("BBB"), 0.0);
		assertEquals(40.0, impl.getPricing("CCC"), 0.0);
		assertEquals(50.0, impl.getPricing("DDD"), 0.0);
		assertEquals(60.0, impl.getPricing("EEE"), 0.0);
		assertEquals(70.0, impl.getPricing("FFF"), 0.0);
	}

	@Test
	public void WhenGettingPriceTheInMemoryCacheisEmpty() {

		assertEquals(0.0, impl.getPricing("AAA"), 0.0);
	}

	@Test
	public void WhenGettingPriceForSymbolReturnsHighestPrice() {

		impl.fillPricingData("AAA", "A1", 49);
		impl.fillPricingData("AAA", "A2", 99);
		impl.fillPricingData("AAA", "A3", 29);

		assertEquals(99.0, impl.getPricing("AAA"), 0.0);

	}

	@Test
	public void WhenGettingPriceForSymbolReturnsLatestPrice() {

		impl.fillPricingData("AAA", "B1", 2367);
		impl.fillPricingData("AAA", "B1", 2376);

		assertEquals(2376.0, impl.getPricing("AAA"), 0.0);
	}

	@BeforeClass
	public static void before() {
		// impl.cleanUp();
	}

	@AfterClass
	public static void after() {

	}
}
