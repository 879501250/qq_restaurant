package com.qq.qqrestaurant.config;

import com.qq.qqrestaurant.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 设置资源映射
     * 若前端资源未放在static目录下而直接放在resources下则需如此配置才能访问到
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
//        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/static/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/static/front/");
    }

    /**
     * 扩展MVC框架的消息转换器
     * 转换器是将Controller层的返回的结果进行转换返回前端
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器，底层使用Jackson将Java对象转换为Json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将上面的消息转换器对象追加到mvc框架的转换器集合中，并优先使用
        converters.add(0, messageConverter);
    }
}
