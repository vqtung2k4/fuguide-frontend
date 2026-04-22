package com.fptdoandemo.fuguide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusLife {
    private String id;

    private String title;
    private String description;
    private String icon;
}
