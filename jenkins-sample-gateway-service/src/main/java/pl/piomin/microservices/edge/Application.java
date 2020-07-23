package pl.piomin.microservices.edge;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZuulProxy
@RestController
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
	
	/* 
	 * The configuration of Spring Cloud Sleuth is very simple. 
	 * We only have to add spring-cloud-starter-sleuth dependency to pom.xml 
	 * and declare sampler @Bean. 
	 * In the sample I declared AlwaysSampler that exports every span, 
	 * but there is also an other other option – 
	 * PercentageBasedSampler that samples a fixed fraction of spans.
	 * */

	@Bean
	public AlwaysSampler defaultSampler() {
	  return new AlwaysSampler();
	}
	
}
