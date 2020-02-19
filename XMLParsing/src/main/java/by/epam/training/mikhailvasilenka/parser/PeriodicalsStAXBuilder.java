package by.epam.training.mikhailvasilenka.parser;

import by.epam.training.mikhailvasilenka.periodic.Booklet;
import by.epam.training.mikhailvasilenka.periodic.Magazine;
import by.epam.training.mikhailvasilenka.periodic.Newspaper;
import by.epam.training.mikhailvasilenka.periodic.Periodical;
import by.epam.training.mikhailvasilenka.periodicEnum.PeriodicalEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

public class PeriodicalsStAXBuilder {

    private static final Logger LOGGER = LogManager.getLogger(PeriodicalsStAXBuilder.class);
    private HashSet<Periodical> periodicals = new HashSet<>();
    private XMLInputFactory inputFactory;

    public PeriodicalsStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public HashSet<Periodical> getPeriodicals() {
        return periodicals;
    }

    public void BuildSetPeriodicals(String filename) {
        try (FileInputStream inputStream = new FileInputStream(new File(filename))){

            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);

            while (reader.hasNext()) {
                if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                    switch (reader.getLocalName()) {
                        case "newspaper":
                            periodicals.add(BuildPeriodical(reader, new Newspaper()));
                            break;
                        case "magazine":
                            periodicals.add(BuildPeriodical(reader, new Magazine()));
                            break;
                        case "booklet":
                            periodicals.add(BuildPeriodical(reader, new Booklet()));
                            break;
                    }
                }
            }
        } catch (XMLStreamException e) {
            LOGGER.error("StAX parsing error! " + e.getMessage());
        } catch (FileNotFoundException e) {
            LOGGER.error("File " + filename + " not found! " + e);
        } catch (IOException e) {
            LOGGER.error("Error while closing InputStream " + e);
        }
    }

    protected Periodical BuildPeriodical(XMLStreamReader reader, Periodical periodical) throws XMLStreamException {

        periodical.setIssn(reader.getAttributeValue(null, PeriodicalEnum.ISSN.getValue()));

        while (reader.hasNext()){
            switch (reader.next()){
                case XMLStreamConstants.START_ELEMENT:
                    String name = reader.getLocalName();
                    switch (PeriodicalEnum.valueOf(name.toUpperCase())) {
                        case TITLE:
                            periodical.setTitle(GetXMLText(reader));
                            break;
                        case VOLUME:
                            periodical.setVolume(Integer.parseInt(GetXMLText(reader)));
                            break;
                        case COLORED:
                            periodical.setColored(Boolean.valueOf(GetXMLText(reader)));
                            break;
                        case MONTHLY:
                            periodical.setMonthly(Boolean.valueOf(GetXMLText(reader)));
                            break;
                        case GLOSSY:
                            periodical.setGlossy(Boolean.valueOf(GetXMLText(reader)));
                        default:
                            break;
                    }
                    break;

                    case XMLStreamConstants.END_ELEMENT:
                        if (PeriodicalEnum.valueOf(reader.getLocalName().toUpperCase()) == PeriodicalEnum.NEWSPAPER ||
                                PeriodicalEnum.valueOf(reader.getLocalName().toUpperCase()) == PeriodicalEnum.MAGAZINE ||
                                PeriodicalEnum.valueOf(reader.getLocalName().toUpperCase()) == PeriodicalEnum.BOOKLET) {
                            return periodical;
                        }
            }
        }
        throw new XMLStreamException("Unknown element!");
    }

    private String GetXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
