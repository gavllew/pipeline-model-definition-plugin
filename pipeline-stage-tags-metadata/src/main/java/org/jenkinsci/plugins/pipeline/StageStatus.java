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
 */

package org.jenkinsci.plugins.pipeline;

import hudson.Extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Extension
public class StageStatus extends StageTagsMetadata {

    public static final String TAG_NAME = "STAGE_STATUS";

    @Override
    public String getTagName() {
        return TAG_NAME;
    }

    @Override
    public List<String> getPossibleValues() {
        List<String> vals = new ArrayList<>();

        // When there are other categories of status values, we'll add their methods too.
        vals.addAll(getSkippedStageValues());
        vals.add(getFailedAndContinued());

        return vals;
    }

    public List<String> getSkippedStageValues() {
        return Arrays.asList(getSkippedForConditional(), getSkippedForFailure());
    }

    public static String getFailedAndContinued() {
        return "FAILED_AND_CONTINUED";
    }

    public static String getSkippedForFailure() {
        return "SKIPPED_FOR_FAILURE";
    }

    public static String getSkippedForConditional() {
        return "SKIPPED_FOR_CONDITIONAL";
    }
}
