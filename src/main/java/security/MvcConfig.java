//package security;
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.format.datetime.DateFormatter;
//import org.springframework.format.datetime.DateFormatterRegistrar;
//import org.springframework.format.support.DefaultFormattingConversionService;
//import org.springframework.format.support.FormattingConversionService;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class MvcConfig extends WebMvcConfigurationSupport {
//
//    @Override
//    public FormattingConversionService mvcConversionService() {
//        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);
//        DateFormatterRegistrar registrar = new DateFormatterRegistrar();
//        registrar.setFormatter(new DateFormatter("dd.MM.yyyy"));
//        registrar.registerFormatters(conversionService);
//        return conversionService;
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        //registry.addViewController("/").setViewName("index");
//        registry.addViewController("/index").setViewName("index");
//        registry.addViewController("/login").setViewName("login");
//    }
//}
