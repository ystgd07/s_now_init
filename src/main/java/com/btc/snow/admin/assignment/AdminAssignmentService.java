package com.btc.snow.admin.assignment;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AdminAssignmentService implements IAdminAssignmentService{

    @Autowired
    IAdminAssignmentMB iAdminAssignmentMB;

}