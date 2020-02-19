package by.epam.training.mikhailvasilenka.handler;

import by.epam.training.mikhailvasilenka.periodic.Booklet;
import by.epam.training.mikhailvasilenka.periodic.Magazine;
import by.epam.training.mikhailvasilenka.periodic.Newspaper;
import by.epam.training.mikhailvasilenka.periodic.Periodical;
import by.epam.training.mikhailvasilenka.periodicEnum.PeriodicalEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;


public class PeriodicalHandler extends DefaultHandler {

    private static final Logger LOGGER = LogManager.getLogger(PeriodicalHandler.class);
    static final int ISSN_ATTR = 0;
    private Set<Periodical> periodicals;
    private Periodical current;
    private PeriodicalEnum currentEnum;
    private EnumSet<PeriodicalEnum> withText;

    public PeriodicalHandler() {
        periodicals = new HashSet<>();
        withText = EnumSet.range(PeriodicalEnum.TITLE, PeriodicalEnum.GLOSSY);
    }

    public Set<Periodical> getPeriodicals() {
        return Collections.unmodifiableSet(periodicals);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("newspaper".equals(localName)) {
            current = new Newspaper();
            current.setIssn(attributes.getValue(ISSN_ATTR).intern());
            LOGGER.info("Method: startElement\ncurrent: newspaper");
        }
        else if ("magazine".equals(localName)) {
            current = new Magazine();
            current.setIssn(attributes.getValue(ISSN_ATTR).intern());
            LOGGER.info("Method: startElement\ncurrent: magazine");

        }
        else if ("booklet".equals(localName)) {
            current = new Booklet();
            current.setIssn(attributes.getValue(ISSN_ATTR).intern());
            LOGGER.info("Method: startElement\ncurrent: booklet");
        }
        else {
            PeriodicalEnum temp = PeriodicalEnum.valueOf(localName.toUpperCase());
            if (withText != null && withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("newspaper".equals(localName) || "magazine".equals(localName) || "booklet".equals(localName)){
            periodicals.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch,start, length).trim().intern();
        if (currentEnum != null) {
            switch (currentEnum){
                case TITLE:
                    current.setTitle(str);
                    break;
                case VOLUME:
                    current.setVolume(Integer.valueOf(str));
                    break;
                case MONTHLY:
                    current.setMonthly(Boolean.valueOf(str));
                    break;
                case COLORED:
                    current.setColored(Boolean.valueOf(str));
                    break;
                case GLOSSY:
                    current.setGlossy(Boolean.valueOf(str));
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentEnum.getDeclaringClass(), currentEnum.name());

            }
            LOGGER.info("Method: characters\nSet: TITLE, VOLUME, COLORED, MONTHLY");
        }
        currentEnum = null;
    }
}
