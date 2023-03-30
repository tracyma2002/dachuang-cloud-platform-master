package cn.edu.ecust.web;

import cn.edu.ecust.common.exception.AppException;
import cn.edu.ecust.domain.entity.AutoScaler.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.beans.factory.annotation.Value;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Main {

//    @Value("${k8s.sharePvcName}")
//    private String sharePvcName;

    public static V1ObjectMeta createAutoScalerMeta(String name,String namespace){
        //指定其元数据
        V1ObjectMeta autoScalerMeta = new V1ObjectMeta();
        Map<String, String> annotations = new HashMap<String, String>();
        annotations.put("test-annotations-key","test-annotations-value");
        autoScalerMeta.annotations(annotations);
        Map<String, String> labels = new HashMap<String, String>();
        labels.put("test-labels-key", "test-labels-value");
        autoScalerMeta.labels(labels);
        autoScalerMeta.name(name);
        autoScalerMeta.namespace(namespace);
        return autoScalerMeta;
    }

    public static V1ObjectMeta createAutoScalerSpecPodTemplateMetedata(String name,String stuName){
        String sharePvcName = "ai-resource"; //共享盘 pvc的名字
        String storageSize = "100Mi";//指定目录大小
        String nfsServerAddress = "172.20.136.195";//nas服务器地址
        String nfsServerPath = "/"+ sharePvcName + "/"+stuName;    //subpath路径在NAS上
        String nasPvcName = "ai-resource"; //学生自己nas盘的名称

        V1ObjectMeta autoScalerSpecPodTemplate_metedata = new V1ObjectMeta();
        Map<String, String> labels = new HashMap<String, String>();
        labels.put("test-labels-key", "test-labels-value");
        // PVC - 共享盘
        labels.put("voyager.cn/auto-mount-share-pvc", "false"); //自动挂载共享盘
        // 自己的盘
        labels.put("voyager.cn/auto-create-pvc", "true");   //自动创建学生自己的pvc
        labels.put("voyager.cn/voyager-valid-storage-type", "true");
        labels.put("owner", "autoscaler-test-"+stuName);
        autoScalerSpecPodTemplate_metedata.labels(labels);
        Map<String, String> annotations = new HashMap<String, String>();
        annotations.put("test-annotations-key","test-annotations-value");

        //使用真实IP
        annotations.put("v1.multus-cni.io/default-network","default/net-macvlan");
        // PVC - 共享盘
//        annotations.put("voyager.cn/share-pvc-names",sharePvcName);    //共享盘 pvc的名字
//        annotations.put("voyager.cn/prefix-mount-path","/home/workdir/jupyter-workdir/dir"); //共享盘的挂载地址
        // 自己的盘
        annotations.put("voyager.cn/storage-size",storageSize);     //指定目录大小
        annotations.put("voyager.cn/nfs-server-address",nfsServerAddress); //nas服务器地址
        annotations.put("voyager.cn/pv-nas-name",nasPvcName); //nas盘的名称
        annotations.put("voyager.cn/nfs-server-path",nfsServerPath);   //subpath路径在NAS上
        autoScalerSpecPodTemplate_metedata.annotations(annotations);
        return autoScalerSpecPodTemplate_metedata;
    }

    public static DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpec createAutoScalerSpecPodTemplateSpec(String name, String image){
        //>>>>podTemplate需要指定他的spec
        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpec autoScalerSpecPodTemplateSpec = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpec();
        //>>>>>>PodTemplateSpec需要设定他的所有containers
        List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecContainers> containersList = new ArrayList<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecContainers>();
        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecContainers container1 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecContainers();
        //>>>>>>>>每一个containers需要设定他的ports
        List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecPorts> PortsList = new ArrayList<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecPorts>();
        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecPorts Port1 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecPorts();
        List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts> volumeMounts = new ArrayList<>();
        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts volumeMounts1 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts();
        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts volumeMounts2 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts();
        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts volumeMounts3 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts();
        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts volumeMounts4 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumeMounts();

//        volumeMounts1.setMountPath("/home/workdir/jupyter-workdir/dir/sharing");
//        volumeMounts1.setName("teacher-a-sharing");
//        volumeMounts.add(volumeMounts1);
//
//        //学生自己的盘
//        volumeMounts2.setMountPath("/home/workdir/jupyter-workdir/javakernel");
//        volumeMounts2.setName("student-1-gc-wms-pvc-nas");
//        volumeMounts2.setSubPath("javakernel");
//        volumeMounts.add(volumeMounts2);
//
//        volumeMounts3.setMountPath("/home/workdir/jupyter-workdir/logs");
//        volumeMounts3.setName("student-1-gc-wms-pvc-nas");
//        volumeMounts3.setSubPath("logs");
//        volumeMounts.add(volumeMounts3);
//
//        volumeMounts4.setMountPath("/home/workdir/jupyter-workdir/dir");
//        volumeMounts4.setName("student-1-gc-wms-pvc-nas");
//        volumeMounts4.setSubPath("dir");
//        volumeMounts.add(volumeMounts4);

        Port1.setContainerPort(9876);
        PortsList.add(Port1);
        container1.setPorts(PortsList);
        //>>>>>>>>每一个containers需要设定他的args、command、image、imagePullPolicy、name
        container1.setName("base-image-container-v3");
        List<String> commands = new ArrayList<String>();
//        commands.add("/bin/sh");
        commands.add("/bin/sh");
        container1.setCommand(commands);
        List<String> container_args = new ArrayList<String>();
        container_args.add("-c");
//        container_args.add("chmod 774 /home/workdir/start.sh && /home/workdir/start.sh && sleep 99999999");
//        container_args.add("jupyter-lab --allow-root --ServerApp.base_url=/jupyter/"+name+" && sleep 99999999");
        container_args.add("export PATH=/root/miniconda3/lib/:$PATH && export PATH=/root/miniconda3/share/:$PATH && export PATH=/root/miniconda3/bin/:$PATH" +
                " && jupyter-lab --allow-root --ServerApp.base_url=/jupyter/"+name+" && sleep 99999999");
        container1.setArgs(container_args);
        container1.setImage(image);
        container1.setImagePullPolicy("IfNotPresent");
//        container1.setVolumeMounts(volumeMounts);
        containersList.add(container1);
        autoScalerSpecPodTemplateSpec.setContainers(containersList);

        //>>>>>>PodTemplateSpec需要设定他的所有volumes
//        List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumes> volumes = new ArrayList<>();
//        //共享盘
//        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumes volumes1 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumes();
//        volumes1.setName("teacher-a-sharing");
//        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecPersistentVolumeClaim persistentVolumeClaim = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecPersistentVolumeClaim();
//        persistentVolumeClaim.setClaimName("teacher-a-sharing");
//        volumes1.setPersistentVolumeClaim(persistentVolumeClaim);
//        volumes.add(volumes1);
//
//        //学生自己的盘
//        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumes volumes2 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecVolumes();
//        volumes2.setName("student-1-gc-wms-pvc-nas");
//        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecPersistentVolumeClaim persistentVolumeClaim2 = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecPersistentVolumeClaim();
//        persistentVolumeClaim2.setClaimName("student-1-gc-wms-pvc-nas");
//        volumes2.setPersistentVolumeClaim(persistentVolumeClaim2);
//        volumes.add(volumes2);
//
//        autoScalerSpecPodTemplateSpec.setVolumes(volumes);
        return autoScalerSpecPodTemplateSpec;
    }

    public static DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpec createAutoScalerSpecServiceTemplateSpec(){
        DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpec SpecServiceTemplateSpec = new DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpec();
        List<DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts> SpecServiceTemplateSpecPorts = new ArrayList<>();
        DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts specServiceTemplateSpecPort = new DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts();
        specServiceTemplateSpecPort.setName("web0");
        specServiceTemplateSpecPort.setProtocol("TCP");
        specServiceTemplateSpecPort.setPort(9876);
        specServiceTemplateSpecPort.setTargetPort(9876);
        SpecServiceTemplateSpecPorts.add(specServiceTemplateSpecPort);
        SpecServiceTemplateSpec.setPorts(SpecServiceTemplateSpecPorts);
        return SpecServiceTemplateSpec;
    }

    public static DomainGpugroupGpugroupV1AutoScalerSpecIngressConfig createAutoScalerSpecIngressConfig(String name, String namespace){
        // metadata:

        V1ObjectMeta meta = new V1ObjectMeta();
        Map<String, String> annotations = new HashMap<String, String>();
        annotations.put("kubernetes.io/ingress.class","nginx");
        annotations.put("nginx.ingress.kubernetes.io/rewrite-target","/jupyter/"+name+"/$2");
        meta.annotations(annotations);
        meta.name("jupyter-ingress");
        meta.namespace(namespace);

        //spec:
        DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpec spec = new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpec();
        List<DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecRules> rules = new ArrayList<>();
        DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecRules rules1 = new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecRules();
        DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecHttp http = new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecHttp();
        List<DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecHttpPaths> paths = new ArrayList<DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecHttpPaths>();
        DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecHttpPaths paths1 = new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecHttpPaths();
        DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecHttpBackend backend = new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecHttpBackend();
        DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecDefaultBackendService service = new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecDefaultBackendService();
        DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecDefaultBackendServicePort port = new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfigSpecDefaultBackendServicePort();

        port.setNumber(9876);
        service.setName(name+"-service");
        service.setPort(port);
        backend.setService(service);
        paths1.setBackend(backend);
        paths1.setPath("/jupyter/"+name+"(/|$)(.*)");
        paths1.setPathType("Prefix");
        paths.add(paths1);
        http.setPaths(paths);
        rules1.setHost("www.voyager-alpha.com");
        rules1.setHttp(http);
        rules.add(rules1);
        spec.setRules(rules);

        return new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfig("networking.k8s.io/v1","Ingress",meta,spec);
    }


    public static CoreV1Api getApi() throws IOException {
        // 加载k8s的config证书 并拉起访问集群
        String kubeConfigPath = "web/src/main/resources/k8s/config";
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);

        // 确认库版本 生成对象访问
        CoreV1Api api = new CoreV1Api();

        return api;
    }
    public static void createNamespace(String name) throws IOException {
//        createConnection();
        CoreV1Api api = getApi();
//        CoreV1Api api = new CoreV1Api();
//        AppsV1Api appsV1Api=new AppsV1Api();
        V1Namespace body=new V1Namespace();
        V1ObjectMeta meta=new V1ObjectMeta();
        meta.setNamespace(name);
        meta.setName(name);
        body.setMetadata(meta);
//        body.setApiVersion("apps/v1");
//        body.setKind("Deployment");
        /*V1Deployment body=new V1Deployment();
        body.setApiVersion("apps/v1");
        body.setKind("Deployment");
        V1ObjectMeta meta=new V1ObjectMeta();
        meta.setNamespace(name);
        meta.setName(name);
        body.setMetadata(meta);
        V1DeploymentSpec spec=new V1DeploymentSpec();
        spec.setReplicas(1);
        body.setSpec(spec);*/
        try {
//            appsV1Api.createNamespacedDeployment(name, body, true, null, null);
            V1Namespace result =api.createNamespace(body,"", null, null);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#createClusterCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    public static void deleteAutoscaler(String name,String namespace) throws IOException {
//        createConnection();
        String kubeConfigPath = "web/src/main/resources/k8s/config";
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);
        CustomObjectsApi api = new CustomObjectsApi(client);
//        CoreV1Api api = new CoreV1Api();
//        AppsV1Api appsV1Api=new AppsV1Api();
        String group = "gpugroup.gpugroup.domain";
        String version = "v1";
        String plural = "autoscalers"; // String | The custom resource's plural name. For TPRs this would be lowercase plural kind.
        Integer gracePeriodSeconds = 56; // Integer | The duration in seconds before the object should be deleted. Value must be non-negative integer. The value zero indicates delete immediately. If this value is nil, the default grace period for the specified type will be used. Defaults to a per object value if not specified. zero means delete immediately.
        Boolean orphanDependents = true; // Boolean | Deprecated: please use the PropagationPolicy, this field will be deprecated in 1.7. Should the dependent objects be orphaned. If true/false, the \"orphan\" finalizer will be added to/removed from the object's finalizers list. Either this field or PropagationPolicy may be set, but not both.
        String propagationPolicy = "propagationPolicy_example"; // String | Whether and how garbage collection will be performed. Either this field or OrphanDependents may be set, but not both. The default policy is decided by the existing finalizer set in the metadata.finalizers and the resource-specific default policy.
        String dryRun = "dryRun_example"; // String | When present, indicates that modifications should not be persisted. An invalid or unrecognized dryRun directive will result in an error response and no further processing of the request. Valid values are: - All: all dry run stages will be processed
        V1DeleteOptions body = new V1DeleteOptions(); // V1DeleteOptions |
        try {
            Object result = api.deleteNamespacedCustomObject(group, version, namespace, plural, name, gracePeriodSeconds, orphanDependents, propagationPolicy, dryRun, body);
            System.out.println(result);
//            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#createClusterCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

//    public static void createNamespace(String namespace) throws IOException {
//        String plural = "autoscalers";
//        String pretty = ""; // String | If 'true', then the output is pretty printed.
//        CoreV1Api api = getApi();
//
//
//        Namespace namespace =
//                Namespace.newBuilder().setMetadata(ObjectMeta.newBuilder().setName("test").build()).build();
//
//        api.createNamespace(namespace1,pretty,);
//
//    }

    public static void createAutoscaler(String name, String namespace,String image,String stuName) throws IOException, ApiException {
        // 加载k8s的config证书 并拉起访问集群
        String kubeConfigPath = "web/src/main/resources/k8s/config";
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);

        // 确认库版本 生成对象访问
        CoreV1Api api = new CoreV1Api();
        // 检查的list集群内某个命名空间下的Pods 并将他打印出来
        V1PodList list =
                api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }
        /**
         *
         * 创建autoscaler
         *
         * yaml:
         * apiVersion: gpugroup.gpugroup.domain/v1
         * kind: AutoScaler
         * metadata:
         *   annotations:
         *     test-annotations-key: test-annotations-value
         *   labels:
         *     test-labels-key: test-labels-value
         *   name: autoscaler-test-002
         *   namespace: test-scaler-002
         * spec:
         *   replicas: 1
         *   autoCreateService: true
         *   podsCreationTimeOutSeconds: 100
         *   podTemplate:
         *     metadata:
         *       annotations:
         *         test-annotations-key: test-annotations-value
         *       labels:
         *         owner: autoscaler-test-002
         *         test-labels-key: test-labels-value
         *     spec:
         *       containers:
         *         - name: base-image-container-v3
         *           command:
         *             - /bin/sh
         *           args:
         *             - '-c'
         *             - >-
         *               chmod 774 /home/workdir/start.sh && /home/workdir/start.sh &&
         *               sleep 99999999
         *           image: 'base-ubuntu:18.04'
         *           imagePullPolicy: IfNotPresent
         *           ports:
         *             - containerPort: 9876
         */

        System.out.println("create auto scaler resource");
        /**
         * spec:
         *      podTemplate
         */

        DomainGpugroupGpugroupV1AutoScalerSpecPodTemplate autoScalerSpecPodTemplate = new DomainGpugroupGpugroupV1AutoScalerSpecPodTemplate(
                createAutoScalerSpecPodTemplateMetedata(name,stuName),
                createAutoScalerSpecPodTemplateSpec(name,image)
        );
        /**
         * spec:
         *      serviceTemplateSpec
         */
        DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpec SpecServiceTemplateSpec = createAutoScalerSpecServiceTemplateSpec();
        /**
         * spec:
         *      ingressConfig
         */
        DomainGpugroupGpugroupV1AutoScalerSpecIngressConfig ingressConfig = createAutoScalerSpecIngressConfig(name,namespace);
        /**
         * spec:
         */
        DomainGpugroupGpugroupV1AutoScalerSpec autoScalerSpec = new DomainGpugroupGpugroupV1AutoScalerSpec(
                false,
                false,
                new DomainGpugroupGpugroupV1AutoScalerSpecIngressConfig(),
                autoScalerSpecPodTemplate,
                100,
                1,
                new DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpec()
        );
        //autocsaler
        DomainGpugroupGpugroupV1AutoScaler autoScalerApi = new DomainGpugroupGpugroupV1AutoScaler(
                "gpugroup.gpugroup.domain/v1",
                "AutoScaler",
                createAutoScalerMeta(name,namespace),
                autoScalerSpec);




        //转换成json输出查看
        String autoScalerApiJson = new Gson().toJson(autoScalerApi);
        System.out.println(autoScalerApiJson);

        // 触发创建
        CustomObjectsApi customApi = new CustomObjectsApi(client);
        Object body = autoScalerApi; // Object | The JSON schema of the Resource to create.
//        String namespace = "test-scaler";
        String group = "gpugroup.gpugroup.domain";
        String version = "v1";
        //String dryRun = "false"; // String | When present, indicates that modifications should not be persisted. An invalid or unrecognized dryRun directive will result in an error response and no further processing of the request. Valid values are: - All: all dry run stages will be processed
        String fieldManager = ""; // String | fieldManager is a name associated with the actor or entity that is making these changes. The value must be less than or 128 characters long, and only contain printable characters, as defined by https://golang.org/pkg/unicode/#IsPrint. This field is required for apply requests (application/apply-patch) but optional for non-apply patch types (JsonPatch, MergePatch, StrategicMergePatch).
        try {
            Object result = customApi.createNamespacedCustomObject(group, version, namespace, "autoscalers", body, null, null, null);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#createClusterCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }


    public static void deleteNamespacedAllObject() throws IOException {
        String kubeConfigPath = "web/src/main/resources/k8s/config";
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api(client);
//        api.delet


    }

    public static void deleteAllAutoscaler(String namespace) throws IOException {
//        createConnection();
        String kubeConfigPath = "web/src/main/resources/k8s/config";
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);
        CustomObjectsApi customApi = new CustomObjectsApi(client);
//        CoreV1Api api = new CoreV1Api();
//        AppsV1Api appsV1Api=new AppsV1Api();
        String group = "gpugroup.gpugroup.domain";
        String version = "v1";
        String plural = "autoscalers"; // String | The custom resource's plural name. For TPRs this would be lowercase plural kind.
        Integer gracePeriodSeconds = 56; // Integer | The duration in seconds before the object should be deleted. Value must be non-negative integer. The value zero indicates delete immediately. If this value is nil, the default grace period for the specified type will be used. Defaults to a per object value if not specified. zero means delete immediately.
        Boolean orphanDependents = true; // Boolean | Deprecated: please use the PropagationPolicy, this field will be deprecated in 1.7. Should the dependent objects be orphaned. If true/false, the \"orphan\" finalizer will be added to/removed from the object's finalizers list. Either this field or PropagationPolicy may be set, but not both.
        String propagationPolicy = "propagationPolicy_example"; // String | Whether and how garbage collection will be performed. Either this field or OrphanDependents may be set, but not both. The default policy is decided by the existing finalizer set in the metadata.finalizers and the resource-specific default policy.
        String dryRun = "dryRun_example"; // String | When present, indicates that modifications should not be persisted. An invalid or unrecognized dryRun directive will result in an error response and no further processing of the request. Valid values are: - All: all dry run stages will be processed
        V1DeleteOptions body = new V1DeleteOptions(); // V1DeleteOptions |
        try {
            Object result = customApi.deleteCollectionNamespacedCustomObject(group,version,namespace,plural,null,gracePeriodSeconds,orphanDependents,propagationPolicy,dryRun,body);
//             customApi.deleteNamespacedCustomObject(group, version, namespace, plural, name, gracePeriodSeconds, orphanDependents, propagationPolicy, dryRun, body);
            System.out.println(result);
//            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#deleteCollectionNamespacedCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException, ApiException, InterruptedException {
//        System.out.println("Hello world!");
//
        String name = "autoscaler-001";
        String namespace = "teacher-t123456-course231";
        String stuName = "pvc"+name;
//        String image = "jupyterlab-base:v1";
        String image = "cppjupyterlab2:v1";
////        for (int i = 6; i < 7; i++) {
////            createAutoscaler("00"+i,namespace,image);
////            sleep(3000);
////        }
//        deleteAutoscaler(name,namespace);
        createAutoscaler(name,namespace,image,stuName);
//        String testnamespce = "test-ns-001";
//         deleteAllAutoscaler(namespace);
//        createNamespace(testnamespce);

//        /**
//         * 获取资源信息
//         */
//        CustomObjectsApi customApi2 = new CustomObjectsApi(client);
//        String group2 = "gpugroup.gpugroup.domain";
//        String namespace2 = "test-scaler";
//        String version2 = "v1";
//        String plural = "autoscalers"; // String | The custom resource's plural name. For TPRs this would be lowercase plural kind.
//        String pretty = ""; // String | If 'true', then the output is pretty printed.
//        Boolean allowWatchBookmarks = true; // Boolean | allowWatchBookmarks requests watch events with type \"BOOKMARK\". Servers that do not implement bookmarks may ignore this flag and bookmarks are sent at the server's discretion. Clients should not assume bookmarks are returned at any specific interval, nor may they assume the server will send any BOOKMARK event during a session. If this is not a watch, this field is ignored. If the feature gate WatchBookmarks is not enabled in apiserver, this field is ignored.
//        String _continue = ""; // String | The continue option should be set when retrieving more results from the server. Since this value is server defined, clients may only use the continue value from a previous query result with identical query parameters (except for the value of continue) and the server may reject a continue value it does not recognize. If the specified continue value is no longer valid whether due to expiration (generally five to fifteen minutes) or a configuration change on the server, the server will respond with a 410 ResourceExpired error together with a continue token. If the client needs a consistent list, it must restart their list without the continue field. Otherwise, the client may send another list request with the token received with the 410 error, the server will respond with a list starting from the next key, but from the latest snapshot, which is inconsistent from the previous list results - objects that are created, modified, or deleted after the first list request will be included in the response, as long as their keys are after the \"next key\".  This field is not supported when watch is true. Clients may start a watch from the last resourceVersion value returned by the server and not miss any modifications.
//        String fieldSelector = ""; // String | A selector to restrict the list of returned objects by their fields. Defaults to everything.
//        String labelSelector = ""; // String | A selector to restrict the list of returned objects by their labels. Defaults to everything.
//        Integer limit = 56; // Integer | limit is a maximum number of responses to return for a list call. If more items exist, the server will set the `continue` field on the list metadata to a value that can be used with the same initial query to retrieve the next set of results. Setting a limit may return fewer than the requested amount of items (up to zero items) in the event all requested objects are filtered out and clients should only use the presence of the continue field to determine whether more results are available. Servers may choose not to support the limit argument and will return all of the available results. If limit is specified and the continue field is empty, clients may assume that no more results are available. This field is not supported if watch is true.  The server guarantees that the objects returned when using continue will be identical to issuing a single list call without a limit - that is, no objects created, modified, or deleted after the first request is issued will be included in any subsequent continued requests. This is sometimes referred to as a consistent snapshot, and ensures that a client that is using limit to receive smaller chunks of a very large result can ensure they see all possible objects. If objects are updated during a chunked list the version of the object that was present at the time the first list result was calculated is returned.
//        String resourceVersion = ""; // String | When specified with a watch call, shows changes that occur after that particular version of a resource. Defaults to changes from the beginning of history. When specified for list: - if unset, then the result is returned from remote storage based on quorum-read flag; - if it's 0, then we simply return what we currently have in cache, no guarantee; - if set to non zero, then the result is at least as fresh as given rv.
//        String resourceVersionMatch = ""; // String | resourceVersionMatch determines how resourceVersion is applied to list calls. It is highly recommended that resourceVersionMatch be set for list calls where resourceVersion is set See https://kubernetes.io/docs/reference/using-api/api-concepts/#resource-versions for details.  Defaults to unset
//        Integer timeoutSeconds = 56; // Integer | Timeout for the list/watch call. This limits the duration of the call, regardless of any activity or inactivity.
//        Boolean watch = false; // Boolean | Watch for changes to the described resources and return them as a stream of add, update, and remove notifications.
//        Object result2 = null;
//        try {
//
//            //* 列出指定命名空间下的所有自定义对象
//            Object result = customApi2.listNamespacedCustomObject(group2, version2, namespace2, plural, pretty, allowWatchBookmarks, _continue, fieldSelector, labelSelector, limit, resourceVersion, resourceVersionMatch, timeoutSeconds, watch);
//            Gson gson =new Gson();
//            System.out.println(gson.toJson(result));
//
//            //* 集群里面读取资源，注意每个字段的含义
//            result2 = customApi2.getNamespacedCustomObject(group2, version2, namespace2, "autoscalers", "autoscaler-test-002");
//            System.out.println(gson.toJson(result2));
//            //获取状态
//            JsonElement jsonElement_result2 = gson.toJsonTree(result2);
//            JsonElement jsonElement_status = jsonElement_result2.getAsJsonObject().get("status");
//            DomainGpugroupGpugroupV1AutoScalerStatus status = gson.fromJson(jsonElement_status,DomainGpugroupGpugroupV1AutoScalerStatus.class);
//            System.out.println(status.toString());
//
//        } catch (ApiException e) {
//            System.err.println("Exception when calling CustomObjectsApi#createClusterCustomObject");
//            System.err.println("Status code: " + e.getCode());
//            System.err.println("Reason: " + e.getResponseBody());
//            System.err.println("Response headers: " + e.getResponseHeaders());
//            System.err.println("Exception when calling CustomObjectsApi#listClusterCustomObject");
//            e.printStackTrace();
//        }


    }
}