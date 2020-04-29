package es.uvigo.esei.letta.matchers;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public abstract class IsEqualToEntity<T> extends TypeSafeMatcher<T> {
	protected final T expected;

	private Consumer<Description> describeTo;
	
	public IsEqualToEntity(final T entity) {
		this.expected = requireNonNull(entity);
	}

	@Override
	public void describeTo(final Description description) {
		if (this.describeTo != null)
            this.describeTo.accept(description);
	}

	protected void addTemplatedDescription(final String attribute, final Object expected) {
		this.describeTo = d -> d.appendText(String.format(
			"%s entity with value '%s' for %s",
			this.expected.getClass().getSimpleName(),
			expected, attribute
		));
	}

	protected void addMatcherDescription(final Matcher<?> matcher) {
		this.describeTo = matcher::describeTo;
	}

	protected void clearDescribeTo() {
		this.describeTo = null;
	}
	
	protected <R> boolean checkAttribute(
		final String attribute,
		final Function<T, R> getter, final T actual,
		final Function<R, Matcher<R>> matcherFactory
	) {
		final R expectedValue = getter.apply(this.expected);
		final R actualValue = getter.apply(actual);

		if (expectedValue == null && actualValue == null) {
			return true;
		} else if (expectedValue == null || actualValue == null) {
			this.addTemplatedDescription(attribute, expectedValue);
			return false;
		} else {
			final Matcher<R> matcher = matcherFactory.apply(expectedValue);
			if (matcher.matches(actualValue)) {
				return true;
			} else {
				this.addMatcherDescription(matcher);
				
				return false;
			}
		}
	}

	protected <R> boolean checkArrayAttribute(
		final String attribute,
		final Function<T, R[]> getter, final T actual,
		final Function<R[], Matcher<Iterable<? extends R>>> matcherFactory
	) {
		final R[] expectedValue = getter.apply(this.expected);
		final R[] actualValue = getter.apply(actual);

		if (expectedValue == null && actualValue == null) {
			return true;
		} else if (expectedValue == null || actualValue == null) {
			this.addTemplatedDescription(attribute, expectedValue);
			return false;
		} else {
			final Matcher<Iterable<? extends R>> matcher =
				matcherFactory.apply(expectedValue);
			
			if (matcher.matches(asList(actualValue))) {
				return true;
			} else {
				this.addMatcherDescription(matcher);
				
				return false;
			}
		}
	}

	protected <R> boolean checkIterableAttribute(
		final String attribute,
		final Function<T, Iterable<R>> getter, final T actual,
		final Function<Iterable<R>, Matcher<Iterable<? extends R>>> matcherFactory
	) {
		final Iterable<R> expectedValue = getter.apply(this.expected);
		final Iterable<R> actualValue = getter.apply(actual);

		if (expectedValue == null && actualValue == null) {
			return true;
		} else if (expectedValue == null || actualValue == null) {
			this.addTemplatedDescription(attribute, expectedValue);
			return false;
		} else {
			final Matcher<Iterable<? extends R>> matcher =
				matcherFactory.apply(expectedValue);
			
			if (matcher.matches(actualValue)) {
				return true;
			} else {
				this.addMatcherDescription(matcher);
				
				return false;
			}
		}
	}

	protected <R> boolean checkAttribute(
		final String attribute, final Function<T, R> getter, final T actual
	) {
		final R expectedValue = getter.apply(this.expected);
		final R actualValue = getter.apply(actual);

		if (expectedValue == null && actualValue == null) {
			return true;
		} else if (expectedValue == null || !expectedValue.equals(actualValue)) {
			this.addTemplatedDescription(attribute, expectedValue);
			return false;
		} else {
            return true;
		}
	}

	protected <R> boolean checkArrayAttribute(
		final String attribute, final Function<T, R[]> getter, final T actual
	) {
		final R[] expectedValue = getter.apply(this.expected);
		final R[] actualValue = getter.apply(actual);

		if (expectedValue == null && actualValue == null) {
			return true;
		} else if (expectedValue == null || actualValue == null) {
			this.addTemplatedDescription(attribute, expectedValue == null ? "null" : Arrays.toString(expectedValue));
			return false;
		} else if (!Arrays.equals(expectedValue, actualValue)) {
			this.addTemplatedDescription(attribute, Arrays.toString(expectedValue));
			return false;
		} else
            return true;
	}

	protected boolean checkIntArrayAttribute(
		final String attribute, final Function<T, int[]> getter, final T actual
	) {
		final int[] expectedValue = getter.apply(this.expected);
		final int[] actualValue = getter.apply(actual);

		if (expectedValue == null && actualValue == null) {
			return true;
		} else if (expectedValue == null || actualValue == null) {
			this.addTemplatedDescription(attribute, expectedValue == null ? "null" : Arrays.toString(expectedValue));
			return false;
		} else if (!Arrays.equals(expectedValue, actualValue)) {
			this.addTemplatedDescription(attribute, Arrays.toString(expectedValue));
			return false;
		} else
            return true;
	}

	@SafeVarargs
	protected static <T> Matcher<Iterable<? extends T>> containsEntityInAnyOrder(
		final Function<T, Matcher<? super T>> converter, final T ... entities
	) {
		final Collection<Matcher<? super T>> entitiesMatchers = stream(entities)
			.map(converter)
		.collect(toList());

		return containsInAnyOrder(entitiesMatchers);
	}

	protected static <T> Matcher<Iterable<? extends T>> containsEntityInAnyOrder(
		final Function<T, Matcher<? super T>> converter, final Iterable<T> entities
	) {
		final Collection<Matcher<? super T>> entitiesMatchers =
			StreamSupport.stream(entities.spliterator(), false)
				.map(converter)
			.collect(toList());

		return containsInAnyOrder(entitiesMatchers);
	}

    @SafeVarargs
    protected static <T> Matcher<Iterable<? extends T>> containsEntityInOrder(
        final Function<T, Matcher<? super T>> converter, final T ... entities
    ) {
        return contains(stream(entities).map(converter).collect(toList()));
    }

    protected static <T> Matcher<Iterable<? extends T>> containsEntityInOrder(
        final Function<T, Matcher<? super T>> converter, final Iterable<T> entities
    ) {
        final List<Matcher<? super T>> matchersList =
        	StreamSupport.stream(entities.spliterator(), false)
        		.map(converter)
        	.collect(toList());
        
		return contains(matchersList);
    }
}
