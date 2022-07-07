package edu.sprint3.pojo;

public class CourierForLogin {
    private String login;
    private String password;

    public CourierForLogin() {

    }

    public CourierForLogin(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
