package com.fptdoandemo.fuguide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private String id;
    private String category;
    private String title;
    private String summary;
    private String publishedDate;
}
