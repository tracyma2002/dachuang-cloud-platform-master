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

import java.util.ArrayList;
import java.util.List;

/**
 * A node selector term, associated with the corresponding weight.
 */
@ApiModel(description = "A node selector term, associated with the corresponding weight.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-12-25T06:46:52.786Z[Etc/UTC]")
public class DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreference {
  public static final String SERIALIZED_NAME_MATCH_EXPRESSIONS = "matchExpressions";
  @SerializedName(SERIALIZED_NAME_MATCH_EXPRESSIONS)
  private List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions> matchExpressions = null;

  public static final String SERIALIZED_NAME_MATCH_FIELDS = "matchFields";
  @SerializedName(SERIALIZED_NAME_MATCH_FIELDS)
  private List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions> matchFields = null;


  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreference matchExpressions(List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions> matchExpressions) {
    
    this.matchExpressions = matchExpressions;
    return this;
  }

  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreference addMatchExpressionsItem(DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions matchExpressionsItem) {
    if (this.matchExpressions == null) {
      this.matchExpressions = new ArrayList<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions>();
    }
    this.matchExpressions.add(matchExpressionsItem);
    return this;
  }

   /**
   * A list of node selector requirements by node&#39;s labels.
   * @return matchExpressions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "A list of node selector requirements by node's labels.")

  public List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions> getMatchExpressions() {
    return matchExpressions;
  }


  public void setMatchExpressions(List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions> matchExpressions) {
    this.matchExpressions = matchExpressions;
  }


  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreference matchFields(List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions> matchFields) {
    
    this.matchFields = matchFields;
    return this;
  }

  public DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreference addMatchFieldsItem(DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions matchFieldsItem) {
    if (this.matchFields == null) {
      this.matchFields = new ArrayList<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions>();
    }
    this.matchFields.add(matchFieldsItem);
    return this;
  }

   /**
   * A list of node selector requirements by node&#39;s fields.
   * @return matchFields
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "A list of node selector requirements by node's fields.")

  public List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions> getMatchFields() {
    return matchFields;
  }


  public void setMatchFields(List<DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreferenceMatchExpressions> matchFields) {
    this.matchFields = matchFields;
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
    sb.append("class DomainGpugroupGpugroupV1AutoScalerSpecPodTemplateSpecAffinityNodeAffinityPreference {\n");
    sb.append("    matchExpressions: ").append(toIndentedString(matchExpressions)).append("\n");
    sb.append("    matchFields: ").append(toIndentedString(matchFields)).append("\n");
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
