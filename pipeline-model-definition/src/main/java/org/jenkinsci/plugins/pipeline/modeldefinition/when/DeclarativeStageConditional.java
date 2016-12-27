/*
 * The MIT License
 *
 * Copyright (c) 2016, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package org.jenkinsci.plugins.pipeline.modeldefinition.when;

import hudson.AbortException;
import hudson.ExtensionPoint;
import hudson.model.AbstractDescribableImpl;
import org.jenkinsci.plugins.workflow.cps.CpsScript;
import org.jenkinsci.plugins.workflow.cps.CpsThread;
import org.jenkinsci.plugins.workflow.steps.StepExecution;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Serializable;

/**
 * Conditionals for when to run a stage.
 */
public abstract class DeclarativeStageConditional extends AbstractDescribableImpl<DeclarativeStageConditional> implements Serializable, ExtensionPoint {

    public abstract boolean evaluate(CpsScript script) throws InterruptedException, AbortException;

    @Override
    public DeclarativeStageConditionalDescriptor getDescriptor() {
        return (DeclarativeStageConditionalDescriptor) super.getDescriptor();
    }

    @Nullable
    protected <T> T getContextVariable(Class<T> key) throws IOException, InterruptedException {
        CpsThread current = CpsThread.current();
        if (current == null) {
            throw new IllegalStateException("Needs to be called within a CPS Thread");
        }
        StepExecution step = current.getStep();
        if (step != null) {
            return step.getContext().get(key);
        }
        return null;
    }
}
