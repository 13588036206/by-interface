package com.youzan.youzanservice;

import com.youzan.dao.model.CodeRecord;
import com.youzan.dao.model.YouzanMessage;

public interface IYouzanMessageService {
    void saveMessage(YouzanMessage youzanMessage);
    void saveCode(CodeRecord codeRecord);
    String getCode(String companyCode);
}
