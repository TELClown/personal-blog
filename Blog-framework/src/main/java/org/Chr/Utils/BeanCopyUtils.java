package org.Chr.Utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils(){}

    public static <V> V copyBean(Object source, Class<V> clazz){
        V target = null;
        try {
            target = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source,target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }
    public static <V,T> List<V> copyBeanList(List<T> source, Class<V> clazz){
        return source.stream()
                .map(item -> copyBean(item,clazz))
                .collect(Collectors.toList());
    }
}
