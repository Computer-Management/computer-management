package com.project.model;

import com.project.enums.DeviceStatusEnum;
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
public class Device extends StandardEntity {
    private String name;
    private DeviceStatusEnum status = DeviceStatusEnum.PREPARE;
    private String total;
    private String note;
    private List<DeviceType> deviceType = new ArrayList<>();
    private Account account;
    private ComputerRoom computerRoom;
}
