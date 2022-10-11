package com.ru.operation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExternalClient {
    public  String FullName;
    public  BigDecimal ChkoYear;
    public  int ClientYears;
    public  String Inn;
    public  String LoyaltyStatus;
    public  LocalDate Period;
    public  int Products;
    public  String Id;

    public  String getFullName() {
        return FullName;
    }

    public  void setFullName(String fullName) {
        FullName = fullName;
    }

    public  BigDecimal getChkoYear() {
        return ChkoYear;
    }

    public  void setChkoYear(BigDecimal chkoYear) {
        ChkoYear = chkoYear;
    }

    public  int getClientYears() {
        return ClientYears;
    }

    public  void setClientYears(int clientYears) {
        ClientYears = clientYears;
    }

    public  String getInn() {
        return Inn;
    }

    public  void setInn(String inn) {
        Inn = inn;
    }

    public  String getLoyaltyStatus() {
        return LoyaltyStatus;
    }

    public  void setLoyaltyStatus(String loyaltyStatus) {
        LoyaltyStatus = loyaltyStatus;
    }

    public  LocalDate getPeriod() {
        return Period;
    }

    public  void setPeriod(LocalDate period) {
        Period = period;
    }

    public  int getProducts() {
        return Products;
    }

    public  void setProducts(int products) {
        Products = products;
    }

    public  String getId() {
        return Id;
    }

    public  void setId(String id) {
        Id = id;
    }
}
