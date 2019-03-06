package com.example.application.services.utils;

import org.springframework.data.util.Pair;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    public static <A, B> Stream<Pair<A,B>> zip(Stream<A> as, Stream<B> bs) {
        Iterator<A> i1 = as.iterator();
        Iterator<B> i2 = bs.iterator();
        Iterable<Pair<A,B>> i=()-> new Iterator<>() {
            public boolean hasNext() {
                return i1.hasNext() && i2.hasNext();
            }

            public Pair<A, B> next() {
                return Pair.of(i1.next(), i2.next());
            }
        };
        return StreamSupport.stream(i.spliterator(), false);
    }
}
