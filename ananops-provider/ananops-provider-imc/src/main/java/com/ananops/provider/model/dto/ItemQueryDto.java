package com.ananops.provider.model.dto;

import com.ananops.base.dto.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by rongshuai on 2019/12/22 13:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class ItemQueryDto extends BaseQuery {
    private static final long serialVersionUID = -5593798802176768165L;

    /**
     * 巡检任务子项对应的状态
     */
    @ApiModelProperty(value = "巡检任务子项对应的状态")
    private Integer status;

    /**
     * 巡检任务子项对应的甲方用户id
     */
    @ApiModelProperty(value = "巡检任务子项对应的甲方用户ID")
    private Long userId;

    /**
     * 对应的巡检任务的Id
     */
    @ApiModelProperty(value = "巡检任务子项对应的巡检任务的ID")
    private Long taskId;

    /**
     * 巡检任务子项对应的工程师Id
     */
    @ApiModelProperty(value = "巡检任务子项对应的工程师Id")
    private Long maintainerId;
}
