/*
 * Kubernetes
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v1.20.8
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package cn.edu.ecust.domain.entity.AutoScaler;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * PreStop is called immediately before a container is terminated due to an API request or management event such as liveness/startup probe failure, preemption, resource contention, etc. The handler is not called if the container crashes or exits. The reason for termination is passed to the handler. The Pod&#39;s termination grace period countdown begins before the PreStop hooked is executed. Regardless of the outcome of the handler, the container will eventually terminate within the Pod&#39;s termination grace period. Other management of the container blocks until the hook completes or until the termination grace period is reached. More info: https://kubernetes.io/docs/concepts/containers/container-lifecycle-hooks/#container-hooks
 */
@ApiModel(description = "PreStop is called immediately before a container is terminated due to an API request or management event such as liveness/startup probe failure, preemption, resource contention, etc. The handler is not called if the container crashes or exits. The reason for termination is passed to the handler. The Pod's termination grace period countdown begins before the PreStop hooked is executed. Regardless of the outcome of the handler, the container will eventually terminate within the Pod's termination grace period. Other management of the container blocks until the hook completes or until the termination grace period is reached. More info: https://kubernetes.io/docs/concepts/containers/container-lifecycle-hooks/#container-hooks")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-12-25T06:46:52.786Z[Etc/UTC]")
public class DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePreStop {
  public static final String SERIALIZED_NAME_EXEC = "exec";
  @SerializedName(SERIALIZED_NAME_EXEC)
  private DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartExec exec;

  public static final String SERIALIZED_NAME_HTTP_GET = "httpGet";
  @SerializedName(SERIALIZED_NAME_HTTP_GET)
  private DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartHttpGet httpGet;

  public static final String SERIALIZED_NAME_TCP_SOCKET = "tcpSocket";
  @SerializedName(SERIALIZED_NAME_TCP_SOCKET)
  private DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartTcpSocket tcpSocket;


  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePreStop exec(DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartExec exec) {
    
    this.exec = exec;
    return this;
  }

   /**
   * Get exec
   * @return exec
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartExec getExec() {
    return exec;
  }


  public void setExec(DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartExec exec) {
    this.exec = exec;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePreStop httpGet(DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartHttpGet httpGet) {
    
    this.httpGet = httpGet;
    return this;
  }

   /**
   * Get httpGet
   * @return httpGet
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartHttpGet getHttpGet() {
    return httpGet;
  }


  public void setHttpGet(DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartHttpGet httpGet) {
    this.httpGet = httpGet;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePreStop tcpSocket(DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartTcpSocket tcpSocket) {
    
    this.tcpSocket = tcpSocket;
    return this;
  }

   /**
   * Get tcpSocket
   * @return tcpSocket
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartTcpSocket getTcpSocket() {
    return tcpSocket;
  }


  public void setTcpSocket(DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePostStartTcpSocket tcpSocket) {
    this.tcpSocket = tcpSocket;
  }


  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecLifecyclePreStop {\n");
    sb.append("    exec: ").append(toIndentedString(exec)).append("\n");
    sb.append("    httpGet: ").append(toIndentedString(httpGet)).append("\n");
    sb.append("    tcpSocket: ").append(toIndentedString(tcpSocket)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

