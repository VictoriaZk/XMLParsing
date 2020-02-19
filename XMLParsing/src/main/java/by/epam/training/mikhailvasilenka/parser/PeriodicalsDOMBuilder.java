package by.epam.training.mikhailvasilenka.parser;

import by.epam.training.mikhailvasilenka.periodic.Booklet;
import by.epam.training.mikhailvasilenka.periodic.Magazine;
import by.epam.training.mikhailvasilenka.periodic.Newspaper;
import by.epam.training.mikhailvasilenka.periodic.Periodical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PeriodicalsDOMBuilder {

    private static final Logger LOGGER = LogManager.getLogger(PeriodicalsDOMBuilder.class);
    private Set<Periodical> periodicals;
    private DocumentBuilder docBuilder;

    public PeriodicalsDOMBuilder() throws ParserConfigurationException {
        periodicals = new HashSet<Periodical>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        docBuilder = factory.newDocumentBuilder();
    }

    public Set<Periodical> getPeriodicals() {
        return periodicals;
    }

    public void buildSetPeriodicals(String fileName) {
        Document doc = null;
        try {
            doc = docBuilder.parse(fileName);
        } catch (SAXException e) {
            LOGGER.error("SAX parser error: " + e);
        } catch (IOException e) {
            LOGGER.error("File error/IO error: " + e);
        }
        Element root = doc.getDocumentElement();
        buildSetByTagName("newspaper", root);
        buildSetByTagName("magazine", root);
        buildSetByTagName("booklet", root);
    }

    private void buildSetByTagName(String tagName, Element root) {
        LOGGER.debug((root.getElementsByTagName(tagName)).getLength());
        NodeList periodicalsList = root.getElementsByTagName(tagName);
        for (int i = 0; i < periodicalsList.getLength(); i++) {
            Element periodicalElement = (Element) periodicalsList.item(i);
            Periodical periodical = buildPeriodical(periodicalElement);
            periodicals.add(periodical);
        }
    }

    protected Periodical buildPeriodical(Element periodicalElement) {
        Periodical periodical;
        switch (periodicalElement.getTagName()) {
            case "newspaper":
                periodical = new Newspaper();
                periodical.setGlossy(Boolean.valueOf(getElementTextContent(periodicalElement, "glossy")));
                break;
            case "magazine":
                periodical = new Magazine();
                periodical.setGlossy(Boolean.valueOf(getElementTextContent(periodicalElement, "glossy")));
                break;
            case "booklet":
                periodical = new Booklet();
                periodical.setGlossy(Boolean.valueOf(getElementTextContent(periodicalElement, "glossy")));
                break;
                default:
                    return Periodical.getEmptyPeriodical();
        }

        periodical.setIssn(periodicalElement.getAttribute("issn"));

        periodical.setTitle(getElementTextContent(periodicalElement, "title"));
        periodical.setColored(Boolean.valueOf(getElementTextContent(periodicalElement, "colored")));
        periodical.setMonthly(Boolean.valueOf(getElementTextContent(periodicalElement, "monthly")));
        periodical.setVolume(Integer.parseInt(getElementTextContent(periodicalElement, "volume")));

        return periodical;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList ndList = element.getElementsByTagName(elementName);
        Node node = ndList.item(0);
        return node.getTextContent();
    }
}
