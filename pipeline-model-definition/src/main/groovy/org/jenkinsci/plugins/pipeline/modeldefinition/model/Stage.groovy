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
package org.jenkinsci.plugins.pipeline.modeldefinition.model

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.jenkinsci.plugins.pipeline.modeldefinition.steps.CredentialWrapper

import javax.annotation.Nonnull

/**
 * An individual stage to be executed within the build.
 *
 * @author Andrew Bayer
 */
@ToString
@EqualsAndHashCode
@SuppressFBWarnings(value="SE_NO_SERIALVERSIONID")
public class Stage implements NestedModel, Serializable {

    String name

    StepsBlock steps

    Agent agent

    PostStage post

    StepsBlock when

    Tools tools

    Environment environment

    Stage name(String n) {
        this.name = n
        return this
    }

    Stage agent(Agent a) {
        this.agent = a
        return this
    }

    Stage agent(Map<String,String> args) {
        this.agent = new Agent(args)
        return this
    }

    Stage agent(String s) {
        this.agent = new Agent(s)
        return this
    }

    Stage steps(StepsBlock s) {
        this.steps = s
        return this
    }

    Stage post(PostStage post) {
        this.post = post
        return this
    }

    Stage when(StepsBlock when) {
        this.when = when
        return this
    }

    Stage tools(Tools tools) {
        this.tools = tools
        return this
    }

    Stage environment(Environment environment) {
        this.environment = environment
        return this
    }

    /**
     * Helper method for translating the key/value pairs in the {@link Environment} into a list of "key=value" strings
     * suitable for use with the withEnv step.
     *
     * @return a list of "key=value" strings.
     */
    List<String> getEnvVars() {
        return environment.findAll{k, v -> !(v instanceof CredentialWrapper)}.collect { k, v ->
            "${k}=${v}"
        }
    }

    @Nonnull
    Map<String, CredentialWrapper> getEnvCredentials() {
        Map<String, CredentialWrapper> m = [:]
        environment.each {k, v ->
            if (v instanceof  CredentialWrapper) {
                m["${k}"] = v;
            }
        }
        return m
    }


    @Override
    public void modelFromMap(Map<String,Object> m) {
        m.each { k, v ->
            this."${k}"(v)
        }
    }

    /**
     * Returns a list of notification closures whose conditions have been satisfied and should be run.
     *
     * @param runWrapperObj The {@link org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper} for the build.
     * @return a list of closures whose conditions have been satisfied.
     */
    List<Closure> satisfiedPostStageConditions(Root root, Object runWrapperObj) {
        return root.satisfiedConditionsForField(post, runWrapperObj)
    }

}
