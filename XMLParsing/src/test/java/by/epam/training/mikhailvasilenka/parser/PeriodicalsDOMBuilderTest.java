package by.epam.training.mikhailvasilenka.parser;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class PeriodicalsDOMBuilderTest {

    private static PeriodicalsDOMBuilder builder;

    @BeforeClass
    public static void initBuilder() throws ParserConfigurationException, IOException, SAXException {
        builder = new PeriodicalsDOMBuilder();
        builder.buildSetPeriodicals("files/periodicals/papers.xml");
    }

    @Test
    public void testBuildSetPeriodicals() {
        System.out.println(builder.getPeriodicals());
        assertEquals(16, builder.getPeriodicals().size());
    }
}