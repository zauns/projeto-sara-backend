// package sara.projeto.saraEmprega.config;

// import org.springframework.beans.factory.annotation.Value; // <--- Importante adicionar este import
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfiguration implements WebMvcConfigurer {

//     @Value("${FRONTEND_URL:http://localhost:3000}")
//     private String frontendUrl;

//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//             .allowedOrigins(frontendUrl, "http://localhost:3000")
//             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
//     }
// }
