package com.rambo.tools;

import java.io.Serializable;


public final class BasicConstant {
    // =============================================================
    //  数组常量
    // =============================================================

    // primitive arrays

    /**
     * 空的<code>byte</code>数组。
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];

    /**
     * 空的<code>short</code>数组。
     */
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];

    /**
     * 空的<code>int</code>数组。
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

    /**
     * 空的<code>long</code>数组。
     */
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];

    /**
     * 空的<code>float</code>数组。
     */
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];

    /**
     * 空的<code>double</code>数组。
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];

    /**
     * 空的<code>char</code>数组。
     */
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

    /**
     * 空的<code>boolean</code>数组。
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];

    // object arrays

    /**
     * 空的<code>Object</code>数组。
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * 空的<code>Class</code>数组。
     */
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

    /**
     * 空的<code>String</code>数组。
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    // =============================================================
    //  对象常量
    // =============================================================

    // 0-valued primitive wrappers
    public static final Byte BYTE_ZERO = new Byte((byte) 0);
    public static final Short SHORT_ZERO = new Short((short) 0);
    public static final Integer INT_ZERO = new Integer(0);
    public static final Long LONG_ZERO = new Long(0L);
    public static final Float FLOAT_ZERO = new Float(0);
    public static final Double DOUBLE_ZERO = new Double(0);
    public static final Character CHAR_NULL = new Character('\0');
    public static final Boolean BOOL_FALSE = Boolean.FALSE;

    /**
     * 代表null值的占位对象。
     */
    public static final Object NULL_PLACEHOLDER = new NullPlaceholder();

    private final static class NullPlaceholder implements Serializable {
        private static final long serialVersionUID = 7092611880189329093L;


        public String toString() {
            return "null";
        }

        private Object readResolve() {
            return NULL_PLACEHOLDER;
        }
    }

    /**
     * 空字符串。
     */
    public static final String EMPTY_STRING = "";
}
