package com.team.saver.search.elasticsearch.market.document;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalTime;

@Getter
@Document(indexName = "alcohols")
@Setting(replicas = 0)
public class MarketDocument {

    @Id
    private String marketDocumentId;

    @Field(type = FieldType.Long, index = false, docValues = false)
    private long marketId;

    @Field(type = FieldType.Text)
    private String marketName;

    @Field(type = FieldType.Text, index = false, docValues = false)
    private String marketDescription;

    @Field(type = FieldType.Text, index = false, docValues = false)
    private String marketImage;

    @Field(type = FieldType.Double, index = false, docValues = false)
    private double locationX;

    @Field(type = FieldType.Double, index = false, docValues = false)
    private double locationY;

    @Field(type = FieldType.Text, index = false, docValues = false)
    private String eventMessage;

    @Field(type = FieldType.Date, index = false, docValues = false)
    private LocalTime openTime;

    @Field(type = FieldType.Date, index = false, docValues = false)
    private LocalTime closeTime;

    @Field(type = FieldType.Text, index = false, docValues = false)
    private String closedDays;

}
