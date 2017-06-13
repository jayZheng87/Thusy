package com.rambo.tools;

import java.io.IOException;
import java.util.Arrays;

import static com.rambo.tools.BasicConstant.EMPTY_STRING;


/**
 * 有关<code>Object</code>处理的工具类。
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 */
public class ObjectUtil {


    // ==========================================================================
    // 比较函数。
    //
    // 以下方法用来比较两个对象的值或类型是否相同。
    // ==========================================================================

    /**
     * 比较两个对象是否完全相等。
     * <p>
     * 此方法可以正确地比较多维数组。
     * <p/>
     * <pre>
     * ObjectUtil.equals(null, null)                  = true
     * ObjectUtil.equals(null, "")                    = false
     * ObjectUtil.equals("", null)                    = false
     * ObjectUtil.equals("", "")                      = true
     * ObjectUtil.equals(Boolean.TRUE, null)          = false
     * ObjectUtil.equals(Boolean.TRUE, "true")        = false
     * ObjectUtil.equals(Boolean.TRUE, Boolean.TRUE)  = true
     * ObjectUtil.equals(Boolean.TRUE, Boolean.FALSE) = false
     * </pre>
     * <p/>
     * </p>
     * <p>
     * param object1 对象1
     * param object2 对象2
     * return 如果相等, 则返回<code>true</code>
     */
    public static boolean isEquals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }

        if (object1 == null || object2 == null) {
            return false;
        }

        if (!object1.getClass().equals(object2.getClass())) {
            return false;
        }

        if (object1 instanceof Object[]) {
            return Arrays.deepEquals((Object[]) object1, (Object[]) object2);
        } else if (object1 instanceof int[]) {
            return Arrays.equals((int[]) object1, (int[]) object2);
        } else if (object1 instanceof long[]) {
            return Arrays.equals((long[]) object1, (long[]) object2);
        } else if (object1 instanceof short[]) {
            return Arrays.equals((short[]) object1, (short[]) object2);
        } else if (object1 instanceof byte[]) {
            return Arrays.equals((byte[]) object1, (byte[]) object2);
        } else if (object1 instanceof double[]) {
            return Arrays.equals((double[]) object1, (double[]) object2);
        } else if (object1 instanceof float[]) {
            return Arrays.equals((float[]) object1, (float[]) object2);
        } else if (object1 instanceof char[]) {
            return Arrays.equals((char[]) object1, (char[]) object2);
        } else if (object1 instanceof boolean[]) {
            return Arrays.equals((boolean[]) object1, (boolean[]) object2);
        } else {
            return object1.equals(object2);
        }
    }

    /**
     * 检查两个对象是否属于相同类型。<code>null</code>将被看作任意类型。
     * <p>
     * param object1 对象1
     * param object2 对象2
     * return 如果两个对象有相同的类型，则返回<code>true</code>
     */
    public static boolean isSameType(Object object1, Object object2) {
        if (object1 == null || object2 == null) {
            return true;
        }

        return object1.getClass().equals(object2.getClass());
    }

    /**
     * 如果对象为<code>null</code>，则返回指定默认对象，否则返回对象本身。
     * <p/>
     * <pre>
     * ObjectUtil.defaultIfNull(null, null)      = null
     * ObjectUtil.defaultIfNull(null, "")        = ""
     * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtil.defaultIfNull("abc", *)        = "abc"
     * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     * <p>
     * param object       要测试的对象
     * param defaultValue 默认值
     * return 对象本身或默认对象
     */
    public static <T, S extends T> T defaultIfNull(T object, S defaultValue) {
        return object == null ? defaultValue : object;
    }

    // ==========================================================================
    // 判空函数。
    // ==========================================================================

    /**
     * 是否为<code>null</code>、空字符串、或空数组。
     */
    public static boolean isEmptyObject(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return StringUtil.isEmpty((String) object);
        } else if (object.getClass().isArray()) {
            return ArrayUtil.isEmptyArray(object);
        } else {
            return false;
        }
    }


    // ==========================================================================
    // Hash code函数。
    //
    // 以下方法用来取得对象的hash code。
    // ==========================================================================

    /**
     * 取得对象的hash值, 如果对象为<code>null</code>, 则返回<code>0</code>。
     * <p>
     * 此方法可以正确地处理多维数组。
     * </p>
     * <p>
     * param object 对象
     * return hash值
     */
    public static int hashCode(Object object) {
        if (object == null) {
            return 0;
        } else if (object instanceof Object[]) {
            return Arrays.deepHashCode((Object[]) object);
        } else if (object instanceof int[]) {
            return Arrays.hashCode((int[]) object);
        } else if (object instanceof long[]) {
            return Arrays.hashCode((long[]) object);
        } else if (object instanceof short[]) {
            return Arrays.hashCode((short[]) object);
        } else if (object instanceof byte[]) {
            return Arrays.hashCode((byte[]) object);
        } else if (object instanceof double[]) {
            return Arrays.hashCode((double[]) object);
        } else if (object instanceof float[]) {
            return Arrays.hashCode((float[]) object);
        } else if (object instanceof char[]) {
            return Arrays.hashCode((char[]) object);
        } else if (object instanceof boolean[]) {
            return Arrays.hashCode((boolean[]) object);
        } else {
            return object.hashCode();
        }
    }

    // ==========================================================================
    // 取得对象的identity。
    // ==========================================================================

    /**
     * 取得对象的原始的hash值, 如果对象为<code>null</code>, 则返回<code>0</code>。
     * <p>
     * 该方法使用<code>System.identityHashCode</code>来取得hash值，该值不受对象本身的
     * <code>hashCode</code>方法的影响。
     * </p>
     * <p>
     * param object 对象
     * return hash值
     */
    public static int identityHashCode(Object object) {
        return object == null ? 0 : System.identityHashCode(object);
    }

    /**
     * 取得对象自身的identity，如同对象没有覆盖<code>toString()</code>方法时，
     * <code>Object.toString()</code>的原始输出。
     * <p/>
     * <pre>
     * ObjectUtil.identityToString(null)          = null
     * ObjectUtil.identityToString("")            = "java.lang.String1e23"
     * ObjectUtil.identityToString(Boolean.TRUE)  = "java.lang.Boolean7fa"
     * ObjectUtil.identityToString(new int[0])    = "int[]7fa"
     * ObjectUtil.identityToString(new Object[0]) = "java.lang.Object[]7fa"
     * </pre>
     * <p>
     * param object 对象
     * return 对象的identity，如果对象是<code>null</code>，则返回<code>null</code>
     */
    public static String identityToString(Object object) {
        if (object == null) {
            return null;
        }

        return appendIdentityToString(new StringBuilder(), object).toString();
    }

    /**
     * 取得对象自身的identity，如同对象没有覆盖<code>toString()</code>方法时，
     * <code>Object.toString()</code>的原始输出。
     * <p/>
     * <pre>
     * ObjectUtil.identityToString(null, "NULL")            = "NULL"
     * ObjectUtil.identityToString("", "NULL")              = "java.lang.String1e23"
     * ObjectUtil.identityToString(Boolean.TRUE, "NULL")    = "java.lang.Boolean7fa"
     * ObjectUtil.identityToString(new int[0], "NULL")      = "int[]7fa"
     * ObjectUtil.identityToString(new Object[0], "NULL")   = "java.lang.Object[]7fa"
     * </pre>
     * <p>
     * param object  对象
     * param nullStr 如果对象为<code>null</code>，则返回该字符串
     * return 对象的identity，如果对象是<code>null</code>，则返回指定字符串
     */
    public static String identityToString(Object object, String nullStr) {
        if (object == null) {
            return nullStr;
        }

        return appendIdentityToString(new StringBuilder(), object).toString();
    }

    /**
     * 将对象自身的identity——如同对象没有覆盖<code>toString()</code>方法时，
     * <code>Object.toString()</code>的原始输出——追加到<code>Appendable</code>中。
     * <p/>
     * <pre>
     * ObjectUtil.appendIdentityToString(buf, null)          = null
     * ObjectUtil.appendIdentityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean7fa")
     * ObjectUtil.appendIdentityToString(buf, new int[0])    = buf.append("int[]7fa")
     * ObjectUtil.appendIdentityToString(buf, new Object[0]) = buf.append("java.lang.Object[]7fa")
     * </pre>
     * <p>
     * param buffer <code>Appendable</code>对象
     * param object 对象
     * return <code>Appendable</code>对象，如果对象为<code>null</code>，则输出
     * <code>"null"</code>
     */
    public static <A extends Appendable> A appendIdentityToString(A buffer, Object object) {

        try {
            if (object == null) {
                buffer.append("null");
            } else {
                buffer.append(ClassUtil.getFriendlyClassNameForObject(object));
                buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return buffer;
    }

    // ==========================================================================
    // toString方法。
    // ==========================================================================

    /**
     * 取得对象的<code>toString()</code>的值，如果对象为<code>null</code>，则返回空字符串
     * <code>""</code>。
     * <p/>
     * <pre>
     * ObjectUtil.toString(null)         = ""
     * ObjectUtil.toString("")           = ""
     * ObjectUtil.toString("bat")        = "bat"
     * ObjectUtil.toString(Boolean.TRUE) = "true"
     * ObjectUtil.toString([1, 2, 3])    = "[1, 2, 3]"
     * </pre>
     * <p>
     * param object 对象
     * return 对象的<code>toString()</code>的返回值，或空字符串<code>""</code>
     */
    public static String toString(Object object) {
        return toString(object, EMPTY_STRING);
    }

    /**
     * 取得对象的<code>toString()</code>的值，如果对象为<code>null</code>，则返回指定字符串。
     * <p/>
     * <pre>
     * ObjectUtil.toString(null, null)           = null
     * ObjectUtil.toString(null, "null")         = "null"
     * ObjectUtil.toString("", "null")           = ""
     * ObjectUtil.toString("bat", "null")        = "bat"
     * ObjectUtil.toString(Boolean.TRUE, "null") = "true"
     * ObjectUtil.toString([1, 2, 3], "null")    = "[1, 2, 3]"
     * </pre>
     * <p>
     * param object  对象
     * param nullStr 如果对象为<code>null</code>，则返回该字符串
     * return 对象的<code>toString()</code>的返回值，或指定字符串
     */
    public static String toString(Object object, String nullStr) {
        if (object == null) {
            return nullStr;
        } else if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        } else if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        } else if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        } else if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        } else if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        } else if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        } else if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        } else if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        } else if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        } else {
            return object.toString();
        }
    }

}
