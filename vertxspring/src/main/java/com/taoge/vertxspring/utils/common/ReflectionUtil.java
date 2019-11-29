package com.taoge.vertxspring.utils.common;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.stream.Stream;

/**
 * reflections反射工具
 * Created by 滔哥 on 2019/11/13
 */
public class ReflectionUtil {

    public static Reflections getReflections(String... packageAddress) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        FilterBuilder filterBuilder = new FilterBuilder();
        Stream.of(packageAddress).forEach(str -> configurationBuilder.addUrls(ClasspathHelper.forPackage(str.trim())));
        filterBuilder.includePackage(packageAddress);
        configurationBuilder.filterInputsBy(filterBuilder);
        return new Reflections(configurationBuilder);
    }

}
