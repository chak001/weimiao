package com.sjzx.controller;


import com.sjzx.entity.CombineProfit;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.Response;
import com.sjzx.model.vo.input.CombineProfitAddVO;
import com.sjzx.model.vo.input.CombineProfitInputVO;
import com.sjzx.model.vo.output.CombineProfitVO;
import com.sjzx.service.CombineProfitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 合并利润表 前端控制器
 * </p>
 *
 * @author 
 * @since 202003
 */
@RestController
@Api(tags = "公司合并利润表管理接口")
@RequestMapping("/combineProfit")
public class CombineProfitController {

    @Autowired
    private CombineProfitService combineProfitService;

    @PostMapping("/listPage")
    @ApiOperation(value = "数据分页")
    public EasyUIResult<CombineProfitVO> listPage(CombineProfitInputVO vo) {
        return combineProfitService.listPage(vo);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加")
    public Response addCombineProfit(CombineProfitAddVO vo) {
        combineProfitService.addCombineProfit(vo);
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public Response updateCombineProfit(CombineProfit vo) {
        combineProfitService.updateCombineProfit(vo);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除")
    public Response deleteCombineProfit(CombineProfit vo) {
        combineProfitService.deleteCombineProfit(vo);
        return Response.success();
    }


}
