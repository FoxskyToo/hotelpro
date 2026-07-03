package duoc.hotelpro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI hotelProOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HotelPro API")
                        .version("1.0")
                        .description("Documentación técnica de los microservicios del sistema HotelPro"));
    }
}