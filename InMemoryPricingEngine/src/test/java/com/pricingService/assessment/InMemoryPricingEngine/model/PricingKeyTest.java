/**
 * 
 */
package com.pricingService.assessment.InMemoryPricingEngine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Abhishek
 *
 */
public class PricingKeyTest {
	PricingKey pricingKeyExpectedInstance;
	PricingKey pricingKeyActualInstance;

	@Before
	public void setUp() {
		pricingKeyExpectedInstance = new PricingKey("AAA", "A1");
		pricingKeyActualInstance = new PricingKey("AAA", "A1");
	}

	@Test
	public void testEquals() {

		assertEquals(pricingKeyExpectedInstance, pricingKeyActualInstance);
	}
	@Test
	public void testHashCode() {

		assertTrue(pricingKeyExpectedInstance.hashCode() == pricingKeyActualInstance.hashCode());
	}	

}
