package com.sycosoft.whattodo.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("prod")
class ProdConfiguration {
}