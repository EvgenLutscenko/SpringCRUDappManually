package com.ua.javarush.dto;

import com.ua.javarush.domain.Status;
import lombok.Data;

@Data
public class TaskInfo {
    private String description;
    private Status status;
}
