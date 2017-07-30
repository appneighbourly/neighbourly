package com.neighbourly;

import com.neighbourly.healthcheck.ApiHealthCheck;
import com.neighbourly.resource.HealthCheckResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NeighbourlyApplication extends Application<NeighbourlyConfiguration> {

    public static final Logger LOGGER = LoggerFactory.getLogger(NeighbourlyApplication.class);

    public static void main(final String[] args) throws Exception {
        new NeighbourlyApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<NeighbourlyConfiguration> bootstrap) {

        // For swagger UI.
        bootstrap.addBundle(new SwaggerBundle<NeighbourlyConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(NeighbourlyConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });

    }

    @Override
    public void run(NeighbourlyConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Application Name: {}", configuration.getAppName());

        // Add Resources here

        environment.jersey().register(new HealthCheckResource());
        environment.healthChecks().register("APIHealthCheck", new ApiHealthCheck(configuration));
    }
}
