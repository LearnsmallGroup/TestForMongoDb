package com.example.mongdb.mongdb.service.impl;

import com.example.mongdb.mongdb.model.entity.ModelInfo;
import com.example.mongdb.mongdb.service.IModelInfoService;
import com.grapecity.documents.excel.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


/**
 * 模板实现类
 * @create by Kellach 2019年9月29日
 */
@Slf4j
@Service
public class ModelInfoServiceImpl extends BaseMDBServiceImpl<ModelInfo> implements IModelInfoService {

    @Autowired
    private IModelInfoService modelInfoService;


    /**
     *
     * @param file
     */
    @Override
    public void dealModelInfoExcel(MultipartFile file,String fileCode) throws Exception{
        ModelInfo minfo = new ModelInfo();
        minfo.setModelCode(fileCode);
        minfo.setModelName("模板");
        minfo.setVersion("1.0");
        Long start = System.currentTimeMillis();
//        Workbook workbook = new Workbook();
//        try {
//            XlsxOpenOptions options = new XlsxOpenOptions();
//            options.setImportFlags(EnumSet.of(ImportFlags.Data,
//                        ImportFlags.Formulas,
//                        ImportFlags.Table,
//                        ImportFlags.MergeArea,
//                        ImportFlags.Style,
//                        ImportFlags.ConditionalFormatting,
//                        ImportFlags.DataValidation,
//                        ImportFlags.PivotTable,
//                        ImportFlags.Shapes));
//            workbook.open(file.getInputStream(),options);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw e;
//        }
//        Long end = System.currentTimeMillis();
//        String fileJson = workbook.toJson();
//        InputStream tmpTest = IOUtils.toInputStream(fileJson,"UTF-8");
        System.out.println("数据大小："+file.getSize());
//        System.out.println("所用时间："+(end-start)/1000);
//        String fileId = modelInfoService.saveFile(tmpTest,file.getOriginalFilename());
        String fileId = modelInfoService.saveFile(file.getInputStream(),file.getOriginalFilename());
        minfo.setWorkBookContentId(fileId);
        modelInfoService.save(minfo);
        System.out.println("上传成功！");
    }

    /**
     *
     * @param file
     * @param fileCode
     * @throws Exception
     */
    @Override
    public void saveExcelBySheet(MultipartFile file, String fileCode) throws Exception {
        Long start = System.currentTimeMillis();
        Workbook workbook = new Workbook();
        XlsxOpenOptions options = new XlsxOpenOptions();
        options.setImportFlags(EnumSet.of(ImportFlags.Data,
                    ImportFlags.Formulas,
                    ImportFlags.Table,
                    ImportFlags.MergeArea,
                    ImportFlags.Style,
                    ImportFlags.ConditionalFormatting,
                    ImportFlags.DataValidation,
                    ImportFlags.PivotTable,
                    ImportFlags.Shapes));
        workbook.open(file.getInputStream(),options);
        IWorksheets worksheets = workbook.getWorksheets();
        int count = worksheets.getCount();
        List<ModelInfo> sheets = new ArrayList<>();
        for(int i=0;i<count;i++){
            IWorksheet sheet = worksheets.get(i);
            ModelInfo minfo = new ModelInfo();
            minfo.setModelCode("sheetTest");
            minfo.setModelName(sheet.getName());
            minfo.setVersion("2.0");
            minfo.setSheetJson(sheet.toJson());
            minfo.setSheetIndex(sheet.getIndex());
            Visibility visible = sheet.getVisible();
            if("Hidden".equals(visible.name())){
                minfo.setHide(true);
            }else{
                minfo.setHide(false);
            }
            sheets.add(minfo);
        }
        modelInfoService.saveList(sheets);
        System.out.println("保存sheet用时："+(System.currentTimeMillis()-start)/1000+" 秒" );
    }

    /**
     *
     * @param file
     * @return
     */
    @Override
    public String getfileJson(InputStream file) {
        Workbook workbook = new Workbook();
        String str = null;
        try{
            str = IOUtils.toString(file,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        workbook.fromJson(str);
        IWorksheets worksheets = workbook.getWorksheets();
        for(int i =0;i<worksheets.getCount();i++){
            IWorksheet sheet = worksheets.get(i);
            ITable iTable = sheet.getTables().get(0);
            String tbName = iTable.getName();
        }
        return str;
    }

    /**
     *
     * @param modelCode
     * @return
     */
    @Override
    public String getNewWorkBookByCode(String modelCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("modelCode").is(modelCode));
        List<ModelInfo> mlist = modelInfoService.getByQuery(query);
        Workbook workbook = new Workbook();
        IWorksheets sheets = workbook.getWorksheets();
        int count = mlist.size();
        for(int i =0;i<count;i++){
            sheets.add();
            IWorksheet sheet = sheets.get(i);
            ModelInfo modelInfo = mlist.get(i);
            sheet.setName(modelInfo.getModelName());
            sheet.setIndex(i);
            sheet.fromJson(modelInfo.getSheetJson());
        }
        return workbook.toJson();
    }
}
