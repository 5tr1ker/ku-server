package com.team.saver.common.util;

import com.team.saver.attraction.dto.AttractionResponse;
import com.team.saver.market.store.dto.MarketResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DistanceCalculator {

    public static List<AttractionResponse> calculateAttractionDistance(List<AttractionResponse> attractionResponses, double locationX, double locationY) {
        List<AttractionResponse> storages = new ArrayList<>(attractionResponses);

        for (AttractionResponse store : attractionResponses) {
            double distance = calculateDistance(store.getLocationX(), store.getLocationY(), locationX, locationY);

            store.setDistance(distance);
        }

        return storages;
    }

    public static List<MarketResponse> calculateMarketDistance(List<MarketResponse> marketResponse, double locationX, double locationY) {
        List<MarketResponse> storages = new ArrayList<>(marketResponse);

        for (MarketResponse store : marketResponse) {
            double distance = calculateDistance(store.getLocationX(), store.getLocationY(), locationX, locationY);

            store.setDistance(distance);
        }

        return storages;
    }

    private static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;

        return d;
    }

    private static double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

}
