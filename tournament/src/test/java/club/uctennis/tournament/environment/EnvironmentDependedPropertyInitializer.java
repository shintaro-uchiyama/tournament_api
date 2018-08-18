package club.uctennis.tournament.environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

public class EnvironmentDependedPropertyInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  private static final String PROFILES_INCLUDE = "spring.profiles.include";

  private Logger logger = LoggerFactory.getLogger(EnvironmentDependedPropertyInitializer.class);


  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    ConfigurableEnvironment environment = applicationContext.getEnvironment();
    MutablePropertySources sources = environment.getPropertySources();

    String[] activeProfiles = environment.getActiveProfiles();

    List<String> resourceTargets = new ArrayList<>(activeProfiles.length + 1);
    for (String profileName : activeProfiles) {
      resourceTargets
          .add(String.format("classpath*:properties/application-%s.properties", profileName));
    }
    resourceTargets.add("classpath*:properties/application-test.properties");
    resourceTargets.add("classpath*:properties/application.properties");
    for (String target : resourceTargets) {
      try {
        for (Resource resource : applicationContext.getResources(target)) {
          try {
            sources.addLast(new ResourcePropertySource(resource));
            logger.debug("Load properties file: {}", resource);
          } catch (IOException ex) {
            logger.info("Failed to load properties file ({}).", resource);
          }
        }
      } catch (IOException ex) {
        logger.info("Failed to get resources ({}).", target);
      }
    }

    String[] includes = environment.getProperty(PROFILES_INCLUDE, "").split(",");
    for (String include : includes) {
      String profileName = include.trim();
      if (!profileName.isEmpty()) {
        environment.addActiveProfile(profileName);
        logger.debug("Add profile '{}' to active profile.", profileName);
      }
    }
  }
}
