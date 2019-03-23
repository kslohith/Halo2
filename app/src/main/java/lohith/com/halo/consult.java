package lohith.com.halo;

public class consult {
    public String name , medicines , symptoms, age;

    public consult(){}

    public consult(String name, String medicines, String symptoms, String age) {
        this.name = name;
        this.medicines = medicines;
        this.symptoms = symptoms;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getMedicines() {
        return medicines;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getAge() {
        return age;
    }
}
