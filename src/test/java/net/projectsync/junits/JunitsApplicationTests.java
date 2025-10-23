package net.projectsync.junits;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// This class is not needed. This slows down executing unit test cases as it uses @SpringBootTest
@SpringBootTest                 // tells Spring to load the full application context for the test
class JunitsApplicationTests {

	@Test                       // a smoke test: it verifies that Spring can start the application without any exceptions
	void contextLoads() {
                                // No assertions are needed; if the context fails to start, the test will fail
	}
}

/*
Purpose of JunitsApplicationTests:

1️⃣ Sanity check for your Spring Boot app
-----------------------------------------
@SpringBootTest
class JunitsApplicationTests {

    @Test
    void contextLoads() {
    }
}

- The empty test method `contextLoads()` doesn’t test any business logic.
- Its job is to start the Spring application context and verify that there are no configuration problems (like missing beans, misconfigured properties, or component scan issues).
- If this test passes, it means your Spring Boot application can start successfully.

2️⃣ Why it matters
------------------
- Acts as a “smoke test” for your application setup.
- Helps catch errors such as:
  - Bean injection failures
  - Missing configuration files
  - Circular dependencies
  - Problems with `@ComponentScan`

3️⃣ When to expand
------------------
- Once this test passes, you typically write real unit tests or integration tests for your services, controllers, repositories, etc.
- This class is basically a safety net for the foundation of your application.

In short: its purpose is to make sure your Spring Boot application context can start without crashing.
 */