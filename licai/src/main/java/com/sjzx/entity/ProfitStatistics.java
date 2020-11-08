package com.sjzx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 合并利润表统计
 * </p>
 *
 * @author 
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProfitStatistics extends Model<ProfitStatistics> {

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
     * 营业收入增速
     */
    private Integer businessIncomeGrowthRate;

    /**
     * 毛利率 =（营业收入 - 营业成本）/营业收入
     */
    private Integer grossProfitMargin;

    /**
     * 费用率 = (销售费用 + 管理费用 + 财务费用）/营业收入
     */
    private Integer costRate;

    /**
     * 费用率/毛利率
     */
    private Integer costInProfit;

    /**
     * 主营利润 营业收入 - 营业成本 - 税金及附加 - (销售费用 + 管理费用 + 财务费用）
     */
    private Long mainProfit;

    /**
     * 经营活动产生的现金流量净额(现金流量表)
     */
    private Long businessToProfit;

    /**
     * 利润质量 经营活动产生的现金流量净额/主营利润
     */
    private Integer profitQuality;

    /**
     * 利润质量 经营活动产生的现金流量净额/净利润
     */
    private Integer profitQualityOne;

    /**
     * 主营利润/利润总额
     */
    private Integer mainProfitInProfitTotal;

    /**
     * 主营利润率 主营利润/营业收入
     */
    private Integer mainProfitInIncomeTotal;

    /**
     * 归属于母公司所有者的净利润
     */
    private Long belongMotherNetProfit;

    /**
     * 归属于母公司所有者的净利润增速
     */
    private Integer belongMotherNetProfitGrowthRate;

    /**
     * 总股本
     */
    private Long totalEquity;

    /**
     * 每股收益
     */
    private BigDecimal sharesProfit;

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
