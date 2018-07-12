module io.github.pmatejko.proxy4cors {
    requires jdk.incubator.httpclient;
    requires spring.web;
    requires tomcat.embed.core;
    requires jackson.annotations;
    requires spring.boot;
    requires spring.boot.autoconfigure;

    exports io.github.pmatejko.proxy4cors.controller;
    exports io.github.pmatejko.proxy4cors.model;
    exports io.github.pmatejko.proxy4cors;

    opens io.github.pmatejko.proxy4cors.controller;
    opens io.github.pmatejko.proxy4cors.model;
    opens io.github.pmatejko.proxy4cors;
}