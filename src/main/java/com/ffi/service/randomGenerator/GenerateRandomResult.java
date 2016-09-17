package com.ffi.service.randomGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by cryptq on 9/13/16.
 */
public class GenerateRandomResult {


    private static final String APPLICATION_CACHE_RANDOM_GENERATOR_RESULT = "applicationCacheRandomGeneratorResult";
    private final Response response;
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateRandomResult.class);
    private final String identifier;
    private String random;


    private final HashMap<String, RandomResult> applicationCache;

    private static final HashMap<String, RandomResult> STATIC_APPLICATION_CACHE = new HashMap<>();

    public static RandomResult getOrCreateRandomResult(GenerateRandomResult generateRandomResult) {

        RandomResult randomResult;
        if (generateRandomResult.applicationCache != null && generateRandomResult.applicationCache.containsKey(generateRandomResult.identifier) ) {
            randomResult = generateRandomResult.applicationCache.get(generateRandomResult.identifier);
        } else {
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis());
            int value = rnd.nextInt(Integer.parseInt(generateRandomResult.random));
            String text = Integer.toString(value);
            randomResult = new RandomResult(generateRandomResult.identifier, text);
            if ( generateRandomResult.applicationCache != null) {
                generateRandomResult.applicationCache.put(generateRandomResult.identifier, randomResult);
            }
        }
        randomResult.views ++;
        return randomResult;
    }

    public GenerateRandomResult(Request request, Response response) {
        this.response = response;
       /* this.applicationCache = request.session(true).attribute(APPLICATION_CACHE_RANDOM_GENERATOR_RESULT);

        synchronized (request.session()) {
            if (this.applicationCache == null) {
                this.applicationCache = new HashMap<>();
                request.session().attribute(APPLICATION_CACHE_RANDOM_GENERATOR_RESULT, this.applicationCache);
            }
        }
        */

        this.applicationCache = STATIC_APPLICATION_CACHE;
        if (request.params().containsKey(":random")) {
            random = request.params().get(":random");
        } else {
            random = "10";
        }

        if (request.params().containsKey(":identifier")) {
            identifier = request.params().get(":identifier");
        } else {
            identifier = "";
        }
    }

    private byte[] getRenderedImage() throws IOException {
        BufferedImage image = getRandomResultImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        return baos.toByteArray();
    }

    private BufferedImage getRandomResultImage() {
        RandomResult randomResult = getOrCreateRandomResult(this);
        RandomResultImageGenerator imageGenerator = new RandomResultImageGenerator(randomResult);
        return imageGenerator.getResponseImage();
    }

    public Response get() {

        byte[] data;
        try {
            data = getRenderedImage();
        } catch (IOException e) {
            LOGGER.error("ERROR", e);
            this.response.status(500);
            this.response.body("Unable to generate the random");
            return null;
        }
        HttpServletResponse servletResponse = this.response.raw();
        servletResponse.setContentType("image/jpg");
        servletResponse.setContentLength(data.length);
        try {
            servletResponse.getOutputStream().write(data);
        } catch (IOException e) {
            LOGGER.error("ERROR", e);
            this.response.status(500);
            this.response.body("Unable to generate the random");
        }
        return this.response;
    }


}
