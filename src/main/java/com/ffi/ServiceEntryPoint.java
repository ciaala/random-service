package com.ffi;
import com.ffi.service.randomGenerator.GenerateRandomResult;
import spark.Request;
import spark.Response;
import static spark.Spark.get;
import static spark.Spark.port;

/**
 * Created by cryptq on 9/13/16.
 */
public class ServiceEntryPoint {

    public static void main(String[] args) {
        port(8080);
        get("/random/:identifier/:random",
                (Request request, Response response) -> new GenerateRandomResult(request, response).get());

    }

 }
