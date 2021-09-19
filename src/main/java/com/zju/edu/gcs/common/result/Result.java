package com.zju.edu.gcs.common.result;

import lombok.Data;

@Data
public class Result<E> {

    private String code;

    private String msg;

    private E data;

}
