package edu.sprint3.pojo;

public class CourierForCreateWithoutLogin {
    private String password;
    private String firstName;

    public CourierForCreateWithoutLogin() {

    }

    public CourierForCreateWithoutLogin(String password, String firstName){
        this.password = password;
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
