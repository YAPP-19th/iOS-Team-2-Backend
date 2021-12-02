package com.yapp.project.info;

import com.yapp.project.common.value.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {
    public List getPostionInfo(String positionInfo){
        List positionList = Position.listOf(positionInfo);
        return positionList;
    }

}
