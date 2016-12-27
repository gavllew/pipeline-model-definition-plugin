package org.jenkinsci.plugins.pipeline.modeldefinition.parser;

import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.jenkinsci.plugins.pipeline.modeldefinition.BaseParserLoaderTest;
import org.jenkinsci.plugins.pipeline.modeldefinition.Messages;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Kohsuke Kawaguchi
 */
public class ModelParserTest extends BaseParserLoaderTest {

    @Test(expected = MultipleCompilationErrorsException.class)
    public void emptyStages() throws Exception {
        parse(getClass().getResource("/errors/emptyStages.groovy"));
    }

    /**
     * Look ma! THERE IS NO STACK TRACE!
     */
    @Test
    public void stageWithoutName() throws Exception {
        ErrorCollector ec = parseForError(getClass().getResource("/errors/stageWithoutName.groovy"));
        String msg = write(ec);
        System.out.println("----");
        System.out.println(msg);
        System.out.println("----");
        assertTrue(msg.contains(Messages.ModelParser_ExpectedStringLiteral()));
        assertFalse(msg.contains("Exception")); // we don't want stack trace please
    }
}
