package com.lilin.mwmw.filter;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 这个类是在spring容器创建Bean的时候使用的  用于@ComponentScan注解进行包扫描中@Filter其中的一个类型自定义类型
 * 可以根据
 */
public class MyTypeFilter implements TypeFilter {

    /**
     * MetadataReader  读取当前类的信息
     * MetadataReaderFactory 可以读取其他类的信息
     * @param metadataReader
     * @param metadataReaderFactory
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        //获取注解信息
        AnnotationMetadata annotationMetadata=metadataReader.getAnnotationMetadata();
        //获取类的信息
        ClassMetadata classMetadata=metadataReader.getClassMetadata();
        System.out.println("自定义过滤器"+classMetadata.getClassName());
        //获取自定义包路径下面的所有类的名字  如果满足下面的条件  那么就注册到容器中
        //他会遍历包扫描下面的类  如果满足条件 就注册到IOC容器中
        if(classMetadata.getClassName().contains("controller")){
            return true;
        }
        return false;
    }
}