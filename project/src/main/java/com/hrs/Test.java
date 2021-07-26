package com.hrs;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;

public class Test {
	Logger log = LogManager.getFormatterLogger();

	public static void main(String[] args) throws Exception {
		String readingsFile = "/" + (args.length >= 1 ? args[0] : null);
		String rulesFiles = "/rules.json";
		new Test(rulesFiles, readingsFile);
		Thread.sleep(5);
	}

	public Test(String rulesFile, String readingsFile) throws Exception {
		try {
			InputStream in = getClass().getResourceAsStream(rulesFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
			RuleReading rule = mapper.readValue(reader, RuleReading.class);

			// json file readings
			List<Reading> finalReadingList = new ArrayList<>();
			if (readingsFile.contains(".json")) {
				getJsonReadings(readingsFile);
			} else if (readingsFile.contains(".yml") || readingsFile.contains(".yaml")) {
				// yaml file readings
				YamlFileReadings(readingsFile, mapper, modelMapper, finalReadingList);
			}
			List<Response> response = valueProcessing(finalReadingList, rule);
			String json = new Gson().toJson(response);
			HttpClinet httpClinet = new HttpClinet();
			String outPut = httpClinet.sendPOST(json);
			log.info(outPut);
			try (FileWriter file = new FileWriter("results.json")) {
				// We can write any JSONArray or JSONObject instance to the file
				file.write(json);
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	private void YamlFileReadings(String readingsFile, ObjectMapper mapper, ModelMapper modelMapper,
			List<Reading> finalReadingList) throws java.io.IOException {
		InputStream in = getClass().getResourceAsStream(readingsFile);
		// readingsFile="/readings.json";
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Map<String, YamlReadings> yamlReadings = mapper.readValue(reader, Map.class);

		for (Entry<String, YamlReadings> entry : yamlReadings.entrySet()) {
			YamlReadings value = mapper.convertValue(entry.getValue(), new TypeReference<YamlReadings>() {
			});
			Reading reading = new Reading();
			reading = modelMapper.map(value, Reading.class);
			reading.setId(entry.getKey());
			finalReadingList.add(reading);
		}
	}

	private List<Reading> getJsonReadings(String readingsFile) throws Exception {
		InputStream in = getClass().getResourceAsStream(readingsFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String readingsFileResult = readAllLines(reader);
		JsonReading[] jsonReadings = new Gson().fromJson(readingsFileResult, JsonReading[].class);
		List<JsonReading> jsonReadingList = Arrays.asList(jsonReadings);

		List<Reading> finalReadingList = new ArrayList<Reading>();

		for (JsonReading rd : jsonReadingList) {
			Reading readings = new Reading();
			readings.setId(rd.getId());
			AtomicReference<String> bloodSugarLevel = new AtomicReference<>();
			AtomicReference<String> diastolic = new AtomicReference<>();
			AtomicReference<String> heartRate = new AtomicReference<>();
			AtomicReference<String> systolic = new AtomicReference<>();
			AtomicReference<String> weight = new AtomicReference<>();
			rd.getReadings().forEach(r -> {
				bloodSugarLevel.set(r.getBloodSugarLevel() != null ? r.getBloodSugarLevel() : bloodSugarLevel.get());
				diastolic.set(r.getDiastolic() != null ? r.getDiastolic() : diastolic.get());
				heartRate.set(r.getHeartRate() != null ? r.getHeartRate() : heartRate.get());
				systolic.set(r.getSystolic() != null ? r.getSystolic() : systolic.get());
				weight.set(r.getWeight() != null ? r.getWeight() : weight.get());
			});
			readings.setBloodSugarLevel(bloodSugarLevel.get());
			readings.setDiastolic(diastolic.get());
			readings.setHeartRate(heartRate.get());
			readings.setSystolic(systolic.get());
			readings.setWeight(weight.get());
			finalReadingList.add(readings);
		}
		return finalReadingList;
	}

	private static String readFileAsString(String file) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file)));
	}

	private static List<Response> valueProcessing(List<Reading> readingList, RuleReading rule) {
		List<Response> responseList = new ArrayList<>();

		List<String> sysrule = rule.getBloodPressure().getSystolic();
		String sysOper = sysrule.get(0);
		String sysValue = sysrule.get(1);

		List<String> diarule = rule.getBloodPressure().getDiastolic();
		String diaOper = diarule.get(0);
		String diaValue = diarule.get(1);

		List<String> heartrule = rule.getBloodPressure().getHeartRate();
		String heartOper = heartrule.get(0);
		String heartValue = heartrule.get(1);

		List<String> sugarrule = rule.getGlucose().getBloodSugarLevel();
		String sugarOper = sugarrule.get(0);
		String sugarValue = sugarrule.get(1);

		List<String> weightrule = rule.getWeight().getWeight();
		String weightOper = weightrule.get(0);
		String weightValue = weightrule.get(1);

		for (Reading readingVal : readingList) {

			if (readingVal.getSystolic() != null && ruleValidation(sysOper, sysValue, readingVal.getSystolic())) {
				responseList.add(
						setReadingValue(readingVal.getId(), "bloodPressure", "systolic", readingVal.getSystolic()));
			}
			if (readingVal.getDiastolic() != null && ruleValidation(diaOper, diaValue, readingVal.getDiastolic())) {
				responseList.add(
						setReadingValue(readingVal.getId(), "bloodPressure", "diastolic", readingVal.getDiastolic()));
			}
			if (readingVal.getHeartRate() != null && ruleValidation(heartOper, heartValue, readingVal.getHeartRate())) {
				responseList.add(
						setReadingValue(readingVal.getId(), "bloodPressure", "heartRate", readingVal.getHeartRate()));
			}
			if (readingVal.getBloodSugarLevel() != null
					&& ruleValidation(sugarOper, sugarValue, readingVal.getBloodSugarLevel())) {
				responseList.add(setReadingValue(readingVal.getId(), "glucose", "bloodSugarLevel",
						readingVal.getBloodSugarLevel()));
			}
			if (readingVal.getWeight() != null && ruleValidation(weightOper, weightValue, readingVal.getWeight())) {
				responseList.add(setReadingValue(readingVal.getId(), "weight", "weight", readingVal.getWeight()));
			}

		}

		return responseList;
	}

	private static Response setReadingValue(String id, String type, String subType, String value) {
		Response readingResult = new Response();
		readingResult.setId(Integer.valueOf(id));
		readingResult.setSubTypeValue(value);
		readingResult.setSubType(subType);
		readingResult.setType(type);
		return readingResult;
	}

	private static boolean ruleValidation(String operation, String ruleValue, String readingValue) {
		Integer ruleInt = Integer.valueOf(ruleValue);
		Integer readingInt = Integer.valueOf(readingValue);
		switch (operation) {
		case "<": {
			if (readingInt < ruleInt) {
				return true;
			}
			break;
		}
		case ">": {
			if (readingInt > ruleInt) {
				return true;
			}
			break;
		}
		case "=": {
			if (readingInt == ruleInt) {
				return true;
			}
			break;
		}
		case ">=": {
			if (readingInt >= ruleInt) {
				return true;
			}
			break;
		}
		case "<=": {
			if (readingInt <= ruleInt) {
				return true;
			}
			break;
		}
		default:
			return false;

		}
		return false;
	}

	public String readAllLines(BufferedReader reader) throws IOException {
		StringBuilder content = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			content.append(line);
			content.append(System.lineSeparator());
		}

		return content.toString();
	}

}
