import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Doctor Vahe = new Doctor.Builder("Vahe", "Atamnabuj").YearsOfExperience(12).build();
        Vahe.RegisteredDoctor();
        Doctor Ando = new Doctor.Builder("Ando", "ginekolog").contInfo("Agarakic").build();
        Ando.RegisteredDoctor();
        Patient Arshak = new Patient.Builder("Arshak", "12/34/23").gender(Gender.MALE).
                contInfo("Ashtarakic").build();
        Vahe.setMedication(Arshak, "vat dex");
        Vahe.setDiagnose(Arshak, "mernuma");
        Vahe.setTreatment(Arshak, "tox hox uti");
        Ando.setMedication(Arshak, "lav dex");
        Ando.setTreatment(Arshak, "qich nsti");
        Ando.setDiagnose(Arshak, "lav klni");
        Arshak.RegisteredPatient();
        Report report = new Report(Arshak);
        report.generatReport();
        report.showReport();
        report.savingReport();

    }
}

class Registration {
    private static final Registration Instance = new Registration();
    public List<Doctor> doctors = new LinkedList<>();
    public List<Patient> patients = new LinkedList<>();

    private Registration() {
    }

    public static Registration getInstance() {
        return Instance;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }
}

class Doctor {
    private final String Name;
    private final String Specialization;
    private int YearsOfExperience;
    private String contInfo;
    private boolean registrated;
    private final Registration registration = Registration.getInstance();

    Doctor(Builder builder) {
        this.Name = builder.Name;
        this.Specialization = builder.Specialization;
        this.YearsOfExperience = builder.YearsOfExperience;
        this.contInfo = builder.contInfo;
    }

    public void RegisteredDoctor() {
        registrated = true;
        registration.doctors.add(this);
    }

    public void setDiagnose(Patient patient, String diagnose) {
        if (registrated) {
            patient.getMedicalCondition().addDiagnose(diagnose);
        } else {
            System.out.println(Name + " you cant dou that, you dont registrated");
        }
    }

    public void setTreatment(Patient patient, String treatment) {
        if (registrated) {
            patient.getMedicalCondition().addTreatment(treatment);
        } else {
            System.out.println(Name + " you cant dou that, you dont registrated");
        }
    }

    public void setMedication(Patient patient, String medicatoin) {
        if (registrated) {
            patient.getMedicalCondition().addMedication(medicatoin);
        } else {
            System.out.println(Name + " you cant dou that, you dont registrated");
        }
    }

    public static class Builder {
        private final String Name;
        private final String Specialization;
        private int YearsOfExperience = 0;
        private String contInfo = "NoN";

        Builder(String name, String Specioalizaton) {
            this.Name = name;
            this.Specialization = Specioalizaton;
        }

        public Builder YearsOfExperience(int val) {
            this.YearsOfExperience = val > 0 ? val : 0;
            return this;
        }

        public Builder contInfo(String val) {
            this.contInfo = val;
            return this;
        }

        public Doctor build() {
            return new Doctor(this);
        }
    }

    public String getName() {
        return Name;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public int getYearsOfExperience() {
        return YearsOfExperience;
    }

    public String getContInfo() {
        return contInfo;
    }
}

class Patient {
    private final String Name;
    private final String DateOfBirth;
    private Gender gender;
    private String contInfo;
    private boolean registrated;
    public final MedicalCondition medicalCondition = new MedicalCondition();

    private final Registration registration = Registration.getInstance();

    Patient(Builder builder) {
        this.Name = builder.Name;
        this.DateOfBirth = builder.DateOfBirth;
        this.gender = builder.gender;
        this.contInfo = builder.contInfo;
    }

    public void RegisteredPatient() {
        registrated = true;
        registration.patients.add(this);
    }

    public void showMedicalCondition() {
        if (registrated) {
            System.out.println("Diagnoses");
            for (String str : medicalCondition.getDiagnoses()) {
                System.out.print(str + " ");
            }
            System.out.println("Medications");
            for (String str : medicalCondition.getMedications()) {
                System.out.print(str + " ");
            }
            System.out.println("Treatments");
            for (String str : medicalCondition.getTreatments()) {
                System.out.print(str + " ");
            }
        } else {
            System.out.println(Name + " you cant dou that, you dont registrated");
        }
        System.out.println();
    }

    public static class Builder {
        private final String Name;
        private final String DateOfBirth;
        private Gender gender = Gender.NON;
        private String contInfo = "NoN";

        Builder(String Name, String DateOfBirth) {
            this.Name = Name;
            this.DateOfBirth = DateOfBirth;
        }

        public Builder gender(Gender val) {
            this.gender = val;
            return this;
        }

        public Builder contInfo(String val) {
            this.contInfo = val;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }

    public String getName() {
        return Name;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getContInfo() {
        return contInfo;
    }

    public MedicalCondition getMedicalCondition() {
        return medicalCondition;
    }

    public Gender getGender() {
        return gender;
    }
}

class MedicalCondition {
    private List<String> diagnoses = new ArrayList<>();
    private List<String> treatments = new ArrayList<>();
    private List<String> medications = new ArrayList<>();

    MedicalCondition() {
        diagnoses.add("DIAGNOSES: ");
        treatments.add("TREATMENTS: ");
        medications.add("MEDICATIONS: ");
    }

    public void addDiagnose(String diagnose) {
        diagnoses.add(diagnose + " ");
    }

    public void addTreatment(String treatment) {
        treatments.add(treatment + " ");
    }

    public void addMedication(String medication) {
        medications.add(medication + " ");
    }

    public List<String> getDiagnoses() {
        return diagnoses;
    }

    public List<String> getTreatments() {
        return treatments;
    }

    public List<String> getMedications() {
        return medications;
    }
}

class Report {
    private final String Path;
    private Patient patient;
    private final LocalDate localDate = LocalDate.now();
    private List<String> medicalCondition = new ArrayList<>(3);
    private List<String> personalInformation = new ArrayList<>(4);

    Report(Patient patient) {
        this.patient = patient;
        Path = "/home/vahe/Desktop/ListOfTask2/E-HealthCare Management System/src/" + patient.getName() +
                localDate.toString() + ".txt";
    }

    public void generatReport() {
        medicalCondition.add("Medical Condition\n");
        medicalCondition.add(patient.getMedicalCondition().getMedications().toString());
        medicalCondition.add(patient.getMedicalCondition().getDiagnoses().toString());
        medicalCondition.add(patient.getMedicalCondition().getTreatments().toString() + "\n");
        personalInformation.add("Personal Information\n");
        personalInformation.add(patient.getGender().toString());
        personalInformation.add(patient.getName());
        personalInformation.add(patient.getContInfo());
        personalInformation.add(patient.getDateOfBirth() + "\n");
    }

    public void showReport() {
        for (int i = 0; i < personalInformation.size(); i++) {
            System.out.println(personalInformation.get(i));
        }
        for (int i = 0; i < medicalCondition.size(); i++) {
            System.out.println(medicalCondition.get(i));
        }
    }

    public void savingReport() {
        File file = new File(Path);
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < personalInformation.size(); i++) {
                bufferedWriter.write(personalInformation.get(i) + "\n");
            }
            bufferedWriter.write("\n");
            for (int i = 0; i < medicalCondition.size(); i++) {
                bufferedWriter.write(medicalCondition.get(i) + "\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

enum Gender {
    MALE, FEMALE, NON
}