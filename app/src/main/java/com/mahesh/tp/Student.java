package com.mahesh.tp;

public class Student {
    String name,PRN,mobileNumber,UPRN;
    double X,XII, FEI, FEII, SEI, SEII, TEI, TEII,BEI,BEII;

    public Student(String name, String PRN) {
        this.name = name;
        this.PRN = PRN;
    }

    public Student(String name, String PRN, String mobileNumber) {
        this.name = name;
        this.PRN = PRN;
        this.mobileNumber = mobileNumber;
    }

    public Student(String name, String PRN, String mobileNumber, String UPRN) {
        this.name = name;
        this.PRN = PRN;
        this.UPRN = UPRN;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPRN() {
        return PRN;
    }

    public void setPRN(String PRN) {
        this.PRN = PRN;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUPRN() {
        return UPRN;
    }

    public void setUPRN(String UPRN) {
        this.UPRN = UPRN;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getXII() {
        return XII;
    }

    public void setXII(double XII) {
        this.XII = XII;
    }

    public double getFEI() {
        return FEI;
    }

    public void setFEI(double FEI) {
        this.FEI = FEI;
    }

    public double getFEII() {
        return FEII;
    }

    public void setFEII(double FEII) {
        this.FEII = FEII;
    }

    public double getSEI() {
        return SEI;
    }

    public void setSEI(double SEI) {
        this.SEI = SEI;
    }

    public double getSEII() {
        return SEII;
    }

    public void setSEII(double SEII) {
        this.SEII = SEII;
    }

    public double getTEI() {
        return TEI;
    }

    public void setTEI(double TEI) {
        this.TEI = TEI;
    }

    public double getTEII() {
        return TEII;
    }

    public void setTEII(double TEII) {
        this.TEII = TEII;
    }

    public double getBEI() {
        return BEI;
    }

    public void setBEI(double BEI) {
        this.BEI = BEI;
    }

    public double getBEII() {
        return BEII;
    }

    public void setBEII(double BEII) {
        this.BEII = BEII;
    }

    public double getTotalSum(){
        return FEI+FEII+SEI+SEII+TEI+TEII+BEI+BEII;
    }
}
