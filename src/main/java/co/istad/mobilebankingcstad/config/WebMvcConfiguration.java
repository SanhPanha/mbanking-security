package co.istad.mobilebankingcstad.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Value("${file.storage-dir}")
    String fileStorageLocation;
    @Value("${file.client-dir}")
    String clientLocation;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(clientLocation)
                .addResourceLocations("file:"+fileStorageLocation);

    }
}
