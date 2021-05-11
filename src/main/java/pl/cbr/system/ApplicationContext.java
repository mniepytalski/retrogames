package pl.cbr.system;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import pl.cbr.system.config.ConfigFile;
import pl.cbr.system.config.ConfigInterface;
import pl.cbr.system.config.SystemConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationContext {

    private final Map<String, ConfigInterface> configurationClasses = new HashMap<>();

    private static ApplicationContext applicationContext;

    public static ApplicationContext getInstance(String packagePath) {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
            applicationContext.start(packagePath);
        }
        return applicationContext;
    }

    public Object getConfiguration(String objectName) {
        return configurationClasses.get(objectName);
    }

    public void start(String packagePath) {
        final Reflections reflections = new Reflections(packagePath);
        SystemConfiguration<ConfigInterface> systemConfiguration = new SystemConfiguration<>();

        log.info("Configuration Classes for package: {}", packagePath);
        Set<Class<?>> allConfigTypes = reflections.getTypesAnnotatedWith(ConfigFile.class);
        for (Class<?> configClass : allConfigTypes) {
            try {
                Optional<Object> objectOptional = createConfigClassForName(configClass);
                if ( objectOptional.isPresent()) {
                    configurationClasses.put(configClass.getTypeName(), (ConfigInterface) objectOptional.get());
                    systemConfiguration.loadConfiguration((ConfigInterface) objectOptional.get());
                    log.info("CLASS: {}", objectOptional.get().toString());
                }
            } catch (ClassNotFoundException e) {
                log.error("Create class: {}",configClass.getTypeName());
            }
        }

        log.info("System Classes for package: {}", packagePath);
        Set<Class<?>> allTypes = reflections.getTypesAnnotatedWith(SystemComponent.class);
        for (Class<?> configClass : allTypes) {
            log.info(""+configClass.getTypeName());
        }
    }

    private Optional<Object> createConfigClassForName(Class<?> configClass) throws ClassNotFoundException {
        Class<?> classForName = Class.forName(configClass.getTypeName());
        Constructor<?>[] constructors = classForName.getConstructors();
        if ( constructors.length>0) {
            try {
                return Optional.of(constructors[0].newInstance());
            } catch (InstantiationException|IllegalAccessException|InvocationTargetException e) {
                log.error("error,",e);
            }
        }
        return Optional.empty();
    }
}
