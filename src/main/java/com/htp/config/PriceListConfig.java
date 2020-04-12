package com.htp.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties("pricelist")
public class PriceListConfig {
    private float textPrice;
    private float imagePrice;
}
