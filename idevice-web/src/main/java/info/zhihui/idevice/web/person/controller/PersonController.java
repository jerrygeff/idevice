package info.zhihui.idevice.web.person.controller;

import info.zhihui.idevice.common.utils.ResultUtil;
import info.zhihui.idevice.common.vo.RestResult;
import info.zhihui.idevice.web.person.biz.PersonBizService;
import info.zhihui.idevice.web.person.vo.PersonAddVo;
import info.zhihui.idevice.web.person.vo.PersonDeleteVo;
import info.zhihui.idevice.web.person.vo.PersonFaceUpdateVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "人员管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonBizService personBizService;

    @PostMapping("/add")
    @Operation(summary = "添加人员到第三方系统")
    public RestResult<String> addPerson(@Validated @RequestBody PersonAddVo personAddVo) {
        String thirdPartyPersonId = personBizService.addLocalPersonToThirdParty(personAddVo);
        return ResultUtil.success(thirdPartyPersonId);
    }

    @PostMapping("/delete")
    @Operation(summary = "从第三方系统删除人员")
    public RestResult<Void> deletePerson(@Validated @RequestBody PersonDeleteVo personDeleteVo) {
        personBizService.deleteThirdPartyPerson(personDeleteVo);
        return ResultUtil.success();
    }

    @PostMapping("/face/update")
    @Operation(summary = "更新人员人脸信息")
    public RestResult<Void> updatePersonFace(@Validated @RequestBody PersonFaceUpdateVo personFaceUpdateVo) {
        personBizService.updatePersonFace(personFaceUpdateVo);
        return ResultUtil.success();
    }

}