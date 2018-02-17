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

package com.lineate.school;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class SearchBenchmark {

    @Param({"1", "10", "100", "1000", "10000", "100000"})
    private int size;

    private int[] ordered;
    private int[] randomized;
    private Set<Integer> hashSet;
    private Set<Integer> treeSet;
    Random rnd;

    private void toIntArray(List<Integer> from, int[] to) {
        for (int i = 0; i < from.size(); i++) {
            to[i] = from.get(i);
        }
    }

    @Setup
    public void setup() {
        rnd = new Random(1234); // repeatability
        ordered = new int[size];
        randomized = new int[size];
        hashSet = new HashSet<>();
        treeSet = new TreeSet<>();

        final ArrayList<Integer> tmp = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            tmp.add(i);
        }

        toIntArray(tmp, ordered);
        hashSet.addAll(tmp);
        treeSet.addAll(tmp);
        Collections.shuffle(tmp, rnd);
        toIntArray(tmp, randomized);
    }

    @Benchmark
    public int baseline() {
        final int required = rnd.nextInt(size);
        return required;
    }

    @Benchmark
    public int ordered() {
        final int required = rnd.nextInt(size);
        for (int i = 0; i < size; i++) {
            if (required == ordered[i]) {
                return i;
            }
        }
        return -1;
    }

    @Benchmark
    public int orderedBinary() {
        final int required = rnd.nextInt(size);
        return Arrays.binarySearch(ordered, required);
    }

    @Benchmark
    public int randomized() {
        final int required = rnd.nextInt(size);
        for (int i = 0; i < size; i++) {
            if (required == randomized[i]) {
                return i;
            }
        }
        return -1;
    }

    @Benchmark
    public boolean hashSet() {
        final int required = rnd.nextInt(size);
        return hashSet.contains(required);
    }

    @Benchmark
    public boolean treeSet() {
        final int required = rnd.nextInt(size);
        return treeSet.contains(required);
    }

}
