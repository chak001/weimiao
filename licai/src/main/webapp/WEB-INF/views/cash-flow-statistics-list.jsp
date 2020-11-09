<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div style="width: 100%;height: 40px">
    公司:&nbsp;&nbsp;<input class="easyui-textbox" id="listw_searchCompanyId">
    规格:&nbsp;&nbsp;<input class="easyui-textbox" id="listw_searchCompanyReportType">
    行业类型:&nbsp;&nbsp;<input class="easyui-textbox" id="listw_searchCompanyType">
    年份:&nbsp;&nbsp;<input class="easyui-textbox" id="listw_searchYear">
    <input type="hidden" id="listw_searchCompanyReportTypeKey"/>
    <input type="hidden" id="listw_searchCompanyTypeKey"/>
    <input type="hidden" id="listw_searchCompanyYearKey"/>
    <button class="easyui-linkbutton" iconCls="icon-search" onclick="cashFlowListSearch()">搜索</button>
    <button id="export2" href="#" class="easyui-linkbutton" >导出excel</button>
</div>
<table id="cash-flow-statistics-list" style="width:100%;height:600px"></table>

<script type="text/javascript">
    $("#listw_searchYear").combobox({
        valueField: 'key',
        textField: 'value',
        data: [{
            key: '',
            value: '全部'
        }, {
            key: '2020',
            value: '2020年'
        }, {
            key: '2019',
            value: '2019年'
        }, {
            key: '2018',
            value: '2018年'
        }, {
            key: '2017',
            value: '2017年'
        }, {
            key: '2016',
            value: '2016年'
        }, {
            key: '2015',
            value: '2015年'
        }],
        onSelect: function (record) {
            $("#listw_searchCompanyYearKey").val(record.key);
        }
    });

    $("#listw_searchCompanyType").combobox({
        valueField: 'key',
        textField: 'value',
        data: [{
            key: '',
            value: '全部'
        }, {
            key: '1',
            value: '汽车制造'
        }, {
            key: '2',
            value: '白色家电'
        }],
        onSelect: function (record) {
            $("#listw_searchCompanyTypeKey").val(record.key);
        }
    });

    $("#listw_searchCompanyReportType").combobox({
        valueField: 'key',
        textField: 'value',
        data: [{
            key: '',
            value: '全部'
        }, {
            key: '1',
            value: '年报'
        }, {
            key: '2',
            value: '第三季度报'
        }, {
            key: '3',
            value: '半年报'
        }, {
            key: '4',
            value: '第一季度报'
        }],
        onSelect: function (record) {
            $("#listw_searchCompanyReportTypeKey").val(record.key);
        }
    });

    $("#listw_searchCompanyId").combogrid({
        panelWidth: 260,
        idField: 'id',
        textField: 'name',
        url: '/company/combogrid',
        mode: 'remote',
        delay: 500,
        columns: [[
            {field: 'id', title: '主键', width: 40, align: 'center'},
            {field: 'code', title: '股票代码', width: 100, align: 'center'},
            {field: 'name', title: '公司名称', width: 100, align: 'center'},
        ]],
    });


    $('#cash-flow-statistics-list').datagrid({
        url: '/cashFlowStatistics/listPage',
        title: '合并现金流量表统计列表',
        pagePosition: 'top',
        singleSelect: false,
        collapsible: true,
        pagination: true,
        rownumbers: true,
        pageSize: 20,
        pageList: [20, 50, 100],
        columns: [[
            {field: 'id', checkbox: true},
            {field: 'code', title: '股票代码', width: 100, align: 'center'},
            {field: 'name', title: '公司名称', width: 100, align: 'center'},
            {field: 'year', title: '年份', width: 40, align: 'center'},
            {
                field: 'reportType', title: '规格', width: 60, align: 'center',
                formatter: function (value, row, index) {
                    if (value === 1) {
                        return '年报';
                    } else if (value === 2) {
                        return '第三季度报';
                    } else if (value === 3) {
                        return '半年报';
                    } else if (value === 4) {
                        return '第一季度报';
                    }
                }
            },
            {field: 'businessToProfit', title: '经营活动产生的现金流量净额', width: 110, align: 'center'},
            {field: 'bonusCash', title: '分红金额', width: 110, align: 'center'},
            {field: 'profitSubstractBonus', title: '现金净增额 经营活动产生的现金流量净额-分红', width: 110, align: 'center'},
            {field: 'cashInIncoming', title: '销售商品提供劳务收到的现金/营业收入', width: 150, align: 'center'},
            {field: 'expandInProfitRate', title: '购建资产/经营活动产生的现金流量净额', width: 150, align: 'center'},
            {field: 'sellInExpandRate', title: '处置资产/购建资产', width: 110, align: 'center'},
            {field: 'remark', title: '备注', width: 300, align: 'center'},
            {field: 'createTime', title: '创建时间', width: 150, align: 'center'},
            {field: 'updateTime', title: '跟新时间', width: 150, align: 'center'}
        ]],
        onBeforeLoad: function (param) {
            param.pageNo = param.page;
            param.pageSize = param.rows;
            param.companyId = $("#listw_searchCompanyId").val();
            param.reportType = $("#listw_searchCompanyReportTypeKey").val();
            param.type = $("#listw_searchCompanyTypeKey").val();
            param.year = $("#listw_searchCompanyYearKey").val();
            return true;
        }
    });
    cashFlowListSearch = function () {
        $('#cash-flow-statistics-list').datagrid('load');
    }
    $("#export2").click(function () {
        location ="/cashFlowStatistics/export";
    });
</script>