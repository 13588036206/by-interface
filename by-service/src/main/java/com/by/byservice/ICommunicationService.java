package com.by.byservice;

import com.by.dao.model.CommunicationLog;

public interface ICommunicationService {
    void saveLog(CommunicationLog communicationLog);
    int countLogs(String company, String inputtime);
}
