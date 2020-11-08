package com.sjzx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 合并现金流量表指标
 * </p>
 *
 * @author 
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CashFlowStatistics extends Model<CashFlowStatistics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司详情表主键
     */
    private Integer companyId;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 参考标准 1-年报 2-第三季度报 3-半年报 4-第一季度报
     */
    private Integer reportType;

    /**
     * 经营活动产生的现金流量净额
     */
    private Long businessToProfit;

    /**
     * 分红金额
     */
    private Long bonusCash;

    /**
     * 现金净增额  经营活动产生的现金流量净额-分红
     */
    private Long profitSubstractBonus;

    /**
     * 销售商品提供劳务收到的现金/营业收入
     */
    private Integer cashInIncoming;

    /**
     * 购建资产/经营活动产生的现金流量净额
     */
    private Integer expandInProfitRate;

    /**
     * 处置资产/购建资产
     */
    private Integer sellInExpandRate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
