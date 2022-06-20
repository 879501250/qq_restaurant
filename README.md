# <center>qq_restaurant
### - 静态资源访问
1. /resources/static
2. /resources/public
3. /resources/resources
4. /resources/META-INF/resources
默认以上路径可直接访问
其他位置需配置静态资源映射

```
// 设置资源映射，直接访问/static目录下文件
protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
}
```

### - 拦截请求
若须对某些资源进行拦截则需配置拦截器

```
public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String uri = request.getRequestURI();

    // 不需要处理的路径，若匹配直接放行
    String[] urls = new String[]{
            "/front/**"
    };
    if (判断是否匹配) {
        filterChain.doFilter(request, response);
        return;
    }
    
    // 否则通过输出流方式向客户端页面相应数据，前端可设置检测到 NOTLOGIN 则自动返回登录页
    response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    return;
}
```

### - MVC消息转换器
若后端向前端传Long等数据如由雪花算法生成的id，可能由于精度问题导致数据错误
因此最好将java对象转化为Json字符串传递，字符串不存在精度问题
#### 1.直接在传递的属性上加注解

```
// 修复long型数据 传到前端js 精度问题
@JsonSerialize(using = ToStringSerializer.class)
private Long id;
```

但若需转换的属性过多，则每个都要配置，且转换类型可能需要自定义Class，因此不推荐
#### 2.使用MVC框架的消息转化器进行转化
先配置对象映射器，将指定属性转化为需要的类型

```
/**
 * 对象映射器:基于jackson将Java对象转为json，或者将json转为Java对象
 * 将JSON解析为Java对象的过程称为 [从JSON反序列化Java对象]
 * 从Java对象生成JSON的过程称为 [序列化Java对象到JSON]
 */
public class JacksonObjectMapper extends ObjectMapper {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper() {
        super();
        //收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        //反序列化时，属性不存在的兼容处理
        this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))

                .addSerializer(BigInteger.class, ToStringSerializer.instance)
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        //注册功能模块 例如，可以添加自定义序列化器和反序列化器
        this.registerModule(simpleModule);
    }
}
```

再利用消息转化器进行转化并返回

```
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
```
