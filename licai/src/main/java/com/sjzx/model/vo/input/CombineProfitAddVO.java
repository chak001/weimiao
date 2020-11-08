package com.sjzx.model.vo.input;

import lombok.Data;
import lombok.experimental.Accessors;

import static com.sjzx.utils.NumberUtils.toLong;

@Data
@Accessors(chain = true)
public class CombineProfitAddVO {

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

    public void setBusinessIncome(String businessIncome) {
        this.businessIncome = toLong(businessIncome);
    }

    public void setBusinessCosts(String businessCosts) {
        this.businessCosts = toLong(businessCosts);
    }

    public void setTaxRevenueTotal(String taxRevenueTotal) {
        this.taxRevenueTotal = toLong(taxRevenueTotal);
    }

    public void setSellingExpenses(String sellingExpenses) {
        this.sellingExpenses = toLong(sellingExpenses);
    }

    public void setManageExpenses(String manageExpenses) {
        this.manageExpenses = toLong(manageExpenses);
    }

    public void setFinancialExpenses(String financialExpenses) {
        this.financialExpenses = toLong(financialExpenses);
    }

    public void setAssetsImpairmentLoss(String assetsImpairmentLoss) {
        this.assetsImpairmentLoss = toLong(assetsImpairmentLoss);
    }

    public void setIncomeFromChangesInFairValue(String incomeFromChangesInFairValue) {
        this.incomeFromChangesInFairValue = toLong(incomeFromChangesInFairValue);
    }

    public void setIncomeFromInvestment(String incomeFromInvestment) {
        this.incomeFromInvestment = toLong(incomeFromInvestment);
    }

    public void setBusinessProfit(String businessProfit) {
        this.businessProfit = toLong(businessProfit);
    }

    public void setNonBusinessIncome(String nonBusinessIncome) {
        this.nonBusinessIncome = toLong(nonBusinessIncome);
    }

    public void setNonBusinessCosts(String nonBusinessCosts) {
        this.nonBusinessCosts = toLong(nonBusinessCosts);
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = toLong(totalProfit);
    }

    public void setIncomeTaxExpense(String incomeTaxExpense) {
        this.incomeTaxExpense = toLong(incomeTaxExpense);
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = toLong(netProfit);
    }

    public void setBelongMotherNetProfit(String belongMotherNetProfit) {
        this.belongMotherNetProfit = toLong(belongMotherNetProfit);
    }
}
