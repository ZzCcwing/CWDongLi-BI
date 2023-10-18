package com.yupi.springbootinit.model.dto.chart;


import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 查询请求
 *
 * @author zcWing
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChartQueryRequest extends PageRequest implements Serializable {

    private Long id;

    /**
     * 图表名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 图表类型
     */
    private String chartType;


    private static final long serialVersionUID = 1L;
}