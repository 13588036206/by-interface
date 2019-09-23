package com.by.byservice;

import com.by.dao.model.CommunicationLog;

public interface ICommunicationService {
    public void saveLog(CommunicationLog communicationLog);
    public int countLogs(String company,String inputtime);
}
