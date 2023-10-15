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
public class Department extends StandardEntity {
    private String name;
    private List<ComputerRoom> computerRooms = new ArrayList<>();
}
