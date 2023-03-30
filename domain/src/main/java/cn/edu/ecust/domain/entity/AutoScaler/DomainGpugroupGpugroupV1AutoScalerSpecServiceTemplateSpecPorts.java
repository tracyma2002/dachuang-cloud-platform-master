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
 * ServicePort contains information on service&#39;s port.
 */
@ApiModel(description = "ServicePort contains information on service's port.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-12-25T06:46:52.786Z[Etc/UTC]")
public class DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts {
  public static final String SERIALIZED_NAME_APP_PROTOCOL = "appProtocol";
  @SerializedName(SERIALIZED_NAME_APP_PROTOCOL)
  private String appProtocol;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_NODE_PORT = "nodePort";
  @SerializedName(SERIALIZED_NAME_NODE_PORT)
  private Integer nodePort;

  public static final String SERIALIZED_NAME_PORT = "port";
  @SerializedName(SERIALIZED_NAME_PORT)
  private Integer port;

  public static final String SERIALIZED_NAME_PROTOCOL = "protocol";
  @SerializedName(SERIALIZED_NAME_PROTOCOL)
  private String protocol;

  public static final String SERIALIZED_NAME_TARGET_PORT = "targetPort";
  @SerializedName(SERIALIZED_NAME_TARGET_PORT)
  private Object targetPort;


  public DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts appProtocol(String appProtocol) {
    
    this.appProtocol = appProtocol;
    return this;
  }

   /**
   * The application protocol for this port. This field follows standard Kubernetes label syntax. Un-prefixed names are reserved for IANA standard service names (as per RFC-6335 and http://www.iana.org/assignments/service-names). Non-standard protocols should use prefixed names such as mycompany.com/my-custom-protocol. This is a beta field that is guarded by the ServiceAppProtocol feature gate and enabled by default.
   * @return appProtocol
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The application protocol for this port. This field follows standard Kubernetes label syntax. Un-prefixed names are reserved for IANA standard service names (as per RFC-6335 and http://www.iana.org/assignments/service-names). Non-standard protocols should use prefixed names such as mycompany.com/my-custom-protocol. This is a beta field that is guarded by the ServiceAppProtocol feature gate and enabled by default.")

  public String getAppProtocol() {
    return appProtocol;
  }


  public void setAppProtocol(String appProtocol) {
    this.appProtocol = appProtocol;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * The name of this port within the service. This must be a DNS_LABEL. All ports within a ServiceSpec must have unique names. When considering the endpoints for a Service, this must match the &#39;name&#39; field in the EndpointPort. Optional if only one ServicePort is defined on this service.
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The name of this port within the service. This must be a DNS_LABEL. All ports within a ServiceSpec must have unique names. When considering the endpoints for a Service, this must match the 'name' field in the EndpointPort. Optional if only one ServicePort is defined on this service.")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts nodePort(Integer nodePort) {
    
    this.nodePort = nodePort;
    return this;
  }

   /**
   * The port on each node on which this service is exposed when type is NodePort or LoadBalancer.  Usually assigned by the system. If a value is specified, in-range, and not in use it will be used, otherwise the operation will fail.  If not specified, a port will be allocated if this Service requires one.  If this field is specified when creating a Service which does not need it, creation will fail. This field will be wiped when updating a Service to no longer need it (e.g. changing type from NodePort to ClusterIP). More info: https://kubernetes.io/docs/concepts/services-networking/service/#type-nodeport
   * @return nodePort
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The port on each node on which this service is exposed when type is NodePort or LoadBalancer.  Usually assigned by the system. If a value is specified, in-range, and not in use it will be used, otherwise the operation will fail.  If not specified, a port will be allocated if this Service requires one.  If this field is specified when creating a Service which does not need it, creation will fail. This field will be wiped when updating a Service to no longer need it (e.g. changing type from NodePort to ClusterIP). More info: https://kubernetes.io/docs/concepts/services-networking/service/#type-nodeport")

  public Integer getNodePort() {
    return nodePort;
  }


  public void setNodePort(Integer nodePort) {
    this.nodePort = nodePort;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts port(Integer port) {
    
    this.port = port;
    return this;
  }

   /**
   * The port that will be exposed by this service.
   * @return port
  **/
  @ApiModelProperty(required = true, value = "The port that will be exposed by this service.")

  public Integer getPort() {
    return port;
  }


  public void setPort(Integer port) {
    this.port = port;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts protocol(String protocol) {
    
    this.protocol = protocol;
    return this;
  }

   /**
   * The IP protocol for this port. Supports \&quot;TCP\&quot;, \&quot;UDP\&quot;, and \&quot;SCTP\&quot;. Default is TCP.
   * @return protocol
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The IP protocol for this port. Supports \"TCP\", \"UDP\", and \"SCTP\". Default is TCP.")

  public String getProtocol() {
    return protocol;
  }


  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts targetPort(Object targetPort) {
    
    this.targetPort = targetPort;
    return this;
  }

   /**
   * Number or name of the port to access on the pods targeted by the service. Number must be in the range 1 to 65535. Name must be an IANA_SVC_NAME. If this is a string, it will be looked up as a named port in the target Pod&#39;s container ports. If this is not specified, the value of the &#39;port&#39; field is used (an identity map). This field is ignored for services with clusterIP&#x3D;None, and should be omitted or set equal to the &#39;port&#39; field. More info: https://kubernetes.io/docs/concepts/services-networking/service/#defining-a-service
   * @return targetPort
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number or name of the port to access on the pods targeted by the service. Number must be in the range 1 to 65535. Name must be an IANA_SVC_NAME. If this is a string, it will be looked up as a named port in the target Pod's container ports. If this is not specified, the value of the 'port' field is used (an identity map). This field is ignored for services with clusterIP=None, and should be omitted or set equal to the 'port' field. More info: https://kubernetes.io/docs/concepts/services-networking/service/#defining-a-service")

  public Object getTargetPort() {
    return targetPort;
  }


  public void setTargetPort(Object targetPort) {
    this.targetPort = targetPort;
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
    sb.append("class DomainGpugroupGpugroupV1AutoScalerSpecServiceTemplateSpecPorts {\n");
    sb.append("    appProtocol: ").append(toIndentedString(appProtocol)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nodePort: ").append(toIndentedString(nodePort)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    targetPort: ").append(toIndentedString(targetPort)).append("\n");
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
