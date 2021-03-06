package hr.foi.air.crvenkappica.registration;

//Klasa za podatke registracije -> lakše kreiranje json objekta
public class RegistrationData {
    private String Name;
    private String Lastname;
    private String Username;
    private String Password;
    private String DOB;
    private String Email;

    public RegistrationData() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String dob) {
        DOB = dob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
