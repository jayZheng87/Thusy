package com.rambo.tools;

import java.util.Random;

public final class JVMRandom extends Random {
    private static final long serialVersionUID = 1L;
    private static final Random SHARED_RANDOM = new Random();
    private boolean constructed = false;

    public JVMRandom() {
        this.constructed = true;
    }

    public synchronized void setSeed(long seed) {
        if (this.constructed) {
            throw new UnsupportedOperationException();
        }
    }

    public synchronized double nextGaussian() {
        throw new UnsupportedOperationException();
    }

    public void nextBytes(byte[] byteArray) {
        throw new UnsupportedOperationException();
    }

    public int nextInt() {
        return nextInt(2147483647);
    }

    public int nextInt(int n) {
        return SHARED_RANDOM.nextInt(n);
    }

    public long nextLong() {
        return nextLong(9223372036854775807L);
    }

    public static long nextLong(long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("Upper bound for nextInt must be positive");
        }
        if ((n & -n) == n) {
            return next63bits() >> 63 - bitsRequired(n - 1L);
        }
        long bits;
        long val;
        do {
            bits = next63bits();
            val = bits % n;
        } while (bits - val + (n - 1L) < 0L);
        return val;
    }

    public boolean nextBoolean() {
        return SHARED_RANDOM.nextBoolean();
    }

    public float nextFloat() {
        return SHARED_RANDOM.nextFloat();
    }

    public double nextDouble() {
        return SHARED_RANDOM.nextDouble();
    }

    private static long next63bits() {
        return SHARED_RANDOM.nextLong() & 0xFFFFFFFF;
    }

    private static int bitsRequired(long num) {
        long y = num;
        int n = 0;
        for (; ; ) {
            if (num < 0L) {
                return 64 - n;
            }
            if (y == 0L) {
                return n;
            }
            n++;
            num <<= 1;
            y >>= 1;
        }
    }

    public static void main(String[] args) {
        JVMRandom jvmRandom = new JVMRandom();
        System.out.println("1.100以内：" + jvmRandom.nextInt(100));
    }
}
