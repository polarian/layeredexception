package io.polarian.layeredexception.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["io.polarian.layeredexception"])
@Configuration
class FeignConfig