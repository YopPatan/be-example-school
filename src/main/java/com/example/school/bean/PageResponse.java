package com.example.school.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {

    private String nextPage;
    private String prevPage;
    private List<T> content;
}
