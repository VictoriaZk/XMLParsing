package by.epam.training.mikhailvasilenka.parser;

import by.epam.training.mikhailvasilenka.periodic.Booklet;
import by.epam.training.mikhailvasilenka.periodic.Magazine;
import by.epam.training.mikhailvasilenka.periodic.Newspaper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PeriodicalsSAXBuilderTest {

    private static PeriodicalsSAXBuilder builder;

    @BeforeClass
    public static void initBuilder() {
        builder = new PeriodicalsSAXBuilder();
        builder.BuildSetPeriodicals("files/periodicals/papers.xml");
    }

    @Test
    public void buildSetPeriodicalsTestSize() {
        assertEquals(16, builder.getPeriodicals().size());
    }

    @Test
    public void buildSetPeriodicalsTestFullElementNewspaper() {
        Newspaper news = new Newspaper();
        news.setIssn("3423-543X");
        news.setMonthly(true);
        news.setTitle("New York Times");
        news.setVolume(48);
        news.setColored(true);
        news.setGlossy(false);

        System.out.println(builder.getPeriodicals());
        assertTrue(builder.getPeriodicals().contains(news));
    }

    @Test
    public void buildSetPeriodicalsTestFullElementMagazine() {
        Magazine mag = new Magazine();
        mag.setIssn("5436-4430");
        mag.setTitle("Cosmopolitan");
        mag.setVolume(94);
        mag.setGlossy(false);
        mag.setColored(false);
        mag.setMonthly(false);

        System.out.println(builder.getPeriodicals());
        assertTrue(builder.getPeriodicals().contains(mag));
    }

    @Test
    public void buildSetPeriodicalsTestFullElementBooklet() {
        Booklet booklet = new Booklet();
        booklet.setIssn("none");
        booklet.setTitle("OUR past");
        booklet.setVolume(5);
        booklet.setColored(false);
        booklet.setGlossy(true);
        booklet.setMonthly(false);



        System.out.println(builder.getPeriodicals());
        assertTrue(builder.getPeriodicals().contains(booklet));
    }

}