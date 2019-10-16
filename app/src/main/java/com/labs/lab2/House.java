package com.labs.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class House {
    private UUID HouseID;
    private int FlatNumber;
    private double Square;
    private int Floor;
    private int NumberOfrooms;
    private String Street;
    private String BuildType;
    private double Lifetime;

    public UUID getHouseID() {
        return HouseID;
    }

    public int getFlatNumber() {
        return FlatNumber;
    }

    public double getSquare() {
        return Square;
    }

    public int getFloor() {
        return Floor;
    }

    public int getNumberOfrooms() {
        return NumberOfrooms;
    }

    public String getStreet() {
        return Street;
    }

    public String getBuildType() {
        return BuildType;
    }

    public double getLifetime() {
        return Lifetime;
    }

    public House(int flatNumber, double square, int floor, int numberOfrooms, String street, String buildType, double lifetime) {
        HouseID = UUID.randomUUID();
        FlatNumber = flatNumber;
        Square = square;
        Floor = floor;
        NumberOfrooms = numberOfrooms;
        Street = street;
        BuildType = buildType;
        Lifetime = lifetime;
    }

    public House() {}

    public List<House> PresetNumberOfRooms(List<House> temp, int count){
        List<House> result = new ArrayList<House>();

        for(House house : temp){
            if (house.getNumberOfrooms() == count){
                result.add(house);
            }
        }

        return result;
    }

    public List<House> RoomsAndFloor(List<House> temp, int count, int left, int right){
        List<House> result = new ArrayList<House>();

        for(House house : temp){
            if (house.getNumberOfrooms() == count && house.getFloor() >= left && house.getFloor() <= right){
                result.add(house);
            }
        }

        return result;
    }

    public List<House> PresetSquare(List<House> temp, double square){
        List<House> result = new ArrayList<House>();

        for(House house : temp){
            if (house.getSquare() > square){
                result.add(house);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return  " HouseID=" + HouseID +
                "\n FlatNumber=" + FlatNumber +
                "\n Square=" + Square +
                "\n Floor=" + Floor +
                "\n NumberOfRooms=" + NumberOfrooms +
                "\n Street='" + Street +
                "\n BuildType='" + BuildType +
                "\n Lifetime=" + Lifetime + "\n";
    }
}
