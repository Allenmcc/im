package com.starsea.im.aggregation.dto;

import lombok.*;
import lombok.experimental.Builder;

/**
 * Created by danny on 16/8/19.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LabelDto {
    private Integer id;
    private String labelOne;
    private String labelTwo;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabelTwo() {
        return labelTwo;
    }

    public void setLabelTwo(String labelTwo) {
        this.labelTwo = labelTwo;
    }

    public String getLabelOne() {
        return labelOne;
    }

    public void setLabelOne(String labelOne) {
        this.labelOne = labelOne;
    }
}
