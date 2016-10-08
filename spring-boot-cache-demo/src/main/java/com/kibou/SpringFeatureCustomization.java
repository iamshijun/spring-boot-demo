package com.kibou;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFeatureCustomization {

	@EnableCaching
	@ConditionalOnProperty(prefix = "spring.test", value = "cache", havingValue = "true", matchIfMissing = false)
	public static class EnableCachingConfiguration {

	}
}
