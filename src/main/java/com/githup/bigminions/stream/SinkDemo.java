package com.githup.bigminions.stream;

import org.junit.Test;

import java.util.function.Consumer;

/**
 * Created by daren on 2017/5/17.
 * 一个简短的关于流的实现
 */
public class SinkDemo {

    public static abstract class Sink implements Consumer {
        Sink downStream;

        Sink() {}
        Sink(Sink downStream) {
            this.downStream = downStream;
        }
    }

    public static abstract class Op {
        Op upStream;

        Op() {}

        Op(Op upStream) {
            this.upStream = upStream;
        }

        abstract Sink opWrapSink(Sink sink);

        public Op peek(Consumer action) {
            return new Op(this) {
                @Override
                Sink opWrapSink(Sink sink) {
                    return new Sink(sink) {
                        @Override
                        public void accept(Object o) {
                            action.accept(o);
                            if(downStream != null) downStream.accept(o);
                        }
                    };
                }
            };
        }
    }

    @Test
    public void impl() {
        Op op = new Op() {
            // 头指针无任何操作
            private String pos = "head";
            @Override
            Sink opWrapSink(Sink sink) {
                return new Sink() {
                    @Override
                    public void accept(Object o) {
                    }
                };
            }
        };
        op = op.peek(o -> System.out.println("peek a " + o));
        op = op.peek(o -> System.out.println("peek b " + o));
        op = op.peek(o -> System.out.println("peek c " + o));
        Sink sink = null;
        do {
            sink = op.opWrapSink(sink);
            op = op.upStream;
        } while (op.upStream != null);
        sink.accept("hahaha");
    }
}
