package com.example.architecture.bad.myfigurecollection;

import com.example.architecture.bad.myfigurecollection.util.StringUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class StringUtilsUnitTest {

    @Test
    public void testExtractStringBefore() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes("Fate/Hollow Ataraxia - Tohsaka Rin - 1/8 - Precious Fantasy Maid Version (Good Smile Company)", '-', 2);
        assertEquals("Fate/Hollow Ataraxia - Tohsaka Rin", value);
    }

    @Test
    public void testExtractStringBeforeForEmpty() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes("", '-', 2);
        assertEquals("", value);
    }

    @Test
    public void testExtractStringBeforeForNull() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes(null, '-', 2);
        assertEquals("", value);
    }

    @Test
    public void testExtractStringBeforeWithValueAsSeparator() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes("-", '-', 2);
        assertEquals("", value);
    }

    @Test
    public void testExtractStringBeforeWithNoSeparator() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes("Fate/Hollow Ataraxia Saber", '-', 2);
        assertEquals("Fate/Hollow Ataraxia Saber", value);
    }

    @Test
    public void testExtractStringBeforeWithOneSeparator() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes("Fate/Hollow Ataraxia - Saber", '-', 2);
        assertEquals("Fate/Hollow Ataraxia", value);
    }

    @Test
    public void testExtractStringBeforeWithExactTwoSeparator() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes("Bakuretsu Tenshi - Meg - 1/8 (Alter)", '-', 2);
        assertEquals("Bakuretsu Tenshi - Meg", value);
    }

    @Test
    public void testExtractStringBeforeWithNoSpaces() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes("BakuretsuTenshi-Meg-1/8(Alter)", '-', 2);
        assertEquals("BakuretsuTenshi-Meg", value);
    }

    @Test
    public void testExtractStringBeforeWithNoSpacesAndNoSeparator() throws Exception {
        String value = StringUtils.extractStringBeforeSeparatorRepeatedNTimes("BakuretsuTenshiMeg1/8(Alter)", '-', 2);
        assertEquals("BakuretsuTenshiMeg1/8(Alter)", value);
    }

    @Test
    public void testExtractStringAfter() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes("Fate/Hollow Ataraxia - Tohsaka Rin - 1/8 - Precious Fantasy Maid Version (Good Smile Company)", '-', 2);
        assertEquals("1/8 - Precious Fantasy Maid Version (Good Smile Company)", value);
    }

    @Test
    public void testExtractStringAfterForEmpty() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes("", '-', 2);
        assertEquals("", value);
    }

    @Test
    public void testExtractStringAfterNull() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes(null, '-', 2);
        assertEquals("", value);
    }

    @Test
    public void testExtractStringAfterWithValueAsSeparator() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes("-", '-', 2);
        assertEquals("", value);
    }

    @Test
    public void testExtractStringAfterWithNoSeparator() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes("Fate/Hollow Ataraxia Saber", '-', 2);
        assertEquals("Fate/Hollow Ataraxia Saber", value);
    }

    @Test
    public void testExtractStringAfterWithOneSeparator() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes("Fate/Hollow Ataraxia - Saber", '-', 2);
        assertEquals("Saber", value);
    }

    @Test
    public void testExtractStringAfterWithExactTwoSeparator() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes("Bakuretsu Tenshi - Meg - 1/8 (Alter)", '-', 2);
        assertEquals("1/8 (Alter)", value);
    }

    @Test
    public void testExtractStringAfterWithNoSpaces() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes("BakuretsuTenshi-Meg-1/8(Alter)", '-', 2);
        assertEquals("1/8(Alter)", value);
    }

    @Test
    public void testExtractStringAfterWithNoSpacesAndNoSeparator() throws Exception {
        String value = StringUtils.extractStringAfterSeparatorRepeatedNTimes("BakuretsuTenshiMeg1/8(Alter)", '-', 2);
        assertEquals("BakuretsuTenshiMeg1/8(Alter)", value);
    }

}