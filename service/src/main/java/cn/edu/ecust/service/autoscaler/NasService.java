//package cn.edu.ecust.service.autoscaler;
//
//
//import cn.edu.ecust.dao.mapper.AutoscalerMapper;
//import cn.edu.ecust.dao.mapper.NasMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.exceptions.ServerException;
//import com.aliyuncs.profile.DefaultProfile;
//import com.google.gson.Gson;
//import java.util.*;
//import com.aliyuncs.nas.model.v20170626.*;
//
//
//
///**
// * @Description:
// * @author: Jiaming Zheng
// * @Date: Create in 19:10 2022/9/27
// * @Modified By:
// **/
//
//@Service
//public class NasService {
//
//    @Autowired
//    private NasMapper nasMapper;
//
//
//
//    private void doCreateNas(){
//
//        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "<accessKeyId>", "<accessSecret>");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CreateFileSystemRequest request = new CreateFileSystemRequest();
//        request.setRegionId("cn-shanghai");
//        request.setProtocolType("standard");
//        request.setStorageType("Capacity");
//        request.setFileSystemType("standard");
//        request.setChargeType("PayAsYouGo");
//        request.setCapacity("1");
//
//        try {
//            CreateFileSystemResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            System.out.println("ErrCode:" + e.getErrCode());
//            System.out.println("ErrMsg:" + e.getErrMsg());
//            System.out.println("RequestId:" + e.getRequestId());
//        }
//
//
//    }
//}
