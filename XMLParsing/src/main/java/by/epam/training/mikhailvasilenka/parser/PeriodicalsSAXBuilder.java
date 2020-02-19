package by.epam.training.mikhailvasilenka.parser;

import by.epam.training.mikhailvasilenka.handler.PeriodicalHandler;
import by.epam.training.mikhailvasilenka.periodic.Periodical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Set;

public class PeriodicalsSAXBuilder {

    private static final Logger LOGGER = LogManager.getLogger(PeriodicalsDOMBuilder.class);
    private Set<Periodical> periodicals;
    private PeriodicalHandler handler;
    private XMLReader reader;

    public PeriodicalsSAXBuilder() {
        handler = new PeriodicalHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
        } catch (SAXException e) {
            LOGGER.error("SAX Parser error: " + e.getMessage());
        }
    }

    public Set<Periodical> getPeriodicals() {
        return periodicals;
    }

    public void BuildSetPeriodicals(String filename) {
        try {
            reader.parse(filename);
        } catch (SAXException e) {
            LOGGER.error("SAX Parser error: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("IO stream error: " + e.getMessage());
        }
        periodicals = handler.getPeriodicals();
    }
}
