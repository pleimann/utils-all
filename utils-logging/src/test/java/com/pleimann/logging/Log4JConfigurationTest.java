package com.pleimann.logging;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class Log4JConfigurationTest {
    @Rule
    public LoggerContextRule init = new LoggerContextRule();

    @Rule
    public ErrorCollector errors = new ErrorCollector();

    private Logger logger;
    private PatternLayout layout;
    private ListAppender list;

    @Before
    public void setUp(){
        final LoggerContext ctx = this.init.getLoggerContext();

        this.logger = this.init.getRootLogger();
        this.list = this.init.getListAppender("LIST");
        this.layout = (PatternLayout)this.init.getAppender("STDOUT").getLayout();
    }

    @Test
    public void testExceptionLogging(){
        RuntimeException testException = new RuntimeException("Test");
        try{
            throw testException;

        }catch(RuntimeException e){
            logger.error("Test", e);
        }

        final List<LogEvent> events = list.getEvents();
        this.errors.checkThat(events, is(Matchers.<LogEvent>iterableWithSize(1)));
        this.errors.checkThat(events.get(0), hasProperty("message", hasProperty("formattedMessage", equalTo(testException.getMessage()))));
    }
}
