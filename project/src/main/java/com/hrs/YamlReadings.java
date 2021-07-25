package com.hrs;

public class YamlReadings {
    private Readings readings;

    public Readings getReadings() {
        return readings;
    }

    public void setReadings(Readings value) {
        this.readings = value;
    }

    public class Readings {
        private BloodPressure bloodPressure;
        private Glucose glucose;
        private Weight weight;

        public BloodPressure getBloodPressure() {
            return bloodPressure;
        }

        public void setBloodPressure(BloodPressure value) {
            this.bloodPressure = value;
        }

        public Glucose getGlucose() {
            return glucose;
        }

        public void setGlucose(Glucose value) {
            this.glucose = value;
        }

        public Weight getWeight() {
            return weight;
        }

        public void setWeight(Weight value) {
            this.weight = value;
        }

        public class Glucose {
            private String bloodSugarLevel;

            public String getBloodSugarLevel() {
                return bloodSugarLevel;
            }

            public void setBloodSugarLevel(String value) {
                this.bloodSugarLevel = value;
            }
        }

        public class BloodPressure {
            private String diastolic;
            private String heartRate;
            private String systolic;

            public String getDiastolic() {
                return diastolic;
            }

            public void setDiastolic(String value) {
                this.diastolic = value;
            }

            public String getHeartRate() {
                return heartRate;
            }

            public void setHeartRate(String value) {
                this.heartRate = value;
            }

            public String getSystolic() {
                return systolic;
            }

            public void setSystolic(String value) {
                this.systolic = value;
            }
        }

        public class Weight {
            private String weight;

            public String getWeight() {
                return weight;
            }

            public void setWeight(String value) {
                this.weight = value;
            }
        }
    }
}