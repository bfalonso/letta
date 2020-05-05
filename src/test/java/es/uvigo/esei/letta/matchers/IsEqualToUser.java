package es.uvigo.esei.letta.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import es.uvigo.esei.letta.entities.User;

public class IsEqualToUser extends IsEqualToEntity<User> {
	public IsEqualToUser(User entity) {
		super(entity);
	}

	@Override
	protected boolean matchesSafely(User actual) {
		this.clearDescribeTo();
		
		if (actual == null) {
			this.addTemplatedDescription("actual", expected.toString());
			return false;
		} else {
			return checkAttribute("login", User::getLogin, actual)
				&& checkAttribute("password", User::getPassword, actual);
		}
	}

	/**
	 * Factory method that creates a new {@link IsEqualToEntity} matcher with
	 * the provided {@link Person} as the expected value.
	 * 
	 * @param user the expected person.
	 * @return a new {@link IsEqualToEntity} matcher with the provided
	 * {@link Person} as the expected value.
	 */
	@Factory
	public static IsEqualToUser equalsToUser(User user) {
		return new IsEqualToUser(user);
	}
	

	@Factory
	public static Matcher<Iterable<? extends User>> containsEventsInAnyOrder(User ... users) {
		return containsEntityInAnyOrder(IsEqualToUser::equalsToUser, users);
	}

}
