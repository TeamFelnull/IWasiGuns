package dev.felnull.iwasi.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IWGUtils {
    /**
     * 一番多くコレクションに含まれている要素を取得
     *
     * @param collection コレクション
     * @param <T>        型
     * @return 一番多く含まれて要素
     */
    public static <T> Optional<T> getMostContain(Collection<T> collection) {
        var counts = collection.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return counts.entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue)).map(Map.Entry::getKey);
    }
}
