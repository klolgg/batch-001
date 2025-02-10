package site.klol.batch;

import org.junit.jupiter.api.Test;

import java.util.Optional;


class BatchApplicationTests {
	@Test
	void contextLoads() {
		Object o = null;
		Object o1 = Optional.ofNullable(o)
			.orElse(null).toString();

		if (o1 != null) {

			}

	}

}
