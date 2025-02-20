package site.klol.batch.common.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SchedulerCondition implements Condition {
    private static final String PROFILE_SCHEDULER = "schd";
    private static final String PROFILE_PRODUCTION = "prd";
    private static final String PROFILE_STAGING = "stg";
    private static final String PROFILE_LOCAL = "local";
    private static final String PROFILE_CLI = "cli";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        
        // Check if CLI profile is active - if so, scheduler should not run
        if (isProfileActive(env, PROFILE_CLI)) {
            return false;
        }

        // Scheduler should only run if scheduler profile is active
        if (!isProfileActive(env, PROFILE_SCHEDULER)) {
            return false;
        }

        // Scheduler can run in any of these environments: production, staging, or local
        return isProfileActive(env, PROFILE_PRODUCTION) ||
               isProfileActive(env, PROFILE_STAGING) ||
               isProfileActive(env, PROFILE_LOCAL);
    }

    private boolean isProfileActive(Environment env, String profile) {
        return env.acceptsProfiles(profile);
    }
}
