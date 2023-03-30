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
 * PodDNSConfigOption defines DNS resolver options of a pod.
 */
@ApiModel(description = "PodDNSConfigOption defines DNS resolver options of a pod.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-12-25T06:46:52.786Z[Etc/UTC]")
public class DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecDnsConfigOptions {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  private String value;


  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecDnsConfigOptions name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Required.
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Required.")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecDnsConfigOptions value(String value) {
    
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getValue() {
    return value;
  }


  public void setValue(String value) {
    this.value = value;
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
    sb.append("class DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecDnsConfigOptions {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
