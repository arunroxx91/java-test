package com.hrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Test {
    Logger log = LogManager.getFormatterLogger();

    public static void main(String[] args) throws Exception {
        String readingsFile=readFileAsString(args.length >= 1 ? args[0] : null);
        String rulesFiles=readFileAsString(args.length >= 2 ? args[1] : "rules.json");
       // String readingsFile=readFileAsString("src/main/resources/readings.json");
        //String rulesFiles=readFileAsString("src/main/resources/rules.json");
        new Test(rulesFiles, readingsFile);
        Thread.sleep(5);
    }

    public Test(String rulesFile, String readingsFile) throws Exception {
        Type type = new Gson().fromJson(rulesFile, Type.class);
        Readings[] readings  = new Gson().fromJson(readingsFile, Readings[].class);
        List<Response> responce =valueProcessing(Arrays.asList(readings),type);
       /* InputStream inputStream = new FileInputStream(new File("src/main/resources/readings.yml"));
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(data);
        Readings pojo = gson.fromJson(jsonElement, Readings.class);
        List<Response> responce1 =valueProcessing(Arrays.asList(pojo),type);
        System.out.println("99999999999999999999"+responce1);*//*
        *//*InputStream inputStream = new FileInputStream(new File("src/main/resources/readings.yml"));
        Yaml yaml = new Yaml(new Constructor(Readings[].class));
        Readings[] data = yaml.load(inputStream);*//*
        System.out.println(data);*/
        String json = new Gson().toJson(responce);
        // Read resource source file for rules
        // Read resource source file for values
        // See which values trigger the rules
        // Report the findings to another server
        //     (don't need to make the actual http request but code should lead up until that final moment)
        HttpClinet httpClinet = new HttpClinet();
        String outPut=httpClinet.sendPOST(json);
        log.info(outPut);
        // Write findings as json file to /code/results.json
        //Write JSON file
        try (FileWriter file = new FileWriter("results.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(json);
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }


    private List<Response> valueProcessing(List<Readings> readingsList, Type rule){
        List<Response> responseList = new ArrayList<>();
        for (Readings readingVal:readingsList) {
            for(ReadingType readingType:readingVal.getReadings()) {
                Response response = new Response();
                Readings readingResult = new Readings();
                ReadingType readingTypeResult = new ReadingType();
                if (readingType.getType().equalsIgnoreCase("bloodPressure")) {
                    if (ruleValidation(rule.getBloodPressure().getSystolic()[0], rule.getBloodPressure().getSystolic()[1], readingType.getSystolic())) {
                        response= setReadingValue(readingVal.getId(), readingType.getType());
                        response.setSubTypeValue(readingType.getSystolic());
                        response.setSubType("systolic");
                        responseList.add(response);
                    }
                    if (ruleValidation(rule.getBloodPressure().getDiastolic()[0], rule.getBloodPressure().getDiastolic()[1], readingType.getDiastolic())) {
                        response=setReadingValue(readingVal.getId(), readingType.getType());
                        response.setSubTypeValue(readingType.getDiastolic());
                        response.setSubType("diastolic");
                        responseList.add(response);
                    }
                    if (ruleValidation(rule.getBloodPressure().getHeartRate()[0], rule.getBloodPressure().getHeartRate()[1], readingType.getHeartRate())) {
                        response=setReadingValue(readingVal.getId(), readingType.getType());
                        response.setSubTypeValue(readingType.getHeartRate());
                        response.setSubType("heartRate");
                        responseList.add(response);
                    }

                } else if (readingType.getType().equalsIgnoreCase("glucose") &&
                        ruleValidation(rule.getGlucose().getBloodSugarLevel()[0], rule.getGlucose().getBloodSugarLevel()[1],
                                readingType.getBloodSugarLevel())) {
                    response= setReadingValue(readingVal.getId(), readingType.getType());
                    response.setSubTypeValue(readingType.getBloodSugarLevel());
                    response.setSubType("bloodSugarLevel");
                    responseList.add(response);

                } else if (readingType.getType().equalsIgnoreCase("weight") &&
                        ruleValidation(rule.getWeight().getWeight()[0],
                                rule.getWeight().getWeight()[1], readingType.getWeight())) {
                    response= setReadingValue(readingVal.getId(), readingType.getType());
                    response.setSubTypeValue(readingType.getWeight());
                    response.setSubType("weight");
                    responseList.add(response);
                }
            }

        }

        return responseList ;
    }

    private  Response setReadingValue(int id, String type){
        Response readingResult= new Response();
        readingResult.setId(id);
        readingResult.setType(type);
        return readingResult;
    }


    private boolean ruleValidation(String operation,String ruleValue,String readingValue){
        Integer ruleInt = Integer.valueOf(ruleValue);
        Integer readingInt = Integer.valueOf(readingValue);
        switch (operation){
            case "<":
            {
                if(readingInt<ruleInt)
                {
                    return true;
                }
                break;
            }
            case ">":
            {
                if(readingInt>ruleInt)
                {
                    return true;
                }
                break;
            }
            case "=":
            {
                if(readingInt==ruleInt)
                {
                    return true;
                }
                break;
            }
            case ">=":
            {
                if(readingInt>=ruleInt)
                {
                    return true;
                }
                break;
            }
            case "<=":
            {
                if(readingInt<=ruleInt)
                {
                    return true;
                }
                break;
            }
            default:
                return false;

        }
return false;
    }

}
