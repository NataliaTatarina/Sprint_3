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

    public Order setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public Order setCourierId(Integer courierId) {
        this.courierId = courierId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Order setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Order setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public Order setMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Order setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public Order setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Order setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public Integer getTrack() {
        return track;
    }

    public Order setTrack(Integer track) {
        this.track = track;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Order setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String[] getColor() {
        return color;
    }

    public Order setColor(String[] color) {
        this.color = color;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Order setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public Order setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public Boolean getFinished() {
        return finished;
    }

    public Order setFinished(Boolean finished) {
        this.finished = finished;
        return this;
    }

    public Boolean getInDelivery() {
        return inDelivery;
    }

    public Order setInDelivery(Boolean inDelivery) {
        this.inDelivery = inDelivery;
        return this;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    public Order setCourierFirstName(String courierFirstName) {
        this.courierFirstName = courierFirstName;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Order setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Order setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
