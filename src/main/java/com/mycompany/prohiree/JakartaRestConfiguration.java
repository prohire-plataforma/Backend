package com.mycompany.prohiree;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
public class JakartaRestConfiguration extends Application {
    // GlassFish escanea las rutas automáticamente, ¡no necesitamos forzar nada aquí!
}