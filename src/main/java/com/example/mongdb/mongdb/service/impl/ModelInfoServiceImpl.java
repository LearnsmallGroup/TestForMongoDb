package com.example.mongdb.mongdb.service.impl;

import com.example.mongdb.mongdb.model.entity.ModelInfo;
import com.example.mongdb.mongdb.service.IModelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;




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
    public void dealModelInfoExcel(MultipartFile file) throws Exception{
        ModelInfo minfo = new ModelInfo();
        minfo.setModelCode("Test01");
        minfo.setModelName("模板");
        minfo.setVersion("1.0");
//        Long start = System.currentTimeMillis();
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
        String fileId = modelInfoService.saveFile(file.getInputStream(),file.getOriginalFilename());
        minfo.setWorkBookContentId(fileId);
        modelInfoService.save(minfo);
        System.out.println("上传成功！");
    }
}
