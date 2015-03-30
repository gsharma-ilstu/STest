package cucumber.features;


import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@Cucumber.Options(
		format={"pretty","html:target/test-reports"},
		/*format = {"pretty", "html:target/html/","json:target/json/"},*/
		features = {"resources"}
		)
public class CucumberRunner {
	
}
