package com.sjzx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIResult<T> {

    private Long total;

    private List<T> rows;

}
