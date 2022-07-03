# Quarkus/Renarde/Quinoa + Htmx starter project

This project is a simple starter project for an HDA-style (Hypermedia-driven)
application featuring:

- Quarkus-based developer experience (everything managed from the dev console
  and through the standard Quarkus build process)
- Renardes ease for web app development (templates, security, etc.)
- Quinoas management of Node-based build processes
- Htmx-powered HDA-style request processing
- Webpack-powered asset optimization (SASS-to-CSS with font files management,
  SVG images optimization)
- Hybrid HDA/MPA application behavior (HDA in a JavaScript-powered browsed,
  MPA in a non-JS one; also gives better SEO capabilities)
- unused CSS rules purging in production builds (based on words found in HTML
  and JS files)
- native image support (through standard Quarkus build process; from
  the combination of the above)
- sample tests for the templates.


### Alternative

For an even simpler version of Quarkus+Htmx integration you can head to
https://github.com/derkoe/quarkus-htmx-todos. This project  uses Quarkus-based
WebJar handling for UI assets and doesn't rely on Node-based asset processing.

Shout out to @derkoe for creating it.


### Limitation

- PurgeCSS, used to remove unneeded CSS styles in production mode, needs to be
  aware of full class names, so your code (HTML or JS as both are scanned)
  needs to use full class names (you cannot dynamically create CSS class names
  in JS code, as this won't be picked up by PurgeCSS, 
  e.g. `const style = 'somerule-' + num;` - the resulting class name won't be
  seen by PurgeCSS);


### Links

- Quarkus - https://quarkus.io/
- Renarde - https://quarkiverse.github.io/quarkiverse-docs/quarkus-renarde/dev/
- Quinoa - https://quarkiverse.github.io/quarkiverse-docs/quarkus-quinoa/dev/
- Htmx - https://htmx.org/
