package com.fpt.core;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.Assert;

@SpringBootApplication(scanBasePackages = "com.fpt")
public class TodoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TodoApplication.class)
                .beanNameGenerator(beanNameGenerator())
                .run(args);
    }

    static BeanNameGenerator beanNameGenerator() {
        return new AnnotationBeanNameGenerator() {
            @Override
            protected String buildDefaultBeanName(BeanDefinition definition) {
                String beanClassName = definition.getBeanClassName();
                Assert.state(beanClassName != null, "No bean class name set");
                return beanClassName;
            }
        };
    }
}
