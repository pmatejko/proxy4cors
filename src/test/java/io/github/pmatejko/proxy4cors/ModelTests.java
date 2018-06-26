package io.github.pmatejko.proxy4cors;

import io.github.pmatejko.proxy4cors.model.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ModelTests {

    @Test
    public void RequestTest() {
        final var req1 = new Request("http://ale.co", "GET");
        final var req2 = new Request("abcabc", "POST");
        final var req3 = new Request("a.pl", null);

        assertEquals("GET", req1.getHttpMethod());
        assertEquals("http://ale.co", req1.getUrl());
        assertEquals("abcabc", req2.getUrl());
        assertEquals("a.pl", req3.getUrl());
        assertNull(req3.getHttpMethod());
        assertEquals("POST", req2.getHttpMethod());
    }

}
