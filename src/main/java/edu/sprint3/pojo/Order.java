package edu.sprint3.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Order {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer track;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;
    public String[] color;
    private String comment;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean cancelled;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean finished;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean inDelivery;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String courierFirstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedAt;

    public Order() {
    }

    public Order(Integer id, Integer courierId, String firstName, String lastName,
                 String address, String metroStation, String phone,
                 Integer rentTime, String deliveryDate, Integer track,
                 Integer status, String[] color, String comment,
                 Boolean cancelled, Boolean finished, Boolean inDelivery,
                 String courierFirstName, String createdAt,
                 String updatedAt) {
        this.id = id;
        this.courierId = courierId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.track = track;
        this.status = status;
        this.color = color;
        this.comment = comment;
        this.cancelled = cancelled;
        this.finished = finished;
        this.inDelivery = inDelivery;
        this.courierFirstName = courierFirstName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public void setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getInDelivery() {
        return inDelivery;
    }

    public void setInDelivery(Boolean inDelivery) {
        this.inDelivery = inDelivery;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    public void setCourierFirstName(String courierFirstName) {
        this.courierFirstName = courierFirstName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
