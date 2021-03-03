package com.example.atlas.spider;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.example.atlas.mapper.*;
import com.example.atlas.model.*;
import com.example.atlas.spider.dto.AjaxParam;
import com.example.atlas.spider.dto.CropData;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/spider")
public class AjaxSpider {

    @Resource
    private CropMapper cropMapper;

    @Resource
    private CropToDiseasesMapper cropToDiseasesMapper;

    @Resource
    private CropToPestMapper cropToPestMapper;

    @Resource
    private DiseasesMapper diseasesMapper;

    @Resource
    private PestMapper pestMapper;

    /**
     * 路径:/spider/start
     * 全量非增量抓取农业病虫害数据(新增前应清空数据库数据,否则报错)
     * 农业病虫害信息云数据库
     * http://cloud.sinoverse.cn/index_bch.html
     * 使用方式 ->
     * 1、增修下方 params 关键字,数据抓取以该数据为关键字进行检索抓取,修改后启动项目
     * 2、启动项目后,修改端口后路径为上述路径,回车
     * 3、等待页面刷新完毕后,数据抓取成功
     */
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String login() {
        String[] params={"玉米","水稻","小米","小麦","无"};

        ArrayList<Crop> crops = new ArrayList<>();
        ArrayList<CropToDiseases> cropToDiseases = new ArrayList<>();
        ArrayList<CropToPest> cropToPests = new ArrayList<>();
        ArrayList<Diseases> diseases = new ArrayList<>();
        ArrayList<Pest> pests = new ArrayList<>();
        Arrays.asList(params).forEach(x->{
            this.StartSpider(new AjaxParam().setT("bch_list").setK(x), crops, cropToDiseases, cropToPests, diseases, pests);
        });
        this.toSaveData(crops, cropToDiseases, cropToPests, diseases, pests);
        return "hello";
    }

    public void StartSpider(AjaxParam param, ArrayList<Crop> cropsList, ArrayList<CropToDiseases> cropToDiseasesList, ArrayList<CropToPest> cropToPestsList, ArrayList<Diseases> diseasesList, ArrayList<Pest> pestsList) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AjaxParam> reqEntity = new HttpEntity<>(param, headers);
        Map body = restTemplate.exchange("http://app.sinoverse.cn:9090/api/cloud.jsp?t=bch_list&k=" + param.getK(), HttpMethod.POST, reqEntity, Map.class).getBody();
        List<HashMap> mapData = (List<HashMap>) body.get("list");
        ArrayList<CropData> cropDatas = new ArrayList<>();
        mapData.forEach(x -> {
            cropDatas.add(JSON.parseObject(JSON.toJSONString(x), CropData.class));
        });
        for (CropData x : cropDatas) {
            if (StringUtils.equals(x.getType1(), "病害")) {
                Crop crop = new Crop();
                int cropId = 0;
                for (int num = 0; num < cropsList.size(); num++) {
                    if (StringUtils.equals(cropsList.get(num).getName(), x.getType4())) {
                        cropId = num;
                    }
                }
                if (cropId == 0) {
                    cropId = cropsList.size() + 1;
                    crop.setId(cropId);
                    crop.setClassify(x.getType2());
                    crop.setDivision(x.getType3());
                    crop.setName(x.getType4());
                    cropsList.add(crop);
                }
                Diseases diseases = new Diseases();
                int diseasesId = 0;
                for (int num = 0; num < diseasesList.size(); num++) {
                    if (StringUtils.equals(diseasesList.get(num).getName(), x.getName())) {
                        diseasesId = num;
                    }
                }
                if (diseasesId == 0) {
                    diseasesId = diseasesList.size() + 1;
                    diseases.setId(diseasesId);
                    diseases.setAlias(x.getYname());
                    diseases.setName(x.getName());
                    diseases.setRemark(x.getDescription());
                    diseasesList.add(diseases);
                }
                CropToDiseases cropToDiseases = new CropToDiseases();
                cropToDiseases.setCropid(cropId);
                cropToDiseases.setDiseasesid(diseasesId);
                cropToDiseasesList.add(cropToDiseases);
            } else if (StringUtils.equals(x.getType1(), "虫害")) {
                Crop crop = new Crop();
                int cropId = 0;
                for (int num = 0; num < cropsList.size(); num++) {
                    if (StringUtils.equals(cropsList.get(num).getName(), x.getType4())) {
                        cropId = num;
                    }
                }
                if (cropId == 0) {
                    cropId = cropsList.size() + 1;
                    crop.setId(cropId);
                    crop.setClassify(x.getType2());
                    crop.setDivision(x.getType3());
                    crop.setName(x.getType4());
                    cropsList.add(crop);
                }
                Pest pest = new Pest();
                int pestId = 0;
                for (int num = 0; num < pestsList.size(); num++) {
                    if (StringUtils.equals(pestsList.get(num).getName(), x.getName())) {
                        pestId = num;
                    }
                }
                if (pestId == 0) {
                    pestId = pestsList.size() + 1;
                    pest.setId(pestId);
                    pest.setAlias(x.getYname());
                    pest.setName(x.getName());
                    pest.setRemark(x.getDescription());
                    pestsList.add(pest);
                }
                CropToPest cropToPest = new CropToPest();
                cropToPest.setCropid(cropId);
                cropToPest.setPestid(pestId);
                cropToPestsList.add(cropToPest);
            }
        }
    }

    private void toSaveData(ArrayList<Crop> cropsList, ArrayList<CropToDiseases> cropToDiseasesList, ArrayList<CropToPest> cropToPestsList, ArrayList<Diseases> diseasesList, ArrayList<Pest> pestsList) {
        cropsList.forEach(x -> this.cropMapper.insert(x));
        diseasesList.forEach(x -> this.diseasesMapper.insert(x));
        pestsList.forEach(x -> this.pestMapper.insert(x));
        cropToDiseasesList.forEach(x -> this.cropToDiseasesMapper.insert(x));
        cropToPestsList.forEach(x -> this.cropToPestMapper.insert(x));
    }

}
