package es.uvigo.esei.letta;
import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import es.uvigo.esei.letta.rest.EventsResource;

@ApplicationPath("/rest/*")
public class LettaApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return Stream.of(
			EventsResource.class
		).collect(toSet());
	}
	
	@Override
	public Map<String, Object> getProperties() {
		// Activates JSON automatic conversion in JAX-RS
		return Collections.singletonMap(
			"com.sun.jersey.api.json.POJOMappingFeature", true
		);
	}
}
