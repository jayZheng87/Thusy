package com.rambo.tools;


import java.beans.Introspector;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static com.rambo.tools.ArrayUtil.isArraySameLength;
import static com.rambo.tools.ArrayUtil.isEmptyArray;
import static com.rambo.tools.BasicConstant.EMPTY_CLASS_ARRAY;
import static com.rambo.tools.BasicConstant.EMPTY_STRING;
import static com.rambo.tools.CollectionUtil.createHashMap;
import static com.rambo.tools.CollectionUtil.createHashSet;

/**
 * 有关 <code>Class</code> 处理的工具类。
 * <p>
 * 这个类中的每个方法都可以“安全”地处理 <code>null</code> ，而不会抛出
 * <code>NullPointerException</code>。
 * </p>
 */
public class ClassUtil {

    /**
     * Suffix for array class names: "[]"
     */
    public static final String ARRAY_SUFFIX = "[]";

    /**
     * The package separator character '.'
     */
    private static final char PACKAGE_SEPARATOR = '.';

    /**
     * The inner class separator character '$'
     */
    private static final char INNER_CLASS_SEPARATOR = '$';

    /**
     * The CGLIB class separator character "$$"
     */
    public static final String CGLIB_CLASS_SEPARATOR = "$$";

    /**
     * The ".class" file suffix
     */
    public static final String CLASS_FILE_SUFFIX = ".class";

    /**
     * 从接口端计算深度
     * param pInterface
     * param targetClass
     * return
     */
    public static int checkInterfaceDeepth(Class<?> pInterface, Class<?> targetClass) {
        if (targetClass.isAssignableFrom(pInterface))
            return 0;
        int min = -1;
        for (Class<?> c : targetClass.getInterfaces()) {
            if (pInterface.isAssignableFrom(c)) {
                int i = checkInterfaceDeepth(pInterface, c) + 1;
                if (min == -1 || min > i) {
                    min = i;
                }
            }
        }
        return min;
    }

    /**
     * param supperClass
     * param targetClass
     * return
     */
    public static int checkSupperClassDeepth(Class<?> supperClass, Class<?> targetClass) {
        if (!supperClass.isAssignableFrom(targetClass)) {
            return -1;
        }
        if (targetClass.isAssignableFrom(supperClass))
            return 0;
        int supperDeepth = checkSupperClassDeepth(supperClass, targetClass.getSuperclass()) + 1;
        int interfaceDeepth = checkInterfaceDeepth(supperClass, targetClass.getSuperclass()) + 1;
        if (supperDeepth < interfaceDeepth)
            return supperDeepth;
        return interfaceDeepth;
    }

    /**
     * 采取驼峰规则
     * <p>
     * param simpleName
     * return
     */
    public static <T> String humpString(Class<T> clazz) {
        String simpleName = clazz.getSimpleName();
        String first = simpleName.charAt(0) + "";
        return first.toLowerCase() + simpleName.substring(1);
    }

    // ==========================================================================
    // 取得友好类名和package名的方法。
    // ==========================================================================

    /**
     * 取得对象所属的类的友好类名。
     * <p>
     * 类似<code>object.getClass().getName()</code>，但不同的是，该方法用更友好的方式显示数组类型。 例如：
     * </p>
     * <p/>
     * <pre>
     *  int[].class.getName() = "[I"
     *  ClassUtil.getFriendlyClassName(int[].class) = "int[]"
     *
     *  Integer[][].class.getName() = "[[Ljava.lang.Integer;"
     *  ClassUtil.getFriendlyClassName(Integer[][].class) = "java.lang.Integer[][]"
     * </pre>
     * <p>
     * 对于非数组的类型，该方法等效于 <code>Class.getName()</code> 方法。
     * </p>
     * <p>
     * 注意，该方法所返回的数组类名只能用于显示给人看，不能用于 <code>Class.forName</code> 操作。
     * </p>
     * <p>
     * param object 要显示类名的对象
     * return 用于显示的友好类名，如果对象为空，则返回<code>null</code>
     */
    public static String getFriendlyClassNameForObject(Object object) {
        if (object == null) {
            return null;
        }

        String javaClassName = object.getClass().getName();

        return toFriendlyClassName(javaClassName, true, javaClassName);
    }

    /**
     * 取得友好的类名。
     * <p>
     * 类似<code>clazz.getName()</code>，但不同的是，该方法用更友好的方式显示数组类型。 例如：
     * </p>
     * <p/>
     * <pre>
     *  int[].class.getName() = "[I"
     *  ClassUtil.getFriendlyClassName(int[].class) = "int[]"
     *
     *  Integer[][].class.getName() = "[[Ljava.lang.Integer;"
     *  ClassUtil.getFriendlyClassName(Integer[][].class) = "java.lang.Integer[][]"
     * </pre>
     * <p>
     * 对于非数组的类型，该方法等效于 <code>Class.getName()</code> 方法。
     * </p>
     * <p>
     * 注意，该方法所返回的数组类名只能用于显示给人看，不能用于 <code>Class.forName</code> 操作。
     * </p>
     * <p>
     * param object 要显示类名的对象
     * return 用于显示的友好类名，如果类对象为空，则返回<code>null</code>
     */
    public static String getFriendlyClassName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        String javaClassName = clazz.getName();

        return toFriendlyClassName(javaClassName, true, javaClassName);
    }

    /**
     * 取得友好的类名。
     * <p>
     * <code>className</code> 必须是从 <code>clazz.getName()</code>
     * 所返回的合法类名。该方法用更友好的方式显示数组类型。 例如：
     * </p>
     * <p/>
     * <pre>
     *  int[].class.getName() = "[I"
     *  ClassUtil.getFriendlyClassName(int[].class) = "int[]"
     *
     *  Integer[][].class.getName() = "[[Ljava.lang.Integer;"
     *  ClassUtil.getFriendlyClassName(Integer[][].class) = "java.lang.Integer[][]"
     * </pre>
     * <p>
     * 对于非数组的类型，该方法等效于 <code>Class.getName()</code> 方法。
     * </p>
     * <p>
     * 注意，该方法所返回的数组类名只能用于显示给人看，不能用于 <code>Class.forName</code> 操作。
     * </p>
     * <p>
     * param javaClassName 要转换的类名
     * return 用于显示的友好类名，如果原类名为空，则返回 <code>null</code> ，如果原类名是非法的，则返回原类名
     */
    public static String getFriendlyClassName(String javaClassName) {
        return toFriendlyClassName(javaClassName, true, javaClassName);
    }

    /**
     * 将Java类名转换成友好类名。
     * <p>
     * param javaClassName     Java类名
     * param processInnerClass 是否将内联类分隔符 <code>'$'</code> 转换成 <code>'.'</code>
     * return 友好的类名。如果参数非法或空，则返回<code>null</code>。
     */
    private static String toFriendlyClassName(String javaClassName, boolean processInnerClass, String defaultIfInvalid) {
        String name = StringUtil.trimToNull(javaClassName);

        if (name == null) {
            return defaultIfInvalid;
        }

        if (processInnerClass) {
            name = name.replace('$', '.');
        }

        int length = name.length();
        int dimension = 0;

        // 取得数组的维数，如果不是数组，维数为0
        for (int i = 0; i < length; i++, dimension++) {
            if (name.charAt(i) != '[') {
                break;
            }
        }

        // 如果不是数组，则直接返回
        if (dimension == 0) {
            return name;
        }

        // 确保类名合法
        if (length <= dimension) {
            return defaultIfInvalid; // 非法类名
        }

        // 处理数组
        StringBuilder componentTypeName = new StringBuilder();

        switch (name.charAt(dimension)) {
            case 'Z':
                componentTypeName.append("boolean");
                break;

            case 'B':
                componentTypeName.append("byte");
                break;

            case 'C':
                componentTypeName.append("char");
                break;

            case 'D':
                componentTypeName.append("double");
                break;

            case 'F':
                componentTypeName.append("float");
                break;

            case 'I':
                componentTypeName.append("int");
                break;

            case 'J':
                componentTypeName.append("long");
                break;

            case 'S':
                componentTypeName.append("short");
                break;

            case 'L':
                if (name.charAt(length - 1) != ';' || length <= dimension + 2) {
                    return defaultIfInvalid; // 非法类名
                }

                componentTypeName.append(name.substring(dimension + 1, length - 1));
                break;

            default:
                return defaultIfInvalid; // 非法类名
        }

        for (int i = 0; i < dimension; i++) {
            componentTypeName.append("[]");
        }

        return componentTypeName.toString();
    }

    /**
     * 取得指定对象所属的类的简单类名，不包括package名。
     * <p>
     * 此方法可以正确显示数组和内联类的名称。 例如：
     * <p/>
     * <pre>
     *  ClassUtil.getSimpleClassNameForObject(Boolean.TRUE) = "Boolean"
     *  ClassUtil.getSimpleClassNameForObject(new Boolean[10]) = "Boolean[]"
     *  ClassUtil.getSimpleClassNameForObject(new int[1][2]) = "int[][]"
     * </pre>
     * <p>
     * 本方法和<code>Class.getSimpleName()</code>的区别在于，本方法会保留inner类的外层类名称。
     * </p>
     * <p>
     * param object 要查看的对象
     * return 简单类名，如果对象为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String getSimpleClassNameForObject(Object object) {
        if (object == null) {
            return null;
        }

        return getSimpleClassName(object.getClass().getName());
    }

    /**
     * 取得指定对象所属的类的简单类名，不包括package名。
     * <p>
     * 此方法可以正确显示数组和内联类的名称。 例如：
     * <p/>
     * <pre>
     *  ClassUtil.getSimpleClassNameForObject(Boolean.TRUE) = "Boolean"
     *  ClassUtil.getSimpleClassNameForObject(new Boolean[10]) = "Boolean[]"
     *  ClassUtil.getSimpleClassNameForObject(new int[1][2]) = "int[][]"
     * </pre>
     * <p>
     * 本方法和<code>Class.getSimpleName()</code>的区别在于，本方法会保留inner类的外层类名称。
     * </p>
     * <p>
     * param object 要查看的对象
     * return 简单类名，如果对象为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String getSimpleClassNameForObject(Object object, boolean processInnerClass) {
        if (object == null) {
            return null;
        }

        return getSimpleClassName(object.getClass().getName(), processInnerClass);
    }

    /**
     * 取得简单类名，不包括package名。
     * <p>
     * 此方法可以正确显示数组和内联类的名称。 例如：
     * <p/>
     * <pre>
     *  ClassUtil.getSimpleClassName(Boolean.class) = "Boolean"
     *  ClassUtil.getSimpleClassName(Boolean[].class) = "Boolean[]"
     *  ClassUtil.getSimpleClassName(int[][].class) = "int[][]"
     *  ClassUtil.getSimpleClassName(Map.Entry.class) = "Map.Entry"
     * </pre>
     * <p>
     * 本方法和<code>Class.getSimpleName()</code>的区别在于，本方法会保留inner类的外层类名称。
     * </p>
     * <p>
     * param clazz 要查看的类
     * return 简单类名，如果类为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String getSimpleClassName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return getSimpleClassName(clazz.getName());
    }

    /**
     * 取得简单类名，不包括package名。
     * <p>
     * 此方法可以正确显示数组和内联类的名称。 例如：
     * <p/>
     * <pre>
     *  ClassUtil.getSimpleClassName(Boolean.class) = "Boolean"
     *  ClassUtil.getSimpleClassName(Boolean[].class) = "Boolean[]"
     *  ClassUtil.getSimpleClassName(int[][].class) = "int[][]"
     *  ClassUtil.getSimpleClassName(Map.Entry.class) = "Map.Entry"
     * </pre>
     * <p>
     * 本方法和<code>Class.getSimpleName()</code>的区别在于，本方法会保留inner类的外层类名称。
     * </p>
     * <p>
     * param clazz 要查看的类
     * return 简单类名，如果类为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String getSimpleClassName(Class<?> clazz, boolean proccessInnerClass) {
        if (clazz == null) {
            return null;
        }

        return getSimpleClassName(clazz.getName(), proccessInnerClass);
    }

    /**
     * 取得类名，不包括package名。
     * <p>
     * 此方法可以正确显示数组和内联类的名称。 例如：
     * <p/>
     * <pre>
     *  ClassUtil.getSimpleClassName(Boolean.class.getName()) = "Boolean"
     *  ClassUtil.getSimpleClassName(Boolean[].class.getName()) = "Boolean[]"
     *  ClassUtil.getSimpleClassName(int[][].class.getName()) = "int[][]"
     *  ClassUtil.getSimpleClassName(Map.Entry.class.getName()) = "Map.Entry"
     * </pre>
     * <p>
     * 本方法和<code>Class.getSimpleName()</code>的区别在于，本方法会保留inner类的外层类名称。
     * </p>
     * <p>
     * param javaClassName 要查看的类名
     * return 简单类名，如果类名为空，则返回 <code>null</code>
     */
    public static String getSimpleClassName(String javaClassName) {
        return getSimpleClassName(javaClassName, true);
    }

    /**
     * 取得类名，不包括package名。
     * <p>
     * 此方法可以正确显示数组和内联类的名称。 例如：
     * <p/>
     * <pre>
     *  ClassUtil.getSimpleClassName(Boolean.class.getName()) = "Boolean"
     *  ClassUtil.getSimpleClassName(Boolean[].class.getName()) = "Boolean[]"
     *  ClassUtil.getSimpleClassName(int[][].class.getName()) = "int[][]"
     *  ClassUtil.getSimpleClassName(Map.Entry.class.getName()) = "Map.Entry"
     * </pre>
     * <p>
     * 本方法和<code>Class.getSimpleName()</code>的区别在于，本方法会保留inner类的外层类名称。
     * </p>
     * <p>
     * param javaClassName 要查看的类名
     * return 简单类名，如果类名为空，则返回 <code>null</code>
     */
    public static String getSimpleClassName(String javaClassName, boolean proccesInnerClass) {
        String friendlyClassName = toFriendlyClassName(javaClassName, false, null);

        if (friendlyClassName == null) {
            return javaClassName;
        }

        if (proccesInnerClass) {
            char[] chars = friendlyClassName.toCharArray();
            int beginIndex = 0;

            for (int i = chars.length - 1; i >= 0; i--) {
                if (chars[i] == '.') {
                    beginIndex = i + 1;
                    break;
                } else if (chars[i] == '$') {
                    chars[i] = '.';
                }
            }

            return new String(chars, beginIndex, chars.length - beginIndex);
        } else {
            return friendlyClassName.substring(friendlyClassName.lastIndexOf(".") + 1);
        }
    }

    /**
     * 取得简洁的method描述。
     */
    public static String getSimpleMethodSignature(Method method) {
        return getSimpleMethodSignature(method, false, false, false, false);
    }

    /**
     * 取得简洁的method描述。
     */
    public static String getSimpleMethodSignature(Method method, boolean withClassName) {
        return getSimpleMethodSignature(method, false, false, withClassName, false);
    }

    /**
     * 取得简洁的method描述。
     */
    public static String getSimpleMethodSignature(Method method, boolean withModifiers, boolean withReturnType, boolean withClassName, boolean withExceptionType) {
        if (method == null) {
            return null;
        }

        StringBuilder buf = new StringBuilder();

        if (withModifiers) {
            buf.append(Modifier.toString(method.getModifiers())).append(' ');
        }

        if (withReturnType) {
            buf.append(getSimpleClassName(method.getReturnType())).append(' ');
        }

        if (withClassName) {
            buf.append(getSimpleClassName(method.getDeclaringClass())).append('.');
        }

        buf.append(method.getName()).append('(');

        Class<?>[] paramTypes = method.getParameterTypes();

        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];

            buf.append(getSimpleClassName(paramType));

            if (i < paramTypes.length - 1) {
                buf.append(", ");
            }
        }

        buf.append(')');

        if (withExceptionType) {
            Class<?>[] exceptionTypes = method.getExceptionTypes();

            if (!isEmptyArray(exceptionTypes)) {
                buf.append(" throws ");

                for (int i = 0; i < exceptionTypes.length; i++) {
                    Class<?> exceptionType = exceptionTypes[i];

                    buf.append(getSimpleClassName(exceptionType));

                    if (i < exceptionTypes.length - 1) {
                        buf.append(", ");
                    }
                }
            }
        }

        return buf.toString();
    }

    /**
     * 取得指定对象所属的类的package名。
     * <p>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * </p>
     * <p>
     * param object 要查看的对象
     * return package名，如果对象为 <code>null</code> ，则返回<code>""</code>
     */
    public static String getPackageNameForObject(Object object) {
        if (object == null) {
            return EMPTY_STRING;
        }

        return getPackageName(object.getClass().getName());
    }

    /**
     * 取得指定类的package名。
     * <p>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * </p>
     * <p>
     * param clazz 要查看的类
     * return package名，如果类为 <code>null</code> ，则返回<code>""</code>
     */
    public static String getPackageName(Class<?> clazz) {
        if (clazz == null) {
            return EMPTY_STRING;
        }

        return getPackageName(clazz.getName());
    }

    /**
     * 取得指定类名的package名。
     * <p>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * </p>
     * <p>
     * param javaClassName 要查看的类名
     * return package名，如果类名为空，则返回 <code>null</code>
     */
    public static String getPackageName(String javaClassName) {
        String friendlyClassName = toFriendlyClassName(javaClassName, false, null);

        if (friendlyClassName == null) {
            return EMPTY_STRING;
        }

        int i = friendlyClassName.lastIndexOf('.');

        if (i == -1) {
            return EMPTY_STRING;
        }

        return friendlyClassName.substring(0, i);
    }

    // ==========================================================================
    // 取得类名和package名的resource名的方法。
    //
    // 和类名、package名不同的是，resource名符合文件名命名规范，例如：
    // java/lang/String.class
    // com/alibaba/commons/lang
    // etc.
    // ==========================================================================

    /**
     * 取得对象所属的类的资源名。
     * <p>
     * 例如：
     * </p>
     * <p/>
     * <pre>
     * ClassUtil.getResourceNameForObjectClass(&quot;This is a string&quot;) = &quot;java/lang/String.class&quot;
     * </pre>
     * <p>
     * param object 要显示类名的对象
     * return 指定对象所属类的资源名，如果对象为空，则返回<code>null</code>
     */
    public static String getResourceNameForObjectClass(Object object) {
        if (object == null) {
            return null;
        }

        return object.getClass().getName().replace('.', '/') + ".class";
    }

    /**
     * 取得指定类的资源名。
     * <p>
     * 例如：
     * </p>
     * <p/>
     * <pre>
     * ClassUtil.getResourceNameForClass(String.class) = &quot;java/lang/String.class&quot;
     * </pre>
     * <p>
     * param clazz 要显示类名的类
     * return 指定类的资源名，如果指定类为空，则返回<code>null</code>
     */
    public static String getResourceNameForClass(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return clazz.getName().replace('.', '/') + ".class";
    }

    /**
     * 取得指定类的资源名。
     * <p>
     * 例如：
     * </p>
     * <p/>
     * <pre>
     * ClassUtil.getResourceNameForClass(&quot;java.lang.String&quot;) = &quot;java/lang/String.class&quot;
     * </pre>
     * <p>
     * param className 要显示的类名
     * return 指定类名对应的资源名，如果指定类名为空，则返回<code>null</code>
     */
    public static String getResourceNameForClass(String className) {
        if (className == null) {
            return null;
        }

        return className.replace('.', '/') + ".class";
    }

    /**
     * 取得指定对象所属的类的package名的资源名。
     * <p>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * </p>
     * <p>
     * param object 要查看的对象
     * return package名，如果对象为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String getResourceNameForObjectPackage(Object object) {
        if (object == null) {
            return null;
        }

        return getPackageNameForObject(object).replace('.', '/');
    }

    /**
     * 取得指定类的package名的资源名。
     * <p>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * </p>
     * <p>
     * param clazz 要查看的类
     * return package名，如果类为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String getResourceNameForPackage(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return getPackageName(clazz).replace('.', '/');
    }

    /**
     * 取得指定类名的package名的资源名。
     * <p>
     * 对于数组，此方法返回的是数组元素类型的package名。
     * </p>
     * <p>
     * param className 要查看的类名
     * return package名，如果类名为空，则返回 <code>null</code>
     */
    public static String getResourceNameForPackage(String className) {
        if (className == null) {
            return null;
        }

        return getPackageName(className).replace('.', '/');
    }

    // ==========================================================================
    // 取得数组类。
    // ==========================================================================

    /**
     * 取得指定一维数组类.
     * <p>
     * param componentType 数组的基础类
     * return 数组类，如果数组的基类为 <code>null</code> ，则返回 <code>null</code>
     */
    public static Class<?> getArrayClass(Class<?> componentType) {
        return getArrayClass(componentType, 1);
    }

    /**
     * 取得指定维数的 <code>Array</code>类.
     * <p>
     * param dimension     维数，如果小于 <code>0</code> 则看作 <code>0</code>
     * return 如果维数为0, 则返回基类本身, 否则返回数组类，如果数组的基类为 <code>null</code> ，则返回
     * <code>null</code>
     */
    public static Class<?> getArrayClass(Class<?> componentClass, int dimension) {
        if (componentClass == null) {
            return null;
        }

        switch (dimension) {
            case 1:
                return Array.newInstance(componentClass, 0).getClass();

            case 0:
                return componentClass;

            default:

                return Array.newInstance(componentClass, new int[dimension]).getClass();
        }
    }

    // ==========================================================================
    // 取得原子类型或者其wrapper类。
    // ==========================================================================

    /**
     * 取得primitive类。
     * <p>
     * 例如：
     * <p/>
     * <pre>
     * ClassUtil.getPrimitiveType(&quot;int&quot;) = int.class;
     * ClassUtil.getPrimitiveType(&quot;long&quot;) = long.class;
     * </pre>
     * <p/>
     * </p>
     */
    public static Class<?> getPrimitiveType(String name) {
        PrimitiveInfo<?> info = PRIMITIVES.get(name);

        if (info != null) {
            return info.type;
        }

        return null;
    }

    /**
     * 取得primitive类。
     * <p>
     * 例如：
     * <p/>
     * <pre>
     * ClassUtil.getPrimitiveType(Integer.class) = int.class;
     * ClassUtil.getPrimitiveType(Long.class) = long.class;
     * </pre>
     * <p/>
     * </p>
     */
    public static Class<?> getPrimitiveType(Class<?> type) {
        return getPrimitiveType(type.getName());
    }

    /**
     * 取得primitive类型的wrapper。如果不是primitive，则原样返回。
     * <p>
     * 例如：
     * <p/>
     * <pre>
     * ClassUtil.getPrimitiveWrapperType(int.class) = Integer.class;
     * ClassUtil.getPrimitiveWrapperType(int[].class) = int[].class;
     * ClassUtil.getPrimitiveWrapperType(int[][].class) = int[][].class;
     * ClassUtil.getPrimitiveWrapperType(String[][].class) = String[][].class;
     * </pre>
     * <p/>
     * </p>
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getWrapperTypeIfPrimitive(Class<T> type) {
        if (type.isPrimitive()) {
            return ((PrimitiveInfo<T>) PRIMITIVES.get(type.getName())).wrapperType;
        }

        return type;
    }

    /**
     * 取得primitive类型的默认值。如果不是primitive，则返回<code>null</code>。
     * <p>
     * 例如：
     * <p/>
     * <pre>
     * ClassUtil.getPrimitiveDefaultValue(int.class) = 0;
     * ClassUtil.getPrimitiveDefaultValue(boolean.class) = false;
     * ClassUtil.getPrimitiveDefaultValue(char.class) = '\0';
     * </pre>
     * <p/>
     * </p>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPrimitiveDefaultValue(Class<T> type) {
        PrimitiveInfo<T> info = (PrimitiveInfo<T>) PRIMITIVES.get(type.getName());

        if (info != null) {
            return info.defaultValue;
        }

        return null;
    }

    private static final Map<String, PrimitiveInfo<?>> PRIMITIVES = createHashMap();

    static {
        addPrimitive(boolean.class, "Z", Boolean.class, "booleanValue", false);
        addPrimitive(short.class, "S", Short.class, "shortValue", (short) 0);
        addPrimitive(int.class, "I", Integer.class, "intValue", 0);
        addPrimitive(long.class, "J", Long.class, "longValue", 0L);
        addPrimitive(float.class, "F", Float.class, "floatValue", 0F);
        addPrimitive(double.class, "D", Double.class, "doubleValue", 0D);
        addPrimitive(char.class, "C", Character.class, "charValue", '\0');
        addPrimitive(byte.class, "B", Byte.class, "byteValue", (byte) 0);
        addPrimitive(void.class, "V", Void.class, null, null);
    }

    private static <T> void addPrimitive(Class<T> type, String typeCode, Class<T> wrapperType, String unwrapMethod, T defaultValue) {
        PrimitiveInfo<T> info = new PrimitiveInfo<T>(type, typeCode, wrapperType, unwrapMethod, defaultValue);

        PRIMITIVES.put(type.getName(), info);
        PRIMITIVES.put(wrapperType.getName(), info);
    }

    /**
     * 代表一个primitive类型的信息。
     */
    @SuppressWarnings("unused")
    private static class PrimitiveInfo<T> {
        final Class<T> type;
        final String typeCode;
        final Class<T> wrapperType;
        final String unwrapMethod;
        final T defaultValue;

        public PrimitiveInfo(Class<T> type, String typeCode, Class<T> wrapperType, String unwrapMethod, T defaultValue) {
            this.type = type;
            this.typeCode = typeCode;
            this.wrapperType = wrapperType;
            this.unwrapMethod = unwrapMethod;
            this.defaultValue = defaultValue;
        }
    }

    // ==========================================================================
    // 类型匹配。
    // ==========================================================================

    /**
     * 检查一组指定类型 <code>fromClasses</code> 的对象是否可以赋值给另一组类型 <code>classes</code>。
     * <p>
     * 此方法可以用来确定指定类型的参数 <code>object1, object2, ...</code> 是否可以用来调用确定参数类型为
     * <code>class1, class2,
     * ...</code> 的方法。
     * </p>
     * <p>
     * 对于 <code>fromClasses</code> 的每个元素 <code>fromClass</code> 和
     * <code>classes</code> 的每个元素 <code>clazz</code>， 按照如下规则：
     * <ol>
     * <li>如果目标类 <code>clazz</code> 为 <code>null</code> ，总是返回 <code>false</code>
     * 。</li>
     * <li>如果参数类型 <code>fromClass</code> 为 <code>null</code> ，并且目标类型
     * <code>clazz</code> 为非原子类型，则返回 <code>true</code>。 因为 <code>null</code>
     * 可以被赋给任何引用类型。</li>
     * <li>调用 <code>Class.isAssignableFrom</code> 方法来确定目标类 <code>clazz</code>
     * 是否和参数类 <code>fromClass</code> 相同或是其父类、接口，如果是，则返回 <code>true</code>。</li>
     * <li>如果目标类型 <code>clazz</code> 为原子类型，那么根据 <a
     * href="http://java.sun.com/docs/books/jls/">The Java Language
     * Specification</a> ，sections 5.1.1, 5.1.2, 5.1.4定义的Widening Primitive
     * Conversion规则，参数类型 <code>fromClass</code> 可以是任何能扩展成该目标类型的原子类型及其包装类。 例如，
     * <code>clazz</code> 为 <code>long</code> ，那么参数类型可以是 <code>byte</code>、
     * <code>short</code>、<code>int</code>、<code>long</code>、<code>char</code>
     * 及其包装类 <code>java.lang.Byte</code>、<code>java.lang.Short</code>、
     * <code>java.lang.Integer</code>、 <code>java.lang.Long</code> 和
     * <code>java.lang.Character</code> 。如果满足这个条件，则返回 <code>true</code>。</li>
     * <li>不满足上述所有条件，则返回 <code>false</code>。</li>
     * </ol>
     * </p>
     * <p>
     * param classes     目标类型列表，如果是 <code>null</code> 总是返回 <code>false</code>
     * param fromClasses 参数类型列表， <code>null</code> 表示可赋值给任意非原子类型
     * return 如果可以被赋值，则返回 <code>true</code>
     */
    public static boolean isAssignable(Class<?>[] classes, Class<?>[] fromClasses) {
        if (!isArraySameLength(fromClasses, classes)) {
            return false;
        }

        if (fromClasses == null) {
            fromClasses = EMPTY_CLASS_ARRAY;
        }

        if (classes == null) {
            classes = EMPTY_CLASS_ARRAY;
        }

        for (int i = 0; i < fromClasses.length; i++) {
            if (isAssignable(classes[i], fromClasses[i]) == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查指定类型 <code>fromClass</code> 的对象是否可以赋值给另一种类型 <code>clazz</code>。
     * <p>
     * 此方法可以用来确定指定类型的参数 <code>object1, object2, ...</code> 是否可以用来调用确定参数类型
     * <code>class1, class2,
     * ...</code> 的方法。
     * </p>
     * <p>
     * 按照如下规则：
     * <ol>
     * <li>如果目标类 <code>clazz</code> 为 <code>null</code> ，总是返回 <code>false</code>
     * 。</li>
     * <li>如果参数类型 <code>fromClass</code> 为 <code>null</code> ，并且目标类型
     * <code>clazz</code> 为非原子类型，则返回 <code>true</code>。 因为 <code>null</code>
     * 可以被赋给任何引用类型。</li>
     * <li>调用 <code>Class.isAssignableFrom</code> 方法来确定目标类 <code>clazz</code>
     * 是否和参数类 <code>fromClass</code> 相同或是其父类、接口，如果是，则返回 <code>true</code>。</li>
     * <li>如果目标类型 <code>clazz</code> 为原子类型，那么根据 <a
     * href="http://java.sun.com/docs/books/jls/">The Java Language
     * Specification</a> ，sections 5.1.1, 5.1.2, 5.1.4定义的Widening Primitive
     * Conversion规则，参数类型 <code>fromClass</code> 可以是任何能扩展成该目标类型的原子类型及其包装类。 例如，
     * <code>clazz</code> 为 <code>long</code> ，那么参数类型可以是 <code>byte</code>、
     * <code>short</code>、<code>int</code>、<code>long</code>、<code>char</code>
     * 及其包装类 <code>java.lang.Byte</code>、<code>java.lang.Short</code>、
     * <code>java.lang.Integer</code>、 <code>java.lang.Long</code> 和
     * <code>java.lang.Character</code> 。如果满足这个条件，则返回 <code>true</code>。</li>
     * <li>不满足上述所有条件，则返回 <code>false</code>。</li>
     * </ol>
     * </p>
     * <p>
     * param clazz     目标类型，如果是 <code>null</code> 总是返回 <code>false</code>
     * param fromClass 参数类型， <code>null</code> 表示可赋值给任意非原子类型
     * return 如果可以被赋值，则返回 <code>null</code>
     */
    public static boolean isAssignable(Class<?> clazz, Class<?> fromClass) {
        if (clazz == null) {
            return false;
        }

        // 如果fromClass是null，只要clazz不是原子类型如int，就一定可以赋值
        if (fromClass == null) {
            return !clazz.isPrimitive();
        }

        // 如果类相同或有父子关系，当然可以赋值
        if (clazz.isAssignableFrom(fromClass)) {
            return true;
        }

        // 对于原子类型，根据JLS的规则进行扩展
        // 目标class为原子类型时，fromClass可以为原子类型和原子类型的包装类型。
        if (clazz.isPrimitive()) {
            return assignmentTable.get(clazz).contains(fromClass);
        }

        return false;
    }

    private final static Map<Class<?>, Set<Class<?>>> assignmentTable = createHashMap();

    static {
        // boolean可以接受：boolean
        assignmentTable.put(boolean.class, assignableSet(boolean.class));

        // byte可以接受：byte
        assignmentTable.put(byte.class, assignableSet(byte.class));

        // char可以接受：char
        assignmentTable.put(char.class, assignableSet(char.class));

        // short可以接受：short, byte
        assignmentTable.put(short.class, assignableSet(short.class, byte.class));

        // int可以接受：int、byte、short、char
        assignmentTable.put(int.class, assignableSet(int.class, byte.class, short.class, char.class));

        // long可以接受：long、int、byte、short、char
        assignmentTable.put(long.class, assignableSet(long.class, int.class, byte.class, short.class, char.class));

        // float可以接受：float, long, int, byte, short, char
        assignmentTable.put(float.class, assignableSet(float.class, long.class, int.class, byte.class, short.class, char.class));

        // double可以接受：double, float, long, int, byte, short, char
        assignmentTable.put(double.class, assignableSet(double.class, float.class, long.class, int.class, byte.class, short.class, char.class));

    }

    private static Set<Class<?>> assignableSet(Class<?>... types) {
        Set<Class<?>> assignableSet = createHashSet();

        for (Class<?> type : types) {
            assignableSet.add(getPrimitiveType(type));
            assignableSet.add(getWrapperTypeIfPrimitive(type));
        }

        return assignableSet;
    }

    // ==========================================================================
    // 定位class的位置。
    // ==========================================================================

    /**
     * 在class loader中查找class的位置。
     */
    public static String locateClass(Class<?> clazz) {
        return locateClass(clazz.getName(), clazz.getClassLoader());
    }

    /**
     * 在class loader中查找class的位置。
     */
    public static String locateClass(String className) {
        return locateClass(className, null);
    }

    /**
     * 在class loader中查找class的位置。
     */
    public static String locateClass(String className, ClassLoader loader) {
        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
        }

        String classFile = className.replace('.', '/') + ".class";
        URL locationURL = loader.getResource(classFile);
        String location = null;

        if (locationURL != null) {
            location = locationURL.toExternalForm();

            if (location.endsWith(classFile)) {
                location = location.substring(0, location.length() - classFile.length());
            }

            location = location.replaceAll("^(jar|zip):|!/$", EMPTY_STRING);
        }

        return location;
    }

    /**
     * Return the short string name of a Java class in uncapitalized JavaBeans
     * property format. Strips the outer class name in case of an inner class.
     * param clazz the class
     * return the short name rendered in a standard JavaBeans property format
     * see java.beans.Introspector#decapitalize(String)
     */
    public static String getShortNameAsProperty(Class<?> clazz) {
        String shortName = ClassUtil.getShortName(clazz);
        int dotIndex = shortName.lastIndexOf('.');
        shortName = (dotIndex != -1 ? shortName.substring(dotIndex + 1) : shortName);
        return Introspector.decapitalize(shortName);
    }

    /**
     * Get the class name without the qualified package name.
     * param className the className to get the short name for
     * return the class name of the class without the package name
     * throws IllegalArgumentException if the className is empty
     */
    public static String getShortName(String className) {
        int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
        int nameEndIndex = className.indexOf(CGLIB_CLASS_SEPARATOR);
        if (nameEndIndex == -1) {
            nameEndIndex = className.length();
        }
        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
        shortName = shortName.replace(INNER_CLASS_SEPARATOR, PACKAGE_SEPARATOR);
        return shortName;
    }

    /**
     * Get the class name without the qualified package name.
     * param clazz the class to get the short name for
     * return the class name of the class without the package name
     */
    public static String getShortName(Class<?> clazz) {
        return getShortName(getQualifiedName(clazz));
    }

    /**
     * Return the qualified name of the given class: usually simply
     * the class name, but component type class name + "[]" for arrays.
     * param clazz the class
     * return the qualified name of the class
     */
    public static String getQualifiedName(Class<?> clazz) {
        if (clazz.isArray()) {
            return getQualifiedNameForArray(clazz);
        } else {
            return clazz.getName();
        }
    }

    /**
     * Build a nice qualified name for an array:
     * component type class name + "[]".
     * param clazz the array class
     * return a qualified name for the array class
     */
    private static String getQualifiedNameForArray(Class<?> clazz) {
        StringBuilder result = new StringBuilder();
        while (clazz.isArray()) {
            clazz = clazz.getComponentType();
            result.append(ARRAY_SUFFIX);
        }
        result.insert(0, clazz.getName());
        return result.toString();
    }

    /**
     * Return the qualified name of the given method, consisting of
     * fully qualified interface/class name + "." + method name.
     * param method the method
     * return the qualified name of the method
     */
    public static String getQualifiedMethodName(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    /**
     * Return all interfaces that the given instance implements as array,
     * including ones implemented by superclasses.
     * param instance the instance to analyze for interfaces
     * return all interfaces that the given instance implements as array
     */
    public static Class[] getAllInterfaces(Object instance) {
        return getAllInterfacesForClass(instance.getClass());
    }

    /**
     * Return all interfaces that the given class implements as array,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * param clazz the class to analyze for interfaces
     * return all interfaces that the given object implements as array
     */
    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
        return getAllInterfacesForClass(clazz, null);
    }

    /**
     * Return all interfaces that the given class implements as array,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * param clazz the class to analyze for interfaces
     * param classLoader the ClassLoader that the interfaces need to be visible in
     * (may be <code>null</code> when accepting all declared interfaces)
     * return all interfaces that the given object implements as array
     */
    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader) {
        Set<Class> ifcs = getAllInterfacesForClassAsSet(clazz, classLoader);
        return ifcs.toArray(new Class[ifcs.size()]);
    }

    /**
     * Return all interfaces that the given instance implements as Set,
     * including ones implemented by superclasses.
     * param instance the instance to analyze for interfaces
     * return all interfaces that the given instance implements as Set
     */
    public static Set<Class> getAllInterfacesAsSet(Object instance) {
        return getAllInterfacesForClassAsSet(instance.getClass());
    }

    /**
     * Return all interfaces that the given class implements as Set,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * param clazz the class to analyze for interfaces
     * return all interfaces that the given object implements as Set
     */
    public static Set<Class> getAllInterfacesForClassAsSet(Class clazz) {
        return getAllInterfacesForClassAsSet(clazz, null);
    }

    /**
     * Return all interfaces that the given class implements as Set,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * param clazz the class to analyze for interfaces
     * param classLoader the ClassLoader that the interfaces need to be visible in
     * (may be <code>null</code> when accepting all declared interfaces)
     * return all interfaces that the given object implements as Set
     */
    public static Set<Class> getAllInterfacesForClassAsSet(Class clazz, ClassLoader classLoader) {
        if (clazz.isInterface() && isVisible(clazz, classLoader)) {
            return Collections.singleton(clazz);
        }
        Set<Class> interfaces = new LinkedHashSet<Class>();
        while (clazz != null) {
            Class<?>[] ifcs = clazz.getInterfaces();
            for (Class<?> ifc : ifcs) {
                interfaces.addAll(getAllInterfacesForClassAsSet(ifc, classLoader));
            }
            clazz = clazz.getSuperclass();
        }
        return interfaces;
    }

    /**
     * Check whether the given class is visible in the given ClassLoader.
     * param clazz the class to check (typically an interface)
     * param classLoader the ClassLoader to check against (may be <code>null</code>,
     * in which case this method will always return <code>true</code>)
     */
    public static boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
        if (classLoader == null) {
            return true;
        }
        try {
            Class<?> actualClass = classLoader.loadClass(clazz.getName());
            return (clazz == actualClass);
            // Else: different interface class found...
        } catch (ClassNotFoundException ex) {
            // No interface class found...
            return false;
        }
    }

    /**
     * Determine the name of the class file, relative to the containing
     * package: e.g. "String.class"
     * param clazz the class
     * return the file name of the ".class" file
     */
    public static String getClassFileName(Class clazz) {
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
        return className.substring(lastDotIndex + 1) + CLASS_FILE_SUFFIX;
    }

    /**
     * 判断该类型是不是包装类型
     * param clazz
     * return
     */
    public static boolean isBasicClass(Class<?> clazz) {
        boolean isPrimitive = false;
        try {
            if (clazz.isPrimitive() || clazz.isAssignableFrom(String.class)) {
                isPrimitive = true;
            } else {
                isPrimitive = ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
            }
        } catch (Exception e) {
            isPrimitive = false;
        }
        return isPrimitive;
    }
}
