package com.pecassystem.pecas.config;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        // 1. Tenta encontrar o recurso exato (ex: _next/..., logo-porsche.png,
                        // index.html)
                        Resource requestedResource = location.createRelative(resourcePath);
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }

                        // Remove barras no final para padronizar
                        String cleanPath = resourcePath.endsWith("/")
                                ? resourcePath.substring(0, resourcePath.length() - 1)
                                : resourcePath;

                        // 2. Tenta encontrar com a extensão .html (ex: lancamentos/entradapeca ->
                        // lancamentos/entradapeca.html)
                        Resource htmlResource = location.createRelative(cleanPath + ".html");
                        if (htmlResource.exists() && htmlResource.isReadable()) {
                            return htmlResource;
                        }

                        // 3. Tenta encontrar dentro de pasta index.html (ex:
                        // lancamentos/entradapeca/index.html)
                        Resource indexSubResource = location.createRelative(cleanPath + "/index.html");
                        if (indexSubResource.exists() && indexSubResource.isReadable()) {
                            return indexSubResource;
                        }

                        // 4. Fallback SPA para rotas dinâmicas do frontend sem extensão de arquivo
                        if (!resourcePath.contains(".")) {
                            Resource rootIndex = location.createRelative("index.html");
                            if (rootIndex.exists() && rootIndex.isReadable()) {
                                return rootIndex;
                            }
                        }

                        return null;
                    }
                });
    }
}
