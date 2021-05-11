package pl.cbr.system.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class SystemConfiguration<T> {

    public void loadConfiguration(T configurationObject) {
        ConfigUtil configUtil = new ConfigUtil();

        Optional<Annotation> annotationOpt = Arrays.stream(configurationObject.getClass().getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(ConfigFile.class)).findFirst();
        if (annotationOpt.isPresent()) {
            ConfigFile configFile = (ConfigFile) annotationOpt.get();
            Properties properties = configUtil.getProperties(configFile.value());
            Field[] fields = configurationObject.getClass().getDeclaredFields();
            for (Field field : fields) {
                Annotation[] annotationsArray = field.getDeclaredAnnotations();
                Optional<Annotation> annotationFieldOpt = Arrays.stream(annotationsArray)
                        .filter(annotation -> annotation.annotationType().equals(ConfigKey.class)).findFirst();
                annotationFieldOpt.ifPresent(annotation -> setValue(configurationObject, field,
                        configFile.prefix()+((ConfigKey) annotation).value(), properties));
            }
        }
    }

    private void setValue(T configObject, Field field, String key,Properties properties) {
        Object value = properties.get(key);
        if ( value==null )
            return;
        try {
            Method method = configObject.getClass().getMethod("set" + StringUtils.capitalize(field.getName()), getClassForType(field));
            if (method.getParameterCount() == 1 ) {
                Class<?>[] methodTypes = method.getParameterTypes();
                if ( methodTypes[0].getTypeName().equals("java.lang.String") ) {
                    method.invoke(configObject, value);
                }
                invokeNumeric(method, value, methodTypes, configObject);
                invokeBoolean(method, value, methodTypes, configObject);
            }
        } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException|IllegalArgumentException e) {
            log.error("setting parameter:{} error",key, e);
        }
    }

    private void invokeNumeric(Method method, Object value, Class<?>[] methodTypes, T configObject) throws InvocationTargetException, IllegalAccessException {
        if (StringUtils.isNumeric(value.toString())) {
            if (methodTypes[0].getTypeName().equals("java.lang.Long") || methodTypes[0].getTypeName().equals("long")) {
                method.invoke(configObject, Long.parseLong(value.toString()));
            }
            if (methodTypes[0].getTypeName().equals("java.lang.Integer") || methodTypes[0].getTypeName().equals("int")) {
                method.invoke(configObject, Integer.parseInt(value.toString()));
            }
        }
    }

    private void invokeBoolean(Method method, Object value, Class<?>[] methodTypes, T configObject) throws InvocationTargetException, IllegalAccessException {
        if ( methodTypes[0].getTypeName().equals("java.lang.Boolean") || methodTypes[0].getTypeName().equals("boolean")) {
            method.invoke(configObject, Boolean.parseBoolean(value.toString()));
        }
    }

    private Class<?> getClassForType(Field field) {
        switch(((Class<?>) field.getGenericType()).getName()) {
            case "java.lang.Long":
                return Long.class;
            case "long":
                return long.class;
            case "java.lang.Integer":
                return Integer.class;
            case "int":
                return int.class;
            case "java.lang.Boolean":
                return Boolean.class;
            case "boolean":
                return boolean.class;
            default:
                return String.class;
        }
    }
}
