package com.example.employee.dtos.request;

import com.example.employee.Enum.LeaveStatus;
import lombok.Data;

@Data
public class ChangeStatusLeaveRequet {
    private Long leaveRequestId;
    private LeaveStatus status;
}
