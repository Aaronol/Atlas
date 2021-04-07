package com.example.atlas.util;

import com.example.atlas.mapper.CropMapper;
import com.example.atlas.mapper.PedigreeMapper;
import com.example.atlas.model.Crop;
import com.example.atlas.model.Pedigree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class DictionaryGeneration {

    @Resource
    private CropMapper cropMapper;

    @Resource
    private PedigreeMapper pedigreeMapper;

    public void generationDictionary(String path) throws IOException {
        List<Crop> allCropData = cropMapper.getAllCropData();
        //添加作物信息进字典
        allCropData.forEach(x -> {
            try {
                if (StringUtils.isNotEmpty(x.getName())) {
                    FileWriteUtil.getFileWrite(path).writeFile(x.getName() + "\tn\t1000");
                }
                if (StringUtils.isNotEmpty(x.getTrainunit())) {
                    FileWriteUtil.getFileWrite(path).writeFile(x.getTrainunit() + "\tn\t1000");
                }
                if (StringUtils.isNotEmpty(x.getOrigin())) {
                    FileWriteUtil.getFileWrite(path).writeFile(x.getOrigin() + "\tn\t1000");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Pedigree> allPedigreeData = pedigreeMapper.getAllPedigreeData();
        //添加谱系信息进字典
        allPedigreeData.forEach(x -> {
            if (StringUtils.isNotEmpty(x.getName())) {
                try {
                    FileWriteUtil.getFileWrite(path).writeFile(x.getName() + "\tn\t1000");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        String[] keyWords = {"作物类型", "作物", "培育单位", "培育地", "产地", "生产地", "发源地", "株高", "千粒重", "系谱", "谱系"};
        //添加关键字信息进字典
        Arrays.asList(keyWords).forEach(x -> {
            try {
                FileWriteUtil.getFileWrite(path).writeFile(x + "\tn\t1000");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        FileWriteUtil.getFileWrite(path).closeIO();
    }

}
