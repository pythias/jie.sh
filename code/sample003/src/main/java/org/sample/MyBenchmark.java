/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import org.openjdk.jmh.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(3)
@Fork(value = 3, jvmArgs = {"-Xms1G", "-Xmx1G"})
public class MyBenchmark {
    public Logger log = null;

    @Setup
    public void setup() {
        log = LoggerFactory.getLogger(MyBenchmark.class);
    }

    @Benchmark
    public void testDebug() {
        log.debug("Hello.");
    }

    @Benchmark
    public void testDebugWithIf() {
        if (log.isDebugEnabled()) {
            log.debug("Hello.");
        }
    }

    @Benchmark
    public void testDebug5() {
        log.debug("Hello {}, {}, {}, {}, {}.", "one", "two", "three", "four", "five");
    }

    @Benchmark
    public void testDebugWithIf5() {
        if (log.isDebugEnabled()) {
            log.debug("Hello {}, {}, {}, {}, {}.", "one", "two", "three", "four", "five");
        }
    }

    @Benchmark
    public void testInfo5() {
        log.info("Hello {}, {}, {}, {}, {}.", "one", "two", "three", "four", "five");
    }

    @Benchmark
    public void testInfoBuild5() {
        StringBuilder sb = new StringBuilder("Hello ");
        sb.append("one").append(", ").append("two").append(", ");
        sb.append("three").append(", ").append("four").append(", ");
        sb.append("five").append(".");
        log.info(sb.toString());
    }
}
