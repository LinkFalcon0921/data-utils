package data.utils.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface ICollectionUtils {
    interface Mapper {
        static <T, U> Collection<U> mapCollectionBy(
                Supplier<Collection<T>> listSupplier,
                Function<T, U> mapDataFunc) {
            return listSupplier.get().stream()
                    .map(mapDataFunc)
                    .collect(Collectors.toList());
        }

        static <T, U> Collection<U> mapCollectionSortedBy(
                Supplier<Collection<T>> listSupplier,
                Function<T, U> mapDataFunc,
                Comparator<U> mappedComparator) {
            return listSupplier.get().stream()
                    .map(mapDataFunc)
                    .sorted(mappedComparator)
                    .collect(Collectors.toList());
        }
    }

    interface Filter {
        static <C extends Collection<V>, V> Predicate<V> contains(C collection) {
            return collection::contains;
        }

        static <C extends Collection<V>, V> Predicate<V> notContains(C collection) {
            return contains(collection).negate();
        }

        /**
         * Return true if the given {@link C} collection not contains all the values.
         */
        static <C extends Collection<V>, V> Predicate<C> notContainsAll(C c) {
            return t -> t.stream().allMatch(ICollectionUtils.Filter.notContains(c));
        }

        /**
         * Return true if the given {@link C} collection not contains any of the values.
         */
        static <C extends Collection<V>, V> Predicate<C> hasDifferences(C c) {
            return t -> t.stream().anyMatch(ICollectionUtils.Filter.notContains(c));
        }
    }
}
