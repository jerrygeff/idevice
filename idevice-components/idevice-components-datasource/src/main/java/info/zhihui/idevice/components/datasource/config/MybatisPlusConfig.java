package info.zhihui.idevice.components.datasource.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import info.zhihui.idevice.common.factory.YmlPropertySourceFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;

@MapperScan(basePackages = "${mybatis-plus.repositoryPackage}")
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@PropertySource(value = "classpath:common-mybatis.yml", factory = YmlPropertySourceFactory.class)
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        return interceptor;
    }

}
