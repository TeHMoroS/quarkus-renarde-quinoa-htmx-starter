package io.quarkus.htmx;

import io.vertx.core.http.HttpServerRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

/**
 * Template utility class. Can be used in templates with <code>inject:utils.*</code> call convention.
 */
@ApplicationScoped
@Named("utils")
public class TemplateUtils {

    @Inject
    HttpServerRequest request;

    /**
     * Check and return the value of the Htmx request header ("HX-Request").
     *
     * @return <code>true</code> if the header is present and is set to "true", <code>false</code> otherwise
     */
    public boolean hxRequest() {
        return Optional.ofNullable(this.request.getHeader("HX-Request"))
            .map(Boolean::parseBoolean)
            .orElse(false);
    }
}
