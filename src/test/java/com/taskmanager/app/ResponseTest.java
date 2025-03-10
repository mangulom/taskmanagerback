package com.taskmanager.app;

import org.junit.jupiter.api.Test;

import com.taskmanager.app.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {
    @Test
    public void testDefaultConstructor() {
        Response response = new Response();
        assertEquals(null, response.getResult());
        assertEquals(null, response.getMessage());
    }

    @Test
    public void testParameterizedConstructor() {
        Response response = new Response("Some result", "Operation successful");
        assertEquals("Some result", response.getResult());
        assertEquals("Operation successful", response.getMessage());
    }

    @Test
    public void testSettersAndGetters() {
        Response response = new Response();
        response.setResult("New result");
        response.setMessage("Operation failed");

        assertEquals("New result", response.getResult());
        assertEquals("Operation failed", response.getMessage());
    }

    @Test
    public void testToString() {
        Response response = new Response("Sample result", "Sample message");
        String expectedString = "Response [result=Sample result, message=Sample message]";
        assertEquals(expectedString, response.toString());
    }
}