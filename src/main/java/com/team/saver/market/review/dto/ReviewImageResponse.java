package com.team.saver.market.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewImageResponse {

    private long imageId;

    private String image;

    public int hashCode() {
        return (imageId + image).hashCode();
    }

    public boolean equals(Object o) {
        if(o instanceof ReviewImageResponse) {
            ReviewImageResponse tmp = ( ReviewImageResponse )o;
            return tmp.imageId == this.imageId;
        }

        return false;
    }

}
