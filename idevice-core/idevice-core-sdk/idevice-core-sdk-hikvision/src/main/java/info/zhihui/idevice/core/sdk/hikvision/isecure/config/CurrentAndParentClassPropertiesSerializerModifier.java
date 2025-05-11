package info.zhihui.idevice.core.sdk.hikvision.isecure.config;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CurrentAndParentClassPropertiesSerializerModifier extends BeanSerializerModifier {
    private static final Set<String> IGNORE_PROPERTIES = Set.of("responseClass");
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        return beanProperties.stream()
                .filter(writer -> {
                    // 获取当前属性的成员（方法或字段）的声明类
                    Class<?> declaringClass = writer.getMember().getDeclaringClass();
                    // 仅保留声明类与当前Bean类相同的属性
                    return declaringClass.equals(beanDesc.getBeanClass()) || declaringClass.equals(beanDesc.getBeanClass().getSuperclass());
                }).filter(writer -> !IGNORE_PROPERTIES.contains(writer.getName()))
                .collect(Collectors.toList());
    }
}