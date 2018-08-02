package club.uctennis.tournament.utils;

import java.util.Map;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class VelocityUtils {

  public String merge(String templateLocation, Map<String, Object> templateVariables) {
    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    velocityEngine.setProperty("classpath.resource.loader.class",
        ClasspathResourceLoader.class.getName());
    velocityEngine.init();
    return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8",
        templateVariables);
  }
}
