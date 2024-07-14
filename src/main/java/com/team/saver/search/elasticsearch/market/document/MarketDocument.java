package com.team.saver.search.elasticsearch.market.document;

import com.team.saver.market.store.entity.Market;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@Document(indexName = "market")
@Setting(settingPath = "/elasticsearch/settings.json")
@Mapping(mappingPath = "/elasticsearch/mappings.json")
public class MarketDocument {

    @Id
    @WriteOnlyProperty
    @Field(name = "id", type = FieldType.Keyword)
    private String marketDocumentId;

    @Field(type = FieldType.Long, index = false, docValues = false)
    private long marketId;

    @Field(type = FieldType.Text, analyzer = "synonym_search")
    private String marketName;

    @Field(type = FieldType.Text, analyzer = "synonym_search")
    private String marketDescription;

    @Field(type = FieldType.Text, index = false)
    private String marketImage;

    @Field(type = FieldType.Double, index = false)
    private double locationX;

    @Field(type = FieldType.Double, index = false)
    private double locationY;

    @Field(type = FieldType.Text, index = false)
    private String eventMessage;

    @Field(type = FieldType.Text, index = false)
    private String openTime;

    @Field(type = FieldType.Text, index = false)
    private String closeTime;

    @Field(type = FieldType.Text, index = false)
    private String closedDays;

    public static MarketDocument createEntity(Market market) {
        return MarketDocument.builder()
                .marketId(market.getMarketId())
                .marketName(market.getMarketName())
                .marketDescription(market.getMarketDescription())
                .marketImage(market.getMarketImage())
                .locationX(market.getLocationX())
                .locationY(market.getLocationY())
                .eventMessage(market.getEventMessage())
                .openTime(market.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString())
                .closeTime(market.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString())
                .closedDays(market.getClosedDays())
                .build();
    }

}

