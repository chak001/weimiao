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
 * 合并利润表
 * </p>
 *
 * @author 
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CombineProfit extends Model<CombineProfit> {

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
     * 1-年报 2-第三季度报 3-半年报 4-第一季度报
     */
    private Integer reportType;

    /**
     * 营业收入
     */
    private Long businessIncome;

    /**
     * 营业成本
     */
    private Long businessCosts;

    /**
     * 税金及附加
     */
    private Long taxRevenueTotal;

    /**
     * 销售费用
     */
    private Long sellingExpenses;

    /**
     * 管理费用
     */
    private Long manageExpenses;

    /**
     * 财务费用
     */
    private Long financialExpenses;

    /**
     * 资产减值损失
     */
    private Long assetsImpairmentLoss;

    /**
     * 公允价值变动收益（损失以“－”号填列）
     */
    private Long incomeFromChangesInFairValue;

    /**
     * 投资收益（损失以“－”号填列）
     */
    private Long incomeFromInvestment;

    /**
     * 营业利润（主业和投资）
     */
    private Long businessProfit;

    /**
     * 营业外收入
     */
    private Long nonBusinessIncome;

    /**
     * 营业外支出
     */
    private Long nonBusinessCosts;

    /**
     * 利润总额
     */
    private Long totalProfit;

    /**
     * 所得税费用
     */
    private Long incomeTaxExpense;

    /**
     * 净利润
     */
    private Long netProfit;

    /**
     * 归属于母公司所有者的净利润
     */
    private Long belongMotherNetProfit;

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
