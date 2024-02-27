package com.fastcampus.couponcore;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@ComponentScan
@EnableAutoConfiguration
public class CouponCoreConfiguration {
}
