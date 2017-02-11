package edu.berkeley.eecs.cs164.pa1;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class RegexParserTest {


    @Test(expected = RegexParseException.class)
    public void testUnbalancedParentheses() throws Exception {
        Assert.assertNull(RegexParser.parse("a(b"));
    }

    @Test(expected = RegexParseException.class)
    public void testUnbalancedParenthesesEscape() throws Exception {
        // These should be OK
        Assert.assertNotNull(RegexParser.parse("a(b)\\)"));
        Assert.assertNotNull(RegexParser.parse("a(b\\))"));
        // This should throw.
        Assert.assertNull(RegexParser.parse("a(b\\)"));
    }

    @Test(expected = RegexParseException.class)
    public void testEmptyRegex() throws Exception {
        // These should be OK
        Assert.assertNotNull(RegexParser.parse(""));
        Assert.assertNotNull(RegexParser.parse("()"));
        Assert.assertNotNull(RegexParser.parse("(a|)"));
    }

    @Test(expected = RegexParseException.class)
    public void testComplicated() throws Exception {
        // These should be OK
        Assert.assertNotNull(RegexParser.parse("a(b|cd)+"));
        Assert.assertNotNull(RegexParser.parse("((a))"));
    }

    @Test(expected = RegexParseException.class)
    public void testBasics() throws Exception {
        // These should be OK
        Assert.assertNotNull(RegexParser.parse("a"));
        Assert.assertNotNull(RegexParser.parse("ab"));
        Assert.assertNotNull(RegexParser.parse("a|b"));
    }

}