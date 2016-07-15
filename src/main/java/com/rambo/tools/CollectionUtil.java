package com.rambo.tools;

import org.apache.commons.lang.ArrayUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public final class CollectionUtil {
    public static <T> ArrayList<T> createArrayList() {
        return new ArrayList();
    }

    public static <T> ArrayList<T> createArrayList(int initialCapacity) {
        return new ArrayList(initialCapacity);
    }

    public static <T> ArrayList<T> createArrayList(Iterable<? extends T> c) {
        ArrayList<T> list;
        if ((c instanceof Collection)) {
            list = new ArrayList((Collection) c);
        } else {
            list = new ArrayList();
            iterableToCollection(c, list);
            list.trimToSize();
        }
        return list;
    }

    public static <T, V extends T> ArrayList<T> createArrayList(V... args) {
        if ((args == null) || (args.length == 0)) {
            return new ArrayList();
        }
        ArrayList<T> list = new ArrayList(args.length);
        for (V v : args) {
            list.add(v);
        }
        return list;
    }

    public static <T> LinkedList<T> createLinkedList() {
        return new LinkedList();
    }

    public static <T> LinkedList<T> createLinkedList(Iterable<? extends T> c) {
        LinkedList<T> list = new LinkedList();

        iterableToCollection(c, list);

        return list;
    }

    public static <T, V extends T> LinkedList<T> createLinkedList(V... args) {
        LinkedList<T> list = new LinkedList();
        if (args != null) {
            for (V v : args) {
                list.add(v);
            }
        }
        return list;
    }

    public static <T> List<T> asList(T... args) {
        if ((args == null) || (args.length == 0)) {
            return Collections.emptyList();
        }
        return Arrays.asList(args);
    }

    public static <K, V> HashMap<K, V> createHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> createHashMap(int initialCapacity) {
        return new HashMap(initialCapacity);
    }

    public static <K, V> LinkedHashMap<K, V> createLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> createLinkedHashMap(int initialCapacity) {
        return new LinkedHashMap(initialCapacity);
    }

    public static <K, V> TreeMap<K, V> createTreeMap() {
        return new TreeMap();
    }

    public static <K, V> TreeMap<K, V> createTreeMap(Comparator<? super K> comparator) {
        return new TreeMap(comparator);
    }

    public static <K, V> ConcurrentHashMap<K, V> createConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

    public static <T> HashSet<T> createHashSet() {
        return new HashSet();
    }

    public static <T, V extends T> HashSet<T> createHashSet(V... args) {
        if ((args == null) || (args.length == 0)) {
            return new HashSet();
        }
        HashSet<T> set = new HashSet(args.length);
        for (V v : args) {
            set.add(v);
        }
        return set;
    }

    public static <T> HashSet<T> createHashSet(Iterable<? extends T> c) {
        HashSet<T> set;
        if ((c instanceof Collection)) {
            set = new HashSet((Collection) c);
        } else {
            set = new HashSet();
            iterableToCollection(c, set);
        }
        return set;
    }

    public static <T> LinkedHashSet<T> createLinkedHashSet() {
        return new LinkedHashSet();
    }

    public static <T, V extends T> LinkedHashSet<T> createLinkedHashSet(V... args) {
        if ((args == null) || (args.length == 0)) {
            return new LinkedHashSet();
        }
        LinkedHashSet<T> set = new LinkedHashSet(args.length);
        for (V v : args) {
            set.add(v);
        }
        return set;
    }

    public static <T> LinkedHashSet<T> createLinkedHashSet(Iterable<? extends T> c) {
        LinkedHashSet<T> set;
        if ((c instanceof Collection)) {
            set = new LinkedHashSet((Collection) c);
        } else {
            set = new LinkedHashSet();
            iterableToCollection(c, set);
        }
        return set;
    }

    public static <T> TreeSet<T> createTreeSet() {
        return new TreeSet();
    }

    public static <T, V extends T> TreeSet<T> createTreeSet(V... args) {
        return createTreeSet(null, args);
    }

    public static <T> TreeSet<T> createTreeSet(Iterable<? extends T> c) {
        return createTreeSet(null, c);
    }

    public static <T> TreeSet<T> createTreeSet(Comparator<? super T> comparator) {
        return new TreeSet(comparator);
    }

    public static <T, V extends T> TreeSet<T> createTreeSet(Comparator<? super T> comparator, V... args) {
        TreeSet<T> set = new TreeSet(comparator);
        if (args != null) {
            for (V v : args) {
                set.add(v);
            }
        }
        return set;
    }

    public static <T> TreeSet<T> createTreeSet(Comparator<? super T> comparator, Iterable<? extends T> c) {
        TreeSet<T> set = new TreeSet(comparator);

        iterableToCollection(c, set);

        return set;
    }

    private static <T> void iterableToCollection(Iterable<? extends T> c, Collection<T> list) {
        for (T element : c) {
            list.add(element);
        }
    }

    public static boolean isEmpty(Collection collection) {
        return (collection == null) || (collection.isEmpty());
    }

    public static boolean isEmpty(Map map) {
        return (map == null) || (map.isEmpty());
    }

    public static String[] toNoNullStringArray(Collection collection) {
        if (collection == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return toNoNullStringArray(collection.toArray());
    }

    static String[] toNoNullStringArray(Object[] array) {
        ArrayList list = new ArrayList(array.length);
        for (int i = 0; i < array.length; i++) {
            Object e = array[i];
            if (e != null) {
                list.add(e.toString());
            }
        }
        return (String[]) list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
