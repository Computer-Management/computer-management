package com.project.model;

import com.project.payload.StandardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComputerRoom extends StandardEntity {
    private String name;
    private String note;
    private String status;
    private Department department;
    private List<Device> devices = new ArrayList<>();
}
