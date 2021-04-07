package com.example.atlas.spider;

import com.alibaba.fastjson.JSON;
import com.example.atlas.mapper.CropMapper;
import com.example.atlas.mapper.CropPedigreeMapper;
import com.example.atlas.mapper.PedigreeMapper;
import com.example.atlas.model.Crop;
import com.example.atlas.model.CropPedigree;
import com.example.atlas.model.Pedigree;
import com.example.atlas.util.FileWriteUtil;
import com.example.atlas.util.ParseHtml;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CropPageProcessor implements PageProcessor {

    @Resource
    private CropMapper cropMapper;

    @Resource
    private CropPedigreeMapper cropPedigreeMapper;

    @Resource
    private PedigreeMapper pedigreeMapper;

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public String key;

    public String value;

    @SneakyThrows
    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(http://crop\\.agridata\\.cn/show\\.asp?page=\\w+)").all());
        page.putField("name", page.getHtml().xpath("/font/table/tbody/tr/td/font/text()").toString());
        String s = "";
        if (CollectionUtils.isNotEmpty(page.getHeaders().get("Set-Cookie"))) {
            s = page.getHeaders().get("Set-Cookie").get(0);
        }
        if (StringUtils.isNotEmpty(s)) {
            String[] split = s.split("=");
            String[] split1 = split[1].split(";");
            this.key = split[0];
            this.value = split1[0];
        }
        Document parse = Jsoup.parse(page.getHtml().getFirstSourceText());
        String[] split = ParseHtml.html2Str(page.getHtml().getFirstSourceText()).split("\\s{1,}");
        System.out.println(JSON.toJSONString(split));
        String[] keyArrays = {"当前位置：首页>选择作物>结果显示", "统一编号", "品种名称", "系谱", "株高", "千粒重", "选育单位", "省", "备注", "方法及组合"};
        List<String> keyWords = Arrays.asList(keyArrays);
        boolean flag = false;
        List<String> datas = new ArrayList<>();
        for (String x : Arrays.asList(split)) {
            if (flag) {
                flag = false;
                if (!datas.contains(x)) {
                    datas.add(x);
                }
            }
            if (keyWords.contains(x)) {
                flag = true;
                if (!datas.contains(x)) {
                    datas.add(x);
                }
            }
        }
        Crop crop = new Crop();
        ArrayList<Pedigree> pedigreesDatas = new ArrayList<>();
        ArrayList<CropPedigree> cropPedigrees = new ArrayList<CropPedigree>();
//        String[] keyArrays = {"当前位置：首页>选择作物>结果显示", "统一编号", "品种名称", "系谱", "株高", "千粒重", "选育单位", "省", "备注"};
        for (String x : keyWords) {
            if (StringUtils.equals(x, keyArrays[0])) {
                crop.setCroptype(datas.get(datas.indexOf(x) + 1));
            } else if (StringUtils.equals(x, keyArrays[2])) {
                crop.setName(datas.get(datas.indexOf(x) + 1));
                crop.setId(this.getId(datas.get(datas.indexOf(x) + 1)));
            } else if (StringUtils.equals(x, keyArrays[3])) {
                String[] pedigreesArrays = datas.indexOf(x) > -1 ? datas.get(datas.indexOf(x) + 1).split("/") : datas.get(datas.indexOf("方法及组合") + 1).split("/");
                List<String> pedigrees = Arrays.stream(pedigreesArrays).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
                int addRow = 0;
                for (String data : pedigrees) {
                    Pedigree pedigree = new Pedigree();
                    CropPedigree cropPedigree = new CropPedigree();
                    int[] pedigreeIds = this.getPedigreeId(data, addRow);
                    addRow = pedigreeIds[1];
                    pedigree.setId(pedigreeIds[0]);
                    pedigree.setName(data);
                    cropPedigree.setCropId(crop.getId());
                    cropPedigree.setPedigreeId(pedigree.getId());
                    pedigreesDatas.add(pedigree);
                    cropPedigrees.add(cropPedigree);
                }
            } else if (StringUtils.equals(x, keyArrays[4])) {
                crop.setHeight(datas.indexOf(x) > -1 ? datas.get(datas.indexOf(x) + 1) : "");
            } else if (StringUtils.equals(x, keyArrays[5])) {
                crop.setWeight(datas.indexOf(x) > -1 ? datas.get(datas.indexOf(x) + 1) : "");
            } else if (StringUtils.equals(x, keyArrays[6])) {
                crop.setTrainunit(datas.indexOf(x) > -1 ? datas.get(datas.indexOf(x) + 1) : "");
            } else if (StringUtils.equals(x, keyArrays[7])) {
                crop.setOrigin(datas.indexOf(x) > -1 ? datas.get(datas.indexOf(x) + 1) : "");
            } else if (StringUtils.equals(x, keyArrays[8])) {
                crop.setRemark(datas.indexOf(x) > -1 ? datas.get(datas.indexOf(x) + 1) : "");
            }
        }

        if (this.cropMapper.selectByPrimaryKey(crop.getId()) == null) {
            this.cropMapper.insert(crop);
        }

        pedigreesDatas.forEach(x -> {
            if (this.pedigreeMapper.selectByPrimaryKey(x.getId()) == null) {
                this.pedigreeMapper.insert(x);
            }
        });
        cropPedigrees.forEach(x -> {
            List<CropPedigree> inDatas = this.cropPedigreeMapper.selectByPrimaryKey(x.getCropId());
            List<Integer> collect = inDatas.stream().map(CropPedigree::getPedigreeId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(inDatas) || !collect.contains(x.getPedigreeId())) {
                this.cropPedigreeMapper.insert(x);
            }
        });
        System.out.println(JSON.toJSONString(crop));
        System.out.println(JSON.toJSONString(pedigreesDatas));
        System.out.println(JSON.toJSONString(cropPedigrees));
//        FileWriteUtil.getFileWrite().wirteFile(JSON.toJSONString(datas));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void setSiteCooick(String key, String value) {
        this.site.addCookie(key, value);
    }

    public int getId(String name) {
        Crop crop = cropMapper.selectByCropName(name);
        if (crop == null || StringUtils.isEmpty(crop.getId().toString())) {
            int i = cropMapper.selectMaxId();
            return i + 1;
        }
        return crop.getId();
    }

    public int[] getPedigreeId(String name, int num) {
        Pedigree pedigree = pedigreeMapper.selectByName(name);
        if (pedigree == null || StringUtils.isEmpty(pedigree.getId().toString())) {
            int i = pedigreeMapper.selectMaxId();
            num += 1;
            return new int[]{i + num, num};
        }
        return new int[]{pedigree.getId(), num};
    }
}
