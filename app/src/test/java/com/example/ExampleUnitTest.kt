package com.example

import com.example.data.local.UserPreferences
import com.example.data.model.MarketGuidebookRepository
import com.example.data.model.RegionRepository
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.pow

class ExampleUnitTest {
    @Test
    fun testCompoundInterestMultiplier() {
        val monthlyAmount = 10000.0
        val years = 15
        val cagrPercent = 14.0

        val totalMonths = years * 12
        val totalInvested = monthlyAmount * totalMonths
        val monthlyRate = (cagrPercent / 100.0) / 12.0

        val futureValueStock = monthlyAmount * (((1.0 + monthlyRate).pow(totalMonths.toDouble()) - 1.0) / monthlyRate) * (1.0 + monthlyRate)

        assertEquals(1800000.0, totalInvested, 0.01)
        assertTrue(futureValueStock > 6000000.0) // At 14%, 10k/mo over 15 yrs generates > ₹60 Lakhs
        val multiplier = futureValueStock / totalInvested
        assertTrue(multiplier > 3.0) // More than 3x capital invested
    }

    @Test
    fun testVipPromoCodes() {
        val validCodes = UserPreferences.VALID_VIP_CODES
        assertTrue(validCodes.contains("VIPFREE"))
        assertTrue(validCodes.contains("CREATOR100"))
        assertTrue(validCodes.contains("GUIDEBOOK2026"))
        assertTrue(validCodes.contains("TRADER"))
        assertFalse(validCodes.contains("INVALID_CODE"))
    }

    @Test
    fun testRegionRepositoryDefaults() {
        val region = RegionRepository.REGION_INDIA
        assertEquals("IN", region.code)
        assertEquals("₹", region.currencySymbol)
        assertEquals("INR", region.currencyCode)
        assertTrue(region.primaryStockIndex.contains("NIFTY"))
        assertTrue(region.regionalIncomeMethods.isNotEmpty())
    }

    @Test
    fun testMasterclassChaptersPresent() {
        val chapters = com.example.data.model.MasterclassRepository.CHAPTERS
        assertEquals(6, chapters.size)
        assertTrue(chapters[0].isFree)
        assertFalse(chapters[1].isFree)
        assertFalse(chapters[2].isFree)
        assertFalse(chapters[3].isFree)
        assertFalse(chapters[4].isFree)
        assertFalse(chapters[5].isFree)
        assertEquals(125, chapters[1].unlockPriceInr)
        assertEquals(175, chapters[2].unlockPriceInr)
        assertEquals(200, chapters[3].unlockPriceInr)
        assertEquals(225, chapters[4].unlockPriceInr)
        assertEquals(250, chapters[5].unlockPriceInr)
        assertTrue(chapters[0].coreContentSections.isNotEmpty())
        assertTrue(chapters[0].agenda.isNotEmpty())
    }
}
