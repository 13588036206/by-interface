package com.youzan.youzanservice;

import com.youzan.dao.model.CodeRecord;
import com.youzan.dao.model.YouzanMessage;

public interface IYouzanMessageService {
    public void saveMessage(YouzanMessage youzanMessage);
    public void saveCode(CodeRecord codeRecord);
    public String getCode(String companyCode);
}
