package site.klol.batch001;

import org.junit.jupiter.api.Test;

import java.util.Optional;


class Batch001ApplicationTests {
	@Test
	void contextLoads() {
		Object o = null;
		Object o1 = Optional.ofNullable(o)
			.orElse(null).toString();

		if (o1 != null) {

			}

	}

}
