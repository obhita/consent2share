package com.feisystems.polrep.web.v1;

import java.util.function.Predicate;

import org.mockito.ArgumentMatcher;

public class ArgumentMatchers {
	public static <T> ArgumentMatcher<T> matching(Predicate<T> matches) {
		return new ArgumentMatcher<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public boolean matches(Object argument) {
				return matches.test((T) argument);
			}
		};
	}
}
