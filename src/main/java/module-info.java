module io.github.pmatejko.proxy4cors {
    requires jdk.incubator.httpclient;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.web;
    requires tomcat.embed.core;
    requires jackson.annotations;
    requires org.apache.logging.log4j;

    exports io.github.pmatejko.proxy4cors.controller;
    exports io.github.pmatejko.proxy4cors.model;
    exports io.github.pmatejko.proxy4cors;

    opens io.github.pmatejko.proxy4cors.controller;
    opens io.github.pmatejko.proxy4cors.model;
    opens io.github.pmatejko.proxy4cors;
}