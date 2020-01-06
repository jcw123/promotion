package com.im.sky.spring.bean;

import com.im.sky.spring.annotation.BeanScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author jiangchangwei
 * @date 2020-1-2 下午 5:30
 **/
@Component
@BeanScan
public class BeanScanBootStrap implements BeanDefinitionRegistryPostProcessor {

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    private static final AnnotationBeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("bean的定义注册");
        if(INITIALIZED.compareAndSet(false, true)) {
            String basePackage = BeanScanBootStrap.class.getPackage().getName();
            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
            provider.addIncludeFilter(new AnnotationTypeFilter(BeanScan.class));
            Set<BeanDefinition> sets = provider.findCandidateComponents(basePackage);
            Iterator<BeanDefinition> iterator = sets.iterator();
            while(iterator.hasNext()) {
                BeanDefinition definition = iterator.next();
                String name = nameGenerator.generateBeanName(definition, registry);
                if(!registry.containsBeanDefinition(name)) {
                    registry.registerBeanDefinition(name, definition);
                }
            }
        }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("注入bean工厂");
    }
}
