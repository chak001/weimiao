<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="width: 100%;height: 40px">
    公司:&nbsp;&nbsp;<input class="easyui-textbox" id="lists_searchCompanyId">
    规格:&nbsp;&nbsp;<input class="easyui-textbox" id="lists_searchCompanyReportType">
    行业类型:&nbsp;&nbsp;<input class="easyui-textbox" id="lists_searchCompanyType">
    年份:&nbsp;&nbsp;<input class="easyui-textbox" id="lists_searchYear">
    <input type="hidden" id="lists_searchCompanyReportTypeKey"/>
    <input type="hidden" id="lists_searchCompanyTypeKey"/>
    <input type="hidden" id="lists_searchCompanyYearKey"/>
    <button class="easyui-linkbutton" iconCls="icon-search" onclick="liabilitiesStatisticListSearch()">搜索</button>
    <%--<button id="export_list" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="export_list()">搜索</button>--%>
   <%-- <button id="export_list" href="#" class="easyui-linkbutton" onclick="export_lists" >导出excel</button>--%>
    <button id="export" href="#" class="easyui-linkbutton" >导出excel</button>
  <%--  <button id="export" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-export',plain:true">导出为excel</button>--%>
</div>
<table id="liabilities-statistic-list" style="width:100%;height:600px"></table>

<script type="text/javascript">
    $("#lists_searchYear").combobox({
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
            $("#lists_searchCompanyYearKey").val(record.key);
        }
    });
    $("#lists_searchCompanyType").combobox({
        valueField:'key',
        textField:'value',
        data: [{
            key: '',
            value: '全部'
        },{
            key: '1',
            value: '汽车制造'
        },{
            key: '2',
            value: '白色家电'
        }],
        onSelect: function (record) {
            $("#lists_searchCompanyTypeKey").val(record.key);
        }
    });

    $("#lists_searchCompanyReportType").combobox({
        valueField:'key',
        textField:'value',
        data: [{
            key: '',
            value: '全部'
        },{
            key: '1',
            value: '年报'
        },{
            key: '2',
            value: '第三季度报'
        },{
            key: '3',
            value: '半年报'
        },{
            key: '4',
            value: '第一季度报'
        }],
        onSelect: function (record) {
            $("#lists_searchCompanyReportTypeKey").val(record.key);
        }
    });

    $("#lists_searchCompanyId").combogrid({
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

    $('#liabilities-statistic-list').datagrid({
        url:'/liabilitiesStatistics/listPage',
        title: '资产负债表统计列表',
        pagePosition: 'top',
        singleSelect: true ,
        collapsible:true,
        pagination:true,
        rownumbers: true,
        pageSize: 20,
        pageList:[20,50,100],
        columns:[[
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
                    }  else if (value === 4) {
                        return '第一季度报';
                    }
                }
            },
            {field: 'totalAssets', title: '总资产', width: 110, align: 'center'},
            {field: 'sharesValue', title: '每股净资产', width: 90, align: 'center'},
            {field:'totalAssetsGrowthRate',title:'总资产增速',width:100,align:'center'},
            {field:'totalLiabilitiesInReportType',title:'资产负债率',width:100,align:'center'},
            {field:'totalLiabilitiesGrowthRate',title:'负债合计增速',width:100,align:'center'},
            {field:'shareHolderEquityGrowthRate',title:'股东权益合计增速',width:100,align:'center'},
            {field:'interestBearingLiabilities',title:'有息负债总额',width:100,align:'center'},
            {field:'industryStatusOne',title:'应付减应收',width:110,align:'center'},
            {field:'monetaryCapital',title:'货币资金',width:100,align:'center'},
            {field:'receivableMoneyInReportType',title:'应收账款占总资产比',width:100,align:'center'},
            {field:'fixedAssetsTotalInReportType',title:'固定资产总和占总资产比',width:100,align:'center'},
            {field:'investmentInReportType',title:'投资资产占总资产比',width:100,align:'center'},
            {field:'remark',title:'备注',width:300,align:'center'},
            {field:'createTime',title:'创建时间',width:150,align:'center'},
            {field:'updateTime',title:'修改时间',width:150,align:'center'}
        ]],
        onBeforeLoad: function (param) {
            param.pageNo = param.page;
            param.pageSize = param.rows;
            param.companyId = $("#lists_searchCompanyId").val();
            param.reportType = $("#lists_searchCompanyReportTypeKey").val();
            param.type = $("#lists_searchCompanyTypeKey").val();
            param.year = $("#lists_searchCompanyYearKey").val();
            return true;
        }
    });

    $('#export_list').datagrid({
        url:'/liabilitiesStatistics/export',
        onBeforeLoad: function (param) {
            param.pageNo = param.page;
            param.pageSize = param.rows;
            param.companyId = $("#lists_searchCompanyId").val();
            param.reportType = $("#lists_searchCompanyReportTypeKey").val();
            param.type = $("#lists_searchCompanyTypeKey").val();
            param.year = $("#lists_searchCompanyYearKey").val();
            return true;
        }
    });

    liabilitiesStatisticListSearch = function () {
        $("#liabilities-statistic-list").datagrid('load');
    };
    export_lists= function () {
        $("#export_list").datagrid('load');
        /*$.ajax({
            url:"/liabilitiesStatistics/export",
            type:"GET",
            data:{"companyId":$("#lists_searchCompanyId").val(),
            "reportType": $("#lists_searchCompanyReportTypeKey").val(),
            "type":$("#lists_searchCompanyTypeKey").val(),
            "year":$("#lists_searchCompanyYearKey").val()}
        });*/
    }
    $("#export").click(function () {
        location ="/liabilitiesStatistics/export";
    });

</script>