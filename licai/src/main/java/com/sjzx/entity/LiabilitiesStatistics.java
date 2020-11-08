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
 * 资产负债表统计指标
 * </p>
 *
 * @author 
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LiabilitiesStatistics extends Model<LiabilitiesStatistics> {

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
     * 总资产
     */
    private Long totalAssets;

    /**
     * 总资产增速
     */
    private Integer totalAssetsGrowthRate;

    /**
     * 资产负债率-负债合计占总资产比
     */
    private Integer totalLiabilitiesInReportType;

    /**
     * 负债合计增速
     */
    private Integer totalLiabilitiesGrowthRate;

    /**
     * 股东权益合计增速
     */
    private Integer shareHolderEquityGrowthRate;

    /**
     * 有息负债总额 = 短期负债 + 一年内到期的非流动负债 + 长期借款 + 应付债券 + 长期应付款 + 应付利息
     */
    private Long interestBearingLiabilities;

    /**
     * 行业地位指标1 应付票据 + 应付账款 + 预收款项 - 应收票据 - 应收账款 - 预付款项 
     */
    private Long industryStatusOne;

    /**
     * 货币资金
     */
    private Long monetaryCapital;

    /**
     * 应收账款占总资产比
     */
    private Integer receivableMoneyInReportType;

    /**
     * 固定资产总和占总资产比
     * (固定资产+在建工程+工程物资)
     */
    private Integer fixedAssetsTotalInReportType;

    /**
     * 投资资产占总资产比
     * 以公允价值计量且变动计入当期损益的金融资产 + 可供出售金融资产 + 持有至到期投资 + 投资房地产 + 长期股权投资（与主业无关）
     */
    private Integer investmentInReportType;

    /**
     * 每股净资产
     */
    private BigDecimal sharesValue;

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
