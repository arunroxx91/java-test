package com.hrs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleReading {
	public BloodPressure bloodPressure;
	public Glucose glucose;
	public Weight weight;
	
	public BloodPressure getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(BloodPressure bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public Glucose getGlucose() {
		return glucose;
	}

	public void setGlucose(Glucose glucose) {
		this.glucose = glucose;
	}

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public class BloodPressure {
		public List<String> systolic;
		public List<String> diastolic;
		public List<String> heartRate;
		public List<String> getSystolic() {
			return systolic;
		}
		public void setSystolic(List<String> systolic) {
			this.systolic = systolic;
		}
		public List<String> getDiastolic() {
			return diastolic;
		}
		public void setDiastolic(List<String> diastolic) {
			this.diastolic = diastolic;
		}
		public List<String> getHeartRate() {
			return heartRate;
		}
		public void setHeartRate(List<String> heartRate) {
			this.heartRate = heartRate;
		}
		
	}

	public class Glucose {
		public List<String> bloodSugarLevel;

		public List<String> getBloodSugarLevel() {
			return bloodSugarLevel;
		}

		public void setBloodSugarLevel(List<String> bloodSugarLevel) {
			this.bloodSugarLevel = bloodSugarLevel;
		}
		
	}

	public class Weight {
		public List<String> weight;

		public List<String> getWeight() {
			return weight;
		}

		public void setWeight(List<String> weight) {
			this.weight = weight;
		}
		
	}

}
