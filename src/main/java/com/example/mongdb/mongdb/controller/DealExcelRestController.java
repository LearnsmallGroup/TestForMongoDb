package com.example.mongdb.mongdb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mongdb.mongdb.model.entity.BaseObj;
import com.example.mongdb.mongdb.model.entity.ModelInfo;
import com.example.mongdb.mongdb.service.IModelInfoService;
import com.grapecity.documents.excel.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * 处理SPExcel的控制器
 * @create by Kellach 2019年9月29日
 */
@RestController
@RequestMapping(value = "/dealExcel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class DealExcelRestController {

    @Autowired
    private IModelInfoService modelInfoService;

    /**
     * 文件导入
     * @param request
     */
    @PostMapping(value = "/uploadFile")
    public void inputExcelModel(StandardMultipartHttpServletRequest request) throws Exception{
        Map<String, MultipartFile> fileMap = request.getFileMap();
        for(Map.Entry<String,MultipartFile> tmp:fileMap.entrySet()){
            String filename = tmp.getValue().getOriginalFilename();
            String fileCode = filename.substring(0,filename.lastIndexOf("."));
            MultipartFile file = tmp.getValue();
            modelInfoService.dealModelInfoExcel(file,fileCode);
        }
    }

    /**
     * 文件导入 By Sheet
     * @param request
     */
    @PostMapping(value = "/uploadFileBySheet")
    public void inputExcelModelBySheet(StandardMultipartHttpServletRequest request) throws Exception{
        Map<String, MultipartFile> fileMap = request.getFileMap();
        for(Map.Entry<String,MultipartFile> tmp:fileMap.entrySet()){
            String filename = tmp.getValue().getOriginalFilename();
            String fileCode = filename.substring(0,filename.lastIndexOf("."));
            MultipartFile file = tmp.getValue();
            modelInfoService.saveExcelBySheet(file,fileCode);
        }
    }

    /**
     * 获取模板的Excel文件
     * @param code
     * @return
     */
    @GetMapping(value = "/getModelSheetFile/{code}")
    public Map<String,Object> getStringSheetJson(@PathVariable("code") String code) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("modelCode").is(code));
        List<ModelInfo> mlist = modelInfoService.getByQuery(query);
        Workbook workbook = new Workbook();
        IWorksheets worksheets = workbook.getWorksheets();
        int i = 0;
        for(ModelInfo mfo : mlist){
            if(i>0) {
                worksheets.add();
            }
            IWorksheet nsheet = workbook.getWorksheets().get(i++);
            nsheet.fromJson(mfo.getSheetJson());
        }
//        IWorksheet nsheet = workbook.getWorksheets().get(0);
//        System.out.println(mlist.get(1).getSheetJson());
//        nsheet.fromJson(mlist.get(7).getSheetJson());
        Object parse = JSON.parse(workbook.toJson());
        map.put("model",parse);
        return map;
    }



    /**
     * 获取模板的Excel文件
     * @param code
     * @return
     */
    @GetMapping(value = "/getModelFile/{code}")
    public Map<String,Object> getStringJson(@PathVariable("code") String code) throws Exception{
        Map<String,Object> map = new HashMap<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("modelCode").is(code));
        ModelInfo oneByQuery = modelInfoService.getOneByQuery(query);
        InputStream file = modelInfoService.getFileById(oneByQuery.getWorkBookContentId());
        Workbook workbook = new Workbook();
////        XlsxOpenOptions options = new XlsxOpenOptions();
////        options.setImportFlags(EnumSet.of(ImportFlags.Data,
////                ImportFlags.Formulas,
////                ImportFlags.Table,
////                ImportFlags.MergeArea,
////                ImportFlags.Style,
////                ImportFlags.ConditionalFormatting,
////                ImportFlags.DataValidation,
////                ImportFlags.PivotTable,
////                ImportFlags.Shapes));
////        workbook.open(file,options);
        String str = null;
        try{
            str = IOUtils.toString(file,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
//        workbook.fromJson(str);
//        IWorksheets worksheets = workbook.getWorksheets();
//        int count = worksheets.getCount();
//        for(int i = 0;i<count;i++){
//            IWorksheet sheet = worksheets.get(i);
//            ITables tables = sheet.getTables();
//            for(ITable tb:tables){
//                String tname = tb.getName();
//                System.out.println(tname);
//                Object[][] initData = this.getInitData();
////                JSONArray jsonArray = this.getJsonArray();
////                tb.getDataRange().setValue(jsonArray.toJSONString());
////                tb.setComment(jsonArray.toJSONString());
//
////                tb.getDataRange()..setValue(jsonArray.toJSONString());
//                //组装后台数据集
////                for(Object obj:initData){
////                tb.setShowHeaders(false);
////                int c = 0;
////                for(Object[] obj:initData){
////                    tb.getRows().add(c).getRange().setValue(obj);
////                    c++;
////                }
//
//            }
//            String name = sheet.getName();
////            if(!"Summary".equals(name)){
////                sheet.delete();
////            }
//        }
//
//
////        String fileJson = workbook.toJson();
//
        map.put("source",this.packageData());
        Object parse = JSON.parse(str);
        map.put("model",parse);
//        System.out.println(fileJson);
        System.out.println("数据大小："+map.size());


        //测试文件
//        File file = new File("C:\\Users\\nitam\\Desktop\\test\\aaaa\\file.ssjson");
//        InputStream fileInput = new FileInputStream(file);
//        String workbook = IOUtils.toString(fileInput, "utf-8");
////        Object parse = JSON.parse(workbook.toJson());
////        Object parse = JSON.parse(workbook);
//        Object parse = JSONObject.toJSON(workbook);
//        map.put("model",parse);
        return map;
    }

    /**
     * 组装前台数据集
     * @create by Kellach 2019年9月30日
     * @return
     */
    private Object packageData(){
        List<BaseObj> zysh = new ArrayList<>();
        List<BaseObj> xssp = new ArrayList<>();
        List<BaseObj> lwsr = new ArrayList<>();
        for(int i =0;i<10;i++){
            BaseObj obj = new BaseObj();
            obj.setXxx("");
            obj.setCode("ABC"+i);
            obj.setName("名称"+i);
            obj.setValue(Math.random()*1000+i);
            zysh.add(obj);
            xssp.add(obj);
            lwsr.add(obj);
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("zysh",zysh);
        map.put("xssp",xssp);
        map.put("lwsr",lwsr);
        JSONObject json = new JSONObject(map);
        return json;
    }

    private JSONArray getJsonArray(){
        JSONArray lwsr = new JSONArray();
        for(int i =0;i<10;i++){
            Map<String,Object> obj = new HashMap<>();
            obj.put("xxx","");
            obj.put("code","ABC"+i);
            obj.put("name","名称"+i);
            obj.put("value",Math.random()*1000+i);
            JSONObject job = new JSONObject(obj);
            lwsr.add(job);
        }
        return lwsr;
    }


    /**
     * 后台数据绑定
     * @return
     */
    private Object[][] getInitData(){
       Object[][] data = new Object[][]{
               {"","code","哈哈",123.32312},
               {"","code23","哈哈打的费",12123.213},
               {"","code321","哈哈阿斯蒂芬",12123.213},
               {"","code","哈哈阿斯蒂芬",12123.213},
               {"","code","哈哈阿斯蒂芬",12123.213},
               {"","code","哈哈阿斯蒂芬",12123.213},
               {"","code","哈哈阿斯蒂芬",12123.213}
       };
       return data;
    }

    /**
     * 获取数据
     * @param json
     */
    @PostMapping(value = "/getJsonData")
    public void getJsonString(@RequestBody String json){
        System.out.println(json);
    }

}
