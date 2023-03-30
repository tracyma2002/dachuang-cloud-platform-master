package cn.edu.ecust.web.controller.template;


import cn.edu.ecust.domain.entity.TemplateExperiment;
import cn.edu.ecust.domain.entity.TemplateInfo;
import cn.edu.ecust.service.Template.TemplateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * @Description: 查询课程模板信息
 * @author: Fen Min
 * @Date: Create in 20:16 2022/7/19
 **/
@RestController
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "/selectTemplateInfo1",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查询模板课程1")
    public List<TemplateInfo> selectTemplateInfo1(){
         return templateService.selectTemplateInfo1();
    }
    @RequestMapping(value = "/selectTemplateInfo2",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查询模板课程2")
    public List<TemplateInfo> selectTemplateInfo2(){
        return templateService.selectTemplateInfo2();
    }
    @RequestMapping(value = "/selectTemplateInfo3",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查询模板课程3")
    public List<TemplateInfo> selectTemplateInfo3(){
        return templateService.selectTemplateInfo3();
    }

//    @RequestMapping(value = "/selectTemplateInfo1",method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation("查询模板课程实验1")
//    public List<TemplateInfo> selectTemplateInfo1(){
//        return templateService.selectTemplateExperiment(courseTemplatesId);
//    }
    @RequestMapping(value = "/selectTemplateExperiment",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查询模板课程实验")
    @ApiResponses({
            @ApiResponse(code = 0000, response = String.class,message = "查询模板课程实验1")
    })
    public List<TemplateExperiment> selectTemplateExperiment(@ApiParam(value = "课程模板Id",required = true)  @RequestParam  int courseTemplatesId
    ){

        return templateService.selectTemplateExperiment(courseTemplatesId);
    }



}
