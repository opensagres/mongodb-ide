package fr.opensagres.nosql.ide.core.utils;

import java.util.Collection;

public class CollectionUtils {

	public static <E> E getFirstElement(Collection<E> c) {
		for (E e : c) {
			return e;
		}
		return null;
	}
}
