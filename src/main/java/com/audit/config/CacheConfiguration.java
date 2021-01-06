package com.audit.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.audit.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.audit.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.audit.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.audit.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.audit.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
            cm.createCache(com.audit.domain.AuditPlanMaster.class.getName() , jcacheConfiguration);
            cm.createCache(com.audit.domain.AuditPlanObjectiveMap.class.getName() , jcacheConfiguration);
            cm.createCache(com.audit.domain.AuditProcedureMaster.class.getName() , jcacheConfiguration);
            cm.createCache(com.audit.domain.AuditProcObjectiveMaster.class.getName() , jcacheConfiguration);
            cm.createCache(com.audit.domain.CostCentreMaster.class.getName() , jcacheConfiguration);
            cm.createCache(com.audit.domain.DeptMaster.class.getName() , jcacheConfiguration);
            cm.createCache(com.audit.domain.LocationMaster.class.getName() , jcacheConfiguration);
            cm.createCache(com.audit.domain.AuditTestingPlanMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.audit.domain.DocumentMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.audit.domain.TestingPlanObservationMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.audit.domain.AuditTrailMaster.class.getName(), jcacheConfiguration);
        };
    }
}
