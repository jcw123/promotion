package com.im.sky.spring;

import com.im.sky.spring.annotation.RPCBean;
import com.im.sky.spring.model.People;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author jiangchangwei
 * @date 2020-9-17 下午 5:35
 **/
@Component
public class DefaultBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final AtomicBoolean RUN = new AtomicBoolean();

    private final AnnotationBeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if(!RUN.compareAndSet(false, true)) {
            return;
        }
        String basePackage = People.class.getPackage().getName();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(RPCBean.class));
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);
        if(beanDefinitions != null) {
            for(BeanDefinition beanDefinition : beanDefinitions) {
                String className = beanDefinition.getBeanClassName();
                String name = null;
                try {
                    Class clz = Class.forName(className);
                    RPCBean rpcBean = (RPCBean) clz.getAnnotation(RPCBean.class);
                    name = rpcBean.name();
                    if(name.length() == 0) {
                       name = clz.getSimpleName();
                       name = name.substring(0, 1).toLowerCase() + name.substring(1);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(name == null) {
                    name = nameGenerator.generateBeanName(beanDefinition, registry);
                }
                registry.registerBeanDefinition(name, beanDefinition);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(DefaultBeanDefinitionRegistryPostProcessor.class.getName() + "execute postProcessBeanFactory");
    }
}
