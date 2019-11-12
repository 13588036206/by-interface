package com.youzan.youzanservice.impl;

import com.youzan.dao.mapper.CodeRecordMapper;
import com.youzan.dao.mapper.YouzanMessageMapper;
import com.youzan.dao.model.CodeRecord;
import com.youzan.dao.model.YouzanMessage;
import com.youzan.youzanservice.IYouzanMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service("youzanMessageService")
public class YouzanMessgeServiceImpl implements IYouzanMessageService {

    @Resource
    private YouzanMessageMapper youzanMessageMapper;

    @Resource
    private CodeRecordMapper codeRecordMapper;


    @Override
    public void saveMessage(YouzanMessage youzanMessage) {
        youzanMessageMapper.insert(youzanMessage);
    }

    @Override
    public void saveCode(CodeRecord codeRecord) {
        try {
            codeRecordMapper.insert(codeRecord);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getCode(String companyCode) {
        String code =null;
        try {
            if(Optional.ofNullable(companyCode).isPresent()) {
                code = codeRecordMapper.getCode(companyCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return code;
    }
}
