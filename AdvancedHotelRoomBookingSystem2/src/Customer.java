import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private String name;
    private String mail;

    public Customer() {

    }

    public Customer(String name, String mail) {
        validateName(name);
        this.name = name;
        validateEmail(mail);
        this.mail = mail;
    }

    private void validateEmail(String mail) {
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(mail);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid Email");
        }
    }

    private void validateName(String name) {
        String pattern = "^[a-zA-Z]+$";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid Name");
        }
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
