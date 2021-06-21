package com.hingebridge.devops.config.dtos;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class PagingDTO
{
    @Min(value = 0L, message = "init {app.page.value}")
    private int init;
    @Min(value = -1L, message = "size {app.page.value}")
    private int size;
}